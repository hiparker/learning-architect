/**
 * Copyright &copy; Edwin All rights reserved.
 */
package com.edwin.practive.springbootmvcframe.modules.sys.mapper;

import com.edwin.practive.springbootmvcframe.core.persistence.BaseMapper;
import com.edwin.practive.springbootmvcframe.modules.sys.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单MAPPER接口
 * @author Edwin
 * @version 2019-08-29
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);

    int deleteRoleMenu(Menu menu);

}
