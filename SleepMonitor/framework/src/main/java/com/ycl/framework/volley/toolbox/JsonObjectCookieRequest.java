package com.ycl.framework.volley.toolbox;

import com.ycl.framework.utils.util.YisLoger;
import com.ycl.framework.volley.AuthFailureError;
import com.ycl.framework.volley.DefaultRetryPolicy;
import com.ycl.framework.volley.NetworkResponse;
import com.ycl.framework.volley.ParseError;
import com.ycl.framework.volley.Request;
import com.ycl.framework.volley.Response;
import com.ycl.framework.volley.RetryPolicy;
import com.ycl.framework.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonObjectRequest 支持Json 格式  on 2015/12/16.
 */
public class JsonObjectCookieRequest extends Request<JSONObject> {

    private String mHeader;
    public String cookieFromResponse;
    private Map<String, String> sendHeader = new HashMap<String, String>(1);
    private Response.Listener<JSONObject> mListener;
    private final String mRequestBody;

    /**
     * 这里的method必须是Method.POST，也就是必须带参数。
     * 如果不想带参数，可以用JsonObjectRequest，给它构造参数传null。GET方式请求。
     */
    public JsonObjectCookieRequest(String url, Map<String, Object> map,
                                   Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mRequestBody = MyJsonObjectRequest.appendParameter(map);
        mListener = listener;
    }


    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return super.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            mHeader = response.headers.toString();
            YisLoger.debug("response __   header __ " + "get headers in parseNetworkResponse " + response.headers.toString());
            //使用正则表达式从reponse的头中提取cookie内容的子串
            Pattern pattern = Pattern.compile("Set-Cookie.*?;");
            Matcher m = pattern.matcher(mHeader);
            if (m.find()) {
                cookieFromResponse = m.group();
                YisLoger.debug("cookie from server " + cookieFromResponse);
            }
            //去掉cookie末尾的分号
            cookieFromResponse = cookieFromResponse.substring(11, cookieFromResponse.length() - 1);
//            YisLoger.debug("LOG", "cookie substring " + cookieFromResponse);
            //将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("Cookie", cookieFromResponse);
//            YisLoger.debug("LOG", "jsonObject " + jsonObject.toString());
            return Response.success(jsonObject,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }


    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return sendHeader;
    }


    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, "utf-8");
            return null;
        }
    }

    //获取 request的 参数 body
    public String getmRequestBody() {
        return mRequestBody;
    }

}