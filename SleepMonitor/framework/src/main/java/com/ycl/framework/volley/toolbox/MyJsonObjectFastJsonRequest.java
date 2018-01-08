package com.ycl.framework.volley.toolbox;

import com.ycl.framework.volley.AuthFailureError;
import com.ycl.framework.volley.NetworkResponse;
import com.ycl.framework.volley.ParseError;
import com.ycl.framework.volley.Response;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * JsonObjectRequest 支持Json 格式  on 2015/12/16.
 */
public class MyJsonObjectFastJsonRequest extends JsonRequest<String> {

    private Map<String, String> sendHeader = new HashMap<String, String>(1);

    public MyJsonObjectFastJsonRequest(String url, Map<String, Object> map, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(url, appendParameter(map), listener, errorListener);
    }


    /**
     * 这里的method必须是Method.POST，也就是必须带参数。
     * 如果不想带参数，可以用JsonObjectRequest，给它构造参数传null。GET方式请求。
     *
     * @param stringRequest 格式应该是 "key1=value1&key2=value2"
     */
    public MyJsonObjectFastJsonRequest(String url, String stringRequest,
                                       Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, stringRequest, listener, errorListener);
    }


    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return sendHeader;
    }

    public void setSendCookie(String cookie) {
        sendHeader.put("Cookie", cookie);
    }

    public static String appendParameter(Map<String, Object> map) {
        String params = "";
        if (map == null)
            return params;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            params += entry.getKey() + "=" + entry.getValue() + "&";
        }
        return params;
    }

}