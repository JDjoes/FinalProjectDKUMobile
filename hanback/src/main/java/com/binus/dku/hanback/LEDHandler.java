package com.binus.dku.hanback;

public class LEDHandler {

    static{
        System.loadLibrary("hello");
    }

    public static native int ndkPlay(int v);

}
