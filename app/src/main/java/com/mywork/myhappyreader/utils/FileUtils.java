package com.mywork.myhappyreader.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

/**
 * Created by MYWork on 2017/8/26.
 */

public class FileUtils {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static boolean verifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {

                // 没有权限，去申请权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized int computeCharsPerPage(TextView textView) {

        final int textWidth = textView.getWidth();
        final int textHeight = textView.getHeight();

        String testString = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ汉字";
        final int num = testString.length();
        final float totalTextSize = textView.getPaint().measureText(testString);
        final float charsPerParagraph = totalTextSize / num;

        final float charWidth = computeCharWidth(textView);

        final float effectiveWidth = textWidth - 0.5f * textWidth / charsPerParagraph;
        float charsPerLine = Math.min(effectiveWidth / charWidth,
                charsPerParagraph * 1.2f);

        final int strHeight = textView.getLineHeight();
        final int effectiveHeight = textHeight - textView.getPaddingTop() - textView.getPaddingBottom();
        final int linesPerPage = effectiveHeight / strHeight;

        return (int) (charsPerLine * linesPerPage);
    }

    public static synchronized int computeTextPageNumber(TextView textView, int textSize) {

        final float factor = 1.0f / computeCharsPerPage(textView);
        final float pages = textSize * factor;
        return Math.max((int)(pages + 1.0f - 0.5f * factor), 1);
    }

    private static float computeCharWidth(TextView textView) {
        return textView.getPaint().getTextSize();
    }
}
