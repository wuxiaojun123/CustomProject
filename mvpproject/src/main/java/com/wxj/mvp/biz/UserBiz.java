package com.wxj.mvp.biz;

import com.wxj.mvp.bean.User;

/**
 * Created by wuxiaojun on 16-8-24.
 */
public class UserBiz implements IUserBiz {



    @Override
    public void login(final String userName, final String userPwd,final OnLoginListener lis) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if("wxj".equals(userName) && "123".equals(userPwd)){
                        User mUser = new User();
                        mUser.name = userName;
                        mUser.pwd = userPwd;
                        if(lis != null){
                            lis.loginSuccess(mUser);
                        }
                    }else{
                        if(lis != null){
                            lis.loginFailed();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    if(lis != null){
                        lis.loginFailed();
                    }
                }
            }
        }).start();
    }


}
