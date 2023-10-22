package com.douyin.service;

import com.douyin.action.ProductAdd;
import com.douyin.domain.ComponentTemplate;
import com.douyin.domain.Product;
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

/**
 * 分页查询模板列表
 * 分页查询模板列表
 */
public class GetProductComponentTemplate {
    public String ct(String accessToken, String apiKey, ComponentTemplate componentTemplate) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        // 准备接口参数
        JSONObject paramJson = new JSONObject();
//        paramJson.put("template_type",);
//        paramJson.put("template_sub_type",);
//        paramJson.put("template_id",);

        paramJson.put("shareable",true);
        paramJson.put("page_num",0);
        paramJson.put("page_size",20);


        JSONObject apiParams = new JSONObject();
        apiParams.put("access_token", accessToken);
        apiParams.put("app_key", "");
        apiParams.put("method", "product.getComponentTemplate");
        apiParams.put("param_json", paramJson);
        apiParams.put("timestamp", System.currentTimeMillis() / 1000L);
        apiParams.put("v", "2");

        // 发起POST请求
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
//            System.out.println(result);
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String accessToken = "c38d6ce4-0d63-43e7-af0e-1988539d244f";
        String apiKey = "bolin20230727";

        GetProductComponentTemplate template = new GetProductComponentTemplate();
        ComponentTemplate componentTemplate = new ComponentTemplate();
        String response = template.ct(accessToken, apiKey, componentTemplate);
        System.out.println(response);
    }
}
