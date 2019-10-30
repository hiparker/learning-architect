package com.edwin.smartdevelop.modules.sys.service;


import com.edwin.smartdevelop.core.service.CrudInterface;
import com.edwin.smartdevelop.modules.sys.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单Service
 * @author Edwin
 * @date 2019年10月30日11:31:16
 */
public interface IMenuService extends CrudInterface<Menu> {

    /**
     * 查询全部菜单集合
     * @return
     */
    List<Menu> findListAll();

    /**
     * 获得菜单Map集合
     * @return
     */
    Map<String, Menu> getMenuMaps();

    /**
     * 获得菜单树
     * @param menus
     * @return
     */
    Menu getMenuTrees(List<Menu> menus);

    /**
     * 根据key 获得菜单Map
     * @param key
     * @return
     */
    Menu getMenuMap(String key);

    /**
     * 设置父节点菜单
     * @param menuMapTemp
     * @param authCopy
     * @param auth
     */
    void setParentMenu(Map<String, Menu> menuMapTemp, List<Menu> authCopy, Set<Menu> auth);

    /**
     * 递归菜单
     * @param menus
     * @param tree
     */
    void recursionMenu(List<Menu> menus, Menu tree);

    /**
     * 删除菜单 根据对象
     * @param menu
     * @return
     */
    int remove(Menu menu);

    /**
     * 删除菜单 根据Id
     * @param id
     * @return
     */
    int remove(String id);
}
