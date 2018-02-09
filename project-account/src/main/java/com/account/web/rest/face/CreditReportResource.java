package com.account.web.rest.face;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.account.persist.model.Credit;
import com.account.service.exception.AppServiceException;
import com.account.service.face.ICreditService;
import com.account.web.rest.face.model.CommonResult;
import com.account.web.rest.face.model.ResultCode;

@Controller
public class CreditReportResource {
	private static final Logger logger = LoggerFactory.getLogger(CreditReportResource.class);
	@Autowired
	private ICreditService creditService;
	
	@RequestMapping("/credit-report/consume")
	@ResponseBody
	public CommonResult consumeReport(Credit credit){
		try {
			String result = creditService.consumeReport(credit);
			return new CommonResult(ResultCode.OPERATION_SUCCEED.getCodeValue(), result);
		} catch (AppServiceException e) {
			logger.error("delete credit failed. params[id = " + credit.getId() + ",consumptionType = " + credit.getConsumptionType() + "]", e);
			return new CommonResult(ResultCode.OPERATION_FAILED.getCodeValue(), e.getMessage());
		}
	}
	
	@RequestMapping("/credit-report/week-consume")
	@ResponseBody
	public CommonResult weekConsumeReport(Credit credit){
		try {
			String result = creditService.weekConsumeReport(credit);
			return new CommonResult(ResultCode.OPERATION_SUCCEED.getCodeValue(), result);
		} catch (AppServiceException e) {
			logger.error("delete credit failed. params[id = " + credit.getId() + ",consumptionType = " + credit.getConsumptionType() + "]", e);
			return new CommonResult(ResultCode.OPERATION_FAILED.getCodeValue(), e.getMessage());
		}
	}
	
	@RequestMapping("/credit-report/month-consume")
	@ResponseBody
	public CommonResult monthConsumeReport(Credit credit){
		try {
			String result = creditService.monthConsumeReport(credit);
			return new CommonResult(ResultCode.OPERATION_SUCCEED.getCodeValue(), result);
		} catch (AppServiceException e) {
			logger.error("delete credit failed. params[id = " + credit.getId() + ",consumptionType = " + credit.getConsumptionType() + "]", e);
			return new CommonResult(ResultCode.OPERATION_FAILED.getCodeValue(), e.getMessage());
		}
	}
}
