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

public class GetProductShopCategory {

    public String productShopCategory(String accessToken, String apiKey, int cateId) {

        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        // 准备接口参数
        JSONObject paramJson = new JSONObject();
        //参数描述
        // 父类目id，根据父id可以获取子类目。首次请求传值为0 可以获取所有一级类目。循环调用接口获取最小层级类目id，
        // 根据响应参数判断is_leaf=true或false。is_leaf=true表示是叶子节点，获取最小层级类目id，
        // is_leaf=false表示不是子节点，请求参数cid=上一次响应参数id，直到获取最小层级类目id。
        paramJson.put("cid", cateId);//是否必须:是；示例值：0；

        JSONObject apiParams = new JSONObject();
        apiParams.put("access_token", accessToken);
        apiParams.put("method", "shop.getShopCategory");
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
        String accessToken = "c38d6ce4-0d63-43e7-af0e-1988539d244f";
        String apiKey = "bolin20230727";
        int cateId = 0;
        GetProductShopCategory ShopCategory = new GetProductShopCategory();
        String category = ShopCategory.productShopCategory(accessToken, apiKey, cateId);
        System.out.println(category);
    }

}
