package com.wxj.mvp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by wuxiaojun on 16-12-22.
 */

public class ToastClickView {


    public static void show(final Context context, final String filePath) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_toast_look, null);
        Button button = (Button) view.findViewById(R.id.id_tv_look);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击事件");
                System.out.println("点击事件");
                Toast.makeText(context, "土司", Toast.LENGTH_SHORT).show();
            }
        });

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();

        toast.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击事件");
                System.out.println("点击事件");
                Toast.makeText(context, "土司", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void show(final Context context, final Uri uri) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_look, null);
        TextView textView = (TextView) view.findViewById(R.id.id_tv_look);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/*");
                context.startActivity(intent);
            }
        });
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }


}
