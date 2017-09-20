package com.mywork.myhappyreader.model;

import android.databinding.ObservableField;
import android.text.Layout;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.mywork.myhappyreader.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MYWork on 2017/9/7.
 * 页面Model类
 */

public class Page {
    private BookInfo mBookInfo;
    private int mTextViewWidth, mTextViewHeight, mLineHeight;
    private int mMaxLine, mPerPageMaxCharCount;
    private int mStart = 0, mEnd = 0;
    public ObservableField<String> content = new ObservableField<>();

    public Page(BookInfo bookInfo) {
        this.mBookInfo = bookInfo;
    }

    public void init(final TextView textView) {
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextViewWidth = textView.getWidth();
                mTextViewHeight = textView.getHeight();
                mLineHeight = textView.getLineHeight();
                mMaxLine = mTextViewHeight / mLineHeight;
                mPerPageMaxCharCount = FileUtils.computeCharsPerPage(textView);
                current(textView);

                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    public void current(TextView textView) {
        String text = mBookInfo.getContext().substring(mStart, mStart + mPerPageMaxCharCount);
        textView.setText(text);
        Layout layout = textView.getLayout();

        int lastIndex = layout.getLineEnd(mMaxLine - 1);
        mEnd = mStart + lastIndex;
        content.set(text.substring(0, lastIndex));
    }

    public void next(TextView textView) {
        mStart = mEnd;
        current(textView);
    }

    public void previous(TextView textView) {
        if(mStart == 0)
            return;
        mEnd = mStart;
        mStart = mEnd - mPerPageMaxCharCount;
        if(mStart < 0) {
            mStart = 0;
        }
        String text = mBookInfo.getContext().substring(mStart, mEnd);
        if(text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        textView.setText(text);
        Layout layout = textView.getLayout();

        int count = layout.getLineCount();
        int spilthLine = 0, spilthIndex = 0;
        if(count > mMaxLine) {
            spilthLine = count - mMaxLine - 1;
            spilthIndex = layout.getLineEnd(spilthLine);
        }
        mStart += spilthIndex;
        content.set(text.substring(spilthIndex));
    }

    public void setStart(int start) {
        this.mStart = start;
    }
}
