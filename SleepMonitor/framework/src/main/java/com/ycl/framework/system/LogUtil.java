package com.ycl.framework.system;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.util.Log;


public class LogUtil {
    protected static boolean mLoggingEnabled = true;

    public LogUtil() {
    }

    public static void setDebugLogging(boolean enabled) {
        mLoggingEnabled = enabled;
    }

    public static boolean isDebugLogging() {
        return mLoggingEnabled;
    }

    public static int v(String tag, String msg) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.v(tag, msg);
        }

        return result;
    }

    public static int v(String tag, String msg, Throwable tr) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.v(tag, msg, tr);
        }

        return result;
    }

    public static int d(String tag, String msg) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.d(tag, msg);
        }

        return result;
    }

    public static int d(String tag, String msg, Throwable tr) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.d(tag, msg, tr);
        }

        return result;
    }

    public static int i(String tag, String msg) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.i(tag, msg);
        }

        return result;
    }

    public static int i(String tag, String msg, Throwable tr) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.i(tag, msg, tr);
        }

        return result;
    }

    public static int w(String tag, String msg) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.w(tag, msg);
        }

        return result;
    }

    public static int w(String tag, String msg, Throwable tr) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.w(tag, msg, tr);
        }

        return result;
    }

    public static int w(String tag, Throwable tr) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.w(tag, tr);
        }

        return result;
    }

    public static int e(String tag, String msg) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.e(tag, msg);
        }

        return result;
    }

    public static int e(String tag, String msg, Throwable tr) {
        int result = 0;
        if (mLoggingEnabled) {
            result = Log.e(tag, msg, tr);
        }
        return result;
    }
}
