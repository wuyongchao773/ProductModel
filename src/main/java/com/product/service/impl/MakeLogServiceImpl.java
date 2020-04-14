package com.product.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.product.dao.MakeLogMapper;
import com.product.model.MakeLog;
import com.product.service.MakeLogService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.LogUtils;
import com.utils.ResultUtils;

@Service
public class MakeLogServiceImpl implements MakeLogService{
	
	PlatformTransactionManager platformTransactionManager;

	@Autowired
	private MakeLogMapper makeLogMapper;
	
	@Override
	public void insertLog(MakeLog makeLog) {
		makeLogMapper.insert(makeLog);
	}
    
	//开启Requires_new 如果有事物就用当前事物，如果没有事物就创建一个新事物处理
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String,Object> selectAll(MakeLog makeLog,String startPage,String pageSize,HttpServletRequest request) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageHelper.startPage(Integer.parseInt(startPage),Integer.parseInt(pageSize));
		List<MakeLog> makeLogs = makeLogMapper.selectAll(makeLog);
		PageInfo<MakeLog> pageInfo = new PageInfo<>(makeLogs);
		int count = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
		JSONArray jsonArray = new JSONArray();
		for(MakeLog log : makeLogs) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",log.getId());
			jsonObject.put("logName",log.getLogName());
			jsonObject.put("userId",log.getUserId());
			jsonObject.put("sysUser",log.getSysUser());
			jsonObject.put("userIp",log.getUserIp());
			jsonObject.put("sysName",log.getSysName());
			jsonObject.put("windowName",log.getWindowName());
			jsonObject.put("logType",log.getLogType());
			jsonObject.put("createDate",simpleDateFormat.format(log.getCreateDate()));
			jsonArray.add(jsonObject);
		}
		return ResultUtils.tableSuccess(jsonArray, count);
	}
}
