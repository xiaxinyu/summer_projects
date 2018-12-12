package com.account.persist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.account.persist.model.HouseRent;
import com.account.persist.model.Page;

/**
 * Created by Summer.Xia on 12/12/2018.
 */
public interface HouseRentMapper {
	int countHouseRent(@Param("houseRent") HouseRent houseRent);

	List<HouseRent> getHouseRents(@Param("houseRent") HouseRent houseRent, @Param("page") Page page);
}
