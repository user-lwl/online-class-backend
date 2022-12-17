package com.lwl.ggkt.vod.service.impl;

import com.lwl.ggkt.exception.GgktException;
import com.lwl.ggkt.model.vod.Video;
import com.lwl.ggkt.vod.service.VideoService;
import com.lwl.ggkt.vod.service.VodService;
import com.lwl.ggkt.vod.utils.ConstantPropertiesUtil;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author user-lwl
 * @createDate 2022/12/3 16:43
 */
@Service
public class VodServiceImpl implements VodService {
    @Resource
    private VideoService videoService;

    @Value("${tencent.video.appid}")
    private String appId;

    /**
     * 上传视频接口
     * @return 视频id
     */
    @Override
    public String uploadVideo() {
        VodUploadClient client = new VodUploadClient(
                ConstantPropertiesUtil.ACCESS_KEY_ID,
                ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        VodUploadRequest request = new VodUploadRequest();
        //视频本地地址
        request.setMediaFilePath("D:\\001.mp4");
        //指定任务流
        request.setProcedure("LongVideoPreset");
        try {
            //调用上传方法，传入接入点地域及上传请求。
            VodUploadResponse response = client.upload("ap-guangzhou", request);
            //返回文件id保存到业务表，用于控制视频播放
            return response.getFileId();
        } catch (Exception e) {
            throw new GgktException(20001, "上传视频失败");
        }
    }

    /**
     * 删除腾讯云视频
     * @param fileId 视频id
     */
    @Override
    public void removeVideo(String fileId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            Credential cred = new Credential(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET
            );
            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, "");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId("videoSourceId");
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(AbstractModel.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            throw new GgktException(20001, "删除视频失败");
        }
    }

    /**
     * 腾讯云点播视频
     * @param courseId 课程id
     * @param videoId 视频id
     * @return 视频播放凭证
     */
    @Override
    public Map<String, Object> getPlayAuth(Long courseId, Long videoId) {
        //根据小节id获取小节对象，获取腾讯云视频id
        Video video = videoService.getById(videoId);
        if(video == null) {
            throw new GgktException(20001,"小节信息不存在");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("videoSourceId",video.getVideoSourceId());
        map.put("appId",appId);
        return map;
    }
}
