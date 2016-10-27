package com.test.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.test.client.bean.IStudentAidlInterface;
import com.test.client.bean.Student;

public class StudentService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    IStudentAidlInterface.Stub mIBinder = new IStudentAidlInterface.Stub() {
        @Override
        public Student getStudent() throws RemoteException {
            return new Student(1, "名字是：哈哈哈");
        }
    };

}
