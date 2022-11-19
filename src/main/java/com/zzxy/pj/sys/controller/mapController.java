package com.zzxy.pj.sys.controller;

import com.zzxy.pj.common.entity.JsonResult;
import com.zzxy.pj.sys.entity.nonvCity;
import com.zzxy.pj.sys.service.mapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("map")
public class mapController {

    @Autowired
    private mapService service;

    @RequestMapping("yqMap")
    public JsonResult findCtiy(){
        List<nonvCity> list=service.findAllCityList();
        return new JsonResult(list);
    }


}
