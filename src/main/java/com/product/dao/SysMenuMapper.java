package com.product.dao;

import java.util.List;

import com.product.model.SysMenu;

public interface SysMenuMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
    List<SysMenu> queryMenuByRoleId(List<String> lists);
    
    List<SysMenu> findAll();
    
    void batchDelete(List<String> lists);
}