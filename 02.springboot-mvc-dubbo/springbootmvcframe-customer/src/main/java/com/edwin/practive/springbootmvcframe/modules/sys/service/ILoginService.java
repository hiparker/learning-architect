package com.edwin.practive.springbootmvcframe.modules.sys.service;

import com.edwin.practive.springbootmvcframe.common.json.AjaxJson;

public interface ILoginService {

    AjaxJson validataLogin(String username, String password);

}
