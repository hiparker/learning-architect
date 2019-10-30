package com.edwin.smartdevelop.modules.sys.service.impl;


import com.edwin.smartdevelop.common.json.AjaxJson;
import com.edwin.smartdevelop.common.utils.PropertiesUtil;
import com.edwin.smartdevelop.common.utils.misc.AESUtil;
import com.edwin.smartdevelop.modules.sys.entity.User;
import com.edwin.smartdevelop.modules.sys.service.ILoginService;
import com.edwin.smartdevelop.modules.sys.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 登陆 Service
 * @author Edwin
 */
@Component
@Service(version = "1.0.0",timeout = 10000,interfaceClass = ILoginService.class,weight = 1)
public class LoginServiceImpl implements ILoginService {


    @Autowired
    private IUserService userService;

    /**
     * 验证登陆账号密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public AjaxJson validataLogin(String username, String password){
        AjaxJson j = new AjaxJson();
        LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){

            //加密 密码用来解析
            String encyPassword = AESUtil.encrypt(password, PropertiesUtil.getConfig("aeskey"));

            User user = userService.validataLogin(new User(username,encyPassword));
            if(user != null){
                j.setSuccess(true);
                body.put("user",user);
                j.setBody(body);
            }else{
                j.setSuccess(false);
                j.setMsg("账号/密码不正确！");
            }

        }else{
            j.setSuccess(false);
            j.setMsg("登陆账号/密码不可为空！");
        }
        return j;
    }

}
