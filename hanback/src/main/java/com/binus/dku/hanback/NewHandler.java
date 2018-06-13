package com.binus.dku.hanback;

public class NewHandler {

    static {
        System.loadLibrary("hanback");
    }

    public static native int ndkPlay(int v);

    public static native int getSwitchValue();

    public static native String printMsg(String m);
}
