package com.binus.dku.hanback;

public class TextLCDHandler {
    static {
        System.loadLibrary("hanback");
    }

    public static native String printMsg(String m);
}
