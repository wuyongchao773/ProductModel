package com.product.service;

import java.util.List;

import com.product.model.SysMenu;

public interface SysMenuService {

	List<SysMenu> queryMenuByRoleId(List<String> menuId);
}
