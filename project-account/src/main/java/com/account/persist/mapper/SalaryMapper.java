package com.account.persist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.account.persist.model.Page;
import com.account.persist.model.Salary;

/**
 * Created by Summer.Xia on 12/12/2018.
 */
public interface SalaryMapper {
	int countSalary(@Param("salary") Salary salary);

	List<Salary> getSalarys(@Param("salary") Salary salary, @Param("page") Page page);
}
