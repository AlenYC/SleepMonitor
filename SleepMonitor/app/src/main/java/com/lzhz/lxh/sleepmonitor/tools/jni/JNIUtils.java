package com.lzhz.lxh.sleepmonitor.tools.jni;

/**
 * 作者：lxh on 2018-02-02:09:43
 * 邮箱：15911638454@163.com
 */

/**
 * Created by wuzhenlin on 2018/2/28.
 */

public class JNIUtils {

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 输入：
     * @param raw_data 是从单片机输出的原始数据，输入数据最好是30秒一段， 数组长度 30000
     *
     * 输出：
     * @param hbrRes 输出心率值和呼吸速率  数组长度 2
     *              hbrRes[0]: HBR 心率值，单位（次/每分钟）
     *              hbrRes[1]: RES_R 呼吸速率，单位（次/每分钟）
     * @param HD  输出心跳数据  数组长度 300
     * @param RD  输出呼吸数据  数组长度 300
     *
     * @param snore 输出鼾声数据   数组长度 3000
     */
    public static native void detect0131(double raw_data[], double hbrRes[],double HD[],double RD[], double snore[]);
}
