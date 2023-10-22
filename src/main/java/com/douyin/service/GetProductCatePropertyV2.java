package com.douyin.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GetProductCatePropertyV2 {
    public String cp(String accessToken, String apiKey, int cateId) {


        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        // 准备接口参数
        JSONObject paramJson = new JSONObject();
        paramJson.put("category_leaf_id", cateId);//是否必须:是；示例值：1342353245；参数描述：叶子类目id 1、传category_leaf_id ，则不需要传first_cid、second_cid、third_cid这三个字段 2、如果没传category_leaf_id，走之前的逻辑，需要传first_cid、second_cid、third_cid这三个字段

        JSONObject apiParams = new JSONObject();
        apiParams.put("access_token", accessToken);
        apiParams.put("method", "product.getCatePropertyV2");
        apiParams.put("param_json", paramJson);
        apiParams.put("timestamp", System.currentTimeMillis() / 1000L);
        apiParams.put("v", "2");

        // 发起 POST 请求
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        try {
            StringEntity entity = new StringEntity(apiParams.toString(), StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String accessToken = "71f852e4-5a8f-44f7-b466-415a0c05d550"; // 替换成你的Access Token
        int cateId = 20172; // 替换成你要查询的商品类目ID，20172为T恤
        String apiKey = "bolin20230727";
        GetProductCatePropertyV2 catePropertyV2 = new GetProductCatePropertyV2();
        String response = catePropertyV2.cp(accessToken, apiKey, cateId);
        System.out.println(response);

    }

}
