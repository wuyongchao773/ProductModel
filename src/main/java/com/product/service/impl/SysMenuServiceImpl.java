package com.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dao.SysMenuMapper;
import com.product.model.SysMenu;
import com.product.service.SysMenuService;

/***
 * @Title 系统菜单
 * @author wuyongchao
 * @date 2019-04-18 00:36:20
 *
 */
@Service
public class SysMenuServiceImpl implements SysMenuService{
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenu> queryMenuByRoleId(List<String> lists) {
		return sysMenuMapper.queryMenuByRoleId(lists);
	}

}
