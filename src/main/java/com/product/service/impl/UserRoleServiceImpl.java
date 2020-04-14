package com.product.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.dao.MakeLogMapper;
import com.product.dao.UserRoleMapper;
import com.product.model.MakeLog;
import com.product.model.UserRole;
import com.product.service.UserRoleService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.LogUtils;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private MakeLogMapper makeLogMapper;

	@Override
	public UserRole queryUserRole(String userId) {
		return userRoleMapper.queryUserRoleByUserId(userId);
	}

	/***
	 * @Title 批量删除用户
	 * @author wuyongchao
	 * @date 2019-12-19 16:16:21
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int batchDeleteUser(List<String> userIds, HttpServletRequest request) {
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		int i = 0;
		try {
			userRoleMapper.batchDeleteUsers(userIds);
			MakeLog makeLog = LogUtils.insertLog(username + "批量删除权限信息成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			i = 1;
		} catch (Exception e) {
			MakeLog makeLog = LogUtils.insertLog(username + "批量删除权限信息失败!", username, "info", request);
			makeLogMapper.insert(makeLog);
			throw e;
		}
		return i;
	}

	@Transactional(rollbackFor = Exception.class)
	public int batchInsertUser(List<UserRole> userRoles,HttpServletRequest request) {
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME,request).get(CodeUtils.COOKIE_VALUE));
		int len = 0;
		try {
			userRoleMapper.batchInsetUserRole(userRoles);
			MakeLog makeLog = LogUtils.insertLog(username + "批量删除权限信息成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			len = 1;
		}catch(Exception e) {
			MakeLog makeLog = LogUtils.insertLog(username + "批量删除权限信息失败!", username, "info", request);
			makeLogMapper.insert(makeLog);
			throw e;
		}
		return len;
	}
}
