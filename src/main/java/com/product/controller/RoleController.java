package com.product.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.product.annotation.LoginInterfaceAnnotation;
import com.product.dto.RoleDto;
import com.product.model.MakeLog;
import com.product.model.Role;
import com.product.model.UserRole;
import com.product.service.MakeLogService;
import com.product.service.RoleService;
import com.product.service.UserRoleService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.LogUtils;
import com.utils.ResultUtils;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MakeLogService makeLogService;

	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(value = "/queryRole", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> queryRoleInfo(HttpServletRequest request) {
		int pageNum = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		PageHelper.startPage(pageNum, pageSize);
		List<RoleDto> lists = roleService.selectRoleAll();
		PageInfo<RoleDto> pageInfo = new PageInfo<>(lists);
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < lists.size(); i++) {
			JSONObject object = new JSONObject();
			object.put("id", String.valueOf(lists.get(i).getId()));
			object.put("roleName", lists.get(i).getRoleName());
			object.put("createDate", sdf.format(lists.get(i).getCreateDate()));
			object.put("realname", lists.get(i).getRealname());
			array.add(object);
		}
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		// 插入日志
		MakeLog makeLog = LogUtils.insertLog(username + "查询角色信息！", username, "info", request);
		makeLogService.insertLog(makeLog);
		int totalCount = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
		return ResultUtils.tableSuccess(array.toString(), totalCount);
	}

	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> addRole(@RequestBody Map<String, Object> params,
			HttpServletRequest request) {
		String roleName = String.valueOf(params.get("rolename"));
		String userid = CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE).toString();
		Role role = new Role();
		role.setRoleName(roleName);
		role.setCreateby(userid);
		role.setCreateDate(new Date());
		int len = roleService.insertSelective(role);
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		if (len > 0) {
			MakeLog makeLog = LogUtils.insertLog(username + "添加用户角色成功!", username, "info", request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.success("添加用户角色成功!");
		} else {
			MakeLog makeLog = LogUtils.insertLog(username + "添加用户角色失败!", username, "info", request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.field("添加用户角色失败!");
		}
	}

	@RequestMapping(value = "/roleDelete", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> roleDelete(HttpServletRequest request) {
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		String ids = request.getParameter("ids");
		String[] id = ids.split(",");
		if (id.length > 0) {
			roleService.batchDeleteRole(id);
			MakeLog makeLog = LogUtils.insertLog(username + "删除角色信息成功", username, "info", request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.success("删除角色信息成功!");
		} else {
			MakeLog makeLog = LogUtils.insertLog(username + "删除角色信息失败", username, "info", request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.field("没有需要删除的数据!");
		}
	}

	@RequestMapping(value = "/modifyRole", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> modifyRole(@RequestBody Role role, HttpServletRequest request) {
		String username = String
				.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		int len = roleService.updateByPrimaryKeySelective(role);
		if (len > 0) {
			MakeLog makeLog = LogUtils.insertLog(username + "修改角色信息成功!", username, "info", request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.success("修改角色信息成功!");
		} else {
			MakeLog makeLog = LogUtils.insertLog(username + "修改角色信息失败!", username, "info", request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.field("修改角色信息失败!");
		}
	}

	@RequestMapping(value = "/userRoleAction", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> userRoleAction(HttpServletRequest request) {
		String userIds = request.getParameter("userIds");
		String roleIds = request.getParameter("roleIds");
		List<String> userList = Arrays.asList(userIds.split(","));
		List<String> roleList = Arrays.asList(roleIds.split(","));
		if (userRoleService.batchDeleteUser(userList, request) > 0) {
			List<UserRole> list = new ArrayList<>();
			// 构建用户角色对象
			for (String usersId : userList) {
				for (String roleId : roleList) {
					UserRole role = new UserRole();
					role.setUserId(usersId);
					role.setRoleId(Integer.parseInt(roleId));
					role.setDeleteStatus((byte) 0);
					list.add(role);
				}
			}
			if (userRoleService.batchInsertUser(list, request) > 0) {
				return ResultUtils.success("分配权限成功!");
			} else {
				return ResultUtils.field("分配权限失败!");
			}
		} else {
			return ResultUtils.field("分配权限失败!原因：批量删除用户信息错误!");
		}
	}
}
