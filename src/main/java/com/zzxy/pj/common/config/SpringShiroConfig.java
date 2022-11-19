package com.zzxy.pj.common.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration//当作配置文件
public class SpringShiroConfig {


    /* @Bean
     public SecurityManager securityManager(){
         DefaultWebSecurityManager manage = new DefaultWebSecurityManager();
         return manage;
     }
 */
    //@Bean注解会把方法的返回值对象交给容器管理，id为方法名
    @Bean
    public SecurityManager securityManager(Realm realm,CacheManager cacheManager,RememberMeManager rememberMeManager,SessionManager sessionManager) {
        DefaultWebSecurityManager sManager=
                new DefaultWebSecurityManager();
        sManager.setRealm(realm);
        sManager.setCacheManager(cacheManager);
        sManager.setRememberMeManager(rememberMeManager);
        sManager.setSessionManager (sessionManager);
        return sManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //LinkedHashMap:有序的
        //用map来存储路径，配置是否放过这些路径
        //key:表示路径，static开始的路径
        //value:表示放过还是认证,value值是固定的几种
        //           authc表示要认证，user表示要认证，anon表示放过
        //           logout表示退出
        Map<String,String> map = new LinkedHashMap<>();
        //map.put("/doLoginUI","anon");//放过登录
        shiroFilter.setLoginUrl("/doLoginUI");
        //静态资源允许匿名访问:"anon"
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        map.put("/user/doLogin","anon");
        map.put("/doLogOut","logout");//配置退出路径
        //其他路径都要认证
        map.put("/**","user");//使用了记住我功能一定要改成user
        //设置到shiro的过滤器中
        shiroFilter.setFilterChainDefinitionMap(map);
        return shiroFilter;
    }


    @Bean
    //授权方法匹配
    public AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        //类似于切入点
        AuthorizationAttributeSourceAdvisor advisor=
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    //CacheManager的方法名不能是cacheManager,因为框架中已经有一个了
    //CacheManager的id不能重复
    public CacheManager shiroCacheManager(){
        //shiro使用的缓存策略：软引用，内存满了的时候缓存会被回收
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cManager=
                new CookieRememberMeManager();
        SimpleCookie cookie=new SimpleCookie("rememberMe");
        cookie.setMaxAge(30);//单位为秒
        cManager.setCookie(cookie);
        return cManager;
    }
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sManager=
                new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60*60*1000);//单位毫秒
        return sManager;
    }

}
