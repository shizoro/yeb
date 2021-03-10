package com.xxxx.yeb.service;

import com.xxxx.yeb.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.yeb.pojo.RespBean;
import com.xxxx.yeb.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

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
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id获取权限列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
