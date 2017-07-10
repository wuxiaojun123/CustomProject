package com.example.wuxiaojun.rxjavaretrift.bean;

/**
 * 历史记录中的漫画详情
 * Created by wuxiaojun on 16-10-9.
 */
public class CartoonDetailsBean {


    public String title;
    public byte[] img;
    public String url;

    public CartoonDetailsBean(String title,byte[] img,String url){
        this.title = title;
        this.img = img;
        this.url = url;
    }


}
