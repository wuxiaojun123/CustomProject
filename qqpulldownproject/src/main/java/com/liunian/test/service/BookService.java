package com.liunian.test.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


import com.wxj.customview.aidl.Book;
import com.wxj.customview.aidl.IMyAidlInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaojun on 16-9-7.
 */
public class BookService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }



    IMyAidlInterface.Stub mBind = new IMyAidlInterface.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            List<Book> mList = new ArrayList<>();
            mList.add(new Book(1,"黑道特种兵"));
            Log.e("asdfas","获取方法");
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return super.asBinder();
        }

    };


}
