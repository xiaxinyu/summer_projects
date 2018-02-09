package com.account.service.face.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.core.tool.DataStructureTool;
import com.account.core.tool.StringTool;
import com.account.persist.mapper.AccumulationMapper;
import com.account.persist.model.Accumulation;
import com.account.persist.model.Page;
import com.account.service.exception.AppServiceException;
import com.account.service.face.IAccumulationService;

/**
 * Created by Summer.Xia on 10/13/2015.
 */
@Service
public class AccumulationServiceImpl implements IAccumulationService{
	@Autowired
	private AccumulationMapper accumulationMapper;
	
	public void addAccumulation(Accumulation accumulation) throws AppServiceException {
		try {
			accumulationMapper.addAccumulation(accumulation);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	public void updateAccumulation(Accumulation accumulation) throws AppServiceException {
		try {
			accumulationMapper.updateAccumulation(accumulation);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	public void deleteAccumulation(String id) throws AppServiceException {
		try {
			accumulationMapper.deleteAccumulation(id);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	public int countAccumulations(Accumulation accumulation) throws AppServiceException {
		int result = 0;
		try {
			result = accumulationMapper.countAccumulations(accumulation);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
		return result;
	}

	public List<Accumulation> getAccumulations(Accumulation accumulation, Page page) throws AppServiceException {
		List<Accumulation> result = null;
		try {
			result = accumulationMapper.getAccumulations(accumulation,page);
			if(DataStructureTool.isNotEmpty(result)){
				for (Accumulation item : result) {
					String time = item.getTime();
					item.setYear(time);
					if(!StringTool.isNullOrEmpty(time)){
						String[] args = time.split("-");
						if(args.length>=2){
							item.setYear(args[0]);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
		return result;
	}
}
