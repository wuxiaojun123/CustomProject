package com.liunian.plug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.rl_main_bg)
    RelativeLayout rl_main_bg;
    @BindView(R.id.btn_toogle_skin)
    Button btn_toogle_skin;

    private List<Map<String, String>> plugins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_toogle_skin})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_toogle_skin:
                showPopupWindow();

                break;
        }
    }


    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        PopupWindow popupWindow = new PopupWindow(view);
        ListView listView = (ListView) view.findViewById(R.id.listview);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        popupWindow.setFocusable(true);//设置点击其他地方或者back键消失popup
        //先去找到所有的插件
        plugins = findPlugins();
        if (plugins.size() == 0) {
            Toast.makeText(getApplicationContext(), "暂无皮肤", Toast.LENGTH_SHORT).show();
            return;
        } else {
            SimpleAdapter adapter = new SimpleAdapter(this,
                    plugins, android.R.layout.simple_list_item_1,
                    new String[]{"label"}, new int[]{android.R.id.text1});
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            popupWindow.setWidth(100);
            popupWindow.setHeight(plugins.size() * 100);
            popupWindow.showAsDropDown(btn_toogle_skin);
        }
    }

    /***
     * 查找所有的插件
     */
    private List<Map<String, String>> findPlugins() {
        List<Map<String, String>> plugins = new ArrayList<>();
        //获取包管理器
        PackageManager pm = this.getPackageManager();
        //获取所有的apk信息包括已下载但是没有安装的
        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (PackageInfo info : installedPackages) {
            String packageName = info.packageName;
            String sharedUserId = info.sharedUserId;
            if (sharedUserId == null
                    || !sharedUserId.equals("com.liunian.skin")
                    || packageName.equals(getPackageName())) {
                continue;
            }
            String label = pm.getApplicationLabel(info.applicationInfo).toString();
            Map<String, String> map = new HashMap<>();
            map.put("label", label);//应用名称
            map.put("pkg", packageName);//插件的包名
            plugins.add(map);
        }
        return plugins;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Map<String,String> map = plugins.get(position);
        String pkg = map.get("pkg");
        //如何访问插件的资源
        Context plugContext = null;
        try {
            //获取插件的上下文
            plugContext = this.createPackageContext(pkg,CONTEXT_IGNORE_SECURITY|CONTEXT_INCLUDE_CODE);
            //访问插件的R文件
            int bu_id = getBgByPackageName(pkg,plugContext);
            rl_main_bg.setBackgroundDrawable(plugContext.getResources().getDrawable(bu_id));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * 根据插件的context和pkg,获取插件的资源文件图片的id
     * @param pkg
     * @param plugContext
     */
    private int getBgByPackageName(String pkg, Context plugContext) {
        int id =0;
        //获取对应R文件的类加载器去加载R文件
        PathClassLoader pathClassLoader = new PathClassLoader(plugContext.getPackageResourcePath(),ClassLoader.getSystemClassLoader());
        //反射
        try {
            Class<?> forNames = Class.forName(pkg+".R$mipmap",true,pathClassLoader);
            Field[] fields = forNames.getDeclaredFields();
            for(Field field:fields){
                if(field.getName().contains("main_bg2")){
                    try {
                        id = field.getInt(R.mipmap.class);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }


}
