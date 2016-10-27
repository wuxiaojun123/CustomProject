package com.wxj.animatordemo.property;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.wxj.animatordemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-6-30.
 */
public class PropertyActivity1 extends Activity implements View.OnClickListener {

    private long duration = 7000;

    @BindView(R.id.btn_translation)
    Button btn_translation;
    @BindView(R.id.img_ic_launcher)
    ImageView img_ic_launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property1);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_translation})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_translation:

                startTranslation();

                break;
        }
    }

    private void startTranslation() {
        img_ic_launcher.animate().translationX(100).translationY(100).setDuration(duration).start();

        int colorA = Color.parseColor("#ff0000");
        int colorB = Color.parseColor("#00ff00");
        int colorC = Color.parseColor("#00ffff");
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(img_ic_launcher,"backgroundColor",colorA,colorB,colorC);
        objectAnimator.setDuration(duration);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.start();
    }


}
