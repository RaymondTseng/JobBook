package com.example.jobbook.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jobbook.bean.MessageBean;
import com.example.jobbook.bean.UmengMessageBean;
import com.example.jobbook.moment.widget.MomentDetailActivity;
import com.example.jobbook.service.MyPushIntentService;
import com.example.jobbook.userdetail.widget.UserDetailActivity;
import com.umeng.message.UTrack;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mitic_xue on 16/10/26.
 */
public class NotificationBroadcast extends BroadcastReceiver {
    public static final String EXTRA_KEY_ACTION = "ACTION";
    public static final String EXTRA_KEY_MSG = "MSG";
    public static final String CUSTOM_MESSAGE = "CUSTOM_MESSAGE";
    public static final int ACTION_CLICK = 10;
    public static final int ACTION_DISMISS = 11;
    public static final int EXTRA_ACTION_NOT_EXIST = -1;
    private static final String TAG = NotificationBroadcast.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(EXTRA_KEY_MSG);
        int action = intent.getIntExtra(EXTRA_KEY_ACTION,
                EXTRA_ACTION_NOT_EXIST);
        try {
            UMessage msg = new UMessage(new JSONObject(message));

            switch (action) {
                case ACTION_DISMISS:
                    UmLog.d(TAG, "dismiss notification");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    UTrack.getInstance(context).trackMsgDismissed(msg);
                    break;
                case ACTION_CLICK:
                    UmLog.d(TAG, "click notification");
                    UTrack.getInstance(context).setClearPrevMessage(true);
                    MyPushIntentService.oldMessage = null;
                    UTrack.getInstance(context).trackMsgClick(msg);
                    UmengMessageBean messageBean = (UmengMessageBean) intent.getSerializableExtra(CUSTOM_MESSAGE);
                    switch (messageBean.getType()) {
                        case MessageBean.FOLLOW:
                            Intent intentFollow = new Intent(context, UserDetailActivity.class);
                            intentFollow.putExtra("person_account_from_message", messageBean.getEvent());
                            intentFollow.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentFollow);
                            break;
                        case MessageBean.LIKE:
                            Intent intentLike = new Intent(context, MomentDetailActivity.class);
                            intentLike.putExtra("moment_id_from_message", messageBean.getEvent());
                            intentLike.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentLike);
                            break;
                        case MessageBean.COMMENT:
                            Intent intentComment = new Intent(context, MomentDetailActivity.class);
                            intentComment.putExtra("moment_id_from_message", messageBean.getEvent());
                            intentComment.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentComment);
                            break;
                    }
                    break;
            }
            //
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
