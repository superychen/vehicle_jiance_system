package com.cqyc.manage.comm.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-11-3
 */
public class FileUtil {


    /**
     * 获取文件的后缀名
     *
     * @param filename 文件名
     * @return string
     */
    public static String subLastFileName(String filename) {
        String res = StringUtils.substringAfterLast(filename, ".");
        if (StringUtils.isBlank(res)) {
            throw new RuntimeException("获取文件后缀名为空,请仔细检查");
        }
        return res;
    }


    /**
     * 判断文件类型是否跟传递的数组中类型匹配
     *
     * @param filename 文件名
     * @param verArray 需要校验的类型数组
     * @return string
     */
    public static boolean isFileType(String filename, String[] verArray) {
        // 文件名称为空的场合
        if (StringUtils.isBlank(filename)) {
            // 返回不和合法
            return false;
        }
        // 获得文件后缀名
        String tmpName = subLastFileName(filename);
        // 遍历名称数组
        for (int i = 0; i < verArray.length; i++) {
            // 判断单个类型文件的场合
            if (StringUtils.equals(verArray[i], tmpName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取文件的byte数组
     */
    public static byte[] getFileByteArray(String fileName) {
        File file = new File(fileName);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        byte[] buffer = null;
        try (FileInputStream fi = new FileInputStream(file)) {
            buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file(无法读取文件) "
                        + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
