package com.product.dto;

import java.io.Serializable;

import com.product.model.Role;

public class RoleDto extends Role implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String realname;

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

}
