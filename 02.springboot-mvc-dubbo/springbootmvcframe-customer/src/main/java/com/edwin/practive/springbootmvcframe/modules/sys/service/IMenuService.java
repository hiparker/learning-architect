package com.edwin.practive.springbootmvcframe.modules.sys.service;

import com.edwin.practive.springbootmvcframe.core.service.CrudInterface;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMenuService extends CrudInterface<Menu> {

    List<Menu> findListAll();

    Map<String, Menu> getMenuMaps();

    Menu getMenuTrees(List<Menu> menus);

    Menu getMenuMap(String key);

    void setParentMenu(Map<String,Menu> menuMapTemp , List<Menu> authCopy,Set<Menu> auth);

    void recursionMenu(List<Menu> menus,Menu tree);

}
