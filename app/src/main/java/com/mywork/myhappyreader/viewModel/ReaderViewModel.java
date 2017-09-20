package com.mywork.myhappyreader.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.mywork.myhappyreader.databinding.ActivityReaderBinding;
import com.mywork.myhappyreader.model.BookInfo;
import com.mywork.myhappyreader.model.Page;
import com.mywork.myhappyreader.view.ReaderActivity;

import java.io.IOException;

/**
 * Created by MYWork on 2017/8/31.
 */

public class ReaderViewModel implements View.OnTouchListener {
    public final static String ARG_PATH = "path";
    private ActivityReaderBinding mBinding;
    private Page currentPage;
    BookInfo mBookInfo;
    public static void startActivity(Context context, String path) {
        Intent intent = new Intent(context, ReaderActivity.class);
        intent.putExtra(ARG_PATH, path);
        context.startActivity(intent);
    }

    public ReaderViewModel(ActivityReaderBinding binding, String path) {
        mBinding = binding;

        mBookInfo = new BookInfo(path);
        currentPage = new Page(mBookInfo);
        currentPage.setStart(0);

        mBinding.root.setOnTouchListener(this);

        currentPage.init(mBinding.reader);
        try {
            mBookInfo.openBook();

            binding.setPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //点击到左边前翻，点击到右边后翻。
        float center = view.getWidth() / 2;
        if(motionEvent.getX() < center) {
            currentPage.previous(mBinding.reader);
        }
        if(motionEvent.getX() > center) {
            currentPage.next(mBinding.reader);
        }
        return false;
    }
}
