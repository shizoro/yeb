package com.xxxx.yeb.mapper;

import com.xxxx.yeb.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cv工程师
 * @since 2021-03-08
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    List<Menu> getMenuByAdminId(Integer id);

    /**
     * 通过权限获取菜单列表
     * @return
     */
    List<Menu> getAllMenusByRole();
}
