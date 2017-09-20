package com.mywork.myhappyreader.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mywork.myhappyreader.R;
import com.mywork.myhappyreader.databinding.ItemFileBinding;
import com.mywork.myhappyreader.view.FileListActivity;
import com.mywork.myhappyreader.view.FileListFragment;

import java.io.File;
import java.util.List;

/**
 * Created by MYWork on 2017/8/27.
 */

public class FileListViewModel {

    public static class FileListAdapter extends RecyclerView.Adapter<FileListHolder>{
        private List<File> data;
        private Context context;
        private FileListItemClick click;

        public FileListAdapter(Context context, List<File> list) {
            this.context = context;
            this.data = list;
            click = new FileListItemClick(context);
        }

        @Override
        public FileListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemFileBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_file, parent, false);
            return new FileListHolder(binding);
        }

        @Override
        public void onBindViewHolder(final FileListHolder holder, int position) {
            holder.getBinding().setFile(data.get(position));
            holder.getBinding().setListener(click);
            holder.getBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    public static class FileListHolder extends RecyclerView.ViewHolder {
        private ItemFileBinding binding;

        public FileListHolder(ItemFileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemFileBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemFileBinding binding) {
            this.binding = binding;
        }

    }

    public static class FileListItemClick {
        private Context mContext;
        public FileListItemClick(Context context) {
            this.mContext = context;
        }
        public void onClick(File file) {
            FileListActivity activity = (FileListActivity) mContext;
            if(file.isDirectory()) {
                FragmentManager manager = activity.getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment, FileListFragment.newInstance(file.getPath()));
                transaction.addToBackStack(file.getPath());
                transaction.commit();
            } else {
                ReaderViewModel.startActivity(mContext, file.getPath());
            }
        }
    }
}
