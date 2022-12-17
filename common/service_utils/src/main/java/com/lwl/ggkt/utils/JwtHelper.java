package com.lwl.ggkt.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {
    //token字符串有效时间
    private static final long TOKEN_EXPIRATION = (long) 24*60*60*1000;
    //加密编码秘钥
    private static final String TOKEN_SIGN_KEY = "123456";

    //根据userid  和  username 生成token字符串
    public static String createToken(Long userId, String userName) {
        return Jwts.builder()
                //设置token分类
                .setSubject("GGKT-USER")
                //token字符串有效时长
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                //私有部分（用户信息）
                .claim("userId", userId)
                .claim("userName", userName)
                //根据秘钥使用加密编码方式进行加密，对字符串压缩
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    //从token字符串获取userid
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }

    //从token字符串获取getUserName
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "lucy");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}