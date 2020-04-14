package com.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dao.RoleMapper;
import com.product.dto.RoleDto;
import com.product.model.Role;
import com.product.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper RoleMapper;
	
	@Override
	public Role selectByPrimaryKey(Integer id) {
		return RoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<RoleDto> selectRoleAll() {
		return RoleMapper.selectRoleAll();
	}

	@Override
	public int insertSelective(Role record) {
		return RoleMapper.insertSelective(record);
	}

	@Override
	public void batchDeleteRole(String[] id) {
		RoleMapper.batchDeleteRole(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return RoleMapper.updateByPrimaryKeySelective(record);
	}

}
