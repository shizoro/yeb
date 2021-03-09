package com.xxxx.yeb.controller;


import com.xxxx.yeb.pojo.Menu;
import com.xxxx.yeb.service.IMenuService;
import com.xxxx.yeb.service.impl.MenuServiceImpl;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cv工程师
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/system")
public class MenuController {

    @Resource
    private MenuServiceImpl menuService;

    @ApiOperation(value = "通过用户id获取菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){
        return menuService.getMenuByAdminId();
    }

}
