/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.mysql.jdbc.Util;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.ExtEproduct;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExportService;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class ExportServiceImpl implements ExportService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Export get( Serializable id) {
		return baseDao.get(Export.class, id);
	}

	@Override
	public Page<Export> findPage(String hql, Page<Export> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,Export.class, params);
	}

	@Override
	public void saveOrUpdate(Export entity) {
	
		baseDao.saveOrUpdate(entity);
	}
	
	
	public void save(String ids[],Export model) {
		//1.根据购销合同的id,查询到购销合同的对象   ['fdsfds','dfdsfs']----------------'fsdfsd','fdsffsdfds'
		String idsStr = UtilFuns.joinInStr(ids);  //'x','y'
		
		String hql = "from Contract c where c.id in ("+idsStr+")";
		List<Contract> list = baseDao.find(hql, Contract.class, null);//得到所有合同
		
		//2.生成一个Export对象，用于接收model数据
		Export export  = new Export();
		
		//3.将model中从页面接收的数据，传递给Export对象
		export.setInputDate(model.getInputDate());
		export.setLcno(model.getLcno());
		export.setConsignee(model.getConsignee());
		export.setMarks(model.getMarks());
		export.setShipmentPort(model.getShipmentPort());
		export.setDestinationPort(model.getDestinationPort());
		export.setTransportMode(model.getTransportMode());
		export.setPriceCondition(model.getPriceCondition());
        export.setRemark(model.getRemark());
        export.setBoxNums(model.getBoxNums());
        export.setGrossWeights(model.getGrossWeights());
        export.setMeasurements(model.getMeasurements());
		
		//4.维护打段设计所 使用的字段
		export.setContractIds(UtilFuns.joinStr(ids, ","));// x,y
		//5.维护好合同号
		StringBuilder sb = new StringBuilder();
		for(Contract contract :list){
			sb.append(contract.getContractNo()).append(" ");
		}
		
		export.setCustomerContract(sb.toString().trim());
		export.setState(0);//代表草稿
		
		//6.加载出购销合同下的所有货物
		Set<ExportProduct> epSet = new HashSet<ExportProduct>();
		for(Contract contract :list){
			//7.遍历每个货物，并且将所有的货物放入到一个报运单下货物的集合中
			Set<ContractProduct> cpSet = contract.getContractProducts();  //购销合同下的货物
			for(ContractProduct cp: cpSet){
				ExportProduct ep = new ExportProduct();
				ep.setExport(export);//设置多方到一方的关系   说明多个报运单下的货物对应同一个报运单
				ep.setFactory(cp.getFactory());
				ep.setProductNo(cp.getProductNo());
				ep.setPackingUnit(cp.getPackingUnit());
				ep.setCnumber(cp.getCnumber());
				ep.setBoxNum(cp.getBoxNum());
				ep.setPrice(cp.getPrice());
				ep.setOrderNo(cp.getOrderNo());
				
				epSet.add(ep);//将生成报运单下的货物加入到报运单下的货物列表中
				
				
				//8购销合下货物的附件内容，同样也要实现搬家，要搬到报运单下货物的附件中
				Set<ExtCproduct> extCset = cp.getExtCproducts();
				Set<ExtEproduct> extEset = new HashSet<ExtEproduct>();
				for(ExtCproduct extcp :extCset){
					ExtEproduct extE = new ExtEproduct();//报运单下的附件
					extE.setExportProduct(ep);//代表当前的附件从属于哪个货物
					
					BeanUtils.copyProperties(extcp, extE);
					extE.setId(null);
					
					extEset.add(extE);//将这个附件加入到报运单下的附件列表
					
				}
				//9.设置它们关联关系   设置报运单货物与它对应的附件的关系
				ep.setExtEproducts(extEset);
				
			}
			
			
			//修改合同的状态
			contract.setState(2);
			baseDao.saveOrUpdate(contract);//11.修改购销合同的状态为已报运  （state=2）
		}
		
		//设置报运单与报运单下货物的关系
		export.setExportProducts(epSet);
		
		//10.保存报运单 ，同时级联保存报运单下的货物及附件
		baseDao.saveOrUpdate(export);
		
		
	}

	@Override
	public void saveOrUpdateAll(Collection<Export> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除
	public void deleteById(Serializable id) {
		Export export = baseDao.get(Export.class, id);
		String cids[] = export.getContractIds().split(",");//购销合同的id(多个)
		String cidVal = UtilFuns.joinInStr(cids);  //[x,y]  ----->'x','y'
		String hql = "from Contract c where c.id in ("+cidVal+")";
		List<Contract> list = baseDao.find(hql, Contract.class, null);
		for(Contract contract: list){
			contract.setState(1);
			baseDao.saveOrUpdate(contract);
		}
		baseDao.deleteById(Export.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(Export.class, ids);
	}

	
}
