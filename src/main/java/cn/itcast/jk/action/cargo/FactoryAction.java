/**
 * 
 */
package cn.itcast.jk.action.cargo;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.jk.service.FactoryService;

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
public class FactoryAction extends BaseAction implements ModelDriven<Factory> {
    private Factory model = new Factory();
	public Factory getModel() {
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
	private FactoryService factoryService ;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	
	private ContractProductService contractProductService;
	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}

	
	//分页查询部门列表
	public String list() throws Exception {
		String hql = "from Factory";
		page =  factoryService.findPage(hql, page, null);
		
	    page.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/factoryAction_list");//设置上一页，下一页的链接
	    
		return "list";
	}
	
	//查看详情
	public String toview() throws Exception {
		//接收参数的工作是ModelDriven拦截器干的   
		Factory dept = factoryService.get(model.getId());
		//将查询的详情加入到值栈的栈顶
		super.push(dept);
		return "toview";
	}
	
	//进入新增页面
	public String tocreate() throws Exception {
		//1.加载所有的部门
		List<Factory> list = factoryService.find("from Factory o where o.state=1", Factory.class, null);
		//2将部门列表放入值栈中
		super.put("deptList", list);
		
		return "tocreate";
	}
	
	//添加部门
	public String insert() throws Exception {
		factoryService.saveOrUpdate(model);
		return "alist";
	}
	
	//删除操作
	public String delete() throws Exception {
		//1.接收要删除的多个id的值----struts2框架可以接收
		//2.判断是删除一条还是多条
		String []ids = model.getId().split(", ");
		if(ids!=null && ids.length>1){
			//说明要删除多个部门
			factoryService.delete(ids);
		}else{
			factoryService.deleteById(model.getId());
		}
		return "alist";
	}
	
	//进入修改页面
	public String toupdate() throws Exception {
		//根据修改的部门id,查询出一个部门对象
//		Factory dept = factoryService.get(model.getId());
//		//2.将部门对象放入值栈的栈顶
//		super.push(dept);
//		
//		//3.加载所有的部门
//		List<Factory> list = factoryService.find("from Factory o where o.state=1", Factory.class, null);
//		//4将部门列表放入值栈中
//		super.put("deptList", list);
//		
//		//2.加载该合同下，已添加的货物列表
//		List<ContractProduct> contractProductlist = contractProductService.find("from ContractProduct cp where cp.contract.id=?", ContractProduct.class, new Object[]{model.getContract().getId()});
//		super.put("dataList", contractProductlist);
	return "tocreate";
	}
	
	//修改操作
	public String update() throws Exception {
		factoryService.saveOrUpdate(model);
		return "alist";
	}
}
