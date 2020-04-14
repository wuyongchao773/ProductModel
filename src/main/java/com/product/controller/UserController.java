package com.product.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
import com.product.model.MakeLog;
import com.product.model.SysUser;
import com.product.service.MakeLogService;
import com.product.service.SysUserService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.IdGenerator;
import com.utils.LogUtils;
import com.utils.ResultUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private MakeLogService makeLogService;

	@RequestMapping(value = "/addUser",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> addUser(@RequestBody SysUser sysUser,HttpServletRequest request){
		Map<String,Object> cookieMap = CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request);
		//先根据用户名进行查询
		SysUser checkUser = sysUserService.selectUserByUserName(sysUser);
		if(null != checkUser) {
			return ResultUtils.field("用户添加失败,原因:此用户已经添加过,请从新输入用户名");
		}
		sysUser.setId(IdGenerator.getUuid());
		//设置密码使用shiro来进行加密
		//设置盐码
		ByteSource salt = ByteSource.Util.bytes(sysUser.getUsername());
		SimpleHash hash = new SimpleHash("MD5",sysUser.getPassword(),salt,2);
		sysUser.setPassword(hash.toString());
		sysUser.setDeleteStatus(false);
		sysUser.setCreateby(String.valueOf(cookieMap.get(CodeUtils.COOKIE_VALUE)));
		sysUser.setCreatetime(new Date());
		int len = sysUserService.insertUser(sysUser);
		String username = String.valueOf(cookieMap.get(CodeUtils.COOKIE_VALUE));
		if(len > 0) {
			//添加日志信息
			MakeLog makeLog = LogUtils.insertLog(username+"新增用户成功",username,"info",request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.success("成功添加用户信息");
		}else {
			MakeLog makeLog = LogUtils.insertLog(username+"添加用户信息失败",username,"info",request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.field("添加用户信息失败");
		}
	}
	
	@RequestMapping(value = "/queryUser",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> queryUserInfo(SysUser sysUser,HttpServletRequest request){
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME,request).get(CodeUtils.COOKIE_VALUE));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("limit");
		//为了拿到昵称 后期会改sql或者放到cookie里
		SysUser realSys = new SysUser();
		realSys.setUsername(username);
		realSys = sysUserService.selectUserByUserName(realSys);
		sysUser.setRealname(realSys.getRealname());
		PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		List<SysUser> sysUsers = sysUserService.queryAll(sysUser);
		//这步是为了拿总数
		PageInfo<SysUser> pages = new PageInfo<>(sysUsers);
		JSONArray array = new JSONArray();
		for(int i = 0 ; i < sysUsers.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",sysUsers.get(i).getId());
			jsonObject.put("username",sysUsers.get(i).getUsername());
			jsonObject.put("realname",sysUsers.get(i).getRealname());
			jsonObject.put("idcard",sysUsers.get(i).getIdcard());
			jsonObject.put("sex",sysUsers.get(i).getSex()==1?"男":"女");
			jsonObject.put("address",sysUsers.get(i).getAddress());
			jsonObject.put("telephone",sysUsers.get(i).getTelephone());
			jsonObject.put("mobile",sysUsers.get(i).getMobile());
			jsonObject.put("createtime",sdf.format(sysUsers.get(i).getCreatetime()));
			array.add(jsonObject);
		}
		
		MakeLog makeLog = LogUtils.insertLog(username+"查询用户操作",username,"info",request);
		makeLogService.insertLog(makeLog);
		return ResultUtils.tableSuccess(array.toString(),Integer.parseInt((String.valueOf(pages.getTotal()))));
	}
	
	@RequestMapping(value = "/userDelete",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> userDelete(HttpServletRequest request){
		String ids = request.getParameter("ids");
		List<String> delId = Arrays.asList(ids.split(","));
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		try {
			sysUserService.batchDeleteSysUser(delId);
			MakeLog makeLog = LogUtils.insertLog(username+"删除 用户成功",username,"info",request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.success("删除成功!");
		} catch(Exception e) {
			MakeLog makeLog = LogUtils.insertLog(username+"删除 用户失败",username,"info",request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.field("删除失败!");
		}
	}
	
	@RequestMapping(value = "/modifyUser",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> modifyUser(@RequestBody SysUser sysUser,HttpServletRequest request){
		String userId = CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE).toString();
		sysUser.setModifyby(userId);
		sysUser.setModifytime(new Date());
		int len = sysUserService.updateByPrimaryKeySelective(sysUser);
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		if(len > 0) {
			MakeLog makeLog = LogUtils.insertLog(username+"修改 用户成功",username,"info",request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.success("修改用户成功");
		}else {
			MakeLog makeLog = LogUtils.insertLog(username+"修改 用户失败",username,"info",request);
			makeLogService.insertLog(makeLog);
			return ResultUtils.field("修改用户失败");
		}
	}
	
	@RequestMapping(value = "/UserRolePrimission",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> queryUser(SysUser sysUser,HttpServletRequest request){
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("limit");
		PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		sysUser.setUsername(username);
		List<SysUser> sysUsers = sysUserService.findAll(sysUser);
		//这步是为了拿总数
		PageInfo<SysUser> pages = new PageInfo<>(sysUsers);
		JSONArray array = new JSONArray();
		for(int i = 0 ; i < sysUsers.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",sysUsers.get(i).getId());
			jsonObject.put("username",sysUsers.get(i).getUsername());
			array.add(jsonObject);
		}
		
		MakeLog makeLog = LogUtils.insertLog(username+"查询用户操作",username,"info",request);
		makeLogService.insertLog(makeLog);
		return ResultUtils.tableSuccess(array.toString(),Integer.parseInt((String.valueOf(pages.getTotal()))));
	}
}
