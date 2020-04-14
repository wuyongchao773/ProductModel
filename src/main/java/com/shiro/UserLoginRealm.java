package com.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.product.dao.RoleMapper;
import com.product.dao.RoleMenuMapper;
import com.product.dao.SysMenuMapper;
import com.product.dao.SysUserMapper;
import com.product.dao.UserRoleMapper;
import com.product.model.Role;
import com.product.model.RoleMenu;
import com.product.model.SysMenu;
import com.product.model.SysUser;
import com.product.model.UserRole;

public class UserLoginRealm extends AuthorizingRealm{

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	/***
	 * @Title 用户授权操作
	 * @author wuyongchao
	 * @date 2019-08-24 13:15:23
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//首先拿到用户名查询用户的角色和权限相关信息
		String username = (String)principalCollection.getPrimaryPrincipal();
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser = sysUserMapper.selectUserByUserName(sysUser);
		if(null == sysUser) {
			return null;
		}
		//根据用户id查询用户角色
		UserRole role = userRoleMapper.queryUserRoleByUserId(sysUser.getId());
		//根据角色id查询角色信息
		Set<String> userRoles = new HashSet<String>();
		Set<String> jurisdicts = new HashSet<String>();
		Role userRole = roleMapper.selectByPrimaryKey(role.getRoleId());
		userRoles.add(userRole.getRoleName());
		List<RoleMenu> list = roleMenuMapper.queryRoleMenu(String.valueOf(role.getRoleId()));
		list.stream().map(roleMenu -> roleMenu.getMenuId()).forEach(t ->{
			SysMenu menu = sysMenuMapper.selectByPrimaryKey(String.valueOf(t));
			jurisdicts.add(menu.getMenuName());
		});
		//将角色信息和权限信息保存到shiro中
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userRoles);
		authorizationInfo.setStringPermissions(jurisdicts);
		return authorizationInfo;
	}

	/***
	 * @Title 验证当前用户登陆的用户
	 * @author wuyongchao
	 * @date 2019-08-24 13:16:23
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser = sysUserMapper.selectUserByUserName(sysUser);
		if(null == sysUser) {
			throw new UnknownAccountException("用户不存在");
		}
		ByteSource salt = ByteSource.Util.bytes(username);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),salt,getName());
		return authenticationInfo;
	}
	

}
