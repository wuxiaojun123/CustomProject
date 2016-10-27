package com.example.TouchTest;

/**
 * Created by wuxiaojun on 16-9-9.
 */
public class TouchTesst {
    protected static final int FLAG_DISALLOW_INTERCEPT = 0x80000;

    public static void main(String[] args){
        //
        int mGroupFlags = 0x8;
        mGroupFlags |= FLAG_DISALLOW_INTERCEPT;
        System.out.println(mGroupFlags);
    }

}
