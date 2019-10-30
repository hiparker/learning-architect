package com.edwin.smartdevelop.modules.sys.service;

import com.edwin.smartdevelop.modules.sys.dict.MenuDict;
import com.edwin.smartdevelop.modules.sys.entity.Menu;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 菜单本地处理Service
 * @author Edwin
 * @date 2019年10月30日11:55:52
 */
@Component
@Service
@Transactional(readOnly = true)
public class MenuService {

    //顶级菜单
    private static final String ZERO = "0";
    private final static String YES = "1";
    private final static String NO = "0";

    private List<Menu> menuList;
    private Map<String,Menu> menuMap;

    @Reference(version = "1.0.0")
    IMenuService iMenuService;

    /**
     * 批量查询
     * @param
     * @return
     */
    public List<Menu> findListAll(){
        if(null == menuList || menuList.isEmpty()){
            menuList = Collections.synchronizedList(iMenuService.findAllList(new Menu()));
            menuMap = Collections.synchronizedMap(new HashMap<>());
            for (Menu menu : menuList) {
                menuMap.put(menu.getId(),menu);
            }
        }
        return menuList;
    }


    /**
     * 递归处理菜单数据
     * @param menus
     * @param tree
     */
    public void recursionMenu(List<Menu> menus, Menu tree){
        if(tree != null){

            boolean saveFlag = false;

            // 目录/菜单
            if(MenuDict.DIRECTORY.getValue().equals(tree.getType()) || MenuDict.MENU.getValue().equals(tree.getType())){
                if(YES.equals(tree.getIsVisible())){
                    menus.add(tree);
                    saveFlag = true;
                }
            }
            // 按钮
            if(MenuDict.BUTTON.getValue().equals(tree.getType())){
                menus.add(tree);
                saveFlag = true;
            }

            if(saveFlag){
                if(null != tree.getChildren() && tree.getChildren().size() > 0){
                    for (Menu menu : tree.getChildren()) {
                        // 菜单
                        if(MenuDict.DIRECTORY.getValue().equals(tree.getType()) || MenuDict.MENU.getValue().equals(tree.getType())){
                            if(YES.equals(tree.getIsVisible())){
                                recursionMenu(menus,menu);
                            }
                        }
                        // 按钮
                        if(MenuDict.BUTTON.getValue().equals(tree.getType())){
                            recursionMenu(menus,menu);
                        }
                    }
                }
            }
        }
    }


    public void setParentMenu(Map<String,Menu> menuMapTemp , List<Menu> authCopy, Set<Menu> auth) {
        int count = 0;
        for (Menu menu : auth) {
            if(null != menu && null == menuMapTemp.get(menu.getParentId())){
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

    /**
     * 权限去重
     * @param list
     * @return
     */
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
