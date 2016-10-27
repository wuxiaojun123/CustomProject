package com.wxj.mvp.biz;

/**
 * Created by wuxiaojun on 16-8-24.
 */
public interface IUserBiz {

    void login(String userName,String userPwd,OnLoginListener lis);

}
