package com.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.product.dao.MakeLogMapper;
import com.product.dao.RoleMenuMapper;
import com.product.dao.SysMenuMapper;
import com.product.model.MakeLog;
import com.product.model.RoleMenu;
import com.product.model.SysMenu;
import com.product.service.PrimissionService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.LogUtils;
import com.utils.ResultUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/***
 * @Title 权限业务层
 * @author wuyongchao
 * @date 2019-12-19 12:33:25
 */
@Service
public class PrimissionServiceImpl implements PrimissionService {

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private MakeLogMapper makeLogMapper;

	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/***
	 * @Title 查询权限信息
	 * @author wuyongchao
	 * @date 2019-12-19 11:56:21
	 * @param page
	 *            页码
	 * @param limit
	 *            查询数量
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> findAll(String startPage, String pageSize, HttpServletRequest request) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		PageHelper.startPage(Integer.parseInt(startPage), Integer.parseInt(pageSize));
		List<SysMenu> sysMenus = sysMenuMapper.findAll();
		PageInfo<SysMenu> pageInfo = new PageInfo<>(sysMenus);
		int count = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
		JSONArray jsonArray = new JSONArray();
		for (SysMenu menu : sysMenus) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", menu.getId());
			jsonObject.put("menuName", menu.getMenuName());
			jsonObject.put("menuDesc", menu.getMenuDesc());
			jsonObject.put("menuUrl", menu.getMenuUrl());
			jsonObject.put("parentId", menu.getParentId());
			jsonObject.put("createBy", menu.getCreateby());
			jsonObject.put("createtime", simpleDateFormat.format(menu.getCreatetime()));
			jsonObject.put("deleteStatus", menu.getDeleteStatus());
			jsonObject.put("sysImage", menu.getSysImage());
			jsonArray.add(jsonObject);
		}
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		MakeLog makeLog = LogUtils.insertLog(username + "查询权限功能", username, "info", request);
		makeLogMapper.insert(makeLog);
		return ResultUtils.tableSuccess(jsonArray, count);
	}

	/***
	 * @Title 修改权限信息
	 * @author wuyongchao
	 * @date 2019-12-18 16:21:14
	 * @param sysMenu
	 *            菜单对象
	 * @return
	 */
	@Override
	public Map<String, Object> modifyPrimission(SysMenu sysMenu, HttpServletRequest request) {
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		sysMenu.setUpdateby(username);
		sysMenu.setUpdatetime(new Date());
		int len = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
		if (len > 0) {
			MakeLog makeLog = LogUtils.insertLog(username + "修改权限信息成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.success("修改权限信息成功!");
		} else {
			MakeLog makeLog = LogUtils.insertLog(username + "修改权限信息失败!", username, "error", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.field("修改权限信息失败!");
		}
	}

	/***
	 * @Title 添加权限信息
	 * @author wuyongchao
	 * @date 2019-12-18 16:21:14
	 * @param sysMenu
	 *            菜单对象
	 * @return
	 */
	@Override
	public Map<String, Object> addPrimission(SysMenu sysMenu, HttpServletRequest request) {
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		sysMenu.setCreateby(username);
		sysMenu.setCreatetime(new Date());
		int len = sysMenuMapper.insertSelective(sysMenu);
		if (len > 0) {
			MakeLog makeLog = LogUtils.insertLog(username + "添加权限信息成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.success("添加权限信息成功!");
		} else {
			MakeLog makeLog = LogUtils.insertLog(username + "添加权限信息失败!", username, "error", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.field("添加权限信息失败!");
		}
	}

	/***
	 * @Title 批量删除权限信息
	 * @author wuyongchao
	 * @date 2019-12-19 12:14:19
	 * @param List<String>
	 *            需要删除的id
	 */
	@Override
	public Map<String, Object> batchDelete(List<String> ids, HttpServletRequest request) {
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		try {
			sysMenuMapper.batchDelete(ids);
			MakeLog makeLog = LogUtils.insertLog(username + "删除权限信息成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.success("删除权限信息成功!");
		} catch (Exception exception) {
			MakeLog makeLog = LogUtils.insertLog(username + "删除权限信息成功!", username, "error", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.field("删除权限信息失败!");
		}
	}

	/***
	 * @Title 角色权限授权功能
	 * @author wuyongchao
	 * @date 2019-12-20 15:48:32
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> rolePrimissionAction(HttpServletRequest request) {
		String roleId = request.getParameter("roleIds");
		String primissionId = request.getParameter("primissionIds");
		List<String> roleIds = Arrays.asList(roleId.split(","));
		List<String> primissionIds = Arrays.asList(primissionId.split(","));
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		try {
			roleMenuMapper.batchDeleteRoleIds(roleIds);
			List<RoleMenu> list = new ArrayList<RoleMenu>();
			for (int i = 0; i < roleIds.size(); i++) {
				for (int j = 0; j < primissionIds.size(); j++) {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRoleId(Integer.parseInt(roleIds.get(i)));
					roleMenu.setMenuId(primissionIds.get(j));
					list.add(roleMenu);
				}
			}
			roleMenuMapper.batchInsertPrimission(list);
			MakeLog makeLog = LogUtils.insertLog(username + "角色权限分配成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.success("角色权限分配成功!");
		} catch (Exception exception) {
			MakeLog makeLog = LogUtils.insertLog(username + "角色权限分配失败!", username, "error", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.field("角色权限分配失败!");
		}
	}
}
