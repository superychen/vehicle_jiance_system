package com.cqyc.manage.service;

import com.cqyc.manage.domain.vo.PdfUploadVo;
import com.github.tobato.fastdfs.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-11-3
 */
public interface FileService {

    /**
     * 通过fastdfs上传文件
     *
     * @param file 上传的文件
     * @return string
     */
    String uploadFile(MultipartFile file);

    /**
     * 通过fastdfs图片上传缩略图
     *
     * @param file 图片文件
     * @return stirng
     */
    String uploadCrtThumbImage(MultipartFile file);

    /**
     * 查询fast文件的信息
     *
     * @param groupName 指定哪一个组
     * @param path      文件存储的路径
     * @return string
     */
    FileInfo fileInfo(String groupName, String path);

    /**
     * 删除文件
     *
     * @param filePath 给出文件路径
     */
    void deleteFile(String filePath);

    /**
     * 下载文件
     *
     * @param groupName 指定哪一个组
     * @param path      文件存储的路径
     * @param fileName  下载的文件名
     * @return string
     */
    String downLoadFile(String groupName, String path, String fileName);

    /**
     * 往pdf添加图片
     *
     * @param pdfUploadVo 修改pdf时需要的vo类
     * @return
     */
    String pdfUpload(PdfUploadVo pdfUploadVo);
}
