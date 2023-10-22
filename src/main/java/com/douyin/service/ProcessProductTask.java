package com.douyin.service;

import com.douyin.action.ProductAdd;
import com.douyin.domain.ComponentTemplate;
import com.douyin.domain.Product;
import com.douyin.utils.StringUtils;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

import static com.douyin.utils.StringUtils.*;

public class ProcessProductTask implements Runnable {
    private String goodsLink;
    private String saveExceptionLinkFilePath;
    private String categoryName;
    private String templateName;
    private int cateId;
    private String accessToken;
    private String apiKey;

    //Runnable task = new ProcessProductTask(line, categoryName, templateName, cateId, accessToken, apiKey);
    public ProcessProductTask(String goodsLink, String categoryName, String templateName, int cateId, String accessToken, String apiKey, String saveExceptionLinkFilePath) {
        this.goodsLink = goodsLink;
        this.categoryName = categoryName;
        this.templateName = templateName;
        this.cateId = cateId;
        this.accessToken = accessToken;
        this.apiKey = apiKey;
        this.saveExceptionLinkFilePath = saveExceptionLinkFilePath;
    }

    @Override
    public void run() {

        //ChromeSystemConfig.config();
        // 设置 ChromeDriver 路径
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.logfile", "chrome.log");
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("org.ansj.log4j.level", "OFF");

        // 创建 ChromeDriverService
        ChromeDriverService service = new ChromeDriverService.Builder().build();

        // 创建 ChromeOptions 对象
        ChromeOptions options = new ChromeOptions();
        //尝试显式设置ChromeOptions以禁用此警告。您可以在创建ChromeOptions对象后添加以下设置：
        options.setExperimentalOption("useAutomationExtension", false);
        // 添加 Chrome DevTools Protocol (CDP) 参数
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // 添加禁用兼容性提示的选项
        //options.addArguments("--disable-infobars");
        options.addArguments("disable-infobars");
        //配置ChromeDriver启动参数：您可以在代码中配置ChromeDriver的启动参数，以便在启动时禁用此警告。示例如下：
        options.addArguments("–-whitelisted-ips=");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("disable-blink-features=AutomationControlled");
        // 创建 ChromeOptions 对象并启用无头模式
        options.addArguments("--headless");
        // 添加禁止更新提示的选项
        options.addArguments("--disable-update");
        // 禁用加载图片
        options.addArguments("--disable-images");
        // 添加禁用显示通知的选项
        options.addArguments("--disable-notifications");
        // 设置用户代理为普通用户的代理
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.5790.102 Safari/537.36");

        // 创建WebDriver对象
        WebDriver driver = new ChromeDriver(service, options);

        try {
//        String goodsLink = goodsLink;
            System.out.println(goodsLink);

            try {
                // 在新标签页中加载网页
                driver.get(goodsLink);
                // 使用 WebDriverWait 等待页面 URL 不再变化
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.urlToBe(goodsLink));
            } catch (Exception e) {
                // 处理异常
                System.out.println("ProcessProductTask-->run异常：" + e.getMessage());
                // 在此处添加继续操作的代码
                StringUtils.openAndClickImage(driver);
            }


            // 最大化浏览器窗口
            //driver.manage().window().maximize();

            // 调用等待网页加载完成的方法，并传递等待时间为2000毫秒（2秒）
            waitForPageLoad(2000);

            // 找到全部属性元素
            List showMoreAttributeElements = driver.findElements(By.className("show-more-attribute"));
            // 如果元素存在，则模拟点击操作
            if (showMoreAttributeElements.size() > 0) {
                WebElement webElement = driver.findElement(By.className("show-more-attribute"));
                webElement.click();
            }

            // 等待一段时间，以确保内容加载完成
            // 注意：这里使用的是硬编码的等待时间，实际中建议使用显式等待或隐式等待
            waitForPageLoad(2 * 1000);

            // 提取商品详情的信息
            WebElement productDetailElement = driver.findElement(By.cssSelector("ul.details-attribute-list.clearfix"));

            // 提取商品信息
            WebElement productElement = driver.findElement(By.className("product-details")); // 根据实际网页上商品元素的class定位方式进行修改


            //供货价
            double result = 0.00;
            String price = productElement.findElement(By.className("price-content")).getText(); // 根据实际网页上商品元素的class定位方式进行修改
            if (!price.contains("-")) {
                double dprice = removeCurrencySymbol(price);
                result = calculatePrice(dprice);
            } else {
                WebElement priceElement = productElement.findElement(By.className("price-content"));
                // 判断 priceElement 是否为空
                if (priceElement != null) {
                    ArrayList<Double> prices = extractPrices(priceElement.getText());
                    result = findMaxPrice(prices);
                    System.out.println("价格列表：" + prices);
                    System.out.println("最大价格：" + result);
                    result = calculatePrice(result);
                } else {
                    System.out.println("未找到价格元素");
                }

            }
            // 进行加法和乘法运算
            // 将结果转换为字符串
            String resultStr = String.format("%.2f", result);
            System.out.println("计算后的结果价格是：" + resultStr);


            Product product = new Product();


            //---------------------------------尺码模板IDsize_info_template_id start---------------------------------------------------------
            GetProductComponentTemplate componentTemplate = new GetProductComponentTemplate();
            ComponentTemplate template = new ComponentTemplate();
            String templateJSON = componentTemplate.ct(accessToken, apiKey, template);
            long templateId = getTemplateIdByName(templateJSON, templateName);
            product.setSizeInfoTemplateId(templateId);
            //---------------------------------尺码模板IDsize_info_template_id end--------------------------------------------------------------


            //----------------------------------商品分类 start--------------------------------------------------------------
            // 使用相对路径定位 <a> 元素，并获取其文本内容
            String categoryRelativeXPath = "//li[@class='details-attribute-item']/a[@class='props-link']";
            WebElement linkElement = driver.findElement(By.xpath(categoryRelativeXPath));

            // 获取元素的文本内容
            String cate = linkElement.getText();
            if (cate.contains(categoryName)) {
                //如何类目是一样的就是继续运行，否则退出此次循环。进入下一个循环。
            } else {
                System.out.println("不是同一类目，当前页的类目是：" + cate);
                driver.quit();
            }
            GetProductShopCategory shopCategory = new GetProductShopCategory();
            String rootCategoryJson = shopCategory.productShopCategory(accessToken, apiKey, 0);
            JSONObject responseCategoryJson = new JSONObject(rootCategoryJson);
            if (responseCategoryJson.has("data")) {
                JSONArray properties = responseCategoryJson.getJSONArray("data");
                // 遍历属性列表，并根据每个属性的ID赋值
                for (int i = 0; i < properties.length(); i++) {
                    JSONObject property = properties.getJSONObject(i);
                    int id = property.getInt("id");
                    String name = property.getString("name");

                    // 创建一个包含属性值的JSON对象
                    // 这里根据属性ID给属性赋值，你可以根据具体需求处理不同属性
                    if (name.equals("男装")) {
                        String leafCategoryJson = shopCategory.productShopCategory(accessToken, apiKey, id);
                        JSONObject leafJson = new JSONObject(leafCategoryJson);
                        if (leafJson.has("data")) {
                            JSONArray leafProperties = leafJson.getJSONArray("data");
                            // 遍历属性列表，并根据每个属性的ID赋值
                            for (int j = 0; j < leafProperties.length(); j++) {
                                JSONObject leafPropertie = leafProperties.getJSONObject(j);
                                String leaf_name = leafPropertie.getString("name");
                                if (leaf_name.equals(categoryName)) {
                                    int leaf_id = leafPropertie.getInt("id");
                                    if (leaf_id > 0) {
                                        product.setCategoryLeafId(leaf_id);
                                        cateId = leaf_id;
                                        System.out.println("类目名称： " + categoryName + " ,id为： " + leaf_id);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //---------------------------------商品分类 end--------------------------------------------------------------


            //---------------------------------category_leaf_id start---------------------------------------------------------
            product.setCategoryLeafId(cateId);
            //---------------------------------category_leaf_id end--------------------------------------------------------------


            //--------------------重要属性product_format_new对象 开始--------------------------------------------
            String[] fabricMaterial = {};//面料材质
            double maxPercentage = -1;//面料材质百分比
            Boolean commit = true;//false仅保存，true保存+提审
            List attributeTitle = new ArrayList();//取得商品属性的一些词语保存在list中。
            // 定位包含li元素的父元素ul
            WebElement ulElement = driver.findElement(By.className("details-attribute-list"));
            // 定位所有li元素
            List<WebElement> liElements = ulElement.findElements(By.tagName("li"));
            List ulist = new ArrayList();
            // 遍历并输出每个li元素的文字内容
            for (WebElement liElement : liElements) {
                String text = liElement.getText();
                ulist.add(text);
//                System.out.println(text);
            }
            GetProductCatePropertyV2 productCatePropertyV2 = new GetProductCatePropertyV2();
            String responseData = productCatePropertyV2.cp(accessToken, apiKey, cateId);
            // 解析返回的属性列表
            JSONObject responseJson = new JSONObject(responseData);
            if (responseJson.has("data")) {
                JSONObject data = responseJson.getJSONObject("data");
                if (data.has("data")) {
                    JSONArray properties = data.getJSONArray("data");
                    // 创建一个包含属性值的JSON对象
                    JSONObject productAttributes = new JSONObject();

                    // 遍历属性列表，并根据每个属性的ID赋值
                    for (int i = 0; i < properties.length(); i++) {
                        JSONObject property = properties.getJSONObject(i);
                        int propertyId = property.getInt("property_id");
                        String propertyName = property.getString("property_name");

                        // 这里根据属性ID给属性赋值，你可以根据具体需求处理不同属性
                        if (propertyName.equals("品牌")) {
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            attributes.put("value", 596120136);
                            attributes.put("name", propertyName);
                            attributes.put("diy_type", 0);
                            attributesArray.put(attributes);
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("货号")) {
                            // 创建一个随机数生成器
                            Random random = new Random();
                            // 生成一个6位随机数字
                            int randomNumber = 100000 + random.nextInt(900000);
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            attributes.put("value", propertyId);
                            attributes.put("name", String.valueOf(randomNumber));
                            attributes.put("diy_type", 0);
                            attributesArray.put(attributes);
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("基础风格")) {
                            JSONArray basicStyles = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value_id
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < basicStyles.length(); j++) {
                                JSONObject basicStyle = basicStyles.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(basicStyle.getString("name")) && count == 0) {
                                        attributes.put("value", basicStyle.getInt("value"));
                                        attributes.put("name", basicStyle.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(basicStyle.getString("name"));

                                        addedValueIds.add(basicStyle.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//青春流行
                                attributes.put("value", Integer.parseInt(basicStyles.getJSONObject(0).get("value").toString()));
                                attributes.put("name", basicStyles.getJSONObject(0).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                matchFound = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);

                        } else if (propertyName.equals("适用场景")) {
                            JSONArray suitables = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value_id
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < suitables.length(); j++) {
                                JSONObject suitable = suitables.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(suitable.getString("name")) && count == 0) {
                                        attributes.put("value", suitable.getInt("value"));
                                        attributes.put("name", suitable.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(suitable.getString("name"));

                                        addedValueIds.add(suitable.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//日常
                                attributes.put("value", Integer.parseInt(suitables.getJSONObject(0).get("value").toString()));
                                attributes.put("name", suitables.getJSONObject(0).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("服装版型")) {
                            JSONArray clothes = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value_id
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < clothes.length(); j++) {
                                JSONObject clothe = clothes.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(clothe.getString("name").replace("型", "")) && count == 0) {
                                        attributes.put("value", clothe.getInt("value"));
                                        attributes.put("name", clothe.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(clothe.getString("name"));

                                        addedValueIds.add(clothe.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//标准
                                attributes.put("value", Integer.parseInt(clothes.getJSONObject(0).get("value").toString()));
                                attributes.put("name", clothes.getJSONObject(0).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("适用季节")) {
                            JSONArray seasons = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value_id
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < seasons.length(); j++) {
                                JSONObject season = seasons.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(season.getString("name")) && count == 0) {
                                        attributes.put("value", season.getInt("value"));
                                        attributes.put("name", season.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(season.getString("name"));

                                        addedValueIds.add(season.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//四季通用
                                attributes.put("value", Integer.parseInt(seasons.getJSONObject(3).get("value").toString()));
                                attributes.put("name", seasons.getJSONObject(3).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("适用对象")) {
                            JSONArray shiyongduixiangs = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < shiyongduixiangs.length(); j++) {
                                JSONObject shiyongduixiang = shiyongduixiangs.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    pageAttrName = getPageAttrNameReplace(pageAttrName);
                                    if (pageAttrName.contains(shiyongduixiang.getString("name")) && count == 0) {
                                        attributes.put("value", shiyongduixiang.getInt("value"));
                                        attributes.put("name", shiyongduixiang.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(shiyongduixiang.getString("name"));

                                        addedValueIds.add(shiyongduixiang.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if ((!matchFound) && count == 0) {//通用
                                attributes.put("value", Integer.parseInt(shiyongduixiangs.getJSONObject(4).get("value").toString()));
                                attributes.put("name", shiyongduixiangs.getJSONObject(4).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("袖长")) {
                            JSONArray sleeveLengths = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < sleeveLengths.length(); j++) {
                                JSONObject sleeveLength = sleeveLengths.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(sleeveLength.getString("name")) && count == 0) {
                                        attributes.put("value", sleeveLength.getInt("value"));
                                        attributes.put("name", sleeveLength.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(sleeveLength.getString("name"));

                                        addedValueIds.add(sleeveLength.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//todo
                                attributes.put("value", Integer.parseInt(sleeveLengths.getJSONObject(0).get("value").toString()));
                                attributes.put("name", sleeveLengths.getJSONObject(0).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = false;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("面料材质")) {
                            JSONArray fabrics = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;

                            String fabricText = StringUtils.getFabricText(driver);
                            fabricMaterial = StringUtils.getMaxPercentageAndText(fabricText);
                            for (int j = 0; j < fabrics.length(); j++) {
                                JSONObject fabric = fabrics.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (fabricMaterial != null) {
                                        pageAttrName = fabricMaterial[0];
                                        maxPercentage = Double.parseDouble(fabricMaterial[1]);
                                    }
                                    if (pageAttrName.contains(fabric.getString("name")) && count == 0) {
                                        attributes.put("value", fabric.getInt("value"));
                                        attributes.put("name", fabric.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);

                                        addedValueIds.add(fabric.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }//API:聚酯纤维（涤纶）... 页面:新型聚酯纤维100%   {"sequence":5600,"name":"尼龙（锦纶）","value_id":168796,"value":"168796"}
                                    if (fabricText.contains(fabric.getString("name").replace("（涤纶）", "").replace("（锦纶）", "")) && count == 0) {
                                        attributes.put("value", fabric.getInt("value"));
                                        attributes.put("name", fabric.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);

                                        addedValueIds.add(fabric.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//聚酯纤维（涤纶）
                                attributes.put("value", Integer.parseInt(fabrics.getJSONObject(6).get("value").toString()));
                                attributes.put("name", fabrics.getJSONObject(6).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("领型")) {
                            JSONArray collarstyles = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < collarstyles.length(); j++) {
                                JSONObject collarstyle = collarstyles.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(collarstyle.getString("name").replace("型", "")) && count == 0) {
                                        attributes.put("value", collarstyle.getInt("value"));
                                        attributes.put("name", collarstyle.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(collarstyle.getString("name"));

                                        addedValueIds.add(collarstyle.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//圆领 commit = false;//todo
                                attributes.put("value", Integer.parseInt(collarstyles.getJSONObject(3).get("value").toString()));
                                attributes.put("name", collarstyles.getJSONObject(3).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = false;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("厚度")) {
                            JSONArray thicknesss = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < thicknesss.length(); j++) {
                                JSONObject thickness = thicknesss.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
//                                    if(pageAttrName.contains("厚薄")){
//                                        pageAttrName = pageAttrName.replace("厚薄","厚度");
//                                    }
                                    if (pageAttrName.contains(thickness.getString("name").replace("款", "")) && count == 0) {
                                        attributes.put("value", thickness.getInt("value"));
                                        attributes.put("name", thickness.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(thickness.getString("name"));

                                        addedValueIds.add(thickness.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//常规款
                                attributes.put("value", Integer.parseInt(thicknesss.getJSONObject(3).get("value").toString()));
                                attributes.put("name", thicknesss.getJSONObject(3).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("服饰工艺")) {
                            JSONArray techniques = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < techniques.length(); j++) {
                                JSONObject technique = techniques.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(technique.getString("name")) && count == 0) {
                                        attributes.put("value", technique.getInt("value"));
                                        attributes.put("name", technique.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        attributeTitle.add(technique.getString("name"));

                                        addedValueIds.add(technique.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {//免烫处理
                                attributes.put("value", Integer.parseInt(techniques.getJSONObject(7).get("value").toString()));
                                attributes.put("name", techniques.getJSONObject(7).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("面料材质成分含量")) {
                            JSONArray ingredientContents = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;

                            String fabricText = StringUtils.getFabricText(driver);
                            fabricMaterial = StringUtils.getMaxPercentageAndText(fabricText);
                            for (int j = 0; j < ingredientContents.length(); j++) {
                                JSONObject ingredientContent = ingredientContents.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if(fabricMaterial != null){
                                        maxPercentage = Double.parseDouble(fabricMaterial[1]);
                                    }
                                    pageAttrName = identifyPercentageRangeRetrueString(maxPercentage);
                                    if (pageAttrName.contains(ingredientContent.getString("name")) && count == 0) {
                                        attributes.put("value", ingredientContent.getInt("value"));
                                        attributes.put("name", ingredientContent.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);

                                        addedValueIds.add(ingredientContent.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
                            if (!matchFound && count == 0) {
//                                int index = identifyPercentageRange(maxPercentage);
                                attributes.put("value", Integer.parseInt(ingredientContents.getJSONObject(7).get("value").toString()));
                                attributes.put("name", ingredientContents.getJSONObject(7).get("name"));
                                attributes.put("diy_type", 0);
                                attributesArray.put(attributes);
                                commit = true;
                                count++;
                            }
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("流行元素")) {
                            JSONArray popularityElements = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < popularityElements.length(); j++) {
                                JSONObject popularityElement = popularityElements.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    String[] pageAttrNameSplit = null;
                                    if (pageAttrName.contains(":")) {
                                        pageAttrNameSplit = pageAttrName.split(":");
                                        if (pageAttrNameSplit[1].contains(popularityElement.getString("name")) && count == 0) {
                                            attributes.put("value", popularityElement.getInt("value"));
                                            attributes.put("name", popularityElement.getString("name"));
                                            attributes.put("diy_type", 0);
                                            attributesArray.put(attributes);
                                            productAttributes.put(String.valueOf(propertyId), attributesArray);

                                            addedValueIds.add(popularityElement.getInt("value"));
                                            matchFound = true;
                                            count++;
                                            break; // 停止内层循环，避免重复添加
                                        }
                                    } else {
                                        if (pageAttrName.contains(popularityElement.getString("name")) && (pageAttrName.contains("流行") || pageAttrName.contains("元素 ")) && count == 0) {
                                            attributes.put("value", popularityElement.getInt("value"));
                                            attributes.put("name", popularityElement.getString("name"));
                                            attributes.put("diy_type", 0);
                                            attributesArray.put(attributes);
                                            productAttributes.put(String.valueOf(propertyId), attributesArray);

                                            addedValueIds.add(popularityElement.getInt("value"));
                                            matchFound = true;
                                            count++;
                                            break; // 停止内层循环，避免重复添加
                                        }
                                    }

                                }
                            }
//                            if (!matchFound && count == 0) {
//                                attributes.put("value", popularityElements.getJSONObject(0).get("value"));
//                                attributes.put("name", popularityElements.getJSONObject(0).get("name"));
//                                attributes.put("diy_type", 0);
//                                attributesArray.put(attributes);
//                                count++;
//                            }

                        } else if (propertyName.equals("上市时间")) {
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            attributes.put("value", propertyId);
                            attributes.put("name", "2023");
                            attributes.put("diy_type", 0);
                            attributesArray.put(attributes);
                            productAttributes.put(String.valueOf(propertyId), attributesArray);
                        } else if (propertyName.equals("袖型")) {
                            JSONArray sleeveTypes = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < sleeveTypes.length(); j++) {
                                JSONObject sleeveType = sleeveTypes.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(sleeveType.getString("name")) && count == 0) {
                                        attributes.put("value", sleeveType.getInt("value"));
                                        attributes.put("name", sleeveType.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        productAttributes.put(String.valueOf(propertyId), attributesArray);
                                        attributeTitle.add(sleeveType.getString("name"));

                                        addedValueIds.add(sleeveType.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
//                            if (!matchFound && count == 0) {
//                                attributes.put("value", sleeveTypes.getJSONObject(0).get("value"));
//                                attributes.put("name", sleeveTypes.getJSONObject(0).get("name"));
//                                attributes.put("diy_type", 0);
//                                attributesArray.put(attributes);
//                                count++;
//                            }

                        } else if (propertyName.equals("细分风格")) {
                            JSONArray subStyles = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < subStyles.length(); j++) {
                                JSONObject subStyle = subStyles.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(subStyle.getString("name")) && count == 0) {
                                        attributes.put("value", subStyle.getInt("value"));
                                        attributes.put("name", subStyle.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        productAttributes.put(String.valueOf(propertyId), attributesArray);

                                        attributeTitle.add(subStyle.getString("name"));

                                        addedValueIds.add(subStyle.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
//                            if (!matchFound && count == 0) {
//                                attributes.put("value", subStyles.getJSONObject(0).get("value"));
//                                attributes.put("name", subStyles.getJSONObject(0).get("name"));
//                                attributes.put("diy_type", 0);
//                                attributesArray.put(attributes);
//                                count++;
//                            }
                        } else if (propertyName.equals("图案")) {
                            JSONArray patterns = property.getJSONArray("options");
                            JSONObject attributes = new JSONObject();
                            JSONArray attributesArray = new JSONArray();
                            HashSet<Integer> addedValueIds = new HashSet<>(); // 用于跟踪已添加的value
                            boolean matchFound = false;
                            int count = 0;
                            for (int j = 0; j < patterns.length(); j++) {
                                JSONObject pattern = patterns.getJSONObject(j);
                                for (int k = 0; k < ulist.size(); k++) {
                                    //取得页面的属性字段
                                    String pageAttrName = (String) ulist.get(k);
                                    if (pageAttrName.contains(pattern.getString("name")) && count == 0) {
                                        attributes.put("value", pattern.getInt("value"));
                                        attributes.put("name", pattern.getString("name"));
                                        attributes.put("diy_type", 0);
                                        attributesArray.put(attributes);
                                        productAttributes.put(String.valueOf(propertyId), attributesArray);

                                        attributeTitle.add(pattern.getString("name"));

                                        addedValueIds.add(pattern.getInt("value"));
                                        matchFound = true;
                                        count++;
                                        break; // 停止内层循环，避免重复添加
                                    }
                                }
                            }
//                            if (!matchFound && count == 0) {
//                                attributes.put("value", patterns.getJSONObject(0).getInt("value"));
//                                attributes.put("name", patterns.getJSONObject(0).getString("name"));
//                                attributes.put("diy_type", 0);
//                                attributesArray.put(attributes);
//                                count++;
//                            }

                        }
                        // 还可以继续处理其他属性...
                    }

                    // 创建商品的其他必要信息，假设你已经获取了类目ID为20172的T恤商品的其他信息
//                    String productName = "T恤商品";
                    // ... 其他商品信息 ...

                    // 构建商品的参数JSON
//                    JSONObject productParams = new JSONObject();
//                    productParams.put("category_id", cateId);
//                    productParams.put("name", productName);
//                    productParams.put("product_format_new", productAttributes);
                    product.setProductFormatNew(productAttributes.toString());
//                    product.setProductFormatNew("{\"2199\":[{\"name\":\"青年\",\"diy_type\":0,\"value\":27586}],\"3035\":[{\"name\":\"常规\",\"diy_type\":0,\"value\":26705}],\"680\":[{\"name\":\"2023\",\"diy_type\":0,\"value\":680}],\"3171\":[{\"name\":\"173038\",\"diy_type\":0,\"value\":3171}],\"241\":[{\"name\":\"薄款\",\"diy_type\":0,\"value\":38566}],\"1687\":[{\"name\":\"品牌\",\"diy_type\":0,\"value\":596120136}],\"785\":[{\"name\":\"聚酯纤维（涤纶）\",\"diy_type\":0,\"value\":54740}],\"631\":[{\"name\":\"商务休闲\",\"diy_type\":0,\"value\":21348}],\"3059\":[{\"name\":\"宽松型\",\"diy_type\":0,\"value\":56134}],\"1343\":[{\"name\":\"夏季\",\"diy_type\":0,\"value\":12493}],\"1829\":[{\"name\":\"V型领\",\"diy_type\":0,\"value\":196268}],\"714\":[{\"name\":\"无袖\",\"diy_type\":0,\"value\":27420}],\"1209\":[{\"name\":\"青春流行\",\"diy_type\":0,\"value\":27385}],\"1825\":[{\"name\":\"印花\",\"diy_type\":0,\"value\":11081}],\"1714\":[{\"name\":\"日常\",\"diy_type\":0,\"value\":17183}]}");
                    product.setCommit(commit);
                    product.setStartSaleType(0);//审核通过后上架售卖时间配置:0-立即上架售卖 1-放入仓库
//                    System.out.println(productParams);
                    // 发起创建商品请求，将productParams作为请求参数发送给 /product/addV2 接口
                }
            }
            //--------------------重要属性product_format_new对象 结束--------------------------------------------


            //----------------------------------商品标题name开始 -------------------------------------
            //分词器
            String dictionaryPath = "E:\\projects2023\\douyinGather\\library\\custom_dictionary.txt";
            String titlePath = "E:\\projects2023\\douyinGather\\library\\title.txt";
            List titleList = readFromFile(titlePath);
            // 添加自定义词语
            StringUtils.importCustomDictionary(dictionaryPath);
            DicLibrary.reload(dictionaryPath);
            //标题－－－有时候为空start-----------------------
            WebElement titleElement = null;
            List<WebElement> titleElements = productElement.findElements(By.className("cjs-title"));
            if (!titleElements.isEmpty()) {
                // 找到了匹配的元素
                titleElement = titleElements.get(0); // 假设只有一个匹配的元素
            }
            // 使用分词器进行分词
            List words = new ArrayList();
            String title = null;
            if (titleElement != null) {
                title = titleElement.getText();

                for (Term term : ToAnalysis.parse(title)) {
                    // 获取词语部分
                    String word = term.getName();
                    words.add(word);
                }
            }
            //标题－－－有时候为空end-----------------------

            // 使用相对路径定位 <span> 元素，并获取其文本内容
            String relativeXPath = "//span[@class='ft-bold']";
            WebElement spanElement = driver.findElement(By.xpath(relativeXPath));
            // 获取元素的文本内容
            String text = spanElement.getText();
            //通过商品属性生成商品标题----------------------------------
            // 使用分词器进行分词
            for (Term term : ToAnalysis.parse(text)) {
                // 获取词语部分
                String word = term.getName();
                words.add(word);
            }
            // 提取商品详情的信息
            productDetailElement = driver.findElement(By.className("details-attribute-list"));
            String s = extractChineseWords(productDetailElement.toString());
            // 使用分词器进行分词
            for (Term term : ToAnalysis.parse(s)) {
                // 获取词语部分
                String word = term.getName();
                words.add(word);
            }
            // 提取商品信息
            productElement = driver.findElement(By.className("product-details")); // 根据实际网页上商品元素的class定位方式进行修改
            String s1 = extractChineseWords(productElement.toString());
            // 使用分词器进行分词
            for (Term term : ToAnalysis.parse(s1)) {
                // 获取词语部分
                String word = term.getName();
                words.add(word);
            }
            StringBuilder sb = new StringBuilder();
            // 使用 CSS 选择器找到包含关键词的父元素
            WebElement keywordBoxElement = null;
            List<WebElement> keywordBoxElements = driver.findElements(By.cssSelector(".keyword-box"));
            if (!keywordBoxElements.isEmpty()) {
                // 如果元素存在，执行对keywordBoxElements的操作
                keywordBoxElement = keywordBoxElements.get(0);
                // 使用 CSS 选择器在父元素中找到关键词元素列表
                List<WebElement> keywordElements = keywordBoxElement.findElements(By.cssSelector(".keyword"));
                // 判断关键词元素列表是否为空
                if (keywordElements.isEmpty()) {
                    System.out.println("没有找到关键词元素！");
                } else {
                    // 遍历关键词元素列表，获取每个元素的文本内容
                    for (WebElement keywordElement : keywordElements) {
                        String keywordText = keywordElement.getText();
                        sb.append(keywordText);
                        System.out.println(keywordText);
                    }
                }
            } else {
                // 如果元素不存在，执行其他操作
                System.out.println(".keyword-box元素不存在。");
            }
            // 使用分词器进行分词
            for (Term term : ToAnalysis.parse(sb.toString())) {
                // 获取词语部分
                String word = term.getName();
                words.add(word);
            }

            StringBuilder titleSB = new StringBuilder();
            List<String> strings = readFromFile(dictionaryPath);
            for (int i = 0; i < strings.size(); i++) {
                String str = strings.get(i);
                for (int j = 0; j < words.size(); j++) {
                    String newWord = (String) words.get(j);
                    if (str.contains(newWord)) {
                        if (!titleSB.toString().contains(newWord)) {
                            if (isChineseStringLengthGreaterThanOrEqualToTwo(newWord)) {
                                if (getCharacterCount(titleSB.toString()) <= 48 && getCharacterCount(newWord) <= 8) {
                                    titleSB.append(newWord);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //记事本读取的词语
            Collections.shuffle(titleList); // 打乱列表顺序
            for (int i = 0; i < titleList.size(); i++) {
                String titleName = (String) titleList.get(i);
                if (!titleSB.toString().contains(titleName)) {
                    if (getCharacterCount(titleSB.toString()) <= 48 && getCharacterCount(titleName) <= 8) {
                        titleSB.append(titleName);
                    } else {
                        break;
                    }
                }
            }

            for (int i = 0; i < attributeTitle.size(); i++) {
                String attrTitle = (String) attributeTitle.get(i);
                if (!titleSB.toString().contains(attrTitle)) {
                    if (getCharacterCount(titleSB.toString()) <= 48 && getCharacterCount(attrTitle) <= 8) {
                        if (extractedTitle(attrTitle)) continue;
                        titleSB.append(attrTitle);
                    } else {
                        break;
                    }
                }
            }

            titleSB.append(categoryName);
            System.out.println("商品标题: " + titleSB);

            product.setName(titleSB.toString());
            //--------------------商品标题name结束--------------------------------------------


            //--------------------导购短标题 开始--------------------------------------------
            /**
             * 导购短标题，短标题可用于物流打单及商品搜索场景，若未填写，则系统将智能生成最优短标题在商详购买页面展示，
             * 说明详见：https://school.jinritemai.com/doudian/web/article/aHUW2MCvHqF3?from=shop_article
             */
            //所以此处省略一万行代码
            //--------------------导购短标题 结束--------------------------------------------


            //--------------------图文信息-主图pic 开始--------------------------------------------
            // 通过 id 属性 "thumblist" 定位包含 <a> 元素的 <div> 元素
            WebElement divElement = driver.findElement(By.id("thumblist"));

            // 在 <div> 元素的上下文中，使用相对路径定位 <a> 元素
            // 定位所有匹配的 <a> 元素
            List<WebElement> hrefElements = divElement.findElements(By.cssSelector("a[target='_blank']"));

            // 遍历列表并获取每个元素的 "href" 属性值
            LinkedList<String> whitePicList = new LinkedList<>();
            LinkedList<String> picList = new LinkedList<>();
            LinkedList<String> allPicList = new LinkedList<>();
            for (WebElement hrefElement : hrefElements) {
                String hrefValue = hrefElement.getAttribute("href");
                boolean isWhiteBackgroud = hasWhiteBackground(hrefValue);
                if (isWhiteBackgroud) {
                    whitePicList.add(hrefValue);
                    System.out.println("这是白色底图： " + hrefValue);
                } else {
                    picList.add(hrefValue);
                }
//                System.out.println("href 属性值：" + hrefValue);
            }
            allPicList.addAll(whitePicList);
            allPicList.addAll(picList);

            // 输出 allPicList 中的元素（不超过5张）
            String allPicListOutput = joinElements(allPicList, 5);
            //System.out.println(allPicListOutput);
            product.setPic(allPicListOutput);
            //--------------------图文信息-主图pic 结束--------------------------------------------


            //--------------------图文信息-白底图 开始--------------------------------------------
            //通过妙手生成
            //--------------------图文信息-白底图 结束--------------------------------------------


            //--------------------商品详情 description 开始--------------------------------------------
            //提取商品详情图片集
            WebElement picElement = driver.findElement(By.className("product-details-content"));

            //商品详情图片集提取,只取最前面50张。
            StringBuilder picURLsBuilder = new StringBuilder();
            int count = 0; // 计数器
            List<WebElement> pics = picElement.findElements(By.tagName("img"));
            for (WebElement pic : pics) {
                String picURL = pic.getAttribute("data-url");
                picURLsBuilder.append(picURL).append("|");
                count++; // 每次迭代计数器加1
                if (count >= 46) {
                    break; // 达到46张图片，退出循环
                }
            }
            String picURLs = picURLsBuilder.toString();
            if (picURLs.endsWith("|")) {
                picURLs = picURLs.substring(0, picURLs.length() - 1);
            }
            product.setDescription(picURLs);
            //System.out.println(picURLs);
            //--------------------商品详情 description 结束--------------------------------------------


            //--------------------发货模式 presell_type 开始--------------------------------------------
            /**
             * 发货模式，0-现货发货，1-预售发货，2-阶梯发货，默认0
             */
            product.setPresellType(0);
            //--------------------发货模式 presell_type 结束--------------------------------------------


            //--------------------现货发货时间 delivery_delay_day 开始--------------------------------------------
            /**
             * delivery_delay_day： 承诺发货时间，单位是天,不传则默认为2天。现货发货(presell_type=0)和阶梯发货模式(presell_type=2)时必填，
             * 支持传入9999 、1、 2 （分别表示当日发、次日发、48小时发），具体支持传入的参数范围/product/getProductUpdateRule
             */
            product.setDeliveryDelayDay(2);
            //--------------------现货发货时间 delivery_delay_day 结束--------------------------------------------


            //-------------------- 商品规格 specs  开始--------------------------------------------
            //颜色列表
            List<WebElement> colorBoxes = driver.findElements(By.cssSelector(".sku-wrap .color-box"));
            StringBuilder colorsBuilder = new StringBuilder();
            colorsBuilder.append("颜色|");
            if (!colorBoxes.isEmpty()) {
                for (WebElement colorBox : colorBoxes) {
                    String colorText = colorBox.findElement(By.cssSelector(".text-color")).getText();
                    colorsBuilder.append(colorText).append(",");
                }
                colorsBuilder.setLength(colorsBuilder.length() - 1); // 去掉最后一个逗号
            } else {
                List<WebElement> colorElements = driver.findElements(By.cssSelector(".sku-wrap .sku-warp-li[data-color]"));
                for (WebElement colorElement : colorElements) {
                    String colorText = colorElement.getAttribute("data-color");
                    colorsBuilder.append(colorText).append(",");
                }

                if (colorsBuilder.length() > 0) {
                    colorsBuilder.setLength(colorsBuilder.length() - 1); // 去掉最后一个逗号
                }
            }
            colorsBuilder.append("^尺码|M,L,XL,2XL,3XL,4XL");
            String colorsAndSize = colorsBuilder.toString();
            product.setSpecs(colorsAndSize);
//            System.out.println(colorsAndSize);
            //-------------------- 商品规格 specs  结束--------------------------------------------


            //--------------------   spec_prices开始--------------------------------------------
            /**
             * sku详情，数量应该等于规格1规格2规格3，sku数量和规格组合数必须一致 sku不可售时，库存可设置为0。price单位为分。 delivery_infos为SKU物流信息，info_value为字符串类型（示例："12"），info_type填weight，info_unit支持mg,g,kg，超市小时达场景主品用普通库存，子品用区域库存（"sku_type": 1 // 区域库存，"stock_num_map": {"123": 99999 // 门店ID:库存数量}）; “gold_process_charge”为黄金加工费，只有计价金类目可填并且必填。 sell_properties为sku规格信息，代替spec_detail_name1、spec_detail_name2、spec_detail_name3，支持填写超过三级规格，样式:[{"property_id":123,"property_name":"颜色","value_id":456,"value_name":"红色","remark":"偏深"},{"property_id":789,"property_name":"净含量","value_id":891,"value_name":"30ml","remark":null,"measurement":{"measure_unit":"ml","measure_unit_id":4,"value":"30"}}] 其中property_id规格项属性id,自定义时传0,property_name为规格项名称,value_id为规格值属性id自定义时传0,value_name为规格值名称,remark为规格值备注(选填),measurement为度量衡信息，当规格值为度量衡属性自定义值时传递。
             */
            String[] colorSizeArray = colorsAndSize.split("\\^");
            String[] colors = parseColors(colorSizeArray[0]);
            String[] sizes = {"M", "L", "XL", "2XL", "3XL", "4XL"};

            // 定位一个价格元素
            WebElement priceElement = driver.findElement(By.cssSelector(".sku-wrap-size .sku-price"));
            // 取得价格文本
            String priceText = priceElement.getText();
            // 移除非数字字符
            String numericText = priceText.replaceAll("[^0-9]", "");
            // 转换为整数
            int p = Integer.parseInt(numericText);
            int lastprice = calculateLastPrice(p);

            JSONArray jsonArray = new JSONArray();
            Random random = new Random();

            for (String color : colors) {
                for (String size : sizes) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("spec_detail_name1", color);
                    jsonObject.put("spec_detail_name2", size);
                    jsonObject.put("stock_num", getRandomStockNum(random));
                    jsonObject.put("price", lastprice);
                    jsonObject.put("code", "");
                    jsonObject.put("step_stock_num", 0);
                    jsonObject.put("supplier_id", "");
                    jsonObject.put("outer_sku_id", "");

                    JSONArray deliveryInfos = new JSONArray();
                    JSONObject deliveryInfo = new JSONObject();
                    deliveryInfo.put("info_type", "weight");
                    deliveryInfo.put("info_value", "100");
                    deliveryInfo.put("info_unit", "mg");
                    deliveryInfos.put(deliveryInfo);

                    jsonObject.put("delivery_infos", deliveryInfos);

                    jsonArray.put(jsonObject);
                }
            }
            product.setSpecPrices(jsonArray.toString());
//            System.out.println(jsonArray);
            //-------------------- spec_prices  结束--------------------------------------------


            //--------------------  备注 开始--------------------------------------------
            /**
             * 商家可见备注
             */
            product.setRemark(goodsLink);
            //--------------------  备注 结束--------------------------------------------


            //--------------------  src 开始--------------------------------------------
            product.setSrc(goodsLink);
            //--------------------  src 结束--------------------------------------------


            ProductAdd productAdd = new ProductAdd();
            String dy = productAdd.dy(accessToken, apiKey, product);
            if (dy.isEmpty()) {
                System.out.println("图片上传失败！链接是：" + goodsLink);
            }
            // 关闭当前标签页
            driver.quit();

        } catch (Exception e) {
            System.out.println(goodsLink + "链接！" + e.getMessage());
            StringUtils.saveLinkToFile(goodsLink, saveExceptionLinkFilePath);// 保存链接到本地文件
            driver.quit();

        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }
}