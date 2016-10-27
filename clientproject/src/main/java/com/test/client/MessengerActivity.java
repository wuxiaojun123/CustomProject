package com.test.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MessengerActivity extends Activity {

    private Button btn_get_data;
    private TextView tv_content;
    private Messenger mServiceMessenger;
    private Messenger mClientMessenger;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String value = msg.getData().getString("key");
            tv_content.setText(value);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        mClientMessenger = new Messenger(mHandler);

        Intent mIntent = new Intent("com.test.server.service.MessengerService");
        bindService(mIntent, mconn, BIND_AUTO_CREATE);

        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_get_data = (Button) findViewById(R.id.btn_get_data);
        btn_get_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = 1;
                try {
                    if (mServiceMessenger != null) {
                        //将客户端messenger信使赋值给服务端(为了方便接受回传过来的数据)
                        msg.replyTo = mClientMessenger;
                        //服务端信使发送数据
                        mServiceMessenger.send(msg);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ServiceConnection mconn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取服务端的messenger信使
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mconn);
    }
}
