package com.edwin.smartdevelop.modules.sys.service;


import com.edwin.smartdevelop.common.json.AjaxJson;

/**
 * 登录Service
 * @author Edwin
 * @date 2019年10月30日11:31:16
 */
public interface ILoginService {

    /**
     * 验证登录
     * @param username
     * @param password
     * @return
     */
    AjaxJson validataLogin(String username, String password);

}
