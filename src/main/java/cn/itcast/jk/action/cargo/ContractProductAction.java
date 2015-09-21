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
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.jk.service.ContractService;
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
public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct> {
    private ContractProduct model = new ContractProduct();
	public ContractProduct getModel() {
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
	private ContractProductService contractProductService ;
	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	private FactoryService factoryService;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	//分页查询部门列表
	public String list() throws Exception {
		String hql = "from ContractProduct";
		page =  contractProductService.findPage(hql, page, null);
		
	    page.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/contractProductAction_list");//设置上一页，下一页的链接
	    
		return "list";
	}
	
	//查看详情
	public String toview() throws Exception {
		//接收参数的工作是ModelDriven拦截器干的   
		ContractProduct dept = contractProductService.get(model.getId());
		//将查询的详情加入到值栈的栈顶
		super.push(dept);
		return "toview";
	}
	
	//进入新增页面
	public String tocreate() throws Exception {
		
		String hql="from Factory f where f.ctype='货物' and f.state=1";
		List<Factory> factory = factoryService.find(hql, Factory.class, null);
		super.put("factoryList", factory);
		
		//2.加载该合同下，已添加的货物列表
		List<ContractProduct> list = contractProductService.find("from ContractProduct cp where cp.contract.id=?", ContractProduct.class, new Object[]{model.getContract().getId()});;		
		super.put("dataList", list);
		
		
		return "tocreate";
	}
	
	//
	public String insert() throws Exception {
		System.out.println(model.getContract().getId()+"----------------------------");
		contractProductService.saveOrUpdate(model);
	
		return "alist";
	}
	
	//删除操作
	public String delete() throws Exception {
		//删除货物
		  ContractProduct cp=contractProductService.get(model.getId());
		  
		  //维护表之间的总金额
		  //合同里面的金额减去 合同下面的货物的金额，减去 合同下面的货物的 附件的总金额
		  Set<ExtCproduct> extCproducts = cp.getExtCproducts();
		  //所有的附件的总金额
		  double extSum=0;
		  for(ExtCproduct ext:extCproducts) {
			  Double amount = ext.getAmount();
			  extSum+=amount;
		  }
		  //销售合同里面的总金额
		  double cpSum;
		  cpSum= cp.getAmount();
		  //合同里面的总金额
		  Contract contract = contractService.get(model.getContract().getId());
		  double cSum;
		  cSum = contract.getTotalAmount();
		  //公式
		  cSum = cSum-cpSum-extSum;
		  
		  contract.setTotalAmount(cSum);
		  contractService.saveOrUpdate(contract);
		  
		  
		  //删除
		  contractProductService.deleteById(model.getId());
		
		
		return tocreate();
	}
	
	//进入修改页面
	public String toupdate() throws Exception {
		//1.加载指定货物的原有信息
		String id = model.getId();
		ContractProduct contractProduct = contractProductService.get(id);
		super.push(contractProduct);
		String hql="from Factory f where f.ctype='货物' and f.state=1";
		List<Factory> factory = factoryService.find(hql, Factory.class, null);
		super.put("factoryList", factory);
		
		return "toupdate";
	}
	
	//修改操作
	public String update() throws Exception {
		contractProductService.saveOrUpdate(model);
		return "alist";
	}
}
