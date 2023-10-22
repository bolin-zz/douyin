package com.douyin.service;

import com.douyin.action.ProductAdd;
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
 * /trade/TopSearchQueryPredict
 * 商品标题热搜词推荐
 * 商家发布商品时，根据上传主图进行热搜词推荐，帮助商家标题填写，提升商品曝光及GMV；
 * 接口申请条件：该能力仅限微应用场景开放，如您想作为微应用开发者对接，可联系您的类目运营。详见：https://op.jinritemai.com/docs/mona-docs/1186/2728
 */
public class TopSearchQueryPredict {

    public String queryPredict(String accessToken, String apiKey, long categoryId, String imageUrl) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        // 准备接口参数
        JSONObject paramJson = new JSONObject();

        paramJson.put("category_id",categoryId);
        paramJson.put("image_url",imageUrl);

        JSONObject apiParams = new JSONObject();
        apiParams.put("access_token", accessToken);
        apiParams.put("app_key", "");
        apiParams.put("method", "trade.TopSearchQueryPredict");
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
            System.out.println(result);
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String accessToken = "c38d6ce4-0d63-43e7-af0e-1988539d244f";
        String apiKey = "bolin20230727";
        long categoryId = 20172;
        String imageUrl = "http://e3h.i.ximgs.net/1/813781/20230716/20230716559585001_750.jpg";

        TopSearchQueryPredict queryPredict = new TopSearchQueryPredict();
        String req = queryPredict.queryPredict(accessToken, apiKey, categoryId, imageUrl);
        System.out.println(req);
    }

}
