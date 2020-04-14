package com.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.annotation.LoginInterfaceAnnotation;
import com.product.model.SysMenu;
import com.product.service.PrimissionService;

/***
 * @Title 权限控制层
 * @author wuyongchao
 * @date 2019-12-18 14:47:21
 */
@Controller
@RequestMapping(value = "/primission")
public class PrimissionController {

	@Autowired
	private PrimissionService primissionService;

	@RequestMapping(value = "/queryPrimission", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> queryPromission(HttpServletRequest request) {
		String startPage = request.getParameter("page");
		String pageSize = request.getParameter("limit");
		return primissionService.findAll(startPage, pageSize,request);
	}
	
	@RequestMapping(value = "/modifyPrimission",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> modifyPrimission(@RequestBody SysMenu sysMenu,HttpServletRequest request){
		return primissionService.modifyPrimission(sysMenu,request);
	}
	
	@RequestMapping(value = "/addPrimission",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> addPrimission(@RequestBody SysMenu sysMenu,HttpServletRequest request){
		return primissionService.addPrimission(sysMenu, request);
	}
	
	@RequestMapping(value = "/primissionDelete",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> primissionDelete(HttpServletRequest request){
		String ids = request.getParameter("ids");
		List<String> delId = Arrays.asList(ids.split(","));
		return primissionService.batchDelete(delId, request);
	}
	
	
	@RequestMapping(value = "/rolePrimissionAction",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> rolePrimissionAction(HttpServletRequest request){
	    return primissionService.rolePrimissionAction(request);
	}
}
