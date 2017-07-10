
package com.example.wuxiaojun.rxjavaretrift.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * 网络判断
 * 
 * @author Sinocall
 */
public class JudgeNetWork {

    /***
     * 判断网络是否可用
     * 
     * @param context
     * @return false表示当前无网络
     */
    public static boolean isNetAvailable(Context context) {
        if(context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info != null) {
                    if (info.isAvailable() && info.isConnected())
                        return true;
                }
            }
        }
        return false;
    }

}
