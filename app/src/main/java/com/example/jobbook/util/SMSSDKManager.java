package com.example.jobbook.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.mob.MobSDK;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by root on 17-1-22.
 */

public class SMSSDKManager {
    public static boolean DEBUG = true;
    public static final String DEFAULT_APPKEY = "16b9c665dd7f8";
    public static final String DEFAULT_APPSECRET = "ff6619fa904b54a03a9aa892187520d2";
    public static int DEFAULT_DELAY = 60;

    public ArrayList<TimeListener> timeList = new ArrayList<>();
    private boolean inited = false;
    private Timer timer;
    private int last = 0;
    private CallbackEventHandler mHandler = new CallbackEventHandler();

    private void startTimer(){
        timer = new Timer();
        notifyEnable();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                last -= 1;
                notifyLastTime();
                if (last == 0) {
                    notifyEnable();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    private SMSSDKManager() {
    }

    public static SMSSDKManager getInstance(){
        return SMSSDKManagerHolder.INSTANCE;
    }

    private static class SMSSDKManagerHolder{
        public static final SMSSDKManager INSTANCE = new SMSSDKManager();
    }

    private void init(Context ctx){
        ApplicationInfo appInfo = null;
        try {
            appInfo = ctx.getPackageManager()
                    .getApplicationInfo(ctx.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appKey ;
        String appSecret ;
        if (appInfo.metaData == null||appInfo.metaData.getString("SMS_MOB_APPKEY") == null||appInfo.metaData.getString("SMS_MOB_APPSECRET") == null){
            appKey = DEFAULT_APPKEY;
            appSecret = DEFAULT_APPSECRET;
        }else{
            appKey = appInfo.metaData.getString("SMS_MOB_APPKEY").trim();
            appSecret = appInfo.metaData.getString("SMS_MOB_APPSECRET").trim();
        }
        log("appkey:"+appKey+"  appsecret:"+appSecret);
        MobSDK.init(ctx, appKey, appSecret);
        inited = true;
        SMSSDK.registerEventHandler(mHandler);
    }

    public void setDefaultDelay(int delay){
        DEFAULT_DELAY = delay;
    }

    public boolean sendMessage(Context ctx,String country,String number){
        if (!inited){
            init(ctx);
        }
        if (last==0) {
            SMSSDK.getVerificationCode(country, number);
            last = DEFAULT_DELAY;
            startTimer();
            return true;
        }else{
            return false;
        }
    }

    private void notifyLastTime(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onLastTimeNotify(last);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }

    private void notifyEnable(){
        for (TimeListener listener:timeList){
            final TimeListener finalListener = listener;
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        finalListener.onAbleNotify(last == 0);
                    }
                });
            }catch (Exception e){
                unRegisterTimeListener(listener);
            }
        }
    }


    public void registerTimeListener(TimeListener listener){
        timeList.add(listener);
        listener.onLastTimeNotify(last);
        listener.onAbleNotify(last==0);
    }

    public void unRegisterTimeListener(TimeListener listener){
        timeList.remove(listener);
    }

    public void verifyCode(Context ctx,String country,String number,String code, final Callback callback){
        if (!inited){
            init(ctx);
        }
        mHandler.setCallback(callback);
        SMSSDK.submitVerificationCode(country, number, code);
    }

    private class CallbackEventHandler extends EventHandler {
        Callback mCallback;
        Handler handler = new Handler();
        public void setCallback(Callback mCallback) {
            this.mCallback = mCallback;
        }

        @Override
        public void afterEvent(int event, int result, final Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                log("回调完成");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    log("提交验证码成功");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mCallback!=null)
                                mCallback.success();
                            mCallback = null;
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    log("获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    log("返回支持发送验证码的国家列表");
                }
            } else {
                log("Error:" + data.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCallback!=null)
                            mCallback.error((Throwable) data);
                        mCallback = null;
                    }
                });
            }
        }
    }

    private void log(String content){
        if (DEBUG) Log.i("SMSSDK", content);
    }

    public interface Callback {
        void success();
        void error(Throwable error);
    }

    public interface TimeListener {
        void onLastTimeNotify(int lastSecond);
        void onAbleNotify(boolean valuable);
    }
}
