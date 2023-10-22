package com.douyin.service;

import com.douyin.domain.ComponentTemplate;
import com.douyin.domain.ProductUpdateRule;
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
 * 查询商品发布规则
 * 根据店铺ID、类目等查询商品发布规则，如查询支持发货模式、发货时效、尺码模板配置规则、商品主图3:4配置规则等
 */
public class GetProductUpdateRule {

    public String pur(String accessToken, String apiKey, ProductUpdateRule productUpdateRule) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        // 准备接口参数
        JSONObject paramJson = new JSONObject();

        paramJson.put("category_id", 20172);
//        paramJson.put("standard_brand_id",productUpdateRule.getStandardBrandId());
//        paramJson.put("spu_id",productUpdateRule.getSpuId());


        JSONObject apiParams = new JSONObject();
        apiParams.put("access_token", accessToken);
        apiParams.put("app_key", "");
        apiParams.put("method", "product.getProductUpdateRule");
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
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String accessToken = "c38d6ce4-0d63-43e7-af0e-1988539d244f";
        String apiKey = "bolin20230727";

        GetProductUpdateRule updateRule = new GetProductUpdateRule();
        ProductUpdateRule rule = new ProductUpdateRule();
        String response = updateRule.pur(accessToken, apiKey, rule);
        System.out.println(response);
    }
}
