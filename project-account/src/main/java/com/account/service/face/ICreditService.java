package com.account.service.face;

import java.util.List;

import com.account.persist.model.Credit;
import com.account.persist.model.Page;
import com.account.service.exception.AppServiceException;

/**
 * Created by Summer.Xia on 09/01/2015.
 */
public interface ICreditService {
	void updateCredit(Credit credit) throws AppServiceException;
	
	void deleteCredit(String id) throws AppServiceException;
	
	public List<Credit> getCredits(Credit credit,Page page) throws AppServiceException;
	
	int countCredit(Credit credit) throws AppServiceException;
	
	void addCredits(List<String[]> rowDatas,String customerName,String recordID) throws AppServiceException;
	
	String consumeReport(Credit credit) throws AppServiceException;
	
	String weekConsumeReport(Credit credit) throws AppServiceException;
	
	String monthConsumeReport(Credit credit) throws AppServiceException;
}
