package com.lzhz.lxh.sleepmonitor.tools.jni;

/**
 * 作者：lxh on 2018-02-02:09:43
 * 邮箱：15911638454@163.com
 */

public class JNIUtils {
   static {
       System.loadLibrary("main");
   }

   // public native String detect0131();
    public native String detect0131(double raw_data, double HBR, double RES_R,
                                    double HD, double RD, double snore);
}
