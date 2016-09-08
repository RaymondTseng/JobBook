package com.example.jobbook.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.bean.PersonBean;
import com.example.jobbook.commons.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Queue;

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
     * @param personBean
     * @return
     */
    public static PersonBean loadPersonBean(SharedPreferences share, PersonBean personBean) {
        personBean = new PersonBean();
        personBean.setAccount(share.getString("account", ""));
        personBean.setPassword(share.getString("password", ""));
        personBean.setTelephone(share.getString("telephone", ""));
        personBean.setUsername(share.getString("username", ""));
        personBean.setHead(share.getString("head", ""));
        return personBean;
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
     * 截取文章作为预览
     *
     * @param content
     * @return
     */
    public static String subContent(String content) {
        if (content.length() < 100) {
            return content;
        }
        String result = content.substring(0, 100);
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
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 有SD卡
            return true;
        }
        return false;
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

}
