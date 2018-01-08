package com.ycl.framework.base;


import org.json.JSONObject;

import java.util.List;

/**
 * netWork 请求结果回调<br> (复杂json结构)
 */

public interface FrameNetworkResponse<T> {
    /**
     * 请求成功
     */
    void successResponse(T bean, List<T> datas, String result);

    /**
     * 请求失败
     */
    void failResponse(JSONObject result);


    /**
     * 请求结束
     */
    void endResponse();

}
