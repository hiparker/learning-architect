package com.edwin.practive.springbootmvcframe.modules.sys.webrest;

import com.edwin.practive.springbootmvcframe.common.json.AjaxJson;
import com.edwin.practive.springbootmvcframe.common.utils.StringUtils;
import com.edwin.practive.springbootmvcframe.core.web.BaseController;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Menu;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Role;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IMenuService;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IRoleService;
import com.edwin.practive.springbootmvcframe.modules.sys.service.MenuService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Menu RestController
 * @author Edwin
 */
@RestController
@RequestMapping("api/v1.0.0/menu")
public class MenuRestController extends BaseController {


    @Reference(version = "1.0.0")
    IMenuService menuService;
    @Reference(version = "1.0.0")
    IRoleService roleService;

    @Autowired
    MenuService menuServiceT;

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

    @GetMapping("treeByRoleData")
    public List<Map<String, Object>> treeByRoleData(String selectIdFlag,Menu menu,ModelMap modelMap){

        List<Menu> menusTemp = menuService.findListAll();
        List<Menu> menus = new ArrayList<>();

        Menu tree = menuService.getMenuTrees(menusTemp);
        // 递归处理数据
        menuServiceT.recursionMenu(menus,tree);


        String[] menuIds = roleService.getRoleMenu(new Role(selectIdFlag));

        // 选中数量
        int count = 0;

        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (Menu me : menus) {
            // 获取父节点元素
            //me.setParent(menuService.getMenuMap(me.getParentId()));

            Map<String, Object> map = Maps.newHashMap();
            map.put("id", me.getId());
            if("0".equals(me.getParentId())){
                map.put("parent", "#");
                Map<String, Object> state = Maps.newHashMap();
                state.put("opened", true);
                map.put("state", state);

            }else{
                /*if(i == 0){
                    map.put("parent", "#");
                }else{
                    map.put("parent", e.getParentId());
                }*/
                map.put("parent", me.getParentId());
            }

            // 处理 角色菜单
            // 选中
            List<String> selectIdsList = new ArrayList<>(Arrays.asList(menuIds));
            if(null != selectIdsList && selectIdsList.contains(me.getId())){
                Map<String, Object> state = Maps.newHashMap();
                state.put("opened", true);
                state.put("selected", true);
                map.put("state", state);
                count++;
            }



            if(StringUtils.isNotBlank(me.getIcon())){
                map.put("icon", me.getIcon());
            }
            if("2".equals(me.getType())){
                map.put("type", "btn");
            }else if("1".equals(me.getType())){
                map.put("type", "menu");
            }else{
                map.put("type", "menu");
            }
            map.put("text", me.getName());
            map.put("name", me.getName());

            mapList.add(map);
        }

        return mapList;
    }


    @GetMapping("treeData")
    public List<Map<String, Object>> treeData(String selectIdFlag,Menu menu,ModelMap modelMap){

        List<Menu> menus = menuService.findListAll();

        List<Map<String, Object>> mapList = Lists.newArrayList();
        for (Menu me : menus) {
            // 获取父节点元素
            me.setParent(menuService.getMenuMap(me.getParentId()));

            Map<String, Object> map = Maps.newHashMap();
            map.put("id", me.getId());
            if("0".equals(me.getParentId())){
                map.put("parent", "#");
                Map<String, Object> state = Maps.newHashMap();
                state.put("opened", true);
                map.put("state", state);

            }else{
                /*if(i == 0){
                    map.put("parent", "#");
                }else{
                    map.put("parent", e.getParentId());
                }*/
                map.put("parent", me.getParentId());
            }

            // 处理 角色菜单

            /*int count = 0;
            for (int i1 = 0; i1 < list.size(); i1++) {
                if (list.get(i1).getParentId().equals(e.getId())){
                    count ++;
                }
            }
            if(menuIds.contains(","+e.getId()+",")&& count == 0){
                Map<String, Object> state = Maps.newHashMap();
                state.put("selected", true);
                map.put("state", state);
            }*/

            if(StringUtils.isNotBlank(me.getIcon())){
                map.put("icon", me.getIcon());
            }
            if("2".equals(me.getType())){
                map.put("type", "btn");
            }else if("1".equals(me.getType())){
                map.put("type", "menu");
            }else{
                map.put("type", "menu");
            }
            map.put("text", me.getName());
            map.put("name", me.getName());

            mapList.add(map);
        }
        return mapList;

    }


    @GetMapping("data")
    public List<Menu> data(Menu menu, ModelMap map){
        return menuService.findListAll();
    }



    @PostMapping("save")
    public AjaxJson save(Menu menu, ModelMap map){
        AjaxJson j = new AjaxJson();

        /**
         * 后台hibernate-validation 插件校验
         */
        String errMsg = beanValidator(menu);
        if (StringUtils.isNotBlank(errMsg)){
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }

        menuService.save(menu);
        j.setMsg("保存成功！");
        return j;
    }

    @PostMapping("del")
    public AjaxJson del(Menu menu, ModelMap map){
        AjaxJson j = new AjaxJson();
        menuService.remove(menu.getId());
        j.setMsg("删除成功！");
        return j;
    }

    @PostMapping("delAll")
    public AjaxJson delAll(@RequestBody List<Map<String,String>>  list, ModelMap map){
        AjaxJson j = new AjaxJson();
        String[] idArray = new String[list.size()];
        for(int i = 0; i<list.size(); i++){
            idArray[i] = list.get(i).get("id");
        }

        for(String id : idArray){
            menuService.remove(id);
        }
        j.setMsg("删除成功");
        return j;
    }

}
