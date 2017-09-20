package com.mywork.myhappyreader.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by MYWork on 2017/8/27.
 */

public class MYFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File file, String s) {
        File target = new File(file.getAbsolutePath() + File.separator + s);
        if(target.isHidden() || !target.canRead() || !target.canWrite()) {
            return false;
        }

        if(target.isDirectory()) {
            if(file.listFiles().length == 0) {
                return false;
            }
        }
        if(!target.isDirectory()) {
            if(!s.endsWith(".txt")){
                return false;
            }
        }
        return true;
    }
}
