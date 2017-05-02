package com.lisn.signing.test;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lisn.signing.BaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ****************************
 * 项目名称：ydzf
 * 创建人：LiShan
 * 邮箱：cnlishan@163.com
 * 创建时间：2016/12/29
 * 版权所有违法必究
 * <p>
 * ****************************
 */


public class LS_Utils {
    /**
     * 如果空返回0 否则返回本身
     * @param data
     * @return
     */
    public static String ifNull(String data){
        if (!TextUtils.isEmpty(data) && data.length()>0){
            return data;
        }
        return "0";
    }

    /*判断String是否为空*/
    public static boolean isNEmpty(String data) {
        if (!TextUtils.isEmpty(data) && data.length()>0){
            return true;
        }
        return false;
    }

    /*判断是否为空为空弹吐司*/
    public static void ifNullToast(String data, String s) {
        if (TextUtils.isEmpty(data)){
            showToast(s);
            return;
        }
    }

    private static Toast toast;
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
    /*Toast工具*/
    public static void showToast(String s) {
        showToast(BaseApp.getInstance(),s);
    }

    public static void String2File(String fileName, String value){
        try {
//            OutputStream os=new FileOutputStream(new File(BaseApp.getInstance().getFilesDir()+"/"+fileName));
            OutputStream os=new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/"+fileName));
            os.write(value.getBytes());
            os.close();
            Log.e("LS_U", Environment.getExternalStorageDirectory() + "/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间——转换日期格式为yyyyMMddHHmmss
     */
    public static String getDateFormat() {
        Date date=new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

        return sd.format(date);
    }
    /**
     * 获取当前时间——转换日期格式为yyyy/MM/dd
     */
    public static String getDateFormat1() {
        Date date=new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");

        return sd.format(date);
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
     * 过滤空字符串
     * @param msg
     * @return
     */
    public static String filterNull(String msg) {
        if (TextUtils.isEmpty(msg) || msg.equals("null")) {
            msg = "";
        }
        return msg;
    }

    /**
     * 隐藏软键盘
     * c 当前activity上下文
     */
    public static void hideKeyBoard(Context c){
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            imm.hideSoftInputFromWindow(((Activity)c).getWindow().getDecorView().getWindowToken(),0);
    }

    /**
     * 让edittext失去焦点
     * @param view
     */
    public static void setEtFocus(View view) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
    }
}
