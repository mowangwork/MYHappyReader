package com.mywork.myhappyreader.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mywork.myhappyreader.R;
import com.mywork.myhappyreader.databinding.ActivityReaderBinding;
import com.mywork.myhappyreader.model.BookInfo;
import com.mywork.myhappyreader.utils.LineBreaker;
import com.mywork.myhappyreader.viewModel.ReaderViewModel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ReaderActivity extends AppCompatActivity {
    ActivityReaderBinding mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_reader);

        String path = getIntent().getStringExtra(ReaderViewModel.ARG_PATH);

        new ReaderViewModel(mBinder,path);
    }
}
