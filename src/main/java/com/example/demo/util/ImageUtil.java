package com.example.demo.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

/**
 * 图片工具类
 * 使用谷歌图片开源工具，来实现图片压缩
 * 同时支持图片缩放，区域裁剪，水印，旋转，保持比例等等（暂未实现）
 *
 * @pathName：ImageUtil
 * @author：ZhangWei
 */
public class ImageUtil {

    /**
     * 将字节流写入本地磁盘中
     *
     * @param img      图片字节流
     * @param pathName 生成图片目标地址
     *                 需完整路径 + 名称
     *                 注意linux下和windows下路径写法不同
     *                 例：d:\\path.jpg
     */
    public static void outputStreamFile(byte[] img, String pathName) {
        try {
            File file = new File(pathName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
            System.out.println("图片已经写入到D盘");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换输入流为字节数组
     *
     * @param inStream 输入流
     * @return 转换后的字节数组
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return null;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            System.out.println("【图片压缩】| 图片原大小={" + srcSize / 1024 + "}kb | 压缩后大小={" + imageBytes.length / 1024 + "}kb" +
                    " | ");
        } catch (Exception e) {
            System.out.println("【图片压缩】msg=图片压缩失败!" + e);
        }
        return imageBytes;
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

}
