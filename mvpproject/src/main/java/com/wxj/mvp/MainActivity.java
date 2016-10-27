package com.wxj.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wxj.mvp.bean.User;
import com.wxj.mvp.presenter.UserLoginViewProsenter;
import com.wxj.mvp.view.IUserLoginView;

public class MainActivity extends Activity implements IUserLoginView,View.OnClickListener{

    private EditText et_name;
    private EditText et_pwd;
    private Button btn_login;
    private Button btn_clean;

    private UserLoginViewProsenter mProsenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_clean = (Button) findViewById(R.id.btn_clean);

        mProsenter = new UserLoginViewProsenter(this);

        btn_login.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login){
            mProsenter.login();
        }else{
            mProsenter.clean();
        }
    }

    @Override
    public String getUserName() {
        return et_name.getText().toString();
    }

    @Override
    public String getUserPwd() {
        return et_pwd.getText().toString();
    }

    @Override
    public void cleanUserName() {
        et_name.setText("");
    }

    @Override
    public void cleanUserPwd() {
        et_pwd.setText("");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void toMainActivity(User user) {
        Toast.makeText(this,"success",1).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(this,"failed",1).show();
    }


}
