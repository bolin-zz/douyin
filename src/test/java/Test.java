import com.douyin.service.BatchUploadImageSync;
import com.douyin.service.QueryMaterialDetail;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String accessToken = "71f852e4-5a8f-44f7-b466-415a0c05d550";
        String apiKey = "bolin20230727";
        LinkedList imageUrls = new LinkedList();
        imageUrls.add("http://e3h.i.ximgs.net/1/813781/20230716/20230716559585002_750.jpg");

        BatchUploadImageSync batchUploadImageSync = new BatchUploadImageSync();
        LinkedList materialIds = batchUploadImageSync.uploadImage(accessToken, apiKey, imageUrls);


        QueryMaterialDetail queryMaterialDetail = new QueryMaterialDetail();
        String json = "";
        while (json == ""){
//            json = queryMaterialDetail.qmd(accessToken, apiKey, materialIds);
            try {
                Thread.sleep(3*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(json);

    }
}
