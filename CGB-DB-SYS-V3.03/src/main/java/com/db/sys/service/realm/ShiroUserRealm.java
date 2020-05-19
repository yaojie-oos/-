package com.db.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
import org.springframework.util.StringUtils;

import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm{
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	/**
	 * 设置凭证匹配器（此方法用于告诉认证
	 * 管理器采用什么加密算法对用户输入密码进行加密）
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		//1.构建CredentialsMatcher对象
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		//2.设置加密算法
		hcm.setHashAlgorithmName("md5");
		//3.设置加密次数
		hcm.setHashIterations(1);
		super.setCredentialsMatcher(hcm);
	}
	
	/**
	 * 通过此方法完成认证信息的获取和封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.从token中获取用户信息（前端用户输入）
		UsernamePasswordToken uptoken = 
		(UsernamePasswordToken)token;
		String username = uptoken.getUsername();
		//2.基于用户名从数据库查询用户信息
		SysUser user = sysUserDao.findUserByUserName(username);
		//3.判定的用户是否存在
		if(user==null)
			throw new UnknownAccountException();
		//4.判定用户是否被禁用
		if(user.getValid()==0)
			throw new LockedAccountException();
		//5.封装用户信息并且返回
		ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user,//principal 表示身份信息
				user.getPassword(),//hashedCredentials 已加密的密码
				credentialsSalt, //salt
				getName());//realmName
		return info;//此对象返回给认证管理器
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.获取登入用户id
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		//2.基于用户id获取用户对应的角色id并进行判定
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if(roleIds==null||roleIds.isEmpty())
			throw new AuthorizationException();
		//3.基于角色id获取对应的菜单id并判定
		Integer[] array= {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
		if(menuIds==null||menuIds.isEmpty())
			throw new AuthorizationException();
		//4.基于菜单id获取权限标识
		List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
		if(permissions==null||permissions.isEmpty())
			throw new AuthenticationException();
		//5.封装数据并且返回
		Set<String> permissionSet = new HashSet<String>();
		for(String per:permissions) {
			if(!StringUtils.isEmpty(per)) {
				permissionSet.add(per);
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissionSet);
		return info;//此信息会返回给授权管理器
	}

}
