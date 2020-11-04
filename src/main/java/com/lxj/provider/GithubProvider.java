package com.lxj.provider;

import com.alibaba.fastjson.JSON;
import com.lxj.dto.AccessTokenDTO;
import com.lxj.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String token = response.body().string();
                String accessToken = token.split("&")[0].split("=")[1];
                return accessToken;
            }
        }
        return null;
    }

    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                GithubUser githubUser = JSON.parseObject(response.body().string(),GithubUser.class);
                return githubUser;
            }
        }
        return null;
    }

}
