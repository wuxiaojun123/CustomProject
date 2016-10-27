package com.liunian.test;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wuxiaojun on 16-9-11.
 */
public class HandlerThread extends android.os.HandlerThread {



    public HandlerThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL("http://scimg.jb51.net/allimg/160815/103-160Q509544OC.jpg");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//基于http协议的连接对象
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            if(conn.getResponseCode() == 200){
                conn.getInputStream();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes,0,len)) != -1){
            baos.write(bytes,0,len);
        }
        is.close();
        return baos.toByteArray();
    }

}
