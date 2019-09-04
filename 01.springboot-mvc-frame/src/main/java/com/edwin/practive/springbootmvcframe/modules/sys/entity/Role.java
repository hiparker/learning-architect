/**
 * Copyright &copy; Edwin All rights reserved.
 */
package com.edwin.practive.springbootmvcframe.modules.sys.entity;

import com.edwin.practive.springbootmvcframe.core.persistence.DataEntity;

import javax.validation.constraints.NotBlank;

/**
 * 角色Entity
 * @author Edwin
 * @version 2019-08-29
 */
public class Role extends DataEntity<Role> {

	private static final long serialVersionUID = 1L;

    @NotBlank()
	private String name; 	// 名称
    @NotBlank
	private String enname; 	// 英文名称
    @NotBlank
    private String isSys;   // 是否启用字符串
    private String menuIds; // 菜单ID

	public Role(){
		super();
	}

	public Role(String id){
		super(id);
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
}