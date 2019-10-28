package com.edwin.practive.springbootmvcframe.modules.sys.web;

import com.edwin.practive.springbootmvcframe.common.utils.StringUtils;
import com.edwin.practive.springbootmvcframe.core.web.BaseController;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Role;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IRoleService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 角色 Controller
 * @author Edwin
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{


    @Reference(version = "1.0.0")
    IRoleService roleService;


    /**
     * 进入 默认查询一条数据
     * @param id
     * @return
     */
    @ModelAttribute
    public Role get(@RequestParam(required=false) String id) {
        Role entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = roleService.get(id);
        }
        if (entity == null){
            entity = new Role();
        }
        return entity;
    }

    @RequestMapping("form")
    public String form(Role role, ModelMap map){
        map.addAttribute("Role",role);
        return "modules/sys/role/role_save";
    }

    @RequestMapping("list")
    public String list(ModelMap map){
         return "modules/sys/role/role_list";
    }


    @RequestMapping("treeDataForm")
    public String treeDataForm(String selectIdFlag, ModelMap map){
        map.addAttribute("selectIdFlag",selectIdFlag);
        return "modules/sys/role/role_tree_select";
    }


}
