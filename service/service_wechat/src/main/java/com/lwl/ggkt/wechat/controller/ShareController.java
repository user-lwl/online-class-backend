package com.lwl.ggkt.wechat.controller;

import com.lwl.ggkt.result.Result;
import com.lwl.ggkt.utils.AuthContextHolder;
import com.lwl.ggkt.utils.Base64Util;
import com.lwl.ggkt.vo.wechat.WxJsapiSignatureVo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/wechat/share")
@Slf4j
public class ShareController {

    @Resource
    private WxMpService wxMpService;

    /**
     * 分享
     * @param url url
     * @return WxJsapiSignatureVo
     * @throws WxErrorException WxErrorException
     */
    @GetMapping("/getSignature")
    public Result<WxJsapiSignatureVo> getSignature(@RequestParam("url") String url) throws WxErrorException {
        String currentUrl = url.replace("guiguketan", "#");
        WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(currentUrl);

        WxJsapiSignatureVo wxJsapiSignatureVo = new WxJsapiSignatureVo();
        BeanUtils.copyProperties(jsapiSignature, wxJsapiSignatureVo);
        wxJsapiSignatureVo.setUserEedId(Base64Util.base64Encode(AuthContextHolder.getUserId()+""));
        return Result.ok(wxJsapiSignatureVo);
    }

}