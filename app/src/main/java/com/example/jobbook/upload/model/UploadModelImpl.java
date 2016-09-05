package com.example.jobbook.upload.model;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.example.jobbook.MyApplication;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.upload.UploadManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;

import okhttp3.Call;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadModelImpl implements UploadModel {

    @Override
    public void uploadImage(Bitmap bm, final OnUploadImageListener listener) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = UploadManager.compressBitmap(stream.toByteArray(), 200);
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        Log.i("img", "img:" + img);
        if (!TextUtils.isEmpty(MyApplication.getmPersonBean().getAccount())) {
            OkHttpUtils.postString().content(img).url(Urls.UPLOAD_IMAGE_URL + "account/" + MyApplication.getmPersonBean().getAccount()).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Log.i("uploadmodelimpl", e.getMessage() + "network error");
                    listener.onFailure("network error", e);
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.i("uploadmodelimpl", "result:" + response);
                    if (response != null && response.equals("success!")) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("response:" + response, null);
                    }
                }
            });
        }

    }

    public interface OnUploadImageListener {
        void onSuccess();

        void onFailure(String msg, Exception e);
    }
}
