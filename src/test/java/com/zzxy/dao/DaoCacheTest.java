package com.zzxy.dao;

import com.zzxy.pj.sys.entity.SysMenu;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @version: java version 1.8
 * @Author: xun
 * @description:
 * @date: 2022-11-11 10:57
 */
@SpringBootTest
public class DaoCacheTest {

    //测试mybatis的一级缓存
    //mybatis的流程：
    //          SqlSessionFactory  工厂
    //          SqlSession      一次请求产生一个sqlSession
    //          Excutor
    //          MaperStatement

    @Autowired
    private SqlSessionFactory ssf;

    @Test
    public void testOneCache() {
        //同一个sqlsession去查同一条sql语句，会触发一级缓存
        //但实际开发中,一次请求同时调用同一sql情况很少，
        //mybatis默认开启一级缓存，但没啥用
        //工厂创建一个sqlSession
        SqlSession sqlSession = ssf.openSession();
        List<Object> list = sqlSession.selectList("com.zzxy.pj.sys.dao.SysMenuDao.findObjects");
        System.out.println(list);

        sqlSession = ssf.openSession();
       list= sqlSession.selectList("com.zzxy.pj.sys.dao.SysMenuDao.findObjects");
        System.out.println(list);

    }

    @Test
    public  void testTowCache(){
        //二级缓存一定要全部commit
        SqlSession sqlSession = ssf.openSession();
        sqlSession = ssf.openSession();
         List<Object> list = sqlSession.selectList("com.zzxy.pj.sys.dao.SysMenuDao.findObjects");
        System.out.println(list);

        SysMenu sysMenu = new SysMenu (null,"BBBB","BBB",1,1,null,null,null,null,null,null,null);
        list= sqlSession.selectList("com.zzxy.pj.sys.dao.SysMenuDao.insertMenu",sysMenu);
        System.out.println(list);
        sqlSession.commit();

        sqlSession = ssf.openSession();
        list = sqlSession.selectList("com.zzxy.pj.sys.dao.SysMenuDao.findObjects");
        System.out.println(list);
        sqlSession.commit();
    }

    public void findLogTest () {

    }
}
