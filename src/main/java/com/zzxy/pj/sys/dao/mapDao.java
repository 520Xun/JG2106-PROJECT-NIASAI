package com.zzxy.pj.sys.dao;

import com.zzxy.pj.sys.entity.nonvCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface mapDao {

    /**
     * 查询城市感染人数
     * @return
     */
    @Select("select name,value from nocv_data limit 0,10")
    List<nonvCity> findAllCityList();

}
