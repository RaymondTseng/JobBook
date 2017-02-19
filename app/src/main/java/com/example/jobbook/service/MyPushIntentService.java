package com.example.jobbook.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.UmengMessageBean;
import com.example.jobbook.bean.UnreadBean;
import com.example.jobbook.moment.widget.MomentDetailActivity;
import com.example.jobbook.receiver.NotificationBroadcast;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.example.jobbook.MyApplication.getAccount;

/**
 * Created by Xu on 2017/1/20.
 */

public class MyPushIntentService extends UmengMessageService {

    private static final String TAG = MyPushIntentService.class.getName();
    public static UMessage oldMessage = null;
    public static int num;
    public static Set<OnRefreshPersonBadgeViewListener> listeners = new HashSet<>();

    public class MyRefreshBinder extends Binder {
        public void refresh(PersonBean personBean) {
            L.i("Binder", "refresh");
            List<UnreadBean> beans = DataSupport.where("account = ?", personBean.getAccount()).find(UnreadBean.class);
            if (beans.size() != 0 && beans.get(0).getNum() != 0) {
                final int unread = beans.get(0).getNum();
                beans.get(0).setNum(0);
                beans.get(0).save();
                num = unread;
                L.i("Binder", "onRefresh");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isDone = false;
                        while (!isDone) {
                            for (OnRefreshPersonBadgeViewListener listener :
                                    listeners) {
                                listener.onRefresh(unread);
                                L.i("Binder", "onRefresh, unread" + unread);
                                isDone = true;
                            }
                        }
                    }
                }).start();

            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyRefreshBinder();
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            L.d(TAG, "message=" + message);      //消息体
            L.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            L.d(TAG, "title=" + msg.title);      //通知标题
            L.d(TAG, "text=" + msg.text);        //通知内容

            UmengMessageBean messageBean = new Gson().fromJson(msg.custom, UmengMessageBean.class);
            dealWithMessageOrSaveInDB(messageBean, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showNotification(UmengMessageBean msg, UMessage message) {
        int id = new Random(System.nanoTime()).nextInt();
        oldMessage = message;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        Notification.Builder mBuilder = new Notification.Builder(this);
        switch (msg.getType()) {
            case MessageBean.FOLLOW:
                mBuilder.setContentTitle("有一个新用户关注你啦")
                        .setTicker("在职谱，有一个新用户关注你啦");
                break;
            case MessageBean.LIKE:
                mBuilder.setContentTitle("有人给你的工作圈点赞啦")
                        .setTicker("在职谱，有人给你的工作圈点赞啦");
                ;
                break;
            case MessageBean.COMMENT:
                mBuilder.setContentTitle("有人评论你的工作圈啦")
                        .setTicker("在职谱，有人评论你的工作圈啦");
                ;
                break;
        }
        mBuilder.setContentText("点击查看")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        Notification notification = mBuilder.build();
        PendingIntent clickPendingIntent = getClickPendingIntent(this, message, msg);
        PendingIntent dismissPendingIntent = getDismissPendingIntent(this, message);
        notification.deleteIntent = dismissPendingIntent;
        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage msg, UmengMessageBean messageBean) {
        L.d(TAG, "click notification");
        UTrack.getInstance(context).setClearPrevMessage(true);
        oldMessage = null;
        UTrack.getInstance(context).trackMsgClick(msg);
        Intent clickIntent = new Intent();
//        clickIntent.setClass(context, NotificationBroadcast.class);
//        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
//                msg.getRaw().toString());
//        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION,
//                NotificationBroadcast.ACTION_CLICK);
//        clickIntent.putExtra(NotificationBroadcast.CUSTOM_MESSAGE, messageBean);
        switch (messageBean.getType()) {
            case MessageBean.FOLLOW:
//                Intent intentFollow = new Intent(context, UserDetailActivity.class);
                clickIntent.setClass(context, UserDetailActivity.class);
                clickIntent.putExtra("person_account_from_message", messageBean.getAccountFrom());
                clickIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intentFollow);
                break;
            case MessageBean.LIKE:
//                Intent intentLike = new Intent(context, MomentDetailActivity.class);
                clickIntent.setClass(context, MomentDetailActivity.class);
                clickIntent.putExtra("moment_id_from_message", messageBean.getEvent());
                clickIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intentLike);
                break;
            case MessageBean.COMMENT:
//                Intent intentComment = new Intent(context, MomentDetailActivity.class);
                clickIntent.setClass(context, MomentDetailActivity.class);
                clickIntent.putExtra("moment_id_from_message", messageBean.getEvent());
                clickIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intentComment);
                break;
        }

        PendingIntent clickPendingIntent = PendingIntent.getActivity(context,
                (int) (System.currentTimeMillis()),
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        L.i("intentservice", "receive");
        return clickPendingIntent;
    }

    public PendingIntent getDismissPendingIntent(Context context, UMessage msg) {
        L.d(TAG, "delete notification");
        Intent deleteIntent = new Intent();
        deleteIntent.setClass(context, NotificationBroadcast.class);
        deleteIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        deleteIntent.putExtra(
                NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_DISMISS);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis() + 1),
                deleteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return deletePendingIntent;
    }

    public interface OnRefreshPersonBadgeViewListener {
        void onRefresh(int num);
    }

    private void dealWithMessageOrSaveInDB(UmengMessageBean messageBean, UMessage msg) {
        num++;
        if (MyApplication.getmLoginStatus() == 1) {
            L.i("MyPush", "account" + messageBean.getAccountTo() + " local account:" + MyApplication.getmPersonBean().getAccount());
            if (messageBean.getAccountTo().equals(MyApplication.getmPersonBean().getAccount())) {
                showNotification(messageBean, msg);
                L.i("MyPush", "lala");
                int unread = 0;
                List<UnreadBean> beans = DataSupport.where("account = ?", getAccount()).find(UnreadBean.class);
                if (beans.size() != 0 && beans.get(0).getNum() != 0) {
                    unread = beans.get(0).getNum() + 1;
                    beans.get(0).setNum(0);
                    beans.get(0).save();
                } else {
                    unread = num;
                }
                for (OnRefreshPersonBadgeViewListener listener :
                        listeners) {
                    listener.onRefresh(unread);
                }
            } else {
                L.i("MyPush", "haha");
                List<UnreadBean> beans = DataSupport.where("account = ?", messageBean.getAccountTo()).find(UnreadBean.class);
                if (beans.size() == 0) {
                    UnreadBean bean = new UnreadBean();
                    bean.setAccount(messageBean.getAccountTo());
                    bean.setNum(1);
                    bean.save();
                } else {
                    ContentValues values = new ContentValues();
                    values.put("num", beans.get(0).getNum() + 1);
                    DataSupport.updateAll(UnreadBean.class, values, "account = ?", messageBean.getAccountTo());
                }
                num--;
            }
        } else {
            List<UnreadBean> beans = DataSupport.where("account = ?", messageBean.getAccountTo()).find(UnreadBean.class);
            if (beans.size() == 0) {
                UnreadBean bean = new UnreadBean();
                bean.setAccount(messageBean.getAccountTo());
                bean.setNum(1);
                bean.save();
            } else {
                ContentValues values = new ContentValues();
                values.put("num", beans.get(0).getNum() + 1);
                DataSupport.updateAll(UnreadBean.class, values, "account = ?", messageBean.getAccountTo());
            }
            num--;
        }
    }

}
