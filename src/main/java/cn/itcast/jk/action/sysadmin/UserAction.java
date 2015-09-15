/**
 * 
 */
package cn.itcast.jk.action.sysadmin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.RoleService;
import cn.itcast.jk.service.UserService;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月10日
 * @version 1.0
 * 
 * 思考问题：为什么继承BaseAction
 * 1.做到当前的Action与Struts2   Action的API解藕合
 * 2.可以在BaseAction中提供一些通用操作，这样子类就可以直接调用父类中的相关方法
 */
public class UserAction extends BaseAction implements ModelDriven<User> {
    private User model = new User();
	public User getModel() {
		return model;
	}
	//分页
	private Page page = new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	//引入业务逻辑
	private UserService userService ;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	
	//分页查询部门列表
	public String list() throws Exception {
		String hql = "from User";
		page =  userService.findPage(hql, page, null);
		
	    page.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/userAction_list");//设置上一页，下一页的链接
		put("page", page);//放在context区域 

		return "list";
	}
	
	//查看详情
	public String toview() throws Exception {
		//接收参数的工作是ModelDriven拦截器干的   
		User user = userService.get(model.getId());
		//将查询的详情加入到值栈的栈顶
		super.push(user);
		return "toview";
	}
	
	//进入新增页面
	public String tocreate() throws Exception {
		//1.加载所有的用户信息
		List<User> userList = userService.find("from User o where o.state=1", User.class, null);
		//2将用户信息放入值栈中
		super.put("userList", userList);
		
		//3.加载所有的部门列表
		List<Dept> deptList = deptService.find("from Dept o where o.state=1", Dept.class, null);
		super.put("deptList", deptList);
		
		
		return "tocreate";
	}
	
	//添加部门
	public String insert() throws Exception {
		System.out.println("wolaila");
		
		userService.saveOrUpdate(model);
		return "alist";
	}
	
	//删除操作
	public String delete() throws Exception {
		//1.接收要删除的多个id的值----struts2框架可以接收
		//2.判断是删除一条还是多条
		String []ids = model.getId().split(", ");
		if(ids!=null && ids.length>1){
			//说明要删除多个部门
			userService.delete(ids);
		}else{
			userService.deleteById(model.getId());
		}
		return "alist";
	}
	
	//进入修改页面
	public String toupdate() throws Exception {
		//1.加载所有的部门列表
		List<Dept> deptList = deptService.find("from Dept o where o.state=1", Dept.class, null);
		super.put("deptList", deptList);

				
		//2.加载原来的用户信息
		User user = userService.get(model.getId());
		super.push(user);

		return "toupdate";
	}
	
	//修改操作
	public String update() throws Exception {
		userService.saveOrUpdate(model);
		return "alist";
	}
	
	//展示角色列表
	public String torole() throws Exception {
		//1.查询所有的角色列表
		List<Role> roles = roleService.find("from Role ", Role.class, null);
		super.put("roleList", roles);
		
		//2.加载当前的用户  用户的id保存在model对象中
		User user = userService.get(model.getId());
		super.push(user);
		
		//3.形成一个当前用户的角色字符串
		StringBuffer sb = new StringBuffer(" ");
		Set<Role> roleSet = user.getRoles();
		Iterator<Role> it = roleSet.iterator();
		while(it.hasNext()){
			Role role = it.next();//得到当前用户的一个角色
			sb.append(role.getId()+",");
		}
		//4.将角色字符串放入值栈中
		super.put("userRoleStr", sb.substring(0, sb.length()-1));//不存在一个用户使得他没有任何角色，所以要在数据库事先加入用户角色的关系。   100,200,300,
		
		//5.转到页面
		return "torole";
	}

	//分配角色 
		public String role() throws Exception {
			//1.得到用户对象
			User user = userService.get(model.getId());
			//2.加载角色对象
			Set<Role> roles = new HashSet<Role>();//加入一个角色的集合，用于存储当前用户选中的角色
			System.out.println(roleids);
			for(String roleid : roleids.split(", ")){
				Role role = roleService.get(roleid);
				roles.add(role);
			}
			
			//3.更新用户的角色列表
			user.setRoles(roles);
			
			//4.保存数据
			userService.saveOrUpdate(user);
			//5.跳页面
			return "alist";
		}
		private String roleids;   //属性驱动
		public void setRoleids(String roleids) {
			this.roleids = roleids;
		}
}
