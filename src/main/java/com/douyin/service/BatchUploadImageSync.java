package com.douyin.service;

import com.douyin.utils.TranslationSorting;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BatchUploadImageSync {

    public LinkedList uploadImage(String accessToken, String apiKey, List imageUrls) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;


        // 构建 image_list 参数
        Map<String, Object> imageListArray = new LinkedHashMap<>();

        LinkedList<String> materialIds = new LinkedList<>();

        for (int i = 0; i < imageUrls.size(); i++) {

            String imageUrl = (java.lang.String) imageUrls.get(i);
            // 获取最后一个斜杠的索引位置
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            int lastDotIndex = imageUrl.lastIndexOf('.');

            String imageName = "";
            if (lastSlashIndex != -1 && lastDotIndex != -1 && lastDotIndex > lastSlashIndex) {
                imageName = imageUrl.substring(lastSlashIndex + 1, lastDotIndex);
//                requestIds.add(imageName);
//                System.out.println("截取的值：" + imageName);
            } else {
                System.out.println("未找到斜杠和点号之间的值。");
            }


            // 截取最后一个斜杠后面的值
            String imageNameAndDot = imageUrl.substring(lastSlashIndex + 1);

            Map<String, Object> image1 = new LinkedHashMap<>();
            image1.put("folder_id", "0");
            image1.put("material_type", "photo");
            image1.put("name", imageNameAndDot);
            image1.put("need_distinct", true);
            image1.put("request_id", imageName);
            image1.put("url", imageUrl);
            imageListArray.put("materials", image1);

            Map<String, Object> apiParams = new LinkedHashMap<>();

            //准备接口参数
            apiParams.put("access_token", accessToken);
            apiParams.put("method", "material.batchUploadImageSync");
            apiParams.put("param_json", imageListArray);
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

                String materialId = parseBatchUploadImageSync(result.toString(), imageName);
                if (!materialId.isEmpty()) {
                    materialIds.add(materialId);
                } else {
                    return null;
                }

            } catch (IOException e) {
                System.out.println("BatchUploadImageSync异常：" + e.getMessage());
                return null;
            }
        }

        return materialIds;
    }

    private String parseBatchUploadImageSync(String batchUploadImageSyncJson, String requestId) {

        // 解析JSON数据
//        JSONObject jsonObject = new JSONObject(batchUploadImageSyncJson);
//        JSONObject successMap = jsonObject.getJSONObject("data").getJSONObject("success_map");
//        JSONObject targetObject = successMap.getJSONObject(requestId);
////        JSONObject rootKey = targetObject.getJSONObject(requestId);
//        String materialId = targetObject.getString("MaterialId");
//        return materialId;

        try {
            // 解析JSON数据
            JSONObject jsonObject = new JSONObject(batchUploadImageSyncJson);
            JSONObject dataObject = jsonObject.optJSONObject("data");
            if (dataObject != null) {
                JSONObject successMap = dataObject.optJSONObject("success_map");
                if (successMap != null) {
                    JSONObject targetObject = successMap.optJSONObject(requestId);
                    if (targetObject != null) {
                        String materialId = targetObject.optString("MaterialId");
                        return materialId;
                    }
                }
            }
            // 如果无法找到相应的键或值，返回一个默认值或处理方式
            return null;
        } catch (JSONException e) {
            System.out.println("parseBatchUploadImageSync异常：" + e.getMessage());
            // 在解析发生异常时，可以返回一个默认值或处理方式
            return "图片解析失败";
        }
    }

    public static void main(String[] args) {
        String accessToken = "71f852e4-5a8f-44f7-b466-415a0c05d550";
        String apiKey = "bolin20230727";
        List imageUrls = new ArrayList();

        BatchUploadImageSync batchUploadImageSync = new BatchUploadImageSync();
        List requestIds = batchUploadImageSync.uploadImage(accessToken, apiKey, imageUrls);
        System.out.println(requestIds);
    }
}
