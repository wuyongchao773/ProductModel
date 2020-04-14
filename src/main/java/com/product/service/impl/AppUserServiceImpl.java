package com.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.product.dao.AppUserMapper;
import com.product.dao.MakeLogMapper;
import com.product.model.AppUser;
import com.product.model.MakeLog;
import com.product.service.AppUserService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.LogUtils;
import com.utils.ResultUtils;

import net.sf.json.JSONObject;

/***
 * @Title 客户业务实现层
 * @author wuyongchao
 * @date 2019-12-21 00:11:12
 */
@Service
public class AppUserServiceImpl implements AppUserService{

	@Autowired
	private AppUserMapper appUserMapper;
	
	@Autowired
	private MakeLogMapper makeLogMapper;
	
	
	/***
	 * @Title 查询所有客户信息
	 * @author wuyongchao
	 * @date 2019-12-21
	 */
	@Override
	public Map<String,Object> findAll(AppUser appUsers,HttpServletRequest request) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageHelper.startPage(Integer.parseInt(request.getParameter("page")),Integer.parseInt(request.getParameter("limit")));
		List<AppUser> users = appUserMapper.findAll(appUsers);
		PageInfo<AppUser> pageInfo = new PageInfo<>(users);
		int count = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
		JSONArray array = new JSONArray();
		for(AppUser appUser : users) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",appUser.getId());
			jsonObject.put("realName",appUser.getRealName());
			jsonObject.put("loginType",appUser.getLoginType());
			jsonObject.put("sex",appUser.getSex());
			jsonObject.put("birthday",simpleDateFormat.format(appUser.getBirthday()));
			jsonObject.put("telphone",appUser.getTelphone());
			jsonObject.put("mobile",appUser.getMobile());
			jsonObject.put("city",appUser.getCity());
			jsonObject.put("county",appUser.getCounty());
			jsonObject.put("province",appUser.getProvince());
			jsonObject.put("language",appUser.getLanguage());
			jsonObject.put("headImage",appUser.getHeadImage());
			jsonObject.put("address",appUser.getAddress());
			jsonObject.put("createTime",simpleDateFormat.format(appUser.getCreateTime()));
			jsonObject.put("deleteBy",appUser.getDeleteBy());
			if(null != appUser.getDeleteTime()) {
				jsonObject.put("deleteTime",simpleDateFormat.format(appUser.getDeleteTime()));
			}else {
				jsonObject.put("dateTime","");
			}
			array.add(jsonObject);
		}
		return ResultUtils.tableSuccess(array, count);
	}
	/***
	 * @Title 将客户拉入黑名单
	 * @author wuyongchao
	 * @date 2019-12-21 02:00:00
	 */
	@Override
	public Map<String, Object> addBlack(HttpServletRequest request) {
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME,request).get(CodeUtils.COOKIE_VALUE));
		String id = request.getParameter("ids");
		List<String> ids = Arrays.asList(id.split(","));
		try {
			appUserMapper.batchAddBlack(ids);
			MakeLog makeLog = LogUtils.insertLog(username + "加入黑名单成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.success("加入黑名单成功!");
		}catch(Exception exception) {
			MakeLog makeLog = LogUtils.insertLog(username + "加入黑名单失败!", username, "error", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.field("加入黑名单失败");
		}
	}
	/***
	 * @Title 将客户解除黑名单
	 * @author wuyongchao
	 * @date 2019-12-21 02:00:00
	 */
	@Override
	public Map<String, Object> updateBlack(HttpServletRequest request) {
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME,request).get(CodeUtils.COOKIE_VALUE));
		String id = request.getParameter("ids");
		List<String> ids = Arrays.asList(id.split(","));
		try {
			appUserMapper.updateBlack(ids);
			MakeLog makeLog = LogUtils.insertLog(username + "解除黑名单成功!", username, "info", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.success("解除黑名单成功!");
		}catch(Exception exception) {
			MakeLog makeLog = LogUtils.insertLog(username + "解除黑名单失败!", username, "error", request);
			makeLogMapper.insert(makeLog);
			return ResultUtils.field("解除黑名单失败");
		}
	}
	
}
