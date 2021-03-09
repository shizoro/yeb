package com.xxxx.yeb.controller;

import com.xxxx.yeb.pojo.Admin;
import com.xxxx.yeb.pojo.AdminLoginParam;
import com.xxxx.yeb.pojo.RespBean;
import com.xxxx.yeb.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录控制器
 * @author arthur
 * @date 2021/3/8 20:01
 */
@RestController
@Api(tags = "LoginController")
public class LoginController {

    @Resource
    private IAdminService adminService;

    @ApiOperation(value = "登录成功返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam,HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), adminLoginParam.getCode(), request);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("退出的登录成功");
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        // 可以获取Principal的原因是在登录判断时更新security上下文的用户对象
        if (null == principal){
            return null;
        }
        Admin admin = adminService.getAdminByUserName(principal.getName());
        // 返回前段时将密码设置为null，提高安全性
        admin.setPassword(null);
        return admin;
    }
}
