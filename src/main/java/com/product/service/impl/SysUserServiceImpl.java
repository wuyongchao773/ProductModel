package com.product.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.product.dao.SysUserMapper;
import com.product.model.SysUser;
import com.product.service.SysUserService;
import com.utils.IdGenerator;

@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser selectUserByUserName(SysUser recode) {
		return sysUserMapper.selectUserByUserName(recode);
	}

	@Override
	public SysUser selectByPrimaryKey(String id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int insertUser(SysUser sysUser) {
		return sysUserMapper.insert(sysUser);
	}

	@Override
	public List<SysUser> queryAll(SysUser sysUser) {
		return sysUserMapper.queryAll(sysUser);
	}

	@Override
	public Integer batchDeleteSysUser(List<String> ids) {
		return sysUserMapper.batchDeleteSysUser(ids);
	}

	@Override
	public int updateByPrimaryKeySelective(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(SysUser record,String createBy) {
		record.setId(IdGenerator.getUuid());
		record.setCreatetime(new Date());
		record.setCreateby(createBy);
		record.setDeleteStatus(false);
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public List<SysUser> findAll(SysUser sysUser) {
		return sysUserMapper.findAll(sysUser);
	}
}
