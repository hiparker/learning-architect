package com.edwin.smartdevelop.core.proxy;

import com.edwin.smartdevelop.common.utils.PropertiesUtil;
import com.edwin.smartdevelop.modules.sys.entity.Menu;
import com.edwin.smartdevelop.modules.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器
 * @author Edwin
 */
@Component
@WebFilter(urlPatterns = "/")
public class SystemFilter implements Filter {
    private static final String LOGIN_URI = "/account/login";
    private static List<String> file_ends;
    private static List<String> ignode_uri;

    static{
        file_ends = new ArrayList<>();
        String endStr = PropertiesUtil.getConfig("webStaticFile");
        String[] ends = endStr.split(",");
        for (String end : ends) {
            file_ends.add(end);
        }


        ignode_uri = new ArrayList<>();
        String authUriStr = PropertiesUtil.getConfig("authUri");
        String[] authUris = authUriStr.split(",");
        for (String uris : authUris) {
            ignode_uri.add(uris);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        String uri = request.getRequestURI();

        boolean flag = validataFileUri(uri);
        if(flag){
            filterChain.doFilter(request,response);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            // 验证是否是 无序拦截URI
            boolean flagUri = validataUri(uri);
            if(flagUri){
                filterChain.doFilter(request,response);
                return;
            }else{
                // 重定向
                response.sendRedirect("/a/account/login");
                return;
            }
        }else{
            // 验证是否是 登录uri  如果是 则直接进入登录成功页面
            if(uri.endsWith(LOGIN_URI)){
                response.sendRedirect("/a/account/index");
                return;
            }
        }


        boolean flagUri = validataAuthUri(user.getMenuAll(),user.getMenus(),uri);
        if(!flagUri){
            // 转发 不修改url
            request.getRequestDispatcher("/a/account/index").forward(request, response);
            return;
        }

        //放行
        filterChain.doFilter(request,response);
    }

    public boolean validataFileUri(String uri){
        for (String fileEnd : file_ends) {
            if(uri.indexOf(fileEnd) != -1){
                return true;
            }
        }
        return false;
    }

    public boolean validataUri(String uri){
        for (String uriTemp : ignode_uri) {
            if(uri.indexOf(uriTemp) != -1){
                return true;
            }
        }
        return false;
    }

    /**
     * 拦截 访问地址在 数据库监控菜单范围内 并且又不再个人权限内的请求
     * @param menuAll
     * @param menus
     * @param uri
     * @return
     */
    public boolean validataAuthUri(List<Menu> menuAll, List<Menu> menus, String uri){

        boolean flag = false;
        for (Menu menu : menuAll) {
            if(!StringUtils.isEmpty(menu.getHref())){
                if(uri.indexOf(menu.getHref()) != -1){
                    flag = true;
                }
            }
        }

        if(!flag){
            return true;
        }else{
            for (Menu menu : menus) {
                if(!StringUtils.isEmpty(menu.getHref())){
                    if(uri.indexOf(menu.getHref()) != -1){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Smart.develop 启动");
    }


}
