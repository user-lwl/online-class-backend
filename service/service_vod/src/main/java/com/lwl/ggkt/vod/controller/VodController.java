package com.lwl.ggkt.vod.controller;

import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.vod.service.VodService;
import com.lwl.ggkt.vod.utils.ConstantPropertiesUtil;
import com.lwl.ggkt.vod.utils.Signature;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @author user-lwl
 * @createDate 2022/12/3 16:43
 */
@Api(tags = "腾讯云点播")
@RestController
//@CrossOrigin
@RequestMapping("/admin/vod")
public class VodController {
    @Resource
    private VodService vodService;

    /**
     * 上传视频
     * @return 视频id
     */
    @PostMapping("/upload")
    public Result<Object> upload() {
        String filedId = vodService.uploadVideo();
        return Result.ok(filedId);
    }

    /**
     * 删除腾讯云视频
     * @param fileId 视频id
     * @return 是否成功
     */
    @DeleteMapping("/remove/{fileId}")
    public Result<Object> remove(@PathVariable String fileId) {
        vodService.removeVideo(fileId);
        return Result.ok();
    }

    /**
     * 返回客户端上传视频签名
     * @return 返回签名
     */
    @GetMapping("/sign")
    public Result<String> sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        Random random;
        try {
            random = SecureRandom.getInstanceStrong();
            sign.setRandom(random.nextInt(java.lang.Integer.MAX_VALUE));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            throw new GgktException(20001, "获取签名失败");
        }
    }
}
