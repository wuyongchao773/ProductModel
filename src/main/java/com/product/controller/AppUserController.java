package com.product.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.annotation.LoginInterfaceAnnotation;
import com.product.model.AppUser;
import com.product.service.AppUserService;

@Controller
@RequestMapping(value = "/appuser")
public class AppUserController {

	@Autowired
	private AppUserService appUserService;

	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> findAll(AppUser appUser, HttpServletRequest request) {
		return appUserService.findAll(appUser, request);
	}

	@RequestMapping(value = "/addBlack", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> addBlack(HttpServletRequest request) {
		return appUserService.addBlack(request);
	}
	
	@RequestMapping(value = "/backBlack", method = RequestMethod.POST )
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> backBlack(HttpServletRequest request){
		return appUserService.updateBlack(request);
	}
}
