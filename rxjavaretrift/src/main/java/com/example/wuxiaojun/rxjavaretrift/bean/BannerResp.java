package com.example.wuxiaojun.rxjavaretrift.bean;

import java.util.List;

/**
 * Created by wuxiaojun on 16-11-9.
 */
public class BannerResp {

    public List<BannerData> data;

    public class BannerBean {

        public String openType;//打开类型
        public String h5url;
        public String adType;//图片类型
        public String iconPath;
        public int isgif;//是否为动态图

    }

    public class BannerData {
        public String name;
        public String code;
        public String adType;
        public List<BannerBean> cons;

    }

}
