package com.cqyc.manage.service.impl;

import com.cqyc.manage.comm.exception.CqycExceptions;
import com.cqyc.manage.comm.exception.ExceptionEnums;
import com.cqyc.manage.comm.prop.PdfProperties;
import com.cqyc.manage.comm.util.FileUtil;
import com.cqyc.manage.comm.util.OtherUtil;
import com.cqyc.manage.domain.vo.PdfUploadVo;
import com.cqyc.manage.service.FileService;
import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-11-3
 */
@Slf4j
@Service
@EnableConfigurationProperties(PdfProperties.class)
public class FileServiceImpl implements FileService {
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Value("${fastdfs.host}")
    private String hostIp;


    @Autowired
    private PdfProperties pdfProperties;

    /**
     * 上传文件
     *
     * @param file 上传的文件
     */
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                    FileUtil.subLastFileName(file.getOriginalFilename()), null);
            log.info("上传得到的fileUrl---->{}", storePath.getFullPath());
            return storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过fastdfs图片上传缩略图
     *
     * @param file 图片文件
     * @return
     */
    @Override
    public String uploadCrtThumbImage(MultipartFile file) {
        try {
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
                    FileUtil.subLastFileName(file.getOriginalFilename()), null);
            String fullPath = thumbImageConfig.getThumbImagePath(storePath.getFullPath());
            log.info("【图片缩略图带有组名的路径】:{}", fullPath);
            return fullPath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 查询fastdfs文件的信息
     *
     * @param groupName 指定哪一个组
     * @param path      文件存储的路径
     * @return
     */
    @Override
    public FileInfo fileInfo(String groupName, String path) {
        FileInfo fileInfo = storageClient.queryFileInfo(groupName, path);
        log.info("192.168.43.13得到上传文件的信息---->{}", fileInfo);
        return fileInfo;
    }

    /**
     * 删除文件
     *
     * @param filePath 给出文件路径
     */
    @Override
    public void deleteFile(String filePath) {
        storageClient.deleteFile(filePath);
    }

    /**
     * 下载文件
     *
     * @param groupName 指定哪一个组
     * @param path      文件存储的路径
     * @param fileName  下载的文件名
     * @return string
     */
    @Override
    public String downLoadFile(String groupName, String path, String fileName) {
        DownloadFileWriter callback = new DownloadFileWriter(fileName);
        String res = storageClient.downloadFile(groupName, path, callback);
        return res;
    }

    @Override
    public String pdfUpload(PdfUploadVo pdfUploadVo) {
        try {
            PdfReader pdfReader = new PdfReader(pdfProperties.getHttp() + pdfUploadVo.getFileId());
            //临时随机的pdf的路径
            String fileName = pdfProperties.getPath() + OtherUtil.getUuid() + ".pdf";
            //总页数
            int totalPage = pdfReader.getNumberOfPages();
            //获取每页的宽度和长度
            Rectangle pageSize = pdfReader.getPageSize(1);
            float height = pageSize.getHeight();
            float width = pageSize.getWidth();
            Image image = Image.getInstance(pdfProperties.getHttp() + pdfUploadVo.getFileImg());
            //校验
            if (pdfUploadVo.getPageNo() > totalPage || pdfUploadVo.getPageNo() <= 0) {
                throw new CqycExceptions(ExceptionEnums.PDF_CONVERSION_ERROR);
            } else if (image.getWidth() + pdfUploadVo.getOffsetX() > width ||
                    image.getHeight() + pdfUploadVo.getOffsetY() > height) {
                //图片本身的宽度或者高度加上偏移量不能大于pdf本身的宽度或者高度
                throw new CqycExceptions(ExceptionEnums.PDF_WIDTH_HEIGHT_ERROR);
            }

            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(fileName));
            //当前的页数
            PdfContentByte under = pdfStamper.getOverContent(pdfUploadVo.getPageNo());
            //设置图片大小
///          image.scaleAbsolute(signRect.getWidth(), signRect.getHeight());
            //设置图片位置
            image.setAbsolutePosition(pdfUploadVo.getOffsetX(), pdfUploadVo.getOffsetY());
            under.addImage(image);
            //设置不可编辑
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();

            //读取临时文件，将文件上传到fastdfs中,然后删除文件
            File file = new File(fileName);
            StorePath storePath = storageClient.uploadFile(new FileInputStream(file), file.length(),
                    FileUtil.subLastFileName(fileName), null);
            String fileUrl = storePath.getFullPath();
            //删除文件
            file.deleteOnExit();
            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件服务系统出现异常,请稍后重试");
        }
    }
}
