/**
 * 
 */
package cn.itcast.jk.action.cargo;

import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractService;

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
public class ContractAction extends BaseAction implements ModelDriven<Contract> {
	private int size;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	
    private Contract model = new Contract();
	public Contract getModel() {
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
	private ContractService contractService ;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	

	//分页查询部门列表    用户等级目前只考虑了员工4，部门经理3,其它没有考虑（   将来可以用1代表总经理   2代表副总）
	public String list() throws Exception {
		User user = super.getCurrentUser();//从session中获取登录的用户
		
		String hql = "from Contract c where 1=1 ";
		
		if(user.getUserinfo().getDegree()==4){
			//说明为普通员工
			hql+=" and c.createBy= '"+user.getId()+"'";
		}
		if(user.getUserinfo().getDegree()==3){
			//说明为部门经理
			hql += " and c.createDept= '"+user.getDept().getId()+"'";
		}
		
		
		page =  contractService.findPage(hql, page, null);
		Contract c = (Contract)page.getResults().get(0);
	    size = c.getContractProducts().size();
		System.out.println(size+"------------------------");
	    page.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/contractAction_list");//设置上一页，下一页的链接
	    
		return "list";
	}
	
	//查看详情
	public String toview() throws Exception {
		//接收参数的工作是ModelDriven拦截器干的   
		Contract dept = contractService.get(model.getId());
		//将查询的详情加入到值栈的栈顶
		super.push(dept);
		return "toview";
	}
	
	//进入新增页面
	public String tocreate() throws Exception {
		
		
		return "tocreate";
	}
	
	//添加
	public String insert() throws Exception {
		//在保存之前，先给创建者，创建者的部门相关属性赋值
		User user = super.getCurrentUser();
		model.setCreateBy(user.getId());//创建者
		model.setCreateDept(user.getDept().getId());//当前记录的创建者所在部门
		contractService.saveOrUpdate(model);
		return "alist";
	}
	
	//删除操作
	public String delete() throws Exception {
		//1.接收要删除的多个id的值----struts2框架可以接收
		//2.判断是删除一条还是多条
		String []ids = model.getId().split(", ");
		if(ids!=null && ids.length>1){
			//说明要删除多个部门
			contractService.delete(ids);
		}else{
			contractService.deleteById(model.getId());
		}
		return "alist";
	}
	
	//进入修改页面
	public String toupdate() throws Exception {
		//根据修改的部门id,查询出一个部门对象
		Contract dept = contractService.get(model.getId());
		//2.将部门对象放入值栈的栈顶
		super.push(dept);
		
		//3.加载所有的部门
		List<Contract> list = contractService.find("from Contract o where o.state=1", Contract.class, null);
		//4将部门列表放入值栈中
		super.put("deptList", list);
		
		
		return "toupdate";
	}
	
	//修改操作
	public String update() throws Exception {
		contractService.saveOrUpdate(model);
		return "alist";
	}
	
	//提交
	public String submit() throws Exception
	{
		changeState(1,0);
		return list();
	}
	//取消
	public String cancel() throws Exception
	{
		changeState(0,1);
		return list();
	}
	private void changeState(int newState,int oldState) {
		String[] ids = model.getId().split(", ");
		if(ids!=null&&ids.length>0)
		{
			for(String id: ids) {
				Contract contract = contractService.get(id);
				if(contract.getState()==oldState) {
					contract.setState(newState);
					contractService.saveOrUpdate(contract);
				}
			}
		}
		else {
			Contract contract = contractService.get(model.getId());
			Integer state = contract.getState();
			if(state==oldState) {
				contract.setState(newState);
				contractService.saveOrUpdate(contract);
			}
		}
	}
}
