package com.zzxy.pj.sys.service.impl;

import com.zzxy.pj.common.util.Assert;
import com.zzxy.pj.sys.dao.mapDao;
import com.zzxy.pj.sys.entity.nonvCity;
import com.zzxy.pj.sys.service.mapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class mapServiceImpl implements mapService {

    @Autowired
    private mapDao mapDao;
    public List<nonvCity> findAllCityList() {
        List<nonvCity> list= mapDao.findAllCityList();
         Assert.isEmpty(list==null||list.size()==0,"查询数据失败");
        return list;
    }


}
