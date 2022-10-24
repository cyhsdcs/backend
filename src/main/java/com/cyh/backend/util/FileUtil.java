package com.cyh.backend.util;

import java.io.*;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;


@Component
public class FileUtil {
    /**
     * 将文件转换成Base64编码
     * 将文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param localFilePath 待处理图片
     * @return
     */
    public static String getFileBase64StrByLocalFile(String localFilePath){
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(localFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @return
     */
    public static boolean generateBase64StringToFile(String fileStr,String fileFilePath){
        if (fileStr == null) //图像数据为空
            return false;
        try
        {
            //Base64解码
            byte[] b = Base64.decodeBase64(fileStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(fileFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static void main(String[] args) {
        String base64 = FileUtil.getFileBase64StrByLocalFile("C:/Users/ChenYuanhong/Desktop/test.pdf");
        System.out.println(base64);
    }

}
