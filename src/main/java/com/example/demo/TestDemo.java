package com.example.demo;

import com.example.demo.util.ImageUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.demo.util.ImageUtil.outputStreamFile;
import static com.example.demo.util.ImageUtil.readInputStream;

/**
 * @pathName：TestDemo
 * @author：ZhangWei
 */
public class TestDemo {

    public static void main(String[] args) throws Exception {
        //http://xxx/xxx/xxx.png(jpg等等)
        File file = new File("https://ss3.bdstatic.com/iPoZeXSm1A5BphGlnYG/skin/822.jpg?2");
        //http://xxx/xxx/xxx.png(jpg等等)
        URL url = new URL("https://ss3.bdstatic.com/iPoZeXSm1A5BphGlnYG/skin/822.jpg?2");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //通过输入流获取图片数据
        InputStream urlInputStream = conn.getInputStream();
        //获取图片的字节流
        byte[] imgBytes = ImageUtil.readInputStream(urlInputStream);
        //压缩图片
        byte[] minImgBytes = ImageUtil.compressPicForScale(imgBytes, 40);
        //将压缩后的字节流转换为输入流
        InputStream inputStream = new ByteArrayInputStream(minImgBytes);
        //获取图片字节流并写入磁盘
        outputStreamFile(readInputStream(inputStream), "D://20190426095107749847010.png");
    }

}
