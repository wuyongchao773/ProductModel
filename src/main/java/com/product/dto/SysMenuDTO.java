package com.product.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/***
 * @Title 系统菜单
 * @author wuyongchao
 * @date 2019-04-21 00:38:00
 *
 */
public class SysMenuDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String menuName;
	
	private String menuDesc;
	
	private String menuUrl;
	
	private String parentId;
	
	private String menuImage;
	
	private List<SysMenuDTO> children = new ArrayList<SysMenuDTO>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<SysMenuDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuDTO> children) {
		this.children = children;
	}

	public String getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}
	
}
