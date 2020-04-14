package com.product.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.annotation.LoginInterfaceAnnotation;
import com.product.model.MakeLog;
import com.product.service.MakeLogService;

/***
 * @Title 日志操作控制层
 * @author wuyongchao
 * @date 2019-12-18 10:22:12
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {
	
	@Autowired
	private MakeLogService makeLogService;

	@RequestMapping(value = "/queryLog",method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String,Object> queryLog(MakeLog makeLog,HttpServletRequest request){
		String startPage = request.getParameter("page");
		String pageSize = request.getParameter("limit");
		return makeLogService.selectAll(makeLog,startPage,pageSize,request);
	}
}
