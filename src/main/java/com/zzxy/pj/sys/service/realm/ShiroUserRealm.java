package com.zzxy.pj.sys.service.realm;

import com.zzxy.pj.sys.dao.SysMenuDao;
import com.zzxy.pj.sys.dao.SysRoleMenuDao;
import com.zzxy.pj.sys.dao.SysUserDao;
import com.zzxy.pj.sys.dao.SysUserRoleDao;
import com.zzxy.pj.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao userRoleDao;

    @Autowired
    private SysRoleMenuDao roleMenuDao;

    @Autowired
    private SysMenuDao menuDao;

    /**
     * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
     */
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //构建凭证匹配对象
        HashedCredentialsMatcher cMatcher=
                new HashedCredentialsMatcher();
        //设置加密算法
        cMatcher.setHashAlgorithmName("MD5");
        //设置加密次数
        cMatcher.setHashIterations(1);
        super.setCredentialsMatcher(cMatcher);
    }
    /**
     * 通过此方法完成认证数据的获取及封装,系统
     * 底层会将认证数据传递认证管理器，由认证
     * 管理器完成认证操作。
     * @return
     */

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        int userId = user.getId();
        List<Integer> roleIds = userRoleDao.findRoleByUserId(userId);
        if (roleIds == null || roleIds.size() == 0){
            throw new AuthorizationException();
        }
        List<Integer> menuIds = roleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(new Integer[]{}));
        if (menuIds == null || menuIds.size() == 0){
            throw new AuthorizationException();
        }
        List<String> permission = menuDao.findPermissions (menuIds.toArray(new Integer[]{}));
        System.out.println(permission+"******************");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        for (String str: permission) {
            if (str != null) {
                set.add(str);
            }
        }
        System.out.println(set+"&*&*&*&*&*&**");
        info.setStringPermissions(set);
        return info;
    }

    /*public static void main(String[] args) {
        String[] arr = {"123","dsfsd","fsdfa"};
        List<String> list = Arrays.asList(arr);
        //遍历，相当于增强for循环
        //list.stream():变成了一个Stream流对象
        //forEach():遍历方法
        //System.out::println：调用System.out的println方法
        list.stream().forEach(System.out::println);
    }*/
    //重写认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户名(用户页面输入)
        //向下造型
        //UsernamePasswordToken封装的是浏览器传过来的的用户数据
        UsernamePasswordToken upToken=
                (UsernamePasswordToken)token;
        //获取用户名
        String username=upToken.getUsername();
        //2.基于用户名查询用户信息
        SysUser user=
                sysUserDao.findUserByName (username);
        //3.判定用户是否存在
        if(user==null)
            throw new UnknownAccountException();//shiro提供的异常
        //4.判定用户是否已被禁用。
        if(user.getValid()==0)
            throw new LockedAccountException();

        //5.封装用户信息
        ByteSource credentialsSalt=
                ByteSource.Util.bytes(user.getSalt());
        //记住：构建什么对象要看方法的返回值
        SimpleAuthenticationInfo info=
                new SimpleAuthenticationInfo(
                        user,//principal (身份,保证唯一)
                        user.getPassword(),//hashedCredentials  密码
                        credentialsSalt, //credentialsSalt   盐
                        getName());//realName  realm的名字
        System.out.println(info+"***********************");
        //6.返回封装结果
        return info;//返回值会传递给认证管理器(后续
        //认证管理器会通过此信息完成认证操作)
    }


}
