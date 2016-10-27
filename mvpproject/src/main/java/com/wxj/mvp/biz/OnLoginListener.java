package com.wxj.mvp.biz;

import com.wxj.mvp.bean.User;

/**
 * Created by wuxiaojun on 16-8-24.
 */
public interface OnLoginListener {

    void loginSuccess(User user);

    void loginFailed();

}
