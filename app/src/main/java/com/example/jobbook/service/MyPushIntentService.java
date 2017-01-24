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
import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.bean.UnreadBean;
import com.example.jobbook.receiver.NotificationBroadcast;
import com.example.jobbook.util.L;
import com.google.gson.Gson;
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
                        while(!isDone) {
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
            Log.d(TAG, "message=" + message);      //消息体
            Log.d(TAG, "custom=" + msg.custom);    //自定义消息的内容
            Log.d(TAG, "title=" + msg.title);      //通知标题
            Log.d(TAG, "text=" + msg.text);        //通知内容
            num++;
            UnreadBean demo = new Gson().fromJson(msg.custom, UnreadBean.class);

            if (MyApplication.getmLoginStatus() == 1) {
                L.i("MyPush", "account" + demo.getAccount() + " local account:" + MyApplication.getmPersonBean().getAccount());
                if (demo.getAccount().equals(MyApplication.getmPersonBean().getAccount())) {
                    showNotification(msg);
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
                }
                else {
                    L.i("MyPush", "haha");
                    List<UnreadBean> beans = DataSupport.where("account = ?", demo.getAccount()).find(UnreadBean.class);
                    if (beans.size() == 0) {
                        UnreadBean bean = new UnreadBean();
                        bean.setAccount(demo.getAccount());
                        bean.setNum(1);
                        bean.save();
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("num", beans.get(0).getNum() + 1);
                        DataSupport.updateAll(UnreadBean.class, values, "account = ?", demo.getAccount());
                    }
                    num--;
                }
            } else {
                List<UnreadBean> beans = DataSupport.where("account = ?", demo.getAccount()).find(UnreadBean.class);
                if (beans.size() == 0) {
                    UnreadBean bean = new UnreadBean();
                    bean.setAccount(demo.getAccount());
                    bean.setNum(1);
                    bean.save();
                } else {
                    ContentValues values = new ContentValues();
                    values.put("num", beans.get(0).getNum() + 1);
                    DataSupport.updateAll(UnreadBean.class, values, "account = ?", demo.getAccount());
                }
                num--;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showNotification(UMessage msg) {
        int id = new Random(System.nanoTime()).nextInt();
        oldMessage = msg;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.article_rb)
                .setAutoCancel(true);
        Notification notification = mBuilder.getNotification();
        PendingIntent clickPendingIntent = getClickPendingIntent(this, msg);
        PendingIntent dismissPendingIntent = getDismissPendingIntent(this, msg);
        notification.deleteIntent = dismissPendingIntent;
        notification.contentIntent = clickPendingIntent;
        manager.notify(id, notification);
    }

    public PendingIntent getClickPendingIntent(Context context, UMessage msg) {
        Intent clickIntent = new Intent();
        clickIntent.setClass(context, NotificationBroadcast.class);
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_MSG,
                msg.getRaw().toString());
        clickIntent.putExtra(NotificationBroadcast.EXTRA_KEY_ACTION,
                NotificationBroadcast.ACTION_CLICK);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                (int) (System.currentTimeMillis()),
                clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        return clickPendingIntent;
    }

    public PendingIntent getDismissPendingIntent(Context context, UMessage msg) {
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

}