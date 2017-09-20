package com.mywork.myhappyreader.view;

import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mywork.myhappyreader.R;
import com.mywork.myhappyreader.databinding.FragmentFileListBinding;
import com.mywork.myhappyreader.databinding.ItemFileBinding;
import com.mywork.myhappyreader.utils.MYFilenameFilter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FileListFragment extends Fragment {

    private String mPath = Environment.getExternalStorageDirectory().getPath();
    public static final String ARG_PATH = "path";
    public FileListFragment() {
    }

    public static FileListFragment newInstance(String path) {
        FileListFragment fragment = new FileListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PATH, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mPath = getArguments().getString(ARG_PATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFileListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_file_list, container, false);
        File file = new File(mPath);
        File[] names = file.listFiles(new MYFilenameFilter());
        List<File> fileList = Arrays.asList(names);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file, File t1) {
                return file.getName().compareTo(t1.getName());
            }
        });

        binding.setData(Arrays.asList(names));
        return binding.getRoot();
    }
}
