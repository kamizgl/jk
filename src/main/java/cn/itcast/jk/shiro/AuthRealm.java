package cn.itcast.jk.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.service.UserService;

public class AuthRealm extends AuthorizingRealm{
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	//授权 这个工作是在认证通过之后才会执行的
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		System.out.println("授权");
		//得到user对象 从指定的realm中获取user信息
		User user = (User)principal.fromRealm(getName()).iterator().next();
		//2获取user的相关角色
		Set<Role> roles = user.getRoles();//得到用户的所有角色
		//3 形成一个权限字符串儿（模块字符串）
		StringBuilder sb = new StringBuilder();
		//4遍历每个角色，并取出角色下的所有权限(模块)
		for (Role role:roles) {
			//取出一个角色，然后加载角色下面的所有的权限
			Set<Module> modules = role.getModules();
			for(Module m: modules) {
				sb.append(m.getCpermission());//用户管理，角色管理
				sb.append(",");
			}
		}
		//5添加权限列表并且返回
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission(sb.toString());
		return info;
	}

	//认证 参数的值，来自于用户在前段输入的用户名，密码
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1转化
		UsernamePasswordToken upToken=	(UsernamePasswordToken)token;
		//2得到用户名
		String username = upToken.getUsername();
		//3.调用业务方法，根据用户名查询一个用户对象。
		User user = userService.findUserByUsername(username);
		System.out.println(user.getPassword()+"-----------------------password");
		if(user==null) {
			return null;//说明用户名错误
		}else {
			return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		}
	}

}
