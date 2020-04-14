package com.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.product.model.UserRole;

public interface UserRoleService {
	
	UserRole queryUserRole(String userId);
	
	int batchDeleteUser(List<String> userIds,HttpServletRequest request);

	int batchInsertUser(List<UserRole> userRoles,HttpServletRequest request);
	
}
