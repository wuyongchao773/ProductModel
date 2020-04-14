package com.product.dao;

import java.util.List;

import com.product.model.SysUser;

public interface SysUserMapper {
 
	int deleteByPrimaryKey(String id);

    int insert(SysUser sysUser);

    int insertSelective(SysUser sysUser);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser sysUser);

    int updateByPrimaryKey(SysUser record);
    
    SysUser selectUserByUserName(SysUser sysUser);
    
    List<SysUser> queryAll(SysUser sysUser);
    
    List<SysUser> findAll(SysUser sysUser);
    
    Integer batchDeleteSysUser(List<String> id);
    
}