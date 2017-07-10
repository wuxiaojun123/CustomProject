package com.wxj.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wuxiaojun on 16-12-28.
 */

public class ToastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyPicture";

                ToastClickView.show(ToastActivity.this, path);

                Snackbar snackbar = Snackbar.make(v,"点击查看啊",Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "土司", Toast.LENGTH_SHORT).show();
                    }
                });

                snackbar.show();
            }
        });

    }


}
