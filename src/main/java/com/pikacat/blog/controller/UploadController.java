package com.pikacat.blog.controller;

import com.pikacat.blog.util.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

    // 文件上传接口
    @PostMapping("")
    public Map<String, Object> fileUpload(@RequestParam("edit") MultipartFile file) {
        Map<String, Object> modelMap = new HashMap<>();
        String location = FileUtil.saveImage(file);
        if (location.startsWith("/")) {
            modelMap.put("code", 0);
            modelMap.put("msg", "上传成功");
            modelMap.put("data", location);
        } else {
            modelMap.put("code", 1);
            modelMap.put("msg", location);
        }
        return modelMap;
    }

}
