package com.wxj.animatordemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wxj.animatordemo.property.PropertyActivity1;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/****
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_property_animator)
    Button btn_property_animator;

    @BindView(R.id.iv_laucher)
    ImageView iv_laucher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_property_animator, R.id.iv_laucher})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_property_animator:

                startActivity(PropertyActivity1.class);

                break;
            case R.id.iv_laucher:
                Toast.makeText(getApplicationContext(), "点击事件", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /*private void startAnim() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 100, 0, 100);
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        iv_laucher.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //清除动画
                iv_laucher.clearAnimation();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_laucher.getLayoutParams();
                params.leftMargin = params.leftMargin + 100;
                params.topMargin = params.topMargin + 100;
                iv_laucher.setLayoutParams(params);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }*/

    public void startActivity(Class<?> cls) {
        Intent mIntent = new Intent(MainActivity.this, cls);
        startActivity(mIntent);
    }

    public void loge(String content) {
        Log.e("动画", content);
    }


}
