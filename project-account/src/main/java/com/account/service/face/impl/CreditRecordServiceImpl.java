package com.account.service.face.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.persist.mapper.CreditRecordMapper;
import com.account.persist.model.CreditRecord;
import com.account.service.exception.AppServiceException;
import com.account.service.face.ICreditRecordService;

/**
 * Created by Summer.Xia on 9/7/2015.
 */
@Service("creditRecordService")
public class CreditRecordServiceImpl implements ICreditRecordService {
	@Autowired
	private CreditRecordMapper creditRecordMapper;

	public void addCreditRecord(CreditRecord creditRecord) throws AppServiceException {
		try{
			creditRecordMapper.addCreditRecord(creditRecord);
		}catch(Exception e){
			throw new AppServiceException(e);
		}
	} 
}
