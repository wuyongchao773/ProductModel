package com.product.dao;

import java.util.List;

import com.product.model.RoleMenu;

public interface RoleMenuMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);
    
    List<RoleMenu> queryRoleMenu(String roleId);

    void batchDeleteRoleIds(List<String> roleIds);
    
    void batchInsertPrimission(List<RoleMenu> roleMenus);
}