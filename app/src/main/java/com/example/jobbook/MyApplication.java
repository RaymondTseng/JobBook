package com.example.jobbook;

import android.app.Application;

import com.example.jobbook.bean.PersonBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/8/24.
 */
public class MyApplication extends Application {

    private PersonBean mPersonBean;

    private static int mLoginStatus = 0;

    public PersonBean getmPersonBean() {
        return mPersonBean;
    }

    public void setmPersonBean(PersonBean mPersonBean) {
        if(mPersonBean != null){
            this.mPersonBean = mPersonBean;
            mLoginStatus = 1;
        }
    }

    public static int getmLoginStatus(){
        return mLoginStatus;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }
}
