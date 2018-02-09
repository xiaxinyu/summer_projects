package com.account.service.face.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.core.tool.DataStructureTool;
import com.account.core.tool.StringTool;
import com.account.persist.mapper.UnEmploymentMapper;
import com.account.persist.model.Page;
import com.account.persist.model.UnEmployment;
import com.account.service.exception.AppServiceException;
import com.account.service.face.IUnEmploymentService;

/**
 * Created by Summer.Xia on 10/13/2015.
 */
@Service
public class UnEmploymentServiceImpl implements IUnEmploymentService{
	@Autowired
	private UnEmploymentMapper unEmploymentMapper;
	
	public void addUnEmployment(UnEmployment unEmployment) throws AppServiceException {
		try {
			unEmploymentMapper.addUnEmployment(unEmployment);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	public void updateUnEmployment(UnEmployment unEmployment) throws AppServiceException {
		try {
			unEmploymentMapper.updateUnEmployment(unEmployment);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	public void deleteUnEmployment(String id) throws AppServiceException {
		try {
			unEmploymentMapper.deleteUnEmployment(id);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	public int countUnEmployments(UnEmployment unEmployment) throws AppServiceException {
		int result = 0;
		try {
			result = unEmploymentMapper.countUnEmployments(unEmployment);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
		return result;
	}

	public List<UnEmployment> getUnEmployments(UnEmployment unEmployment, Page page) throws AppServiceException {
		List<UnEmployment> result = null;
		try {
			result = unEmploymentMapper.getUnEmployments(unEmployment,page);
			if(DataStructureTool.isNotEmpty(result)){
				for (UnEmployment item : result) {
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
