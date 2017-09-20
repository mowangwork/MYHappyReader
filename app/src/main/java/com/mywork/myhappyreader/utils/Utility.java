package com.mywork.myhappyreader.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.mywork.myhappyreader.viewModel.FileListViewModel;

import java.io.File;
import java.util.List;

/**
 * Created by MYWork on 2017/8/26.
 */

public class Utility {
    @BindingAdapter("bind:image")
    public static void loadImage(ImageView image, Drawable resId){
        image.setImageDrawable(resId);
    }

    @BindingAdapter("bind:data")
    public static void setData(RecyclerView recyclerView, List<File> data){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new FileListViewModel.FileListAdapter(recyclerView.getContext(), data));
    }

}
