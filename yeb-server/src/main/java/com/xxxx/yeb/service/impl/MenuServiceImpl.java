package com.xxxx.yeb.service.impl;

import com.xxxx.yeb.pojo.Admin;
import com.xxxx.yeb.pojo.Menu;
import com.xxxx.yeb.mapper.MenuMapper;
import com.xxxx.yeb.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cv工程师
 * @since 2021-03-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 通过用户id获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        // 得到登录用户的id
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        // 查询缓存是否有数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        if (CollectionUtils.isEmpty(menus)){
            // 如果没有数据，数据中查询，并设置到缓存中
            menus = menuMapper.getMenuByAdminId(adminId);
            valueOperations.set("menu_" + adminId,menus);
        }
        return menus;
    }
}
