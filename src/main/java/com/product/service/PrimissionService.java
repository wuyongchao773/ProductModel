package com.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.product.model.SysMenu;

/***
 * @Title 权限接口层
 * @author wuyongchao
 * @date 2019-12-18 14:52:3
 */
public interface PrimissionService {

	public Map<String,Object> findAll(String startPage,String pageSize,HttpServletRequest request);
	
	public Map<String,Object> modifyPrimission(SysMenu sysMenu,HttpServletRequest request);
	
	public Map<String,Object> addPrimission(SysMenu sysMenu,HttpServletRequest request);
	
	public Map<String,Object> batchDelete(List<String> ids,HttpServletRequest request);
	
	public Map<String,Object> rolePrimissionAction(HttpServletRequest request);
	
}
