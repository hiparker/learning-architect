package com.edwin.smartdevelop.modules.sys.service.impl;


import com.edwin.smartdevelop.common.json.AjaxJson;
import com.edwin.smartdevelop.common.utils.IdGen;
import com.edwin.smartdevelop.common.utils.PropertiesUtil;
import com.edwin.smartdevelop.common.utils.misc.AESUtil;
import com.edwin.smartdevelop.core.persistence.Page;
import com.edwin.smartdevelop.core.service.CrudService;
import com.edwin.smartdevelop.modules.sys.entity.User;
import com.edwin.smartdevelop.modules.sys.mapper.UserMapper;
import com.edwin.smartdevelop.modules.sys.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户 Service
 * @author Edwin
 */
@Component
@Service(version = "1.0.0",timeout = 10000,interfaceClass = IUserService.class,weight = 1)
@Transactional(readOnly = true)
public class UserServiceImpl extends CrudService<UserMapper,User> implements IUserService {

    private final static String AES_KEY = "aeskey";

    /**
     * 检查密码
     */
    @Override
    public User validataLogin(User user){
        return mapper.validataLogin(user);
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public AjaxJson updatePassword(User user){
        AjaxJson j = new AjaxJson();
        User temp = this.get(user);
        if(temp != null){
            if(temp.getPassword().equals(AESUtil.encrypt(user.getOldPassword(), PropertiesUtil.getConfig(AES_KEY)))){
                //加密 密码用来解析
                String encyPassword = AESUtil.encrypt(user.getPassword(), PropertiesUtil.getConfig(AES_KEY));
                user.setPassword(encyPassword);
                user.preUpdate();
                mapper.updatePassword(user);
                j.setMsg("修改成功！");
            }else{
                j.setSuccess(false);
                j.setMsg("旧密码不正确！");
            }
        }else{
            j.setSuccess(false);
            j.setMsg("找不到该用户！");
        }
        return j;
    }


    /**
     * 增加/修改
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public int save(User user){
        if(StringUtils.isEmpty(user.getPassword())){
            user.setPassword(this.get(user).getPassword());
        }else{
            //加密 密码用来解析
            String encyPassword = AESUtil.encrypt(user.getPassword(), PropertiesUtil.getConfig(AES_KEY));
            user.setPassword(encyPassword);
        }

        int count = super.save(user);

        // 删除用户角色
        mapper.deleteUserRole(user);
        // 添加用户角色
        String[] roleids = user.getRoleIds().split(",");
        for (String roleid : roleids) {
            mapper.insertUserRole(IdGen.uuid(),user.getId(),roleid);
        }

        return count;
    }


    /**
     * 删除
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public int remove(User user){
        // 删除用户角色
        mapper.deleteUserRole(user);
        return super.delete(user);
    }
    @Transactional(readOnly = false)
    public int remove(String id){
        User user = new User();
        user.setId(id);
        // 删除用户角色
        mapper.deleteUserRole(user);
        return super.delete(user);
    }

    /**
     * 批量查询
     * @param user
     * @return
     */
    @Override
    public List<User> findList(User user){
        return super.findList(user);
    }

    /**
     * 批量查询
     * @param user
     * @return
     */
    @Override
    public Page findPage(Page page, User user){
        return super.findPage(page,user);
    }



    /**
     * 查询
     * @param user
     * @return
     */
    @Override
    public User get(User user){
        return super.get(user);
    }



}
