package com.example.staticProxy;

/**
 * Created by wuxiaojun on 16-11-28.
 */

public class ShoppingImpl implements Shopping {

    @Override
    public Object[] doShopping(long money) {

        System.out.println("本来要做的事情：逛淘宝，逛商场，买买买");
        System.out.println(String.format("本来要花费的钱：花了%s块钱",money));

        return new Object[]{"鞋子","衣服","零食"};
    }

}
