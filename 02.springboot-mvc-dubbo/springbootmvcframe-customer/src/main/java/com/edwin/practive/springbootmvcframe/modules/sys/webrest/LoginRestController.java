package com.edwin.practive.springbootmvcframe.modules.sys.webrest;

import com.edwin.practive.springbootmvcframe.common.json.AjaxJson;
import com.edwin.practive.springbootmvcframe.core.web.BaseController;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

/**
 * 登录 RestController
 * @author Edwin
 */
@RestController
@RequestMapping("api/v1.0.0/account")
@Api(value = "登录 RestController")
public class LoginRestController extends BaseController{

    @ApiOperation(value = "获得授权数据")
    @GetMapping("getAuth")
    public AjaxJson getAuth(HttpServletRequest request, ModelMap modelMap){
        AjaxJson j = new AjaxJson();
        User userT = (User) request.getSession().getAttribute("user");
        LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("param",userT.getMenu());
        j.setBody(body);
        return j;
    }

}
