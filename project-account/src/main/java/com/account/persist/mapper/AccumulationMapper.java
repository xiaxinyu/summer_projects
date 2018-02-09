package com.account.persist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.account.persist.model.Accumulation;
import com.account.persist.model.Page;

/**
 * Created by Summer.Xia on 10/14/2015.
 */
public interface AccumulationMapper {
	void addAccumulation(Accumulation accumulation);
	
	void updateAccumulation(Accumulation accumulation);
	
	void deleteAccumulation(String id);
	
	int countAccumulations(@Param("accumulation")Accumulation accumulation);
	
	List<Accumulation> getAccumulations(@Param("accumulation")Accumulation accumulation,@Param("page")Page page);
}
