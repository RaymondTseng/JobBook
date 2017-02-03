package com.example.jobbook.upload;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import com.example.jobbook.util.L;

import android.util.Log;
import android.widget.Toast;

import com.example.jobbook.MyApplication;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * Created by Xu on 2016/9/7.
 */
public class CropUtils {
    public static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final int REQUEST_CAMERA = 0x03;
    private static final String EXTRA_VIEW_TAG = "viewTag";//同一个页面多个地方需要选择图片时，config里tag字段用于标识

    public static final int TYPE_AVATAR = 1;
    public static final int TYPE_NORMAL = 2;

    public static Uri getUri() {
        return uri;
    }

    private static Uri uri;

    private static CropConfig config = new CropConfig();


    private static Uri buildUri() {
        File cacheFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "crop");
        if (!cacheFolder.exists()) {
            try {
                boolean result = cacheFolder.mkdir();
                Log.d("uri", "generateUri " + cacheFolder + " result: " + (result ? "succeeded" : "failed"));
            } catch (Exception e) {
                Log.e("uri", "generateUri failed: " + cacheFolder, e);
            }
        }
        if (MyApplication.getmLoginStatus() == 1) {
            String name = MyApplication.getAccount() + "_head.jpeg";
            uri = Uri
                    .fromFile(cacheFolder)
                    .buildUpon()
                    .appendPath(name)
                    .build();
            L.e("crop", uri.toString());
        }
        return uri;
    }

    public static void pickAvatarFromGallery(Activity context) {
        context.startActivityForResult(pickFromGallery(null, TYPE_AVATAR), REQUEST_SELECT_PICTURE);
    }

    public static void pickAvatarFromGallery(Fragment context){
        context.startActivityForResult(pickFromGallery(null, TYPE_AVATAR), REQUEST_SELECT_PICTURE);
    }

    public static void pickAvatarFromCamera(Activity context) {
        context.startActivityForResult(pickFromCamera(null, TYPE_AVATAR), REQUEST_CAMERA);
    }

    public static void pickAvatarFromCamera(Fragment context) {
        context.startActivityForResult(pickFromCamera(null, TYPE_AVATAR), REQUEST_CAMERA);
    }


    public static Intent pickFromGallery(CropConfig config, int type) {
        if (config != null) {
            CropUtils.config = config;//怎么避免前后两次config
        } else {
            CropUtils.config = new CropConfig();
        }
        setType(type);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
//        context.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
    }

    private static void setType(int type) {
        if (type == TYPE_AVATAR) {
            config.isOval = true;
            config.aspectRatioX = 1;
            config.aspectRatioY = 1;
            config.hideBottomControls = true;
            config.showGridLine = false;
            config.showOutLine = false;
            config.maxHeight = 400;
            config.maxWidth = 400;
        } else if (type == TYPE_NORMAL) {//什么都不用做


        } else {

        }
    }

    public static Intent pickFromCamera(CropConfig config, int type) {
        if (config != null) {
            CropUtils.config = config;
        } else {
            CropUtils.config = new CropConfig();
        }

        setType(type);

        Uri mDestinationUri = buildUri();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, mDestinationUri);
        return intent;
//        context.startActivityForResult(intent, REQUEST_CAMERA);
    }

//    public static void pickFromGallery(Activity context) {
//
//        pickFromGallery(context, null, 0);
//    }
//
//    public static void pickFromCamera(Activity context) {
//        pickFromCamera(context, null, 0);
//    }

    /**
     * 注意，调用时data为null的判断
     *
     * @param context
     * @param cropHandler
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void handleResult(Activity context, CropHandler cropHandler, int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {//第一次，选择图片后返回
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    prepareForCrop(data.getData()).start(context);
                } else {
                    Toast.makeText(context, "Cannot retrieve selected image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//第二次返回，图片已经剪切好

                Uri finalUri = UCrop.getOutput(data);
                cropHandler.handleCropResult(finalUri, config.tag);

            } else if (requestCode == REQUEST_CAMERA) {//第一次，拍照后返回
                prepareForCrop(uri).start(context);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            cropHandler.handleCropError(data);
        }

    }

    public static void handleResult(Context context, Fragment fragment,
                                    CropHandler cropHandler, int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {//第一次，选择图片后返回
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    prepareForCrop(data.getData()).start(context, fragment);
                } else {
                    Toast.makeText(context, "Cannot retrieve selected image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {//第二次返回，图片已经剪切好

                Uri finalUri = UCrop.getOutput(data);
                cropHandler.handleCropResult(finalUri, config.tag);

            } else if (requestCode == REQUEST_CAMERA) {//第一次，拍照后返回
                prepareForCrop(uri).start(context, fragment);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            cropHandler.handleCropError(data);
        }

    }

    private static UCrop prepareForCrop(Uri sourceUri) {
        Uri mDestinationUri = buildUri();
        UCrop uCrop = UCrop.of(sourceUri, mDestinationUri);

        uCrop.withAspectRatio(config.aspectRatioX, config.aspectRatioY);
        uCrop.withMaxResultSize(config.maxWidth, config.maxHeight);

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.NONE);
        options.setCompressionQuality(config.quality);
        options.setOvalDimmedLayer(config.isOval);
        options.setShowCropGrid(config.showGridLine);
        options.setHideBottomControls(config.hideBottomControls);
        options.setShowCropFrame(config.showOutLine);
        options.setToolbarColor(config.toolbarColor);
        options.setStatusBarColor(config.statusBarColor);

        uCrop.withOptions(options);

        return uCrop;
    }


    public static class CropConfig {
        public int aspectRatioX = 1;
        public int aspectRatioY = 1;
        public int maxWidth = 1080;
        public int maxHeight = 1920;

        //options
        public int tag;
        public boolean isOval = false;//是否为椭圆
        public int quality = 80;

        public boolean hideBottomControls = true;//底部操作条
        public boolean showGridLine = true;//内部网格
        public boolean showOutLine = true;//最外面的矩形线

        public
        @ColorInt
        int toolbarColor = 0xFF3F51B5;
        public
        @ColorInt
        int statusBarColor = 0xFF3F51B5;


        public void setAspectRation(int x, int y) {
            this.aspectRatioX = x;
            this.aspectRatioY = y;
        }

        public void setMaxSize(int width, int height) {
            this.maxHeight = height;
            this.maxWidth = width;
        }

    }


    public interface CropHandler {
        void handleCropResult(Uri uri, int tag);
        void handleCropError(Intent data);
    }

}
