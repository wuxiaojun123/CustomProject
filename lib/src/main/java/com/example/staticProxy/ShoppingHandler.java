package com.example.staticProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wuxiaojun on 16-11-28.
 */

public class ShoppingHandler implements InvocationHandler {

    Object base;

    public ShoppingHandler(Object base){
        this.base = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("doShopping".equals(method.getName())){
            //这里是代理Shopping接口的对象
            //先黑点钱(修改输入参数)
            Long money = (Long) args[0];
            long readCost = (long) (money*0.5);

            System.out.println(String.format("invoke中  花了%s块钱",readCost));

            //帮忙买东西
            Object[] things = (Object[]) method.invoke(base,readCost);

            //偷梁换柱
            if(things != null && things.length > 1){
                things[0] = "被掉包的东西！！！";
            }

            return things;
        }

        if("doSomething".equals(method.getName())){
            //可以代理别的，做些别的事情
            return null;
        }

        if("doSomethingElse".equals(method.getName())){
            //做些别的事情
            return null;
        }

        return null;
    }


}
