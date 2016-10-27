package com.test.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_aidl;
    private Button btn_messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_aidl = (Button) findViewById(R.id.btn_aidl);
        btn_messenger = (Button) findViewById(R.id.btn_messenger);
        btn_aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AIDLActivity.class);
            }
        });
        btn_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MessengerActivity.class);
            }
        });
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(MainActivity.this, cls));
    }


}
