package com.product.service;

import java.util.List;

import com.product.dto.RoleDto;
import com.product.model.Role;

public interface RoleService {

	Role selectByPrimaryKey(Integer id);
	
	List<RoleDto> selectRoleAll();
	
	int insertSelective(Role record);
	
	void batchDeleteRole(String[] id);
	
	int updateByPrimaryKeySelective(Role record);
	
}
