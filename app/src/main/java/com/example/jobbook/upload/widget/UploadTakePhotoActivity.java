package com.example.jobbook.upload.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.jobbook.MyApplication;
import com.example.jobbook.upload.UploadManager;
import com.example.jobbook.upload.presenter.UploadPresenter;
import com.example.jobbook.upload.presenter.UploadPresenterImpl;
import com.example.jobbook.upload.view.UploadView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Xu on 2016/9/5.
 */
public class UploadTakePhotoActivity extends Activity implements UploadView {

    private static int TAKE_PHOTO = 1;
    private static int IMAGE_CROP = 2;

    private UploadPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UploadPresenterImpl(this);
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takeIntent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO) {
            Log.i("personfragment", "take photo");
            if (data == null) {
                return;
            } else {
                Bundle extra = data.getExtras();
                if (extra != null) {
                    Log.i("personfragment", "get_pic_data");
                    Bitmap bm = extra.getParcelable("data");
                    Uri uri = UploadManager.saveBitmap(this, bm, MyApplication.getmPersonBean().getAccount());
                    presenter.uploadImage(bm);
//                    startImageZoom(uri);
                } else {
                    return;
                }
            }
        } else if (requestCode == IMAGE_CROP) {
            if (data == null) {
                finish();
                return;
            } else {
                Bundle extra = data.getExtras();
                if (extra == null) {
                    finish();
                    return;
                }
                Bitmap bm = extra.getParcelable("data");
                presenter.uploadImage(bm);
            }
        }
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, IMAGE_CROP);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void uploadSuccess() {
        Toast.makeText(this, "上传成功！", Toast.LENGTH_LONG).show();
        Log.i("uploadactivity", "上传成功！");
    }

    @Override
    public void uploadFailure() {
        Toast.makeText(this, "上传失败", Toast.LENGTH_LONG).show();
        Log.i("uploadactivity", "上传失败！");
    }

    @Override
    public void uploadSuccessFinish() {
        finish();
    }

    @Override
    public void uploadFailureFinish() {
        finish();
    }

}
