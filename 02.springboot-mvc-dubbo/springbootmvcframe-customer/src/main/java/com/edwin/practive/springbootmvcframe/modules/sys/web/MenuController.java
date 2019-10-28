package com.edwin.practive.springbootmvcframe.modules.sys.web;

import com.edwin.practive.springbootmvcframe.common.utils.StringUtils;
import com.edwin.practive.springbootmvcframe.core.web.BaseController;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Menu;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IMenuService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Menu Controller
 * @author Edwin
 */
@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {


    @Reference(version = "1.0.0")
    IMenuService menuService;

    /**
     * 进入 默认查询一条数据
     * @param id
     * @return
     */
    @ModelAttribute
    public Menu get(@RequestParam(required=false) String id) {
        Menu entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = menuService.get(id);
        }
        if (entity == null){
            entity = new Menu();
        }
        return entity;
    }

    @RequestMapping("form")
    public String form(Menu menu,String flag,ModelMap map){
        menu.setParent(menuService.getMenuMap(menu.getParentId()));

        if("add".equals(flag)){
            Menu parent = menu;
            menu = new Menu();
            menu.setParent(parent);
            menu.setParentId(parent.getId());
        }

        map.addAttribute("menu",menu);
        return "modules/sys/menu/menu_save";
    }

    @RequestMapping("treeDataForm")
    public String treeDataForm(String selectIds,Menu menu,ModelMap map){
        map.addAttribute("selectIds",selectIds);
        map.addAttribute("menu",menu);
        return "modules/sys/menu/menu_tree_select";
    }

    @RequestMapping("treeDataByRoleForm")
    public String treeDataByRoleForm(String selectIdFlag,Menu menu,ModelMap map){
        map.addAttribute("selectIdFlag",selectIdFlag);
        return "modules/sys/menu/menu_role_tree_select";
    }

    @RequestMapping("list")
    public String list(ModelMap map){
         return "modules/sys/menu/menu_list";
    }

}
