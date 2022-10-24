package com.cyh.backend.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Component
public class JsonUtil {
    /**
     * 读取JSON文件转换为字符串
     * @param filePath
     * @return
     */
    public static String readJsonFile(String filePath) {
        File file = null;
        StringBuilder sb = new StringBuilder();
        try {
            file = ResourceUtils.getFile(filePath);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    sb.append(readLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    public static void main(String []args){

        System.out.println(readJsonFile("classpath:static/menu.json"));
    }
}
