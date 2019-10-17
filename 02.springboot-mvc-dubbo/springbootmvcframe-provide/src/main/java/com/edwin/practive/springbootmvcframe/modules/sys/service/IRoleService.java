package com.edwin.practive.springbootmvcframe.modules.sys.service;

import com.edwin.practive.springbootmvcframe.core.service.CrudInterface;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Role;

public interface IRoleService   extends CrudInterface<Role> {

    int roleMenuSave(Role role);

    String[] getRoleMenu(Role role);

}
