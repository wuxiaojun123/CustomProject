package com.test.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.client.bean.IStudentAidlInterface;
import com.test.client.bean.Student;


public class AIDLActivity extends Activity {

    private Button btn_get_data;
    private IStudentAidlInterface mIStudentAidlInterface;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        //绑定进程通信的服务
        Intent mIntent = new Intent("com.test.server.service.StudentService");
        bindService(mIntent, mconn, Context.BIND_AUTO_CREATE);

        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_get_data = (Button) findViewById(R.id.btn_get_data);
        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIStudentAidlInterface != null) {
                    try {
                        Student mStudent = mIStudentAidlInterface.getStudent();
                        tv_content.setText("获取到服务进程的数据是：" + mStudent.studentId + "-------" + mStudent.name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private ServiceConnection mconn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIStudentAidlInterface = IStudentAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIStudentAidlInterface = null;
        }
    };

}
