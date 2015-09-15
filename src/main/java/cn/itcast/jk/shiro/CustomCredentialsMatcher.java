package cn.itcast.jk.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.itcast.util.Encrypt;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	//执行密码的判断  返回true证明认证过程成功执行后续操作
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		//1.AuthenticationToken中包含了用户在前段输入的用户名和密码
		UsernamePasswordToken upToken =	(UsernamePasswordToken)token;
		System.out.println(new String(upToken.getPassword())+"--------------password ren");
		System.out.println(upToken.getUsername()+"-----------------username ren");
		//2.从upToken中取出密码，用户名（前段输入的用户名和密码）
		String md5 = Encrypt.md5(new String(upToken.getPassword()), upToken.getUsername());
		System.out.println(md5+"-------------------md5 ren");
		//3.得到从数据库中查询的密码，在AuthRealm中的doGetAuthenticationInfo()方法中去得到
		String dbPwd =(String)info.getCredentials();
		System.out.println(dbPwd+"--------------- bdPwd");
		return this.equals(md5,dbPwd);
	}
	
	
}
