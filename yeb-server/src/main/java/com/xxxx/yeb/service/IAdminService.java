package com.xxxx.yeb.service;

import com.xxxx.yeb.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.yeb.pojo.RespBean;

import java.security.Principal;

/**
 * 登录逻辑
 * @author zoro
 * @since 2021-03-08
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录成功返回token
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);
}
