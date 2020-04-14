package com.product.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.product.model.MakeLog;

/***
 * @Title 操作日期接口层
 * @author wuyongchao
 * @date 2019-12-16 17:03:12
 *
 */
public interface MakeLogService {

	public void insertLog(MakeLog makeLog);
	
	public Map<String,Object> selectAll(MakeLog makeLog,String startPage,String endPage,HttpServletRequest request);

}
