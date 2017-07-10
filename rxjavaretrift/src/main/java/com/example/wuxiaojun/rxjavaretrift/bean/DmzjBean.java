package com.example.wuxiaojun.rxjavaretrift.bean;

import java.util.List;

/**
 * Created by wuxiaojun on 16-11-9.
 */
public class DmzjBean {

    public String status;//状态 成功"ok"  失败"error"
    public int count;//返回多少条数据
    public int count_total;//总共多少条数据
    public int pages;//页数
    public List<PostsBean> posts;//正文数据

    public DmzjBean(){

    }

    //实体数据
    public class PostsBean {
        public int id;
        public String url;//路径
        public String title;//标题
        public String content;//简介
        public String thumbnail;//需要显示的封面图
        public List<Categories> categories;//分类
        public List<Tags> tags;//标记

        public PostsBean(int id){
        }
    }

    //分类数据
    public class Categories {
        public String title;
    }

    //标记
    public class Tags {
        public String slug;//分为4种样式，01,02,03,04
    }

}
