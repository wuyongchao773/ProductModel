package com.product.dao;

import java.util.List;

import com.product.model.UserRole;

public interface UserRoleMapper {
	
	int deleteByPrimaryKey(Integer id);

	int insert(UserRole record);

	int insertSelective(UserRole record);

	UserRole selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserRole record);

	int updateByPrimaryKey(UserRole record);

	UserRole queryUserRoleByUserId(String userId);
	
	void batchDeleteUsers(List<String> userIds);
	
	void batchInsetUserRole(List<UserRole> userRoles);
	
}