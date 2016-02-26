package com.github.woozet.zipman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Downloader.class);
    private String url;
    private String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String defaultDir) {
        this.path = defaultDir;
    }

    private void execute() {
        FileOutputStream fos = null;
        InputStream is;
        long totalBytes = 0;
        byte[] buffer = new byte[1024];
        int readBytes;

        try {
            fos = new FileOutputStream(this.path);

            URL postUrl = new URL(this.url);
            URLConnection urlConnection = postUrl.openConnection();
            is = urlConnection.getInputStream();

            while ((readBytes = is.read(buffer)) != -1) {
                totalBytes += readBytes;
                log.info("Written total : " + totalBytes + " / " + urlConnection.getContentLengthLong());
                fos.write(buffer, 0, readBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        log.info("Downloading thread is running...");
        execute();
        log.info("Download completed.");
    }
}
