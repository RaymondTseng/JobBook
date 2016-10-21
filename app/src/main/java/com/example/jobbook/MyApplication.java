package com.example.jobbook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.util.Util;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Xu on 2016/8/24.
 */
public class MyApplication extends Application {

    private static PersonBean mPersonBean;

    private static int mLoginStatus = 0;

    private static String account;

    private Handler handler = null;

    public static PersonBean getmPersonBean(Context context) {
        if (mPersonBean != null) {
            return mPersonBean;
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
            return Util.loadPersonBean(sharedPreferences);
        }
    }

    public static PersonBean getmPersonBean() {
        if (mPersonBean != null) {
            return mPersonBean;
        }
        return null;
    }

    public static void setmPersonBean(Context context, PersonBean personBean) {
        if (personBean != null) {
            mPersonBean = personBean;
            mLoginStatus = 1;
            SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
            Util.savePersonBean(sharedPreferences, personBean);
            setAccount(mPersonBean.getAccount());
        }
    }

    public static void clearmPersonBean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE);
        Util.clearPersonBean(sharedPreferences);
        mPersonBean = null;
        setmNoLoginStatus();
        setAccount(null);
    }

    public static String getAccount() {
        if (account != null) {
            return account;
        }
        return null;
    }

    private static void setAccount(String account) {
        MyApplication.account = account;
    }

//    public static LinkedList<String> getmSearchRecordList() {
//        return mSearchRecordList;
//    }
//
//    public static void setmSearchRecordList(LinkedList<String> list) {
//        if (list != null) {
//            mSearchRecordList = list;
//        }
//    }
//
//    public static void addmSearchRecordList(String item) {
//        if (item != null) {
//            if (mSearchRecordList.size() <= 10) {
//                mSearchRecordList.add(item);
//            }else {
//                int index = mSearchRecordList.size() - 10;
//                while(index != 0) {
//                    mSearchRecordList.removeFirst();
//                    index --;
//                }
//                mSearchRecordList.addLast(item);
//            }
//        }
//    }


    public static int getmLoginStatus() {
        return mLoginStatus;
    }

    public static void setmNoLoginStatus() {
        mLoginStatus = 0;
        mPersonBean = null;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 内存泄漏检测工具
//        LeakCanary.install(this);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
