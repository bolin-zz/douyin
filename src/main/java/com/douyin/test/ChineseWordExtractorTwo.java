package com.douyin.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseWordExtractorTwo {
    public static void main(String[] args) {
        String data = "{\n" +
                "\t\"code\": 10000,\n" +
                "\t\"data\": {\n" +
                "\t\t\"data\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 0,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [],\n" +
                "\t\t\t\t\"property_id\": 1687,\n" +
                "\t\t\t\t\"property_name\": \"品牌\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 0,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 0,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [],\n" +
                "\t\t\t\t\"property_id\": 3171,\n" +
                "\t\t\t\t\"property_name\": \"货号\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 0,\n" +
                "\t\t\t\t\"sequence\": 347,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"text\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"青春流行\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"27385\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27385\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"时尚都市\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"16688\",\n" +
                "\t\t\t\t\t\t\"value_id\": 16688\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"商务绅士\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"15354\",\n" +
                "\t\t\t\t\t\t\"value_id\": 15354\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1209,\n" +
                "\t\t\t\t\"property_name\": \"基础风格\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 3,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"日常\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1000,\n" +
                "\t\t\t\t\t\t\"value\": \"17183\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17183\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"商务\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"33946\",\n" +
                "\t\t\t\t\t\t\"value_id\": 33946\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"沙滩\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1100,\n" +
                "\t\t\t\t\t\t\"value\": \"19440\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19440\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"旅游\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"34411\",\n" +
                "\t\t\t\t\t\t\"value_id\": 34411\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"居家\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"7447\",\n" +
                "\t\t\t\t\t\t\"value_id\": 7447\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"雨天\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"13006\",\n" +
                "\t\t\t\t\t\t\"value_id\": 13006\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"运动\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"32603\",\n" +
                "\t\t\t\t\t\t\"value_id\": 32603\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"宴会\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"15737\",\n" +
                "\t\t\t\t\t\t\"value_id\": 15737\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"婚礼\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"26464\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26464\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"上班\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"955\",\n" +
                "\t\t\t\t\t\t\"value_id\": 955\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"舞蹈\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1200,\n" +
                "\t\t\t\t\t\t\"value\": \"31188\",\n" +
                "\t\t\t\t\t\t\"value_id\": 31188\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"校园\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"19272\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19272\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1714,\n" +
                "\t\t\t\t\"property_name\": \"适用场景\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 2,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"标准\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"10498\",\n" +
                "\t\t\t\t\t\t\"value_id\": 10498\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"宽松型\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"56134\",\n" +
                "\t\t\t\t\t\t\"value_id\": 56134\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"修身型\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"16252\",\n" +
                "\t\t\t\t\t\t\"value_id\": 16252\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"紧身型\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"196299\",\n" +
                "\t\t\t\t\t\t\"value_id\": 196299\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"特宽型\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"56135\",\n" +
                "\t\t\t\t\t\t\"value_id\": 56135\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 3059,\n" +
                "\t\t\t\t\"property_name\": \"服装版型\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 0,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"冬季\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"27291\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27291\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"秋季\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"22923\",\n" +
                "\t\t\t\t\t\t\"value_id\": 22923\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"夏季\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"12493\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12493\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"春季\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"38906\",\n" +
                "\t\t\t\t\t\t\"value_id\": 38906\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"春夏\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"9218\",\n" +
                "\t\t\t\t\t\t\"value_id\": 9218\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"春秋\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"26484\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26484\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"秋冬\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"33514\",\n" +
                "\t\t\t\t\t\t\"value_id\": 33514\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"四季通用\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"183496\",\n" +
                "\t\t\t\t\t\t\"value_id\": 183496\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1343,\n" +
                "\t\t\t\t\"property_name\": \"适用季节\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 351,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"老年\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"36165\",\n" +
                "\t\t\t\t\t\t\"value_id\": 36165\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"中年\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"21189\",\n" +
                "\t\t\t\t\t\t\"value_id\": 21189\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"青少年\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"19942\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19942\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"青年\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"27586\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27586\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"儿童\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"25317\",\n" +
                "\t\t\t\t\t\t\"value_id\": 25317\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"中老年\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"20287\",\n" +
                "\t\t\t\t\t\t\"value_id\": 20287\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"学生\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"8284\",\n" +
                "\t\t\t\t\t\t\"value_id\": 8284\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"情侣\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"14080\",\n" +
                "\t\t\t\t\t\t\"value_id\": 14080\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"通用\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1000,\n" +
                "\t\t\t\t\t\t\"value\": \"1991\",\n" +
                "\t\t\t\t\t\t\"value_id\": 1991\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"亲子\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"18215\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18215\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 2199,\n" +
                "\t\t\t\t\"property_name\": \"适用对象\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 2,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"长袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"30229\",\n" +
                "\t\t\t\t\t\t\"value_id\": 30229\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"短袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"6176\",\n" +
                "\t\t\t\t\t\t\"value_id\": 6176\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"无袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"27420\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27420\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"五分袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"4564\",\n" +
                "\t\t\t\t\t\t\"value_id\": 4564\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"七分袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"36625\",\n" +
                "\t\t\t\t\t\t\"value_id\": 36625\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 714,\n" +
                "\t\t\t\t\"property_name\": \"袖长\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 348,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 0,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"桑蚕丝\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3700,\n" +
                "\t\t\t\t\t\t\"value\": \"28315\",\n" +
                "\t\t\t\t\t\t\"value_id\": 28315\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"柞蚕丝\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3000,\n" +
                "\t\t\t\t\t\t\"value\": \"14931\",\n" +
                "\t\t\t\t\t\t\"value_id\": 14931\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"棉\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3800,\n" +
                "\t\t\t\t\t\t\"value\": \"19165\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19165\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"亚麻\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1400,\n" +
                "\t\t\t\t\t\t\"value\": \"290\",\n" +
                "\t\t\t\t\t\t\"value_id\": 290\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"锦纶\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3200,\n" +
                "\t\t\t\t\t\t\"value\": \"22672\",\n" +
                "\t\t\t\t\t\t\"value_id\": 22672\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"腈纶\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4200,\n" +
                "\t\t\t\t\t\t\"value\": \"1743\",\n" +
                "\t\t\t\t\t\t\"value_id\": 1743\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"氨纶\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2900,\n" +
                "\t\t\t\t\t\t\"value\": \"27486\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27486\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"铜氨纤维\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5300,\n" +
                "\t\t\t\t\t\t\"value\": \"9599\",\n" +
                "\t\t\t\t\t\t\"value_id\": 9599\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"醋酯纤维\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3300,\n" +
                "\t\t\t\t\t\t\"value\": \"7384\",\n" +
                "\t\t\t\t\t\t\"value_id\": 7384\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"粘胶纤维\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3500,\n" +
                "\t\t\t\t\t\t\"value\": \"26402\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26402\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"丙纶\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2200,\n" +
                "\t\t\t\t\t\t\"value\": \"17607\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17607\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"维纶\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1200,\n" +
                "\t\t\t\t\t\t\"value\": \"11945\",\n" +
                "\t\t\t\t\t\t\"value_id\": 11945\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"莫代尔纤维\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1000,\n" +
                "\t\t\t\t\t\t\"value\": \"28544\",\n" +
                "\t\t\t\t\t\t\"value_id\": 28544\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"莱赛尔纤维\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"3434\",\n" +
                "\t\t\t\t\t\t\"value_id\": 3434\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"山羊绒\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1600,\n" +
                "\t\t\t\t\t\t\"value\": \"26913\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26913\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"羊羔毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1300,\n" +
                "\t\t\t\t\t\t\"value\": \"27126\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27126\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"马海毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2300,\n" +
                "\t\t\t\t\t\t\"value\": \"33926\",\n" +
                "\t\t\t\t\t\t\"value_id\": 33926\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"绵羊毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3400,\n" +
                "\t\t\t\t\t\t\"value\": \"34940\",\n" +
                "\t\t\t\t\t\t\"value_id\": 34940\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"羊驼毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1700,\n" +
                "\t\t\t\t\t\t\"value\": \"21991\",\n" +
                "\t\t\t\t\t\t\"value_id\": 21991\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"安哥拉兔毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"13330\",\n" +
                "\t\t\t\t\t\t\"value_id\": 13330\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"兔毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2100,\n" +
                "\t\t\t\t\t\t\"value\": \"6946\",\n" +
                "\t\t\t\t\t\t\"value_id\": 6946\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"骆驼毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"37783\",\n" +
                "\t\t\t\t\t\t\"value_id\": 37783\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"骆驼绒\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2600,\n" +
                "\t\t\t\t\t\t\"value\": \"32029\",\n" +
                "\t\t\t\t\t\t\"value_id\": 32029\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"原驼毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"8794\",\n" +
                "\t\t\t\t\t\t\"value_id\": 8794\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"美洲驼毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2400,\n" +
                "\t\t\t\t\t\t\"value\": \"19072\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19072\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"骆马毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3100,\n" +
                "\t\t\t\t\t\t\"value\": \"5589\",\n" +
                "\t\t\t\t\t\t\"value_id\": 5589\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"牦牛毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"17304\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17304\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"牦牛绒\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2700,\n" +
                "\t\t\t\t\t\t\"value\": \"2791\",\n" +
                "\t\t\t\t\t\t\"value_id\": 2791\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"牛毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2800,\n" +
                "\t\t\t\t\t\t\"value\": \"7221\",\n" +
                "\t\t\t\t\t\t\"value_id\": 7221\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"鹿毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1100,\n" +
                "\t\t\t\t\t\t\"value\": \"18433\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18433\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"马毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"28452\",\n" +
                "\t\t\t\t\t\t\"value_id\": 28452\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"野兔毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1900,\n" +
                "\t\t\t\t\t\t\"value\": \"29399\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29399\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"水獭毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2000,\n" +
                "\t\t\t\t\t\t\"value\": \"10900\",\n" +
                "\t\t\t\t\t\t\"value_id\": 10900\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"海豹毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4600,\n" +
                "\t\t\t\t\t\t\"value\": \"34113\",\n" +
                "\t\t\t\t\t\t\"value_id\": 34113\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"麝鼠毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4100,\n" +
                "\t\t\t\t\t\t\"value\": \"34800\",\n" +
                "\t\t\t\t\t\t\"value_id\": 34800\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"驯鹿毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3600,\n" +
                "\t\t\t\t\t\t\"value\": \"479\",\n" +
                "\t\t\t\t\t\t\"value_id\": 479\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"水貂毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5200,\n" +
                "\t\t\t\t\t\t\"value\": \"22245\",\n" +
                "\t\t\t\t\t\t\"value_id\": 22245\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"貂毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"17454\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17454\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"鼬鼠毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5100,\n" +
                "\t\t\t\t\t\t\"value\": \"33465\",\n" +
                "\t\t\t\t\t\t\"value_id\": 33465\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"银鼠毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2500,\n" +
                "\t\t\t\t\t\t\"value\": \"12694\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12694\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"北极狐毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1800,\n" +
                "\t\t\t\t\t\t\"value\": \"2668\",\n" +
                "\t\t\t\t\t\t\"value_id\": 2668\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"马鬃尾毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3900,\n" +
                "\t\t\t\t\t\t\"value\": \"37548\",\n" +
                "\t\t\t\t\t\t\"value_id\": 37548\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"马正身毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4000,\n" +
                "\t\t\t\t\t\t\"value\": \"17713\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17713\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"貉子毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"16784\",\n" +
                "\t\t\t\t\t\t\"value_id\": 16784\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"狐狸毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"1178\",\n" +
                "\t\t\t\t\t\t\"value_id\": 1178\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"鸵鸟毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5400,\n" +
                "\t\t\t\t\t\t\"value\": \"474\",\n" +
                "\t\t\t\t\t\t\"value_id\": 474\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"PU\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4300,\n" +
                "\t\t\t\t\t\t\"value\": \"38562\",\n" +
                "\t\t\t\t\t\t\"value_id\": 38562\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"粘纤\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4400,\n" +
                "\t\t\t\t\t\t\"value\": \"37780\",\n" +
                "\t\t\t\t\t\t\"value_id\": 37780\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"再生纤维素纤维\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4500,\n" +
                "\t\t\t\t\t\t\"value\": \"27113\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27113\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"莫代尔\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4800,\n" +
                "\t\t\t\t\t\t\"value\": \"14929\",\n" +
                "\t\t\t\t\t\t\"value_id\": 14929\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"棉纶\",\n" +
                "\t\t\t\t\t\t\"sequence\": 4900,\n" +
                "\t\t\t\t\t\t\"value\": \"54691\",\n" +
                "\t\t\t\t\t\t\"value_id\": 54691\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"麂皮\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5000,\n" +
                "\t\t\t\t\t\t\"value\": \"29291\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29291\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"聚酯纤维（涤纶）\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5500,\n" +
                "\t\t\t\t\t\t\"value\": \"54740\",\n" +
                "\t\t\t\t\t\t\"value_id\": 54740\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"尼龙（锦纶）\",\n" +
                "\t\t\t\t\t\t\"sequence\": 5600,\n" +
                "\t\t\t\t\t\t\"value\": \"168796\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168796\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 785,\n" +
                "\t\t\t\t\"property_name\": \"面料材质\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 1,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"multi_select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"高领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"16622\",\n" +
                "\t\t\t\t\t\t\"value_id\": 16622\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"圆领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"9896\",\n" +
                "\t\t\t\t\t\t\"value_id\": 9896\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"连帽\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"35801\",\n" +
                "\t\t\t\t\t\t\"value_id\": 35801\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"衬衫领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"18230\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18230\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"翻领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"30551\",\n" +
                "\t\t\t\t\t\t\"value_id\": 30551\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"堆堆领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"862\",\n" +
                "\t\t\t\t\t\t\"value_id\": 862\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"立领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"10565\",\n" +
                "\t\t\t\t\t\t\"value_id\": 10565\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"V型领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1000,\n" +
                "\t\t\t\t\t\t\"value\": \"196268\",\n" +
                "\t\t\t\t\t\t\"value_id\": 196268\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"门筒领（亨利领）\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1100,\n" +
                "\t\t\t\t\t\t\"value\": \"11588\",\n" +
                "\t\t\t\t\t\t\"value_id\": 11588\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1829,\n" +
                "\t\t\t\t\"property_name\": \"领型\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 8,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 1,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"超薄\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"19981\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19981\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"薄款\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"38566\",\n" +
                "\t\t\t\t\t\t\"value_id\": 38566\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"加厚款\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"185297\",\n" +
                "\t\t\t\t\t\t\"value_id\": 185297\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"常规款\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"17314\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17314\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 241,\n" +
                "\t\t\t\t\"property_name\": \"厚度\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 0,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"植绒\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"8647\",\n" +
                "\t\t\t\t\t\t\"value_id\": 8647\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"热转印\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"13737\",\n" +
                "\t\t\t\t\t\t\"value_id\": 13737\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"吊染\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1000,\n" +
                "\t\t\t\t\t\t\"value\": \"13327\",\n" +
                "\t\t\t\t\t\t\"value_id\": 13327\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"扎染\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"38542\",\n" +
                "\t\t\t\t\t\t\"value_id\": 38542\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"烫钻\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"29817\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29817\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"免烫处理\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1500,\n" +
                "\t\t\t\t\t\t\"value\": \"7593\",\n" +
                "\t\t\t\t\t\t\"value_id\": 7593\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"褶皱\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1300,\n" +
                "\t\t\t\t\t\t\"value\": \"29155\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29155\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"泼墨\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1100,\n" +
                "\t\t\t\t\t\t\"value\": \"14987\",\n" +
                "\t\t\t\t\t\t\"value_id\": 14987\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"烂花\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1600,\n" +
                "\t\t\t\t\t\t\"value\": \"17241\",\n" +
                "\t\t\t\t\t\t\"value_id\": 17241\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"磨边\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"6096\",\n" +
                "\t\t\t\t\t\t\"value_id\": 6096\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"做旧\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"35885\",\n" +
                "\t\t\t\t\t\t\"value_id\": 35885\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"蜡染\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"19998\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19998\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"破洞\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1700,\n" +
                "\t\t\t\t\t\t\"value\": \"12322\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12322\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"磨毛\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1400,\n" +
                "\t\t\t\t\t\t\"value\": \"5873\",\n" +
                "\t\t\t\t\t\t\"value_id\": 5873\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"水洗\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1200,\n" +
                "\t\t\t\t\t\t\"value\": \"29426\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29426\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"亮片\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"12344\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12344\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"印花\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"11081\",\n" +
                "\t\t\t\t\t\t\"value_id\": 11081\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1825,\n" +
                "\t\t\t\t\"property_name\": \"服饰工艺\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 1,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 0,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"29%及以下\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"64324\",\n" +
                "\t\t\t\t\t\t\"value_id\": 64324\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"95%及以上\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"14098\",\n" +
                "\t\t\t\t\t\t\"value_id\": 14098\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"29%-39%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"168365\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168365\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"39%-49%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"168366\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168366\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"49%-59%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"168367\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168367\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"59%-69%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"168368\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168368\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"69%-79%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"168369\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168369\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"79%-89%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"168370\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168370\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"89%-95%(含)\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"168371\",\n" +
                "\t\t\t\t\t\t\"value_id\": 168371\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1467,\n" +
                "\t\t\t\t\"property_name\": \"面料材质成分含量\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 1,\n" +
                "\t\t\t\t\"sequence\": 346,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"破洞\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"12322\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12322\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"不对称\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"15821\",\n" +
                "\t\t\t\t\t\t\"value_id\": 15821\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"贴布\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"38077\",\n" +
                "\t\t\t\t\t\t\"value_id\": 38077\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"烫金\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"13012\",\n" +
                "\t\t\t\t\t\t\"value_id\": 13012\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"金属装饰\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"24221\",\n" +
                "\t\t\t\t\t\t\"value_id\": 24221\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"异色车线\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"8221\",\n" +
                "\t\t\t\t\t\t\"value_id\": 8221\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"罗纹装饰\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1000,\n" +
                "\t\t\t\t\t\t\"value\": \"22599\",\n" +
                "\t\t\t\t\t\t\"value_id\": 22599\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"多口袋\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1200,\n" +
                "\t\t\t\t\t\t\"value\": \"7599\",\n" +
                "\t\t\t\t\t\t\"value_id\": 7599\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"异色包边\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1300,\n" +
                "\t\t\t\t\t\t\"value\": \"5166\",\n" +
                "\t\t\t\t\t\t\"value_id\": 5166\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"异色门襟\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1400,\n" +
                "\t\t\t\t\t\t\"value\": \"16912\",\n" +
                "\t\t\t\t\t\t\"value_id\": 16912\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"双层领\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1500,\n" +
                "\t\t\t\t\t\t\"value\": \"27427\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27427\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"亮片\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1600,\n" +
                "\t\t\t\t\t\t\"value\": \"12344\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12344\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"烫钻\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1700,\n" +
                "\t\t\t\t\t\t\"value\": \"29817\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29817\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"补丁\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1800,\n" +
                "\t\t\t\t\t\t\"value\": \"18527\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18527\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"流苏\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1900,\n" +
                "\t\t\t\t\t\t\"value\": \"26394\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26394\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"徽章\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2100,\n" +
                "\t\t\t\t\t\t\"value\": \"35659\",\n" +
                "\t\t\t\t\t\t\"value_id\": 35659\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"印花\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2200,\n" +
                "\t\t\t\t\t\t\"value\": \"11081\",\n" +
                "\t\t\t\t\t\t\"value_id\": 11081\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"毛边\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2300,\n" +
                "\t\t\t\t\t\t\"value\": \"11701\",\n" +
                "\t\t\t\t\t\t\"value_id\": 11701\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"撞色\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2400,\n" +
                "\t\t\t\t\t\t\"value\": \"27300\",\n" +
                "\t\t\t\t\t\t\"value_id\": 27300\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"提花\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2500,\n" +
                "\t\t\t\t\t\t\"value\": \"30465\",\n" +
                "\t\t\t\t\t\t\"value_id\": 30465\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"口袋\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2600,\n" +
                "\t\t\t\t\t\t\"value\": \"9142\",\n" +
                "\t\t\t\t\t\t\"value_id\": 9142\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"绣标\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2700,\n" +
                "\t\t\t\t\t\t\"value\": \"23633\",\n" +
                "\t\t\t\t\t\t\"value_id\": 23633\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"镂空\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3000,\n" +
                "\t\t\t\t\t\t\"value\": \"4404\",\n" +
                "\t\t\t\t\t\t\"value_id\": 4404\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"亮面\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3100,\n" +
                "\t\t\t\t\t\t\"value\": \"37317\",\n" +
                "\t\t\t\t\t\t\"value_id\": 37317\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"开衩\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3300,\n" +
                "\t\t\t\t\t\t\"value\": \"32466\",\n" +
                "\t\t\t\t\t\t\"value_id\": 32466\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"纽扣\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3400,\n" +
                "\t\t\t\t\t\t\"value\": \"12409\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12409\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"刺绣/绣花\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3500,\n" +
                "\t\t\t\t\t\t\"value\": \"4231\",\n" +
                "\t\t\t\t\t\t\"value_id\": 4231\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"拼接\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3600,\n" +
                "\t\t\t\t\t\t\"value\": \"33150\",\n" +
                "\t\t\t\t\t\t\"value_id\": 33150\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"拉链\",\n" +
                "\t\t\t\t\t\t\"sequence\": 3700,\n" +
                "\t\t\t\t\t\t\"value\": \"36702\",\n" +
                "\t\t\t\t\t\t\"value_id\": 36702\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1766,\n" +
                "\t\t\t\t\"property_name\": \"流行元素\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 0,\n" +
                "\t\t\t\t\"sequence\": 0,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 0,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [],\n" +
                "\t\t\t\t\"property_id\": 680,\n" +
                "\t\t\t\t\"property_name\": \"上市时间\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 0,\n" +
                "\t\t\t\t\"sequence\": 353,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"text\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"常规\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"26705\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26705\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"插肩袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"10665\",\n" +
                "\t\t\t\t\t\t\"value_id\": 10665\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"蝙蝠袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 300,\n" +
                "\t\t\t\t\t\t\"value\": \"1594\",\n" +
                "\t\t\t\t\t\t\"value_id\": 1594\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"落肩袖\",\n" +
                "\t\t\t\t\t\t\"sequence\": 100,\n" +
                "\t\t\t\t\t\t\"value\": \"15091\",\n" +
                "\t\t\t\t\t\t\"value_id\": 15091\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 3035,\n" +
                "\t\t\t\t\"property_name\": \"袖型\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 0,\n" +
                "\t\t\t\t\"sequence\": 356,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 0,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"小清新\",\n" +
                "\t\t\t\t\t\t\"sequence\": 800,\n" +
                "\t\t\t\t\t\t\"value\": \"28690\",\n" +
                "\t\t\t\t\t\t\"value_id\": 28690\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"商务休闲\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"21348\",\n" +
                "\t\t\t\t\t\t\"value_id\": 21348\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"中国风\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1100,\n" +
                "\t\t\t\t\t\t\"value\": \"18839\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18839\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"青春活力\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1200,\n" +
                "\t\t\t\t\t\t\"value\": \"18803\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18803\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"朋克\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1300,\n" +
                "\t\t\t\t\t\t\"value\": \"19037\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19037\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"日系复古\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1400,\n" +
                "\t\t\t\t\t\t\"value\": \"11270\",\n" +
                "\t\t\t\t\t\t\"value_id\": 11270\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"英式学院\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1600,\n" +
                "\t\t\t\t\t\t\"value\": \"23883\",\n" +
                "\t\t\t\t\t\t\"value_id\": 23883\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"基础大众\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"1468\",\n" +
                "\t\t\t\t\t\t\"value_id\": 1468\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"精致韩风\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"10936\",\n" +
                "\t\t\t\t\t\t\"value_id\": 10936\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"欧美简约\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"4729\",\n" +
                "\t\t\t\t\t\t\"value_id\": 4729\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"美式休闲\",\n" +
                "\t\t\t\t\t\t\"sequence\": 600,\n" +
                "\t\t\t\t\t\t\"value\": \"37408\",\n" +
                "\t\t\t\t\t\t\"value_id\": 37408\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"嘻哈\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"16477\",\n" +
                "\t\t\t\t\t\t\"value_id\": 16477\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"商务风\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1800,\n" +
                "\t\t\t\t\t\t\"value\": \"184591\",\n" +
                "\t\t\t\t\t\t\"value_id\": 184591\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"甜美风\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1700,\n" +
                "\t\t\t\t\t\t\"value\": \"192481\",\n" +
                "\t\t\t\t\t\t\"value_id\": 192481\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 631,\n" +
                "\t\t\t\t\"property_name\": \"细分风格\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 0,\n" +
                "\t\t\t\t\"sequence\": 359,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"category_id\": 20172,\n" +
                "\t\t\t\t\"cid\": 20172,\n" +
                "\t\t\t\t\"diy_type\": 1,\n" +
                "\t\t\t\t\"important_type\": 0,\n" +
                "\t\t\t\t\"multi_select_max\": 5,\n" +
                "\t\t\t\t\"options\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"人物\",\n" +
                "\t\t\t\t\t\t\"sequence\": 200,\n" +
                "\t\t\t\t\t\t\"value\": \"12451\",\n" +
                "\t\t\t\t\t\t\"value_id\": 12451\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"圆点\",\n" +
                "\t\t\t\t\t\t\"sequence\": 400,\n" +
                "\t\t\t\t\t\t\"value\": \"14803\",\n" +
                "\t\t\t\t\t\t\"value_id\": 14803\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"迷彩\",\n" +
                "\t\t\t\t\t\t\"sequence\": 500,\n" +
                "\t\t\t\t\t\t\"value\": \"23066\",\n" +
                "\t\t\t\t\t\t\"value_id\": 23066\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"渐变\",\n" +
                "\t\t\t\t\t\t\"sequence\": 700,\n" +
                "\t\t\t\t\t\t\"value\": \"19918\",\n" +
                "\t\t\t\t\t\t\"value_id\": 19918\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"笑脸\",\n" +
                "\t\t\t\t\t\t\"sequence\": 900,\n" +
                "\t\t\t\t\t\t\"value\": \"182879\",\n" +
                "\t\t\t\t\t\t\"value_id\": 182879\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"植物花卉\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1100,\n" +
                "\t\t\t\t\t\t\"value\": \"6993\",\n" +
                "\t\t\t\t\t\t\"value_id\": 6993\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"民族风花型\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1300,\n" +
                "\t\t\t\t\t\t\"value\": \"7944\",\n" +
                "\t\t\t\t\t\t\"value_id\": 7944\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"波点\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1400,\n" +
                "\t\t\t\t\t\t\"value\": \"28305\",\n" +
                "\t\t\t\t\t\t\"value_id\": 28305\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"千鸟格\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1500,\n" +
                "\t\t\t\t\t\t\"value\": \"6795\",\n" +
                "\t\t\t\t\t\t\"value_id\": 6795\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"碎花\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1600,\n" +
                "\t\t\t\t\t\t\"value\": \"26315\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26315\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"几何图案\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1700,\n" +
                "\t\t\t\t\t\t\"value\": \"1635\",\n" +
                "\t\t\t\t\t\t\"value_id\": 1635\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"3D效果\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1800,\n" +
                "\t\t\t\t\t\t\"value\": \"31967\",\n" +
                "\t\t\t\t\t\t\"value_id\": 31967\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"纯色\",\n" +
                "\t\t\t\t\t\t\"sequence\": 1900,\n" +
                "\t\t\t\t\t\t\"value\": \"10089\",\n" +
                "\t\t\t\t\t\t\"value_id\": 10089\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"人字纹\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2100,\n" +
                "\t\t\t\t\t\t\"value\": \"18001\",\n" +
                "\t\t\t\t\t\t\"value_id\": 18001\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"骷髅头\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2200,\n" +
                "\t\t\t\t\t\t\"value\": \"23593\",\n" +
                "\t\t\t\t\t\t\"value_id\": 23593\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"卡通动漫\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2300,\n" +
                "\t\t\t\t\t\t\"value\": \"29635\",\n" +
                "\t\t\t\t\t\t\"value_id\": 29635\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"name\": \"格子\",\n" +
                "\t\t\t\t\t\t\"sequence\": 2400,\n" +
                "\t\t\t\t\t\t\"value\": \"26327\",\n" +
                "\t\t\t\t\t\t\"value_id\": 26327\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"property_id\": 1869,\n" +
                "\t\t\t\t\"property_name\": \"图案\",\n" +
                "\t\t\t\t\"property_type\": 0,\n" +
                "\t\t\t\t\"relation_id\": 0,\n" +
                "\t\t\t\t\"required\": 0,\n" +
                "\t\t\t\t\"sequence\": 0,\n" +
                "\t\t\t\t\"status\": 0,\n" +
                "\t\t\t\t\"type\": \"select\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"tpl_type\": 2\n" +
                "\t},\n" +
                "\t\"log_id\": \"20230803234318C030AF345616C3E7CF1A\",\n" +
                "\t\"msg\": \"success\",\n" +
                "\t\"sub_code\": \"\",\n" +
                "\t\"sub_msg\": \"\"\n" +
                "}";
        String chineseWords = extractChineseWords(data);
        System.out.println(chineseWords);
    }

    public static String extractChineseWords(String input) {
        String regex = "[\\u4e00-\\u9fa5]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        StringBuilder chineseWords = new StringBuilder();
        while (matcher.find()) {
            chineseWords.append(matcher.group()).append(" ");
        }
        return chineseWords.toString().trim();
    }
}
