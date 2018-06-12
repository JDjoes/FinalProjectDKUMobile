package com.binus.dku.hanback;

public class Handler {

    static {
        System.loadLibrary("hanback");
    }

    public static native int ndkPlay(int v);

    public static native String printMsg(String m);
}
