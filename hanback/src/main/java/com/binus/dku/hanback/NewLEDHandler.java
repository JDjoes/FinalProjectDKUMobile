package com.binus.dku.hanback;

public class NewLEDHandler {

    static {
        System.loadLibrary("hanback");
    }

    public static native int ndkPlay(int v);
}
