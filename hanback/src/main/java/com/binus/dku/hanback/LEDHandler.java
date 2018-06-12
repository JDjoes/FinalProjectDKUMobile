package com.binus.dku.hanback;

public class LEDHandler {

    static{
        System.loadLibrary("hanback");
    }

    public static native int ndkPlay(int v);

}
