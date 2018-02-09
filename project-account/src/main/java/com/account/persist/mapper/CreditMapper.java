package com.account.persist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.account.persist.model.Credit;
import com.account.persist.model.KeyValue;
import com.account.persist.model.Page;

/**
 * Created by Summer.Xia on 08/27/2014.
 */
public interface CreditMapper {
	void addCredit(Credit credit);
	
	void addCreditList(@Param("credits")List<Credit> credits);
	
	void updateCredit(Credit credit);
	
	void deleteCredit(String id);
	
	int countCredit(@Param("credit")Credit credit);
	
	List<Credit> getCredits(@Param("credit")Credit credit,@Param("page")Page page);
	
	List<KeyValue> consumeReport(@Param("credit")Credit credit);
	
	List<KeyValue> weekConsumeReport(@Param("credit")Credit credit);
	
	List<KeyValue> monthConsumeReport(@Param("credit")Credit credit);
}
