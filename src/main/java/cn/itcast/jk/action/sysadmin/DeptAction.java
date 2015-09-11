/**
 * 
 */
package cn.itcast.jk.action.sysadmin;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年7月19日
 * @version 1.0
 * 
 * 继承BaseAction的两个优点:
 *      1.BaseAction中已经继承了ActionSupport,并实现了其它很多接口（如RequestAware注入reqest），意味着功能比ActionSupprot更强大
 *      2.可以实现与struts2的ActionSupport解藕
 *      
 */
public class DeptAction extends BaseAction implements ModelDriven<Dept> {
	private Dept model = new Dept();
	public Dept getModel() {
		return model;
	}
	
	//依赖DpetService
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	//分页组件引入
	private Page page = new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
	//======================================业务方法===========================
	
	
	/**
	 * 查询所有部门列表，并进行分页
	 */
	/**
	 * 
	 */
	public String list() throws Exception {
		page = deptService.findPage("from Dept", page, Dept.class, null);
		page.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/deptAction_list");//不要加？号，原因是所有的参数都在Page组件中
		put("page", page);//放在context区域 
		return "plist";
	}
	
	public String toview() throws Exception{
		Dept dept = deptService.get(Dept.class, model.getId());
		push(dept);
		return "view";
	}
	
	
	public String tocreate() throws Exception {
		List<Dept> dept = deptService.find("from Dept", Dept.class, null);
		put("deptList",dept);
		return "tocreate";
	}
	public String insert() throws Exception{
//		String deptName = model.getDeptName();
//		String id = model.getParentDept().getId();
		deptService.saveOrUpdate(model);
		return "toinsert";
	}
	
}
