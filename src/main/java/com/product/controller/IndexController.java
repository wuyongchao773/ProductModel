package com.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.annotation.LoginInterfaceAnnotation;
import com.product.dto.SysMenuDTO;
import com.product.model.Role;
import com.product.model.RoleMenu;
import com.product.model.SysMenu;
import com.product.model.SysUser;
import com.product.model.UserRole;
import com.product.service.RoleMenuService;
import com.product.service.RoleService;
import com.product.service.SysMenuService;
import com.product.service.SysUserService;
import com.product.service.UserRoleService;
import com.utils.ResultUtils;

/***
 * @Title 主页控制器
 * @author wuyongchao
 * @date 2019-04-17 23:27:00
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleMenuService roleMenuService;
	
	
	@RequestMapping(value = "/init",method = RequestMethod.GET)
	@LoginInterfaceAnnotation
	public String index() {
		return "indexPage";
	}
	
	@RequestMapping(value = "/queryUserMenuOrSelfInfo",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> queryUserMenuOrSelfInfo(HttpServletRequest request){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String username = request.getParameter("userId");
		SysUser recode = new SysUser();
		recode.setUsername(username);
		SysUser sysUser = sysUserService.selectUserByUserName(recode);
		if(null == sysUser) {
			return ResultUtils.field("没有查询到指定用户信息!");
		}
		responseMap.put("userInfo",sysUser);
		//查询用户对应的权限信息
		UserRole userRole = userRoleService.queryUserRole(sysUser.getId());
		if(null == userRole) {
			return ResultUtils.field("没有找到用户所对应的权限");
		}
		Role role = roleService.selectByPrimaryKey(userRole.getRoleId());
		responseMap.put("roleInfo",role);
		
		List<RoleMenu> roleIds = roleMenuService.queryRoleMenu(String.valueOf(role.getId()));
		//提取id
		List<String> lists = roleIds.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
		
		List<SysMenu> sysMenuList = sysMenuService.queryMenuByRoleId(lists);
		
		List<SysMenuDTO> sysMenus = this.referencesSysMenu(sysMenuList);
		//提取数据
		responseMap.put("menuInfo",sysMenus);
		return responseMap;
	}
	
	/***
	 * @Title 将查询出来的所有系统菜单信息 进行拼接
	 * @author wuyongchao
	 * @date 2019-04-21 00:40:00
	 * @param sysMenus 系统菜单数据
	 * @return 拼接好的系统菜单数据
	 */
	@SuppressWarnings("unused")
	private List<SysMenuDTO> referencesSysMenu(List<SysMenu> sysMenus){
		List<SysMenuDTO> packageSysMenu = new ArrayList<SysMenuDTO>();
		//获得为空的对象
		List<SysMenu> parentSysMenu = sysMenus.stream().filter(t -> t.getParentId().equals("0")).collect(Collectors.toList());
		//获得不为空的对象
		List<SysMenu> childerSysMenu = sysMenus.stream().filter(t -> !t.getParentId().equals("0")).collect(Collectors.toList());
		//封装所有父节点信息
		for(SysMenu sysMenu : parentSysMenu) {
			SysMenuDTO dto = new SysMenuDTO();
			dto.setId(sysMenu.getId());
			dto.setMenuName(sysMenu.getMenuName());
			dto.setMenuDesc(sysMenu.getMenuDesc());
			dto.setMenuUrl(sysMenu.getMenuUrl());
			dto.setParentId(sysMenu.getParentId());
			dto.setMenuImage(sysMenu.getSysImage());
			packageSysMenu.add(dto);
		}
		//根据父节点便利看看有没有一样的有的话给子节点
		for(int i = 0; i < packageSysMenu.size(); i++) {
			for(int j = 0; j < childerSysMenu.size(); j++) {
				if(packageSysMenu.get(i).getId().equals(childerSysMenu.get(j).getParentId())) {
					SysMenuDTO dtos = new SysMenuDTO();
					dtos.setId(childerSysMenu.get(j).getId());
					dtos.setMenuName(childerSysMenu.get(j).getMenuName());
					dtos.setMenuDesc(childerSysMenu.get(j).getMenuDesc());
					dtos.setMenuUrl(childerSysMenu.get(j).getMenuUrl());
					dtos.setParentId(childerSysMenu.get(j).getParentId());
					dtos.setMenuImage(childerSysMenu.get(j).getSysImage());
					packageSysMenu.get(i).getChildren().add(dtos);
				}
			}
		}
		
		return packageSysMenu;
	}
}
