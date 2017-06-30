package com.example.jobbook.person.model;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import com.example.jobbook.util.L;

import com.example.jobbook.MyApplication;
import com.example.jobbook.api.bean.ResultBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.upload.UploadManager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;

import okhttp3.Call;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadModelImpl implements UploadModel {

    public static final String SUCCESS = "success!";
//    public static final int PHOTO = L.i("photo", "result:success");

    @Override
    public void uploadImage(final Bitmap bm, final OnUploadImageListener listener) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = UploadManager.compressBitmap(stream.toByteArray(), 200);
        String img = Base64.encodeToString(bytes, Base64.DEFAULT);
        L.i("img", "img:" + img);
        if (!TextUtils.isEmpty(MyApplication.getAccount())) {
            OkHttpUtils.postString().content(img).url(Urls.UPLOAD_IMAGE_URL + "account/" +
                    MyApplication.getAccount()).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    L.i("uploadmodelimpl", e.getMessage() + "network error");
                    L.i("photo", "onError");
                    listener.onFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    L.i("uploadmodelimpl", "result:" + response);
                    ResultBean resultBean = new Gson().fromJson(response, ResultBean.class);
                    if (resultBean.getStatus().equals("true")) {
                        listener.onSuccess(bm);
                    } else {
                        listener.onFailure(resultBean.getResponse(), null);
                        L.i("photo", "result:failure");
                    }
                }
            });
        }
        bm.recycle();

    }

    public interface OnUploadImageListener {
        void onSuccess(Bitmap bm);

        void onFailure(String msg, Exception e);
    }
}
