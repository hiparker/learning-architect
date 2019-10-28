package com.edwin.practive.springbootmvcframe.modules.sys.service.impl;

import com.edwin.practive.springbootmvcframe.common.utils.StringUtils;
import com.edwin.practive.springbootmvcframe.core.service.CrudService;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Menu;
import com.edwin.practive.springbootmvcframe.modules.sys.mapper.MenuMapper;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IMenuService;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Service(version = "1.0.0",timeout = 10000,interfaceClass = IMenuService.class)
@Transactional(readOnly = true)
public class MenuServiceImpl extends CrudService<MenuMapper,Menu> implements IMenuService  {

    private static final String ZERO = "0"; //顶级菜单
    private final static String YES = "1";
    private final static String NO = "0";

    private List<Menu> menuList;
    private Map<String,Menu> menuMap;


    /**
     * 增加/修改
     * @param menu
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public int save(Menu menu){

        if(StringUtils.isEmpty(menu.getId())){
            Menu parent = this.getMenuMap(menu.getParent().getId());
            menu.setParentIds(parent.getParentIds()+parent.getId()+",");
        }

        int count = super.save(menu);
        menuList.clear();
        menuMap.clear();
        this.findListAll();
        return count;
    }


    /**
     * 删除
     * @param menu
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int remove(Menu menu){
        if(menu.getParentId().equals(0)){
            return 0;
        }
        //删除权限
        mapper.deleteRoleMenu(menu);
        int count = super.delete(menu);
        menuList.clear();
        menuMap.clear();
        this.findListAll();
        return count;
    }
    @Override
    @Transactional(readOnly = false)
    public int remove(String id){
        Menu menu = this.get(id);
        if(menu.getParentId().equals(0)){
            return 0;
        }
        //删除权限
        mapper.deleteRoleMenu(menu);
        int count = super.delete(menu);
        menuList.clear();
        menuMap.clear();
        this.findListAll();
        return count;
    }

    /**
     * 批量查询
     * @param
     * @return
     */
    @Override
    public List<Menu> findListAll(){
        if(null == menuList || menuList.isEmpty()){
            menuList = Collections.synchronizedList(super.findAllList(new Menu()));
            menuMap = Collections.synchronizedMap(new HashMap<>());
            for (Menu menu : menuList) {
                menuMap.put(menu.getId(),menu);
            }
        }
        return menuList;
    }



    /**
     * 批量查询
     * @param menu
     * @return
     */
    @Override
    public Menu get(Menu menu){
        return super.get(menu);
    }




    // 获取菜单信息
    @Override
    public Map<String, Menu> getMenuMaps() {
        return menuMap;
    }

    // 获取菜单树信息
    @Override
    public Menu getMenuTrees(List<Menu> menus) {
        for (Menu menu : menus) {
            menu.setChildren(this.getChildsList(menu,menus));
        }
        //获取首个菜单
        for (Menu menu : menus) {
            if(menu.getId().equals("1")){
                return menu;
            }
        }
        return null;
    }

    // 获取菜单树信息
    private List<Menu> getChildsList(Menu parent,List<Menu> menus) {
        List<Menu> childs = new ArrayList<>();
        for (Menu menu : menus) {
            if(menu.getParentId().equals(parent.getId())){
                childs.add(menu);
            }
        }
        return childs;
    }
    // 递归处理菜单数据
    @Override
    public void recursionMenu(List<Menu> menus,Menu tree){
        if(tree != null){

            boolean saveFlag = false;

            // 目录/菜单
            if("0".equals(tree.getType()) || "1".equals(tree.getType())){
                if(YES.equals(tree.getIsVisible())){
                    menus.add(tree);
                    saveFlag = true;
                }
            }
            // 按钮
            if("2".equals(tree.getType())){
                menus.add(tree);
                saveFlag = true;
            }

            if(saveFlag){
                if(null != tree.getChildren() && tree.getChildren().size() > 0){
                    for (Menu menu : tree.getChildren()) {
                        // 菜单
                        if("0".equals(tree.getType()) || "1".equals(tree.getType())){
                            if(YES.equals(tree.getIsVisible())){
                                recursionMenu(menus,menu);
                            }
                        }
                        // 按钮
                        if("2".equals(tree.getType())){
                            recursionMenu(menus,menu);
                        }
                    }
                }
            }
        }
    }

    // 递归处理菜单数据
    @Override
    public List<Menu> recursionMenuReturn(List<Menu> menus,Menu tree){
        if(tree != null){

            boolean saveFlag = false;

            // 目录/菜单
            if("0".equals(tree.getType()) || "1".equals(tree.getType())){
                if(YES.equals(tree.getIsVisible())){
                    menus.add(tree);
                    saveFlag = true;
                }
            }
            // 按钮
            if("2".equals(tree.getType())){
                menus.add(tree);
                saveFlag = true;
            }

            if(saveFlag){
                if(null != tree.getChildren() && tree.getChildren().size() > 0){
                    for (Menu menu : tree.getChildren()) {
                        // 菜单
                        if("0".equals(tree.getType()) || "1".equals(tree.getType())){
                            if(YES.equals(tree.getIsVisible())){
                                recursionMenu(menus,menu);
                            }
                        }
                        // 按钮
                        if("2".equals(tree.getType())){
                            recursionMenu(menus,menu);
                        }
                    }
                }
            }
        }

        return menus;
    }

    @Override
    public void setParentMenu(Map<String,Menu> menuMapTemp , List<Menu> authCopy,Set<Menu> auth) {
        int count = 0;
        for (Menu menu : auth) {
            if(null == menuMapTemp.get(menu.getParentId())){
                Menu parM = this.getMenuMap(menu.getParentId());
                if(parM != null){
                    authCopy.add(this.getMenuMap(menu.getParentId()));
                    count ++;
                }
            }
        }
        //去重复
        removeDuplicate(authCopy);

        //创建一个KEY（id） 的权限MAP 用于后续快速匹配 避免几何循环
        Map<String,Menu> menuMapT = new HashMap<>();
        for (Menu menu : auth) {
            if(menu != null){
                menuMapT.put(menu.getId(),menu);
            }
        }

        if(count != 0){
            setParentMenu(menuMapT,authCopy,new HashSet<>(authCopy));
        }
    }
    // 权限去重
    private List<Menu> removeDuplicate(List<Menu> list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).getId().equals(list.get(i).getId()))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    @Override
    public Menu getMenuMap(String key) {
        Menu m = new Menu();
        if(menuMap == null){
            this.findListAll();
        }

        if(menuMap.get(key) != null){
            BeanUtils.copyProperties(menuMap.get(key),m);
            return m;
        }
        return null;
    }




}
