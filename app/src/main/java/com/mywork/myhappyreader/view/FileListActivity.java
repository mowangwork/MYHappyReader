package com.mywork.myhappyreader.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mywork.myhappyreader.R;
import com.mywork.myhappyreader.databinding.ActivityFileListBinding;
import com.mywork.myhappyreader.utils.FileUtils;
import com.mywork.myhappyreader.viewModel.FileListViewModel;

import java.io.File;

public class FileListActivity extends AppCompatActivity {

    private ActivityFileListBinding mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_file_list);
        setSupportActionBar(mBinder.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FileUtils.verifyStoragePermissions(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment, FileListFragment.newInstance(Environment.getExternalStorageDirectory().getPath()));
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0)
                getSupportFragmentManager().popBackStack();
            else
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public ActivityFileListBinding getBinder() {
        return mBinder;
    }
}
