package com.douyin.service;


import com.douyin.utils.TranslationSorting;
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
import java.util.*;

/**
 * /material/queryMaterialDetail
 * 根据素材id查素材详情
 */
public class QueryMaterialDetail {


    public LinkedList qmd(String accessToken, String apiKey, LinkedList materialIds) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        LinkedList<String> imageList = new LinkedList<>();
        // 准备接口参数
        Map<String, Object> paramJson = new LinkedHashMap<>();
        for (int i = 0; i < materialIds.size(); i++) {
            String materialId = (String) materialIds.get(i);
            paramJson.put("material_id", materialId);

            Map<String, Object> apiParams = new LinkedHashMap<>();

            //公共参数
            apiParams.put("access_token", accessToken);
            apiParams.put("method", "material.queryMaterialDetail");
            apiParams.put("param_json", paramJson);
            apiParams.put("timestamp", System.currentTimeMillis() / 1000L);
            apiParams.put("v", "2");

            //序列化参数
            String sortParamJson = TranslationSorting.marshal(apiParams);

            // 发起POST请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");

            try {
                StringEntity entity = new StringEntity(sortParamJson, StandardCharsets.UTF_8);
                httpPost.setEntity(entity);

                HttpResponse response = httpClient.execute(httpPost);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));

                StringBuilder result = new StringBuilder();
                List list = new ArrayList();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                //解析AuditStatus
                String json = parseData(result.toString(), materialId);
                if (json == "" || json == null) {
                    return null;
                }
                if (json != "" || json != null) {
                    // 解析JSON数据
                    JSONObject jsonObject = new JSONObject(json);

                    // 获取byte_url的值
                    String byteUrl = jsonObject.getString("byte_url");
                    imageList.add(byteUrl);
                }

            } catch (IOException e) {
                System.out.println("QueryMaterialDetail异常：" + e.getMessage());
            }
        }

        return imageList;
    }

    private String parseData(String queryMaterialDetailJson, String materialId) {

        // 解析JSON数据
        JSONObject jsonObject = new JSONObject(queryMaterialDetailJson);
        JSONObject successMap = jsonObject.getJSONObject("data").getJSONObject("material_info");

        // 获取AuditStatus的值
        int auditStatus = successMap.getInt("audit_status");

        if (auditStatus == 1 || auditStatus == 2) {
//            System.out.printf("图片未上传完成，请等待！");
            return "";
        }
        JSONObject json = new JSONObject();
        if (auditStatus == 3) {
            String material_id = successMap.getString("material_id");
            String materil_name = successMap.getString("materil_name");
            String byte_url = successMap.getString("byte_url");
            json.put("material_id", material_id);
            json.put("materil_name", materil_name);
            json.put("byte_url", byte_url);
            return json.toString();
        }
        // 输出结果
        System.out.println("AuditStatus for " + materialId + " is: " + auditStatus);
        return "";
    }

    public static void main(String[] args) {
        String accessToken = "71f852e4-5a8f-44f7-b466-415a0c05d550";
        String apiKey = "bolin20230727";
        LinkedList requestIds = new LinkedList();

        QueryMaterialDetail materialDetail = new QueryMaterialDetail();
        List prettifyPics = materialDetail.qmd(accessToken, apiKey, requestIds);
        System.out.println(prettifyPics);
    }
}
