package com.account.service.face.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.core.exception.DateParseException;
import com.account.core.tool.DateTool;
import com.account.core.tool.StringTool;
import com.account.persist.mapper.CreditMapper;
import com.account.persist.model.Credit;
import com.account.persist.model.KeyValue;
import com.account.persist.model.Page;
import com.account.service.exception.AppServiceException;
import com.account.service.face.ICreditService;


/**
 * Created by Summer.Xia on 09/01/2015.
 */
@Service("creditService")
public class CreditServiceImpl implements ICreditService{
	@Autowired
	private CreditMapper creditMapper;

	public void updateCredit(Credit credit) throws AppServiceException {
		try{
			creditMapper.updateCredit(credit);
		}catch(Exception e){
			throw new AppServiceException(e);
		}
	}

	public void deleteCredit(String id) throws AppServiceException {
		try{
			creditMapper.deleteCredit(id);
		}catch(Exception e){
			throw new AppServiceException(e);
		}
	}

	public List<Credit> getCredits(Credit credit, Page page) throws AppServiceException {
		List<Credit> result = null;
		try {
			result = creditMapper.getCredits(credit, page);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
		return result;
	}
	
	public int countCredit(Credit credit) throws AppServiceException{
		int result = 0;
		try {
			result = creditMapper.countCredit(credit);
		}catch(Exception e){
			throw new AppServiceException(e);
		}
		return result;
	}
	
	public void addCredits(List<String[]> rowDatas,String customerName,String recordID) throws AppServiceException{
		try {
			if(rowDatas != null && rowDatas.size() > 0){
				if(StringTool.isNullOrEmpty(customerName)){
					customerName = "others";
				}
				List<Credit> credits = new ArrayList<Credit>();
				for (String[] rowData : rowDatas) {
					if(rowData != null && rowData.length >= 7 ){
						Credit credit = new Credit();
						credit.setCreateuser(customerName);
						credit.setUpdateuser(customerName);
						credit.setId(StringTool.generateID());
						credit.setCardId(StringTool.trim(rowData[0]));
						credit.setTransactionDate(DateTool.changeStringToDate(StringTool.trim(rowData[1]), DateTool.DF_YYYY_MM_DD));
						credit.setBookKeepingDate(DateTool.changeStringToDate(StringTool.trim(rowData[2]), DateTool.DF_YYYY_MM_DD));
						credit.setTransactionDesc(StringTool.trim(rowData[3]));
						credit.setBalanceCurrency(StringTool.trim(rowData[4]));
						credit.setBalanceMoney(StringTool.changeObjToDouble(StringTool.trim(rowData[5])));
						credit.setCardTypeId(1);
						credit.setCardTypeName(StringTool.trim(rowData[6]));
						credit.setRecordID(recordID);
						credits.add(credit);
					}
				}
				if(credits != null && credits.size() > 0){
					creditMapper.addCreditList(credits);
				}else{
					throw new AppServiceException("No exist effective credit data, can't call add credits!");
				}
			}else{
				throw new AppServiceException("No exist original credit data, can't call add credits!");
			}
		}catch(Exception e){
			throw new AppServiceException(e);
		}
	}

	private void fetchCreditParam(Credit credit) throws DateParseException{
		if(StringTool.isNullOrEmpty(credit.getCardTypeName())){
			credit.setCardTypeName(null);
		}
		if(!StringTool.isNullOrEmpty(credit.getConsumeID())){
			credit.setConsumes(credit.getConsumeID().split(","));
		}
		if(!StringTool.isNullOrEmpty(credit.getTransactionDateStartStr())){
			credit.setTransactionDateStart(DateTool.changeStringToDate(credit.getTransactionDateStartStr(), DateTool.DF_MM_DD_YYYY));
		}
		if(!StringTool.isNullOrEmpty(credit.getTransactionDateEndStr())){
			credit.setTransactionDateEnd(DateTool.changeStringToDate(credit.getTransactionDateEndStr(), DateTool.DF_MM_DD_YYYY));
		}
		if(StringTool.isNullOrEmpty(credit.getDemoArea())){
			credit.setDemoArea(null);
		}
	}
	
	public String consumeReport(Credit credit) throws AppServiceException {
		String result = StringTool.EMPTY;
		try {
			fetchCreditParam(credit);
			List<KeyValue> list = creditMapper.consumeReport(credit);
			result = JSONArray.fromObject(list).toString();
		}catch(Exception e){
			throw new AppServiceException(e);
		}
		return result;
	}

	public String weekConsumeReport(Credit credit) throws AppServiceException {
		String result = StringTool.EMPTY;
		try {
			fetchCreditParam(credit);
			List<KeyValue> list = creditMapper.weekConsumeReport(credit);
			result = JSONArray.fromObject(list).toString();
		}catch(Exception e){
			throw new AppServiceException(e);
		}
		return result;
	}
	
	public String monthConsumeReport(Credit credit) throws AppServiceException{
		String result = StringTool.EMPTY;
		try {
			fetchCreditParam(credit);
			List<KeyValue> list = creditMapper.monthConsumeReport(credit);
			result = JSONArray.fromObject(list).toString();
		}catch(Exception e){
			throw new AppServiceException(e);
		}
		return result;
	}
}
