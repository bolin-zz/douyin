package com.douyin.action;

import com.douyin.domain.Product;
import com.douyin.service.BatchUploadImageSync;
import com.douyin.service.QueryMaterialDetail;
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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.douyin.utils.StringUtils.convertToList;


/**
 * T恤的参数
 */
public class ProductAdd {


    public String dy(String accessToken, String apiKey, Product product) {

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = "http://bhtsp.dongmingwangluo.com/api/data?apiKey=" + apiKey;

        // 准备接口参数
        JSONObject paramJson = new JSONObject();

//        paramJson.put("outer_product_id", product.getOuterProductId());//是否必须:否；示例值：232334；参数描述：推荐使用，外部商家编码，支持字符串。超市小时达场景不推荐使用
        paramJson.put("product_type", product.getProductType());//是否必须:是；示例值：0；参数描述：0-普通，3-虚拟，6玉石闪购，7云闪购
        paramJson.put("category_leaf_id", product.getCategoryLeafId());//是否必须:是；示例值：20000；参数描述：叶子类目ID通过/shop/getShopCategory接口获取
//        paramJson.put("product_format", "货号|8888^上市年份季节|2018年秋季");//是否必须:否；示例值："货号|8888^上市年份季节|2018年秋季"；参数描述：（已废弃，用新字段product_format_new）属性名称|属性值 之间用|分隔, 多组之间用^分开
        paramJson.put("name", product.getName());//是否必须:是；示例值：xxx补水液；参数描述：商品名称，最多60个字符(30个汉字)，不能含emoj表情
//        paramJson.put("recommend_remark", product.getRecommendRemark());//是否必须:否；示例值：这个商品很好啊；参数描述：商家推荐语，不能含emoj表情


        BatchUploadImageSync batchUploadImageSync = new BatchUploadImageSync();
        List<String> fileList = convertToList(product.getPic());
        LinkedList requestIds = batchUploadImageSync.uploadImage(accessToken, apiKey, fileList);
        QueryMaterialDetail materialDetail = new QueryMaterialDetail();
        LinkedList pics = materialDetail.qmd(accessToken, apiKey, requestIds);
        while (pics == null) {
            pics = materialDetail.qmd(accessToken, apiKey, requestIds);
        }
        if(!pics.isEmpty()){
            System.out.println("商品轮播图上传完成");
        }
        String images = pics.isEmpty() ? "" : (String) pics.stream().collect(Collectors.joining("|"));
        paramJson.put("pic", images);//是否必须:是；示例值：img_url1|img_url2|img_url3；参数描述：商品轮播图，多张图片用 \"|\" 分开，第一张图为主图，最多5张，至少600x600，大小不超过1M。


        List<String> descriptionList = convertToList(product.getDescription());
        LinkedList reqIds = batchUploadImageSync.uploadImage(accessToken, apiKey, descriptionList);
        if (reqIds.isEmpty()) {
            return null;
        }
        LinkedList descImages = materialDetail.qmd(accessToken, apiKey, reqIds);
        while (descImages == null) {
            descImages = materialDetail.qmd(accessToken, apiKey, reqIds);
        }
        if(!descImages.isEmpty()){
            System.out.println("商品描述图片上传完成");
        }
        String dpImages = descImages.isEmpty() ? "" : (String) descImages.stream().collect(Collectors.joining("|"));
        paramJson.put("description", dpImages);//是否必须:是；示例值：img_url1|img_url2|img_url3；参数描述：商品描述，目前只支持图片。多张图片用 \"|\" 分开。不能用其他网站的文本粘贴，这样会出现css样式不兼容。总图片数量不得超过50张。


        paramJson.put("pay_type", 1);//是否必须:否；示例值：1；参数描述：支付方式，0货到付款 1在线支付，2，货到付款+在线支付
//        paramJson.put("delivery_method", 7);//是否必须:否；示例值：7；参数描述：海南免税生效。 0:离岛免税、1:邮寄、2:离岛自提+邮寄
//        paramJson.put("cdf_category", "1");//是否必须:否；示例值：1；参数描述：海南免税：海关限购分类编码
        paramJson.put("reduce_type", 2);//是否必须:是；示例值：1；参数描述：1 减库存类型：1-拍下减库存 2-付款减库存
        paramJson.put("assoc_ids", "");//是否必须:否；示例值：1|2|3；参数描述：同店商品推荐：为空表示使用系统推荐；多个product_id使用“|”分开
        paramJson.put("freight_id", 0);//是否必须:是；示例值：0；参数描述：运费模板id，传0表示包邮，通过/freightTemplate/list接口获取
        paramJson.put("weight", 1000);//是否必须:否；示例值：1000；参数描述：重量
        paramJson.put("weight_unit", 1);//是否必须:否；示例值：1；参数描述：重量单位，0-kg, 1-g
        paramJson.put("delivery_delay_day", 2);//是否必须:否；示例值：2；参数描述：delivery_delay_day： 承诺发货时间，单位是天,不传则默认为2天。现货发货(presell_type=0)和阶梯发货模式(presell_type=2)时必填，支持传入9999 、1、 2 （分别表示当日发、次日发、48小时发），具体支持传入的参数范围/product/getProductUpdateRule
        paramJson.put("presell_type", 0);//是否必须:否；示例值：0；参数描述：发货模式，0-现货发货，1-预售发货，2-阶梯发货，默认0
//        paramJson.put("presell_delay",7);//是否必须:否；示例值：7；参数描述：全款预售模式时的发货时间/阶梯模式下阶梯发货时间，具体支持传入的参数范围/product/getProductUpdateRule。
//        paramJson.put("presell_end_time","2020-02-21 18:54:27");//是否必须:否；示例值：1；参数描述：预售结束时间，格式2020-02-21 18:54:27，最多支持设置距离当前30天
        paramJson.put("supply_7day_return", 1);//是否必须:否；示例值：1；参数描述：【该字段将在2023年4月30日下线，请开发使用最新的after_sale_service字段传值；】是否支持7天无理由，0不支持，1支持，2支持（拆封后不支持）
        paramJson.put("mobile", "18473578997");//是否必须:是；示例值：40012345；参数描述：客服电话号码
        paramJson.put("commit", product.getCommit());//是否必须:是；示例值：true；参数描述：false仅保存，true保存+提审
//        paramJson.put("out_product_id", product.getOutProductId());//是否必须:否；示例值：1；参数描述：外部product_id。超市小时达场景不推荐使用
        paramJson.put("remark", product.getRemark());//是否必须:否；示例值：备注；参数描述：商家可见备注
//        paramJson.put("spec_name", product.getSpecName());//是否必须:否；示例值：颜色-尺码；参数描述：如果不填，则规格名为各级规格名用 \"-\" 自动生成
        paramJson.put("specs", product.getSpecs());//是否必须:否；示例值："颜色|红色,黑色^尺码|S,M"；参数描述：店铺通用规格，能被同类商品通用。多规格用^分隔，规格与规格值用|分隔，多个规格值用,分隔。如果创建或编辑现货+预售商品或新预售商品时，须包含发货时效，如：颜色|黑色,白色,黄色^尺码|S,M,L^发货时效|5天内发货,15天内发货
        /*
         *是否必须:是；
         * 示例值：[{\"spec_detail_name1\":\"红色\",\"spec_detail_name2\":\"S\",\"spec_detail_name3\":\"\",\"stock_num\":11,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"红色\",\"spec_detail_name2\":\"M\",\"spec_detail_name3\":\"\",\"stock_num\":22,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"黑色\",\"spec_detail_name2\":\"S\",\"spec_detail_name3\":\"\",\"stock_num\":44,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"黑色\",\"spec_detail_name2\":\"M\",\"spec_detail_name3\":\"\",\"stock_num\":55,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]}]；
         * 参数描述：
         * sku详情，数量应该等于规格1规格2规格3，sku数量和规格组合数必须一致 sku不可售时，库存可设置为0。price单位为分。
         * delivery_infos为SKU物流信息，info_value为字符串类型（示例："12"），info_type填weight，info_unit支持mg,g,kg，
         * 超市小时达场景主品用普通库存，子品用区域库存（"sku_type": 1 // 区域库存，"stock_num_map": {"123": 99999 // 门店ID:库存数量}）;
         * “gold_process_charge”为黄金加工费，只有计价金类目可填并且必填。
         * sell_properties为sku规格信息，代替spec_detail_name1、spec_detail_name2、spec_detail_name3，
         * 支持填写超过三级规格，
         * 样式:[{"property_id":123,"property_name":"颜色","value_id":456,"value_name":"红色","remark":"偏深"},
         * {"property_id":789,"property_name":"净含量","value_id":891,"value_name":"30ml","remark":null,"measurement":{"measure_unit":"ml","measure_unit_id":4,"value":"30"}}]
         * 其中property_id规格项属性id,自定义时传0,property_name为规格项名称,value_id为规格值属性id自定义时传0,value_name为规格值名称,
         * remark为规格值备注(选填),measurement为度量衡信息，当规格值为度量衡属性自定义值时传递。
         */
        paramJson.put("spec_prices", product.getSpecPrices());
//        paramJson.put("spec_pic", product.getSpecPic());//是否必须:否；示例值：img_url,img_url,img_url；参数描述：如颜色-尺寸, 颜色就是主规格, 颜色如黑,白,黄,它们图片url以逗号分隔 注：\"pic\"、\"description\"、\"spec_pic\"这三个字段里的传入的图片数量总和，不得超过50张。
//        paramJson.put("maximum_per_order", 200);//是否必须:否；示例值：200；参数描述：每个用户每次下单限购件数
//        paramJson.put("limit_per_buyer", 100);//是否必须:否；示例值：1；参数描述：每个用户累计限购件数
//        paramJson.put("minimum_per_order", 1);//是否必须:否；示例值：1；参数描述：每个用户每次下单至少购买的件数
        paramJson.put("product_format_new", product.getProductFormatNew());//是否必须:否；示例值：；参数描述：属性，通过/product/getCatePropertyV2获取 格式：{"property_id":[{"value":value,"name":"property_name","diy_type":0}]} name的类型是string，value和diy_type的类型是number
//        paramJson.put("spu_id", 14567);//是否必须:否；示例值：14567；参数描述：spu_id
//        paramJson.put("appoint_delivery_day", 2);//是否必须:否；示例值：2；参数描述：可预约发货天数
//        paramJson.put("third_url", "http://img.alicdn.com/xxxx");//是否必须:否；示例值：http://img.alicdn.com/xxxx；参数描述：third_url--第三方回调URL，也称为第三方通知URL
//        paramJson.put("extra", "略");//是否必须:否；示例值：略；参数描述：extra
        paramJson.put("src", product.getSrc());//是否必须:否；示例值：略；参数描述：src字段来区分这些商品是来自哪个平台。另外，如果你的商品数据来自于不同的数据源
        paramJson.put("standard_brand_id", 596120136);//是否必须:否；示例值：596120136；参数描述：品牌id，通过/brand/list获取，无品牌id则传596120136
//        paramJson.put("need_check_out",true);//是否必须:否；示例值：true；参数描述：卡券类型需要传true
//        paramJson.put("car_vin_code","BA111111111111111");//是否必须:否；示例值：BA111111111111111；参数描述：汽车vin码
//        paramJson.put("presell_config_level",3);//是否必须:否；示例值：3；参数描述：0：全款预售，1：sku预售，2：现货+预售 ，3：新预售
//        paramJson.put("need_recharge_mode",true);//是否必须:否；示例值：true；参数描述：充值模式
//        paramJson.put("account_template_id","122112");//是否必须:否；示例值：122112；参数描述：账号模板id
//        paramJson.put("presell_delivery_type",1);//是否必须:否；示例值：1；参数描述：全款预售和sku预售时传递，其他不传： 0 预售结束后发货 1支付完成后发货
//        paramJson.put("white_back_ground_pic_url", "http://aaaa");//是否必须:否；示例值："http://aaaa"；参数描述：白底图url(仅素材中心url有效)，白底图比例要求1:1
//        paramJson.put("long_pic_url", "http://aaaa");//是否必须:否；示例值："http://aaaa"；参数描述：3:4长图url(仅素材中心url有效)
//        paramJson.put("after_sale_service","{ 'supply_day_return_selector':'7-2' }");//是否必须:否；示例值：1；参数描述：推荐传入，售后服务参数,key:value格式。supply_day_return_selector(7天无理由选项)：N天-政策代号，N只支持7和15，政策代号枚举https://bytedance.feishu.cn/docs/doccnF946oh1c98e7mo9DlYZtig 。 supply_red_ass_return（红屁屁无忧）：0不支持，1支持。supply_allergy_return（过敏无忧,仅跨境可选）：0不支持，1支持。 damaged_order_return（坏损包退）：0不支持，1支持。 support_allergy_returnV2（过敏包退，境内可选）：0不支持，1支持
        paramJson.put("sell_channel", 0);//是否必须:否；示例值：1；参数描述：售卖方式;0:全渠道手售卖,1:仅指定直播间售卖
        paramJson.put("start_sale_type", product.getStartSaleType());//是否必须:否；示例值：1；参数描述：审核通过后上架售卖时间配置:0-立即上架售卖 1-放入仓库
//        paramJson.put("material_video_id","vaaaa");//是否必须:否；示例值："vaaaa"；参数描述：主图视频ID，可以先通过https://op.jinritemai.com/docs/api-docs/69/1617接口上传视频，获取审核通过的视频素材ID进行传入 任务需要验证
        paramJson.put("pickup_method", "0");//是否必须:否；示例值：1；参数描述：提取方式新字段，推荐使用。"0": 普通商品-使用物流发货, "1": 虚拟商品-无需物流与电子交易凭证, "2": 虚拟商品-使用电子交易凭证, "3": 虚拟商品-充值直连
        paramJson.put("size_info_template_id", product.getSizeInfoTemplateId());//是否必须:否；示例值：101；参数描述：尺码模板ID
//        paramJson.put("substitute_goods_url", "https://xxx.xxx.xxx");//是否必须:否；示例值：https://xxx.xxx.xxx；参数描述：外部商品url
        paramJson.put("sale_channel_type", "onlineOnly");//是否必须:否；示例值：sameAsOffline；参数描述：销售渠道类型，可选onlineOnly（纯电商，仅在线上售卖）或sameAsOffline（专柜同款，线上线下都有售卖），云零售商家（https://fxg.jinritemai.com/ffa/merchant-growth/cloud-retail）可以设置
        paramJson.put("store_id", 108143558);//是否必须:否；示例值：12345；参数描述：门店ID--衣衣的商城：108143558
//        paramJson.put("main_product_id", 312121);//是否必须:否；示例值：312121；参数描述：主商品ID
//        paramJson.put("sale_limit_id",123);//是否必须:否；示例值：123；参数描述：限售模板ID
//        paramJson.put("name_prefix", "钛钢木质耳饰");//是否必须:否；示例值：钛钢木质耳饰；参数描述：通过/product/getRecommendName接口推荐的商品标题前缀（注意系统根据类目属性生成前缀字符串时，末尾有一个空格）
//        paramJson.put("reference_price", 12300);//是否必须:否；示例值：12300；参数描述：参考价，单位分，需大于商品价格并小于商品价格的10倍
//        paramJson.put("main_image_three_to_four", "img_url1|img_url2|img_url3");//是否必须:否；示例值："img_url1|img_url2|img_url3"；参数描述：商品主图3:4，多张图片用 \"|\" 分开，最多5张，大小不超过1M。
//        paramJson.put("is_c2b_switch_on",true);//是否必须:否；示例值：true；参数描述：是否支持c2b小程序（特定c2b定制商家使用，请使用/product/getProductUpdateRule接口获取是否支持）
//        paramJson.put("micro_app_id","abcde");//是否必须:否；示例值："abcde"；参数描述：支持c2b定制时的小程序id（特定c2b定制商家使用，请使用/product/getProductUpdateRule接口获取是否支持）
//        paramJson.put("is_auto_charge",false);//是否必须:否；示例值：false；参数描述：是否是自动充值商品
//        paramJson.put("short_product_name", "新品牛肉干");//是否必须:否；示例值："新品牛肉干"；参数描述：导购短标题，短标题可用于物流打单及商品搜索场景，若未填写，则系统将智能生成最优短标题在商详购买页面展示，说明详见：https://school.jinritemai.com/doudian/web/article/aHUW2MCvHqF3?from=shop_article


        JSONObject apiParams = new JSONObject();
        apiParams.put("access_token", accessToken);
        apiParams.put("app_key", "");
        apiParams.put("method", "product.addV2");
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

    // 测试
    public static void main(String[] args) {
        String accessToken = "c38d6ce4-0d63-43e7-af0e-1988539d244f";
        String apiKey = "bolin20230727";
        ProductAdd productAdd = new ProductAdd();

        Product product = new Product();
        product.setOuterProductId("232334");
        product.setProductType(0);
        product.setName("春秋T恤");
        product.setRecommendRemark("这个商品很好啊");
        product.setPic("http://e3h.i.ximgs.net/1/813781/20230716/20230716559585001_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585002_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585003_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585004_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585005_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585006_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585007_750.jpg");
        product.setDescription("http://e3h.i.ximgs.net/1/813781/20230716/20230716559585008_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585009_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585010_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585011_750.jpg|http://e3h.i.ximgs.net/1/813781/20230716/20230716559585012_750.jpg");
        product.setSpecName("");
        product.setSpecs("颜色|红色,黑色^尺码|S,M");
        product.setSpecPrices("[{\"spec_detail_name1\":\"红色\",\"spec_detail_name2\":\"S\",\"spec_detail_name3\":\"\",\"stock_num\":11,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"红色\",\"spec_detail_name2\":\"M\",\"spec_detail_name3\":\"\",\"stock_num\":22,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"黑色\",\"spec_detail_name2\":\"S\",\"spec_detail_name3\":\"\",\"stock_num\":44,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]},{\"spec_detail_name1\":\"黑色\",\"spec_detail_name2\":\"M\",\"spec_detail_name3\":\"\",\"stock_num\":55,\"price\":100,\"code\":\"\",\"step_stock_num\":0,\"supplier_id\":\"\",\"outer_sku_id\":\"\",\"delivery_infos\":[{\"info_type\":\"weight\",\"info_value\":\"100\",\"info_unit\":\"mg\"}]}]");
        product.setSpecPic("");
        product.setSrc("");

        // 构建商品属性信息
        JSONObject properties = new JSONObject();
//        properties.put("品牌", "");      // 设置厚度属性
//        properties.put("适用场景", "日常");      // 设置厚度属性
//        properties.put("适用对象", "学生");      // 设置厚度属性
//        properties.put("领型", "翻领");      // 设置厚度属性
//        properties.put("面料材质成分含量", "100%棉"); // 设置面料材质成分含量属性
//        properties.put("服装版型", "标准");      // 设置厚度属性
//        properties.put("袖长", "长袖");      // 设置厚度属性
//        properties.put("厚度", "薄款");      // 设置厚度属性
//        properties.put("基础风格", "青春流行");      // 设置厚度属性
//        properties.put("适用季节", "秋季");      // 设置厚度属性
//        properties.put("面料材质", "棉");      // 设置厚度属性
//        properties.put("服饰工艺", "印花");  // 设置服饰工艺属性
//        properties.put("20172", "[{\"value\":27385, \"name\":\"青春流行\",\"diy_type\":0},{\"value\":168367, \"name\":\"100%棉\",\"diy_type\":0},{\"value\":17183, \"name\":\"日常\",\"diy_type\":0},{\"value\":19165, \"name\":\"棉\",\"diy_type\":0},{\"value\":38566, \"name\":\"薄款\",\"diy_type\":0},{\"value\":8284, \"name\":\"学生\",\"diy_type\":0},{\"value\":30551, \"name\":\"翻领\",\"diy_type\":0},{\"value\":30229, \"name\":\"长袖\",\"diy_type\":0},{\"value\":10498, \"name\":\"标准\",\"diy_type\":0},{\"value\":22923, \"name\":\"秋季\",\"diy_type\":0},{\"value\":11081, \"name\":\"印花\",\"diy_type\":0}]");
        product.setProductFormatNew("{\"1209\":[{\"value\":27385, \"name\":\"青春流行\",\"diy_type\":0}],\"1467\":[{\"value\":168367, \"name\":\"100%棉\",\"diy_type\":0}],\"1714\":[{\"value\":17183, \"name\":\"日常\",\"diy_type\":0}],\"785\":[{\"value\":19165, \"name\":\"棉\",\"diy_type\":0}],\"241\":[{\"value\":38566, \"name\":\"薄款\",\"diy_type\":0}],\"2199\":[{\"value\":8284, \"name\":\"学生\",\"diy_type\":0}],\"1829\":[{\"value\":30551, \"name\":\"翻领\",\"diy_type\":0}],\"714\":[{\"value\":30229, \"name\":\"长袖\",\"diy_type\":0}],\"3059\":[{\"value\":10498, \"name\":\"标准\",\"diy_type\":0}],\"1343\":[{\"value\":22923, \"name\":\"秋季\",\"diy_type\":0}],\"1825\":[{\"value\":11081, \"name\":\"印花\",\"diy_type\":0}]}");

        String response = productAdd.dy(accessToken, apiKey, product);
        System.out.println(response);
    }
}
