package com.zzxy.pj.sys.service;


import com.zzxy.pj.sys.entity.nonvCity;

import java.util.List;

public interface mapService {

    /**
     * 查找城市感染人数(显示在地图上)
     * @return
     */
    List<nonvCity> findAllCityList();


}
