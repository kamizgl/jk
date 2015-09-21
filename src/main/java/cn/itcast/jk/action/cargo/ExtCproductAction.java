/**
 * 
 */
package cn.itcast.jk.action.cargo;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.service.ExtCproductService;
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
public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {
    private ExtCproduct model = new ExtCproduct();
	public ExtCproduct getModel() {
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
	private ExtCproductService extCproductService ;
	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}
	private FactoryService factoryService ;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	private ContractService contractService;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	//分页查询部门列表
	public String list() throws Exception {
		String hql = "from ExtCproduct";
		page =  extCproductService.findPage(hql, page, null);
		
	    page.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/extCproductAction_list");//设置上一页，下一页的链接
	    
		return "list";
	}
	
	//查看详情
	public String toview() throws Exception {
		//接收参数的工作是ModelDriven拦截器干的   
		ExtCproduct dept = extCproductService.get(model.getId());
		//将查询的详情加入到值栈的栈顶
		super.push(dept);
		return "toview";
	}
	
	//进入新增页面
	public String tocreate() throws Exception {
	    //1.查询生产附件的所有厂家
		String hql = "from Factory f where f.ctype='附件' and f.state=1";
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
		super.put("factoryList", factoryList);
		//2.查询指定货物下的所有附件列表
		hql = "from ExtCproduct ecp where ecp.contractProduct.id=?";
		List<ExtCproduct> dataList = extCproductService.find(hql, ExtCproduct.class, new Object[]{model.getContractProduct().getId()});
		super.put("dataList", dataList);
		
		return "tocreate";
	}
	
	//添加部门
	public String insert() throws Exception {
		extCproductService.saveOrUpdate(model);
		return tocreate();
	}
	
	//删除操作
	public String delete() throws Exception {
		//1.加载指定附件对象
		ExtCproduct extCproduct = extCproductService.get(model.getId());
		
		//2.加载这个附件所对应购销合同
		Contract contract = extCproduct.getContractProduct().getContract();
		
		//3.修改购销合同的总金额 
		contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
		
		//4.删除附件，更新购销合同
		extCproductService.deleteById(extCproduct.getId());
		contractService.saveOrUpdate(contract);
		
		//5.跳转页面
		return tocreate();
	}
	
	//进入修改页面
	public String toupdate() throws Exception {
		 //1.查询生产附件的所有厂家
		String hql = "from Factory f where f.ctype='附件' and f.state=1";
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
		super.put("factoryList", factoryList);
		
		
		//2.根据修改的部门id,查询出一个附件对象
		ExtCproduct ecp = extCproductService.get(model.getId());
		super.push(ecp);
		
		
		return "toupdate";
	}
	
	//修改操作
	public String update() throws Exception {
		//1.找到要更新的附件对象
		ExtCproduct extCproduct = extCproductService.get(model.getId());
		
		
		extCproduct.setFactory(model.getFactory());
		extCproduct.setFactoryName(model.getFactoryName());
		extCproduct.setProductNo(model.getProductNo());
		extCproduct.setProductImage(model.getProductImage());
		extCproduct.setCnumber(model.getCnumber());
		extCproduct.setPackingUnit(model.getPackingUnit());
		extCproduct.setOrderNo(model.getOrderNo());
		extCproduct.setProductDesc(model.getProductDesc());
		extCproduct.setProductRequest(model.getProductRequest());
		extCproduct.setPrice(model.getPrice());
       	
		//2.更新附件
		extCproductService.saveOrUpdate(extCproduct);
		return tocreate();
	}
}
