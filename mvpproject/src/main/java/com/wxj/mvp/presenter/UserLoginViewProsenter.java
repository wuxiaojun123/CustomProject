package com.wxj.mvp.presenter;

import android.os.Handler;

import com.wxj.mvp.bean.User;
import com.wxj.mvp.biz.IUserBiz;
import com.wxj.mvp.biz.OnLoginListener;
import com.wxj.mvp.biz.UserBiz;
import com.wxj.mvp.view.IUserLoginView;

/**
 * Created by wuxiaojun on 16-8-24.
 */
public class UserLoginViewProsenter {

    private Handler mHandler = new Handler();
    private IUserLoginView iUserLoginView;
    private IUserBiz mIuserBiz;

    public UserLoginViewProsenter(IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
        mIuserBiz = new UserBiz();
    }

    public void login() {
        mIuserBiz.login(iUserLoginView.getUserName(), iUserLoginView.getUserPwd(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.toMainActivity(user);
                    }
                });
            }

            @Override
            public void loginFailed() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.loginFailed();
                    }
                });
            }
        });
    }

    public void clean() {
        iUserLoginView.cleanUserName();
        iUserLoginView.cleanUserPwd();
    }

}
