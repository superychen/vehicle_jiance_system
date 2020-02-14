package com.cqyc.manage.controller;

import com.cqyc.manage.comm.util.FileUtil;
import com.cqyc.manage.domain.CommEntity;
import com.cqyc.manage.domain.vo.PdfUploadVo;
import com.cqyc.manage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: cqyc
 * Description:
 * Created by cqyc on 19-11-3
 */
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传
     */
    @PostMapping("/upload")
    public CommEntity uploadFile(@RequestParam("file") MultipartFile[] files) {
        List<String> lists = new ArrayList<>();
        if (files.length > 3 || files.length == 0) {
            return CommEntity.create("文件不能为空或者不能上传三个文件以上", 500);
        }
        for (int i = 0; i < files.length; i++) {
            String filePath = fileService.uploadFile(files[i]);
            lists.add(filePath);
        }
        return CommEntity.create(lists, 200);
    }


    /**
     * 上传缩略图
     */
    @PostMapping("/upload/tumb")
    public CommEntity uploadTumbPic(@RequestParam("file") MultipartFile file) {
        String[] pic = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        if (FileUtil.isFileType(file.getOriginalFilename(), pic)) {
            return CommEntity.create(fileService.uploadCrtThumbImage(file), 200);
        } else {
            throw new RuntimeException("文件类型出错");
        }
    }

    /**
     * 得到上传文件的类型
     */
    @GetMapping("/info")
    public CommEntity fileInfo(@RequestParam("filePath") String filePath) {
        //group1/M00/00/00/wKgrDV2b7liAdNk2AA6-Ihmij-I692.jpg
        String groupName = StringUtils.substringBefore(filePath, "/");
        String path = StringUtils.substringAfter(filePath, "/");
        if (StringUtils.isAnyBlank(groupName, path)) {
            throw new RuntimeException("得到的文件路径错误");
        }
        return CommEntity.create(fileService.fileInfo(groupName, path), 200);
    }


    @DeleteMapping("/del/file")
    public CommEntity deleteFile(@RequestParam("filePath") String filePath) {
        fileService.deleteFile(filePath);
        return CommEntity.create("删除成功", 200);
    }

    @PostMapping("/download")
    public CommEntity downLoadFile(@RequestParam("filePath") String filePath) {
        String groupName = StringUtils.substringBefore(filePath, "/");
        String path = StringUtils.substringAfter(filePath, "/");
        String lastFileName = FileUtil.subLastFileName(filePath);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + lastFileName;
        return CommEntity.create(fileService.downLoadFile(groupName, path, fileName), 200);
    }


    /**
     * 修改pdf
     *
     * @return
     */
    @PostMapping("/pdf/upload")
    public CommEntity pdfUpload(@RequestBody @Validated PdfUploadVo pdfUploadVo, BindingResult result) {
        if (result.hasFieldErrors()) {
            log.error("修改pdf时出现异常,---->{}", result.getFieldErrors().get(0).getDefaultMessage());
            return CommEntity.create("上传参数出现异常", 500);
        }
        log.info("pdf地址:{}-----图片地址:{}--", pdfUploadVo.getFileId(), pdfUploadVo.getOffsetX());
        String targetStr = "pdfs/";
        pdfUploadVo.setFileId(StringUtils.substringAfter(pdfUploadVo.getFileId(), targetStr));
        pdfUploadVo.setFileImg(StringUtils.substringAfter(pdfUploadVo.getFileImg(), targetStr));
        String filePath = fileService.pdfUpload(pdfUploadVo);
        return CommEntity.create(filePath, 200);
    }

}
