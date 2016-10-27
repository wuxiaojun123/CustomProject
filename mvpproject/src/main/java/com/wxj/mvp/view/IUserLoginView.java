package com.wxj.mvp.view;

import com.wxj.mvp.bean.User;

/**
 * Created by wuxiaojun on 16-8-24.
 */
public interface IUserLoginView {

    String getUserName();

    String getUserPwd();

    void cleanUserName();

    void cleanUserPwd();

    void showProgress();

    void hideProgress();

    void toMainActivity(User user);

    void loginFailed();

}
