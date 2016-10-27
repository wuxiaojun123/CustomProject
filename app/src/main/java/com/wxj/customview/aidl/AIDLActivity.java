package com.wxj.customview.aidl;

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

import com.wxj.customview.R;
import com.wxj.customview.utils.LogUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-9-7.
 */
public class AIDLActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.id_btn_bind)
    Button id_btn_bind;
    @BindView(R.id.id_tv_content)
    TextView id_tv_content;

    private IMyAidlInterface myAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);

        Intent mIntent = new Intent("com.liunian.test.service.BookService");
        bindService(mIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @OnClick({R.id.id_btn_bind})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.id_btn_bind:
                try {
                    if(myAidlInterface != null){
                        List<Book> list = myAidlInterface.getBookList();
                        id_tv_content.setText(list.get(0).bookId + "======" + list.get(0).bookName);
                        LogUtils.e("哈哈哈哈"+list.get(0).bookId );
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.e("执行了方法"+name.getClassName()+"======service");
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlInterface = null;
        }
    };

}
