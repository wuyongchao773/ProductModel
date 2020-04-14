package com.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.product.model.AppUser;

public interface AppUserService {

	public Map<String,Object> findAll(AppUser appUser,HttpServletRequest request);
	
	public Map<String,Object> addBlack(HttpServletRequest request);
	
	public Map<String,Object> updateBlack(HttpServletRequest request);
	
}
