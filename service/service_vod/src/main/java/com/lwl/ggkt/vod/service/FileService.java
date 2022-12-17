package com.lwl.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 文件上传
     * @param file 文件
     * @return 文件地址
     */
    String upload(MultipartFile file);
}