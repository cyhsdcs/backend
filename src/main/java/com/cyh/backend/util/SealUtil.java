package com.cyh.backend.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SealUtil {

    static String grantCode = "f9eb06a1498c418594d5864ccd359842";

    private static String systemID = "CIS2021-7-27";

    private static String url="http://175.178.47.182:80";

    public static String getSealByGrantCodeV2(String sealType, String organizationCode) {
        String baseUrl = url+"/mssg2/order/getSealByGrantCodeV2";

        JSONObject content = new JSONObject();
        content.put("sealType", sealType);
        content.put("grantCode", grantCode);
        content.put("organizationCode", organizationCode);
        content.put("systemID", systemID);

        JSONObject jsonObject = new JSONObject();
        String dateTimeStr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        jsonObject.put("reqId", dateTimeStr);
        jsonObject.put("content", content);
        System.out.println(jsonObject);
        try {
            JSONObject res = HttpUtil.Post(baseUrl, jsonObject);
            if(res.get("code").equals(200)){
                JSONObject data = JSONObject.parseObject((String) res.getString("data"));
                String img = data.getString("sealImg");
                return img;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<String> genPdfHashByGrantCode(String organizationCode, String scene, String filePath, String sealType) {
        String baseUrl = url+"/mssg2/order/genPdfHashByGrantCode";

        JSONObject content = new JSONObject();
        content.put("scene", scene);
        content.put("grantCode", grantCode);
        content.put("organizationCode", organizationCode);
        content.put("systemID", systemID);

        JSONArray jsonArray = new JSONArray();
        JSONObject sealDataList =  new JSONObject();
        sealDataList.put("fileStr", FileUtil.getFileBase64StrByLocalFile(filePath));
        sealDataList.put("sealType", sealType);
        sealDataList.put("version", "1.0");
        sealDataList.put("ruleType", "RECTANGLE");



        JSONObject rule1 = new JSONObject();
        rule1.put("page", "1");
        rule1.put("bottom", "40");
        rule1.put("left", "200");
        sealDataList.put("rectangleRule", rule1);

        jsonArray.add(sealDataList);

        content.put("sealDataList", jsonArray);

        JSONObject jsonObject = new JSONObject();
        String dateTimeStr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        jsonObject.put("reqId", dateTimeStr);
        jsonObject.put("content", content);
        System.out.println(jsonObject);
        try {
            JSONObject res = HttpUtil.Post(baseUrl, jsonObject);
            if(res.get("code").equals(200)){
                JSONObject data = JSONObject.parseObject((String) res.getString("data"));
                JSONObject fileHash = (JSONObject) data.getJSONArray("fileHashList").get(0);
                System.out.print(data);
                List<String> fil = new ArrayList<>();
                fil.add(fileHash.getString("fileHash"));
                fil.add(fileHash.getString("fileUniqueId"));
                return fil;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String addSignTask(String organizationCode, String scene, String fileUniqueId, String fileHash, String sealType) {
        String baseUrl = url+"/mssg2/order/addSignTask";

        JSONObject content = new JSONObject();
        content.put("dataType", "HASH");
        content.put("algo", "SM3withSM2");
        content.put("scene", scene);
        content.put("grantCode", grantCode);
        content.put("organizationCode", organizationCode);
        content.put("systemID", systemID);

        JSONArray jsonArray = new JSONArray();
        JSONObject sealDataList =  new JSONObject();

        sealDataList.put("sealType", sealType);
        sealDataList.put("fileUniqueId", fileUniqueId);
        sealDataList.put("data", fileHash);

        jsonArray.add(sealDataList);

        content.put("sealDataList", jsonArray);

        JSONObject jsonObject = new JSONObject();
        String dateTimeStr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        jsonObject.put("reqId", dateTimeStr);
        jsonObject.put("content", content);
        System.out.println(jsonObject);
        try {
            JSONObject res = HttpUtil.Post(baseUrl, jsonObject);
            if(res.get("code").equals(200)){
                JSONObject data = JSONObject.parseObject(res.getString("data"));
                String signTaskId = data.getString("signTaskId");
                System.out.println(data);
                return signTaskId;
            } else {
                return res.getString("msg");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String detachedPdf(String organizationCode, String scene, String fileUniqueId, String signTaskId, String fileHash, String sealType) {
        String baseUrl = url+"/mssg2/order/detachedPdf";

        JSONObject content = new JSONObject();
        content.put("scene", scene);
        content.put("grantCode", grantCode);
        content.put("organizationCode", organizationCode);
        content.put("systemID", systemID);
        content.put("signTaskId", signTaskId);

        JSONArray jsonArray = new JSONArray();
        JSONObject sealDataList =  new JSONObject();

        sealDataList.put("sealType", sealType);
        sealDataList.put("fileUniqueId", fileUniqueId);
        sealDataList.put("data", fileHash);

        jsonArray.add(sealDataList);

        content.put("sealDataList", jsonArray);

        JSONObject jsonObject = new JSONObject();
        String dateTimeStr = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        jsonObject.put("reqId", dateTimeStr);
        jsonObject.put("content", content);
        System.out.println(jsonObject);
        try {
            JSONObject res = HttpUtil.Post(baseUrl, jsonObject);
            if(res.get("code").equals(200)){
                JSONObject data = JSONObject.parseObject(res.getString("data"));
                JSONObject file = (JSONObject) data.getJSONArray("fileList").get(0);
                System.out.println(file.getString("fileB64"));
                return file.getString("fileB64");
            } else {
                return res.getString("msg");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

//    public static void main(String[] args){
//        List<String> list = SealUtil.genPdfHashByGrantCode("914403000000002626", "S02", "/Users/cyh/Desktop/work/1.pdf", "4");
//        String fileHash = list.get(0);
//        String fileUniqueId = list.get(1);
//        String signTaskId = SealUtil.addSignTask("914403000000002626", "S02", fileUniqueId, fileHash, "4");
//        System.out.println(fileHash);
//        System.out.println(fileUniqueId);
//        System.out.println(signTaskId);
//    }

    public static void main(String[] args){
        String fileb64 = SealUtil.detachedPdf("914403000000002626","S02", "119e6053992a4730a4f11be36df387bf", "16642598722178837","yeDfEadfHT8+krD5/yVsP5OaXQOF2KSlf02ArYb4BGI=", "4");
        FileUtil.generateBase64StringToFile(fileb64, "/Users/cyh/Desktop/sealed.pdf");
    }
}
