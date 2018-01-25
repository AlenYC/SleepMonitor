package com.lzhz.lxh.sleepmonitor.tools.Net;

import android.content.Context;

import com.lzhz.lxh.sleepmonitor.tools.Tool;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by dk on 2016/5/31.
 */
public class NetUtil {
    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private static Request buildMultipartFormRequest(String url, NetParamas params, List<String> paths) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        LinkedHashMap<String, String> param = params.getParams();
        for (String key : param.keySet()) {
            builder.addFormDataPart(key, param.get(key));
        }
        if (paths != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < paths.size(); i++) {
                File file = new File(paths.get(i));
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + "image[" + i + "]" + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    public static void put(String msg, String url, NetParamas params, List<String> paths, NetCallBack callback, Context context) {
        try {
            Tool.showProgressDialog(msg, false, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        client.newCall(buildMultipartFormRequest(url, params, paths)).enqueue(callback);
    }

    public static void get(String url, NetParamas params, NetCallBack callback ) {
        StringBuilder builder = new StringBuilder(url.trim());
        HashMap<String, String> param = params.getParams();
        for (String key : param.keySet()) {
            builder.append(key);
            if (!key.equals(""))
                builder.append("=");
            builder.append(param.get(key)).append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(builder.toString()).build();
        client.newCall(request).enqueue(callback);
    }

    public static void get(String url, NetParamas params, final NetCallBack callback, String msg, Context context) {
        Tool.showProgressDialog(msg, false, context);
        StringBuilder builder = new StringBuilder(url.trim());
        LinkedHashMap<String, String> param = params.getParams();
        for (String key : param.keySet()) {
            builder.append(key)
                    .append("=").append(param.get(key))
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(builder.toString()).build();
        client.newCall(request).enqueue(callback);
    }

    public static void post(String url, NetParamas params, final NetCallBack callback, String msg, Context context) {
        Tool.showProgressDialog(msg, false, context);
        FormBody.Builder builder = new FormBody.Builder();
        LinkedHashMap<String, String> param = params.getParams();
        for (String key : param.keySet()) {
            builder.add(key, param.get(key));
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().post(builder.build()).url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void post(String url, NetParamas params, final NetCallBack callback) {
        FormBody.Builder builder = new FormBody.Builder();
        LinkedHashMap<String, String> param = params.getParams();
        for (String key : param.keySet()) {
            builder.add(key, param.get(key));
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().post(builder.build()).url(url).build();
        client.newCall(request).enqueue(callback);
    }


    public static void download(String url, NetCallBack callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
