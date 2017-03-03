package com.example.jobbook.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class Util {

    public static void toAnotherActivity(Context mContext, Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }

    public static void toAnotherActivity(Context mContext, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 检测错误类型
     *
     * @param error
     * @return
     */
    public static boolean checkError(String error) {
        return false;
    }

    /**
     * 检测账号是否有非法字符
     *
     * @param str
     * @return
     */
    public static boolean illegalCharactersCheck(String str) {
        String[] illegalCharacters = Constants.illegalCharacters;
        for (String illegalCharacter : illegalCharacters) {
            if (str.contains(illegalCharacter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取登录状态
     *
     * @return
     */
    public static int getLoginStatus() {
        return MyApplication.getmLoginStatus();
    }

    /**
     * 加载已存在的PersonBean
     *
     * @param share
     * @return
     */
    public static PersonBean loadPersonBean(SharedPreferences share) {
        PersonBean personBean = new PersonBean();
        if (share != null && !TextUtils.isEmpty(share.getString("account", ""))) {
            personBean.setAccount(share.getString("account", ""));
            personBean.setPassword(share.getString("password", ""));
            personBean.setTelephone(share.getString("telephone", ""));
            personBean.setUsername(share.getString("username", ""));
            personBean.setHead(share.getString("head", ""));
            personBean.setMoment(share.getString("moment", ""));
            personBean.setFans(share.getString("fans", ""));
            personBean.setFollow(share.getString("follow", ""));
            personBean.setWorkPosition(share.getString("workposition", ""));
            personBean.setWorkSpace(share.getString("workspace", ""));
            return personBean;
        }
        return null;
    }

    /**
     * 保存新注册或登录的PersonBean
     *
     * @param share
     * @param personBean
     */
    public static void savePersonBean(SharedPreferences share, PersonBean personBean) {
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("account", personBean.getAccount());
        edit.putString("password", personBean.getPassword());
        edit.putString("telephone", personBean.getTelephone());
        edit.putString("username", personBean.getUsername());
        edit.putString("head", personBean.getHead());
        edit.putString("fans", personBean.getFans());
        edit.putString("follow", personBean.getFollow());
        edit.putString("moment", personBean.getMoment());
        edit.putString("workspace", personBean.getWorkSpace());
        edit.putString("workposition", personBean.getWorkPosition());
        edit.commit();
    }

    /**
     * 清空已保存的PersonBean
     *
     * @param share
     */
    public static void clearPersonBean(SharedPreferences share) {
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.clear();
        edit.commit();
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 截取文章作为预览
     *
     * @param content
     * @return
     */
    public static String subContent(String content, int size) {
        if (content.length() < size) {
            return content;
        }
        String result = content.substring(0, size);
        result = result + "...";
        return result;
    }

    /**
     * 返回搜索纪录
     *
     * @param share
     * @return
     */
    public static LinkedList<String> getSearchList(SharedPreferences share) {
        LinkedList<String> searchRecord = new LinkedList<>();
        int index = 0;
        while (!TextUtils.isEmpty(share.getString(index + "", ""))) {
            searchRecord.add(share.getString(index + "", ""));
            index++;
        }
        return searchRecord;
    }

    /**
     * 设置搜索记录
     *
     * @param share
     * @param newSearchRecord
     */
    public static void setSearchList(SharedPreferences share, LinkedList<String> newSearchRecord) {
        LinkedList<String> searchRecord = Util.getSearchList(share);
        int index = searchRecord.size();
        SharedPreferences.Editor editor = share.edit();
        for (int i = index; i < newSearchRecord.size(); i++) {
            editor.putString(i + "", newSearchRecord.get(i));
        }
        editor.commit();
    }

    /**
     * 清空搜索记录
     *
     * @param share
     */
    public static void clearSearchList(SharedPreferences share) {
        if (Util.getSearchList(share) != null) {
            SharedPreferences.Editor editor = share.edit();
            editor.clear();
            editor.commit();
        }
    }

    /**
     * 判断是否有SD卡
     *
     * @return 有SD卡返回true，否则false
     */
    public static boolean hasSDCard() {
        // 获取外部存储的状态
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static String getMD5(String val) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] m = md5.digest();
        return getString(m);
    }

    private static String getString(byte[] m) {
        StringBuilder hex = new StringBuilder(m.length * 2);
        for (byte b : m) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 显示弹出窗口
     * @param view
     * @param content
     */
    public static void showSnackBar(View view, String content) {
//        View view = activity.getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView)demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * 显示弹出窗口
     * @param view
     * @param content
     */
    public static void showSnackBar(View view, String content, String buttonText) {
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView)demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        //R.color.colorBlue
        snackbar.setActionTextColor(Color.parseColor("#457ff4"));
        snackbar.setAction(buttonText, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    /**
     * 显示弹出窗口
     * @param view
     * @param content
     */
    public static void showSnackBar(View view, String content, String buttonText, View.OnClickListener listener) {
//        View view = activity.getWindow().getDecorView();
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_SHORT);
        View demo = snackbar.getView();
        ((TextView)demo.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);
        //R.color.colorBlue
        snackbar.setActionTextColor(Color.parseColor("#457ff4"));
        snackbar.setAction(buttonText, listener);
        snackbar.show();
    }


    /**
     * 返回“几分钟前”的字符串，最大为"24小时前"
     * @param currentTime
     * @param originalTime
     * @return
     */
//    public static String getTime(long currentTime, long originalTime) {
//        long delta = currentTime - originalTime;
//        if (delta < 0) {
//            return null;
//        }
//        if (delta < 1L * 60000L) {
////            long seconds = toSeconds(delta);
////            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
//            return "刚刚";
//        }
//        if (delta < 60L * 60000L) {
//            long minutes = delta / 60L / 1000L;
//            return (minutes <= 0 ? 1 : minutes) + "分钟前";
//        }
//        if (delta < 24L * 3600000L) {
//            long hours = delta / 60L / 60L / 1000L;
//            return (hours <= 0 ? 1 : hours) + "小时前";
//        }
//        Date date = new Date(originalTime);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
//        calendar.setTime(date);
//        String time = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
//        return time;
//
//    }

}
