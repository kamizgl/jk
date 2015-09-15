package cn.itcast.jk.action.sysadmin;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ModuleService;
import cn.itcast.jk.service.RoleService;

public class RoleAction extends BaseAction implements ModelDriven<Role>{
	 private Role model = new Role();
		public Role getModel() {
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
		
		private RoleService roleService;
		public void setRoleService(RoleService roleService) {
			this.roleService = roleService;
		}
		private ModuleService moduleService;
		public void setModuleService(ModuleService moduleService) {
			this.moduleService = moduleService;
		}
		//分页查询部门列表
		public String list() throws Exception {
			String hql = "from Role";
			List<Role> list = roleService.find(hql, Role.class,null);
		    super.put("dataList",list);
			return "list";
		}
		
		//查看详情
		public String toview() throws Exception {
			//接收参数的工作是ModelDriven拦截器干的   
			Role dept = roleService.get(model.getId());
			//将查询的详情加入到值栈的栈顶
			super.push(dept);
			return "toview";
		}
		
		//进入新增页面
		public String tocreate() throws Exception {
			
			
			
			return "tocreate";
		}
		
		//添加部门
		public String insert() throws Exception {
			roleService.saveOrUpdate(model);
			return "alist";
		}
		
		//删除操作
		public String delete() throws Exception {
			//1.接收要删除的多个id的值----struts2框架可以接收
			//2.判断是删除一条还是多条
			String []ids = model.getId().split(", ");
			if(ids!=null && ids.length>1){
				//说明要删除多个部门
				roleService.delete(ids);
			}else{
				roleService.deleteById(model.getId());
			}
			return "alist";
		}
		
		//进入修改页面
		public String toupdate() throws Exception {
			super.push(roleService.get(model.getId()));

			return "toupdate";
		}
		
		//修改操作
		public String update() throws Exception {
			roleService.saveOrUpdate(model);
			return "alist";
		}
		
		//进入到权限分配的页面
		public String tomodule() throws Exception {
			Role role = roleService.get(model.getId());
			super.push(role);
			return "tomodule";
		}
		
		//加载权限列表，形成符合zTree要求的json数据格式
		//加载json字符串有哪些？  json-lib ，  struts2-json-plugin.jar 插件，手动拼接json串
		//[{id:"",pId:"",name:"",checked:"true|false"},{id:"",pId:"",name:"",checked:"true|false"}]
		public void roleModuleJsonStr()throws Exception{
			//1.加载出所有的权限菜单 （模块）
			List<Module> list = moduleService.find("from Module", Module.class, null);
			//2.根据当前的用户角色编号，得到用户角色对象
			Role role = roleService.get(model.getId());
			
			//3.根据角色，通过关联级别的数据检索，得到它的权限列表
			Set<Module> moduleSet = role.getModules();
			//4.遍历所有的权限列表，并且拼接json字符串
			StringBuilder sb = new StringBuilder();
			int size=list.size();
			sb.append("[");
			for(Module m :list){
				size--;
				sb.append("{id:\"");
				sb.append(m.getId());
				
				sb.append("\",pId:\"");
				sb.append(m.getParentId());
				
				sb.append("\",name:\"");
				sb.append(m.getName());
				
				sb.append("\",checked:\"");
				if(moduleSet.contains(m)){
					sb.append("true");
				}else{
					sb.append("false");
				}
				
				sb.append("\"}");
				if(size>0)
					sb.append(",");
			}
			sb.append("]");
			
			//5.手动返回字符串并输出 
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			
			PrintWriter out = response.getWriter();
			out.write(sb.toString());
			out.flush();
			out.close();
		}
		
		// 保存权限树上选中的结点
		public String module() throws Exception {
			//1.分隔字符串
			String []ids = moduleIds.split(",");
			Set<Module> modules = new HashSet<Module>();
			for(String id: ids){
				Module m = moduleService.get(id);//根据id,加载模块对象
				modules.add(m);//将模块对象放入集合中
			}
			
			//2.加载角色对象
			Role role = roleService.get(model.getId());
			
			//3.更新角色的权限列表
			role.setModules(modules);
			
			//4.更新角色
			roleService.saveOrUpdate(role);
			
			return "alist";
		}
		//封装zTree树上选中的结点的id
		private String moduleIds;
		public String getModuleIds() {
			return moduleIds;
		}
		
		public void setModuleIds(String moduleIds) {
			this.moduleIds = moduleIds;
		}
		
		

}
