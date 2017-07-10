package com.dex.liunian.host;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    @BindView(R.id.iv_img)
    ImageView imageView;
    @BindView(R.id.tv_content)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //插件apk的路径
        String apkPath = Environment.getExternalStorageDirectory() + "/1.apk";
        //插件资源对象
        Resources resources = getBundleResource(this, apkPath);
        if (resources == null) {
            return;
        }
        //获取图片资源   资源名称，资源类型，包名
        int resDraw = resources.getIdentifier("img_drawable", "drawable", "com.example.wuxiaojun.plug_service_skin1");
        Drawable drawable = resources.getDrawable(resDraw);
        imageView.setImageDrawable(drawable);
        //获取文本资源
        int contentId = resources.getIdentifier("content", "string", "com.example.wuxiaojun.plug_service_skin1");
        Log.e(TAG, contentId + "资源id是");
        String text = resources.getString(contentId);
        textView.setText(text);
    }

    public Resources getBundleResource(Context context, String apkPath) {
        AssetManager assetManager = createAssetManager(apkPath);
        return new Resources(
                assetManager, context.getResources().getDisplayMetrics(),
                context.getResources().getConfiguration()
        );
    }

    public AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                    assetManager, apkPath
            );
            return assetManager;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
