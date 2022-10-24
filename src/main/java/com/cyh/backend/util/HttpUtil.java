package com.cyh.backend.util;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpUtil {

    public static JSONObject Post(String url, JSONObject jsonObject) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject),MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if(response.isSuccessful())
            return JSONObject.parseObject(response.body().string());
        else
            return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(HttpUtil.Post("http://localhost:18088/mssg2/order/getSealByGrantCodeV2", null));
    }

}
