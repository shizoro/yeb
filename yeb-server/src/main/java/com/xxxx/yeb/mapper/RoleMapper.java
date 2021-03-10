package com.xxxx.yeb.mapper;

import com.xxxx.yeb.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id获取权限列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
