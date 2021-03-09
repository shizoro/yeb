package com.xxxx.yeb.service;

import com.xxxx.yeb.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cv工程师
 * @since 2021-03-08
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 通过用户id获取菜单列表
     * @return
     */
    List<Menu> getMenuByAdminId();

}
