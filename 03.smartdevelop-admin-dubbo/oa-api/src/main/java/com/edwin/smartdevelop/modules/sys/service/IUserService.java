package com.edwin.smartdevelop.modules.sys.service;


import com.edwin.smartdevelop.common.json.AjaxJson;
import com.edwin.smartdevelop.core.service.CrudInterface;
import com.edwin.smartdevelop.modules.sys.entity.User;

/**
 * 用户Service
 * @author Edwin
 * @date 2019年10月30日11:31:16
 */
public interface IUserService  extends CrudInterface<User> {

    /**
     * 验证登录
     * @param user
     * @return
     */
    User validataLogin(User user);

    /**
     * 修改密码
     * @param user
     * @return
     */
    AjaxJson updatePassword(User user);

}
