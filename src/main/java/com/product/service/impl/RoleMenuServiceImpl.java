package com.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dao.RoleMenuMapper;
import com.product.model.RoleMenu;
import com.product.service.RoleMenuService;

@Service
public class RoleMenuServiceImpl implements RoleMenuService{

	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Override
	public List<RoleMenu> queryRoleMenu(String roleId) {
		return roleMenuMapper.queryRoleMenu(roleId);
	}

}
