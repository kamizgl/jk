package cn.itcast.jk.action;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.itcast.common.SysConstant;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.util.UtilFuns;

/**
 * @Description: 登录和退出类
 * @Author:		传智播客 java学院	传智.宋江
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014年10月31日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;



	//SSH传统登录方式
	public String login() throws Exception {
		
//		if(true){
//			String msg = "登录错误，请重新填写用户名密码!";
//			this.addActionError(msg);
//			throw new Exception(msg);
//		}
//		User user = new User(username, password);
//		User login = userService.login(user);
//		if (login != null) {
//			ActionContext.getContext().getValueStack().push(user);
//			session.put(SysConstant.CURRENT_USER_INFO, login);	//记录session
//			return SUCCESS;
//		}
//		return "login";
		
		
		if(UtilFuns.isEmpty(userName)) {
			return "login";
		}try {
			//1得到Subject
			Subject subject=SecurityUtils.getSubject();
			//2.调用login()方法，实现登陆功能，此时将自动AuthRealm.doGetAuthenticationInfo()来实现登录的判断
		
			UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
			subject.login(token);
			//3.从realm得到用户信息
			User user =(User) subject.getPrincipal();
			//4.为了加载当前这个用户的相关数据（角色，权限），使用即时加载方式 
			System.out.println(user.getDept().getDeptName());
			Set<Role> roles = user.getRoles();
			for(Role role :roles){
				Set<Module> modules = role.getModules();
				for(Module m :modules){
					System.out.println(m.getCpermission());
				}
			}
		//5将用户信息存入session中
			session.put(SysConstant.CURRENT_USER_INFO, user);
		}
		catch(Exception e) {
			super.put("errorInfo", "用户名密码错误，请重新登陆");
			e.printStackTrace();
			return LOGIN;
		}
		return SUCCESS;
	}
	
	
	//退出
	public String logout(){
		session.remove(SysConstant.CURRENT_USER_INFO);		//删除session
		
		return "logout";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

