package com.account.service.face;

import com.account.persist.model.CreditRecord;
import com.account.service.exception.AppServiceException;

/**
 * Created by Summer.Xia on 9/7/2015.
 */
public interface ICreditRecordService {
	void addCreditRecord(CreditRecord creditRecord) throws AppServiceException;
}
