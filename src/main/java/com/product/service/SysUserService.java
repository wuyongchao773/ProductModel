package com.product.service;

import java.util.List;

import com.product.model.SysUser;

public interface SysUserService {
	
	SysUser selectUserByUserName(SysUser recode);
	
	SysUser selectByPrimaryKey(String id);
	
	int insertUser(SysUser sysUser);
	
	List<SysUser> queryAll(SysUser sysUser);
	
	Integer batchDeleteSysUser(List<String> ids);
	
	int updateByPrimaryKeySelective(SysUser record);
	
    int insertSelective(SysUser record,String createBy);
    
    List<SysUser> findAll(SysUser sysUser);
    
}
