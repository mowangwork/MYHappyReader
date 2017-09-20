package com.mywork.myhappyreader.model;

import org.mozilla.universalchardet.CharsetListener;
import org.mozilla.universalchardet.Constants;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MYWork on 2017/9/5.
 * 书籍信息
 */

public class BookInfo {
    private File mBook;
    private int mStart = 0;
    private StringBuilder mContent = new StringBuilder();
    private List<String> mPageContent = new ArrayList<>();

    public BookInfo(String path) {
        mBook = new File(path);
    }

    public String getTitle() {
        return mBook.getName();
    }

    public String getPath() {
        return mBook.getPath();
    }

    public void openBook() throws IOException {
        BufferedReader bufferedReader =
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(mBook),
                                getDetector()));
        String valueString;
        while ((valueString = bufferedReader.readLine()) != null) {
            mContent.append(valueString);
            mContent.append("\n");
        }
        if(mContent.length() > 0) {
            mContent.deleteCharAt(mContent.length() - 1);
        }
        bufferedReader.close();
    }

    private String getDetector() throws IOException {
        // (1)
        FileInputStream fileInputStream = new FileInputStream(mBook);
        UniversalDetector detector = new UniversalDetector(null);
        byte[] buf = new byte[4096];
        // (2)
        int nread;
        while ((nread = fileInputStream.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();
        // (4)
        String encoding = detector.getDetectedCharset();
        if (encoding == null)
            encoding = Constants.CHARSET_UTF_16LE;
        // (5)
        detector.reset();
        return encoding;
    }

    public String getContext() {
        return mContent.toString();
    }
}
