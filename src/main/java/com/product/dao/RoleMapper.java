package com.product.dao;

import java.util.List;

import com.product.dto.RoleDto;
import com.product.model.Role;

public interface RoleMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insert(Role record);

	int insertSelective(Role record);

	Role selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Role record);

	int updateByPrimaryKey(Role record);
	
	List<RoleDto> selectRoleAll();
	
	void batchDeleteRole(String[] id);
	
}