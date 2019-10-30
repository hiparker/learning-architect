/**
 * Copyright &copy; Edwin All rights reserved.
 */
package com.edwin.smartdevelop.modules.sys.entity;


import com.edwin.smartdevelop.core.persistence.DataEntity;
import lombok.Data;

/**
 * 角色Entity
 * @author Edwin
 * @version 2019-08-29
 */
@Data
public class Role extends DataEntity<Role> {

	private static final long serialVersionUID = 1L;

	private String name; 	// 名称
	private String enname; 	// 英文名称
    private String isSys;   // 是否启用字符串
    private String menuIds; // 菜单ID

    public Role(){
        super();
    }

    public Role(String id){
        super(id);
    }
}