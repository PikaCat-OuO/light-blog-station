package com.pikacat.blog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {

    // 文件的保存路径
    private static String uploadPath;

    // 保存图片
    public static String saveImage(MultipartFile file) {
        return saveToDisk(file, List.of(".JPG", ".JPEG", ".GIF", ".PNG"));
    }

    // 保存文件
    private static String saveToDisk(MultipartFile file, List<String> extensions) {
        if (file.isEmpty()) {
            return "文件内容为空";
        }
        // 获得原文件名
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            // 检查文件扩展名是否符合规范
            String fileExtension = fileName.substring(fileName.lastIndexOf("."));
            if (!extensions.contains(fileExtension.toUpperCase())) {
                return "文件扩展名不符合规格";
            }
            // UUID随机取文件名，防止上传WebShell
            fileName = UUID.randomUUID() + fileExtension;
            File diskLocation = new File(uploadPath + fileName);
            // 检查有无对应的目录，如果没有就创建对应的目录
            if (!diskLocation.getParentFile().exists()) {
                if (!diskLocation.getParentFile().mkdirs()) {
                    // 如果创建失败就返回
                    return "文件保存失败";
                }
            }
            // 保存到磁盘
            try {
                file.transferTo(diskLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "/" + fileName;
        }
        return "文件名为空";
    }

    @Value("${upload-path}")
    public void setUploadPath(String uploadPath) {
        FileUtil.uploadPath = uploadPath;
    }
}
