package com.edwin.smartdevelop.modules.sys.web;

import com.edwin.smartdevelop.core.web.BaseController;
import com.edwin.smartdevelop.modules.sys.entity.User;
import com.edwin.smartdevelop.modules.sys.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * User Controller
 * @author Edwin
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {


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

    @RequestMapping("list")
    public String list(ModelMap map){
         return "modules/sys/user/user_list";
    }

}
