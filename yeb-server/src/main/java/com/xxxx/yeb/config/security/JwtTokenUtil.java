package com.xxxx.yeb.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token工具类
 * @author arthur
 * @date 2021/3/8 17:11
 */
@Component
public class JwtTokenUtil {

    private final String CLAIM_KEY_USERNAME = "username";
    private final String CLAIM_KEY_CREATED = "created";
    /**
     * JWT 加解密使用的密钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * JWT的超期限时间（60*60*24）
     */
    @Value("$jwt.expiration")
    private Long expiration;

    /**
     * 根据用户信息生成 Token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 根据负载生成JWT Token
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.ES512,secret)
                .setExpiration(generateExpiration())
                .compact();
    }

    /**
     * 生成失效时间
     * @return
     */
    private Date generateExpiration() {
        return new Date(System.currentTimeMillis() + expiration * 100);
    }

    /**
     * 解析Token
     * 从token中获取JWT的负载
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据Token获取失效时间
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 从token中获取登录用户名
     * @param token
     * @return
     */
    public String getUserNameFormToken(String token){
        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            // 获取name
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

    /**
     * 验证令牌
     * 验证Token是否失效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){
        // 获取name
        String userName = getUserNameFormToken(token);
        // 判断name和失效时间是否为true
        return userName.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    /**
     * 判断Token是否失效
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token){
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 判断token是否刷新，如果没有失效则可以刷新
     * @param token
     * @return
     */
    public boolean canRefresh(String token){
        // 返回失效时间
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    
}
