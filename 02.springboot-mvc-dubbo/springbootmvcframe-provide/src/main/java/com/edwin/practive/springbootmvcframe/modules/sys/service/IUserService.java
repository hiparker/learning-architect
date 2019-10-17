package com.edwin.practive.springbootmvcframe.modules.sys.service;

import com.edwin.practive.springbootmvcframe.common.json.AjaxJson;
import com.edwin.practive.springbootmvcframe.core.service.CrudInterface;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.User;

public interface IUserService  extends CrudInterface<User> {

    User validataLogin(User user);

    AjaxJson updatePassword(User user);

}
