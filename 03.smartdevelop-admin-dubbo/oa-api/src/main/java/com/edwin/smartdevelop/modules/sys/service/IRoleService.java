package com.edwin.smartdevelop.modules.sys.service;


import com.edwin.smartdevelop.core.service.CrudInterface;
import com.edwin.smartdevelop.modules.sys.entity.Role;

/**
 * 角色Service
 * @author Edwin
 * @date 2019年10月30日11:31:16
 */
public interface IRoleService   extends CrudInterface<Role> {

    /**
     * 保存角色菜单
     * @param role
     * @return
     */
    int roleMenuSave(Role role);

    /**
     * 获取角色菜单
     * @param role
     * @return
     */
    String[] getRoleMenu(Role role);

}
