package com.edwin.practive.springbootmvcframe.modules.sys.web;

import com.edwin.practive.springbootmvcframe.common.json.AjaxJson;
import com.edwin.practive.springbootmvcframe.common.utils.StringUtils;
import com.edwin.practive.springbootmvcframe.core.persistence.Page;
import com.edwin.practive.springbootmvcframe.core.web.BaseController;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.User;
import com.edwin.practive.springbootmvcframe.modules.sys.service.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * User Controller
 * @author Edwin
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{


    @Reference(version = "1.0.0")
    IUserService userService;

    /**
     * 进入 默认查询一条数据
     * @param id
     * @return
     */
    @ModelAttribute
    public User get(@RequestParam(required=false) String id) {
        User entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = userService.get(id);
        }
        if (entity == null){
            entity = new User();
        }
        return entity;
    }

    @RequestMapping("form")
    public String form(User user,ModelMap map){
        map.addAttribute("user",user);
        return "modules/sys/user/user_save";
    }

    @RequestMapping("info")
    public String info(HttpServletRequest request, ModelMap map){
        User user = (User) request.getSession().getAttribute("user");
        map.addAttribute("user",user);
        return "modules/sys/user/user_info";
    }

    @RequestMapping("updatePasswordForm")
    public String updatePasswordForm(HttpServletRequest request, ModelMap map){
        User user = (User) request.getSession().getAttribute("user");
        map.addAttribute("user",user);
        return "modules/sys/user/user_editpwd";
    }

    @RequestMapping("updatePassword")
    @ResponseBody
    public AjaxJson updatePassword(User user, HttpServletRequest request, ModelMap map){
        return userService.updatePassword(user);
    }


    @RequestMapping("list")
    public String list(ModelMap map){
         return "modules/sys/user/user_list";
    }

    @RequestMapping("data")
    @ResponseBody
    public Map<String, Object> data(HttpServletRequest request, Page page, User user, ModelMap map){
        User sessionUser = (User) request.getSession().getAttribute("user");
        // 超级管理员
        if("0".equals(sessionUser.getId())){
            user.setAdmin(true);
        }
        //简单分页
        Page pageInfo = userService.findPage(page,user);
        return getBootstrapData(pageInfo);
    }



    @PostMapping("save")
    @ResponseBody
    public AjaxJson save(User user,ModelMap map){
        AjaxJson j = new AjaxJson();

        /**
         * 后台hibernate-validation 插件校验
         */
        String errMsg = beanValidator(user);
        if (StringUtils.isNotBlank(errMsg)){
            j.setSuccess(false);
            j.setMsg(errMsg);
            return j;
        }

        userService.save(user);
        j.setMsg("保存成功！");
        return j;
    }

    @RequestMapping("del")
    @ResponseBody
    public AjaxJson del(User user,ModelMap map){
        AjaxJson j = new AjaxJson();
        userService.delete(user);
        j.setMsg("删除成功！");
        return j;
    }

    @RequestMapping("delAll")
    @ResponseBody
    public AjaxJson delAll(@RequestBody List<Map<String,String>>  list, ModelMap map){
        AjaxJson j = new AjaxJson();
        String[] idArray = new String[list.size()];
        for(int i = 0; i<list.size(); i++){
            idArray[i] = list.get(i).get("id");
        }



        for(String id : idArray){
            // 超级管理员ID
            if("0".equals(id)){
                j.setMsg("超级管理员不可删除！");
                j.setSuccess(false);
                return j;
            }
            User u = new User();
            u.setId(id);
            userService.delete(u);
        }
        j.setMsg("删除成功");
        return j;
    }

}
