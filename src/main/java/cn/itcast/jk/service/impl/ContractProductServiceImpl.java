/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.mysql.jdbc.Util;

import cn.itcast.common.SysConstant;
import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.util.Encrypt;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class ContractProductServiceImpl implements ContractProductService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ContractProduct get( Serializable id) {
		return baseDao.get(ContractProduct.class, id);
	}

	@Override
	public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,ContractProduct.class, params);
	}

	@Override
	public void saveOrUpdate(ContractProduct entity) {
		//不是空就是更新，是空就是保存，同时需要维护表格中的总金额
	
		if(UtilFuns.isEmpty(entity.getId())) {
			double amout = 0;//总金额
			if(UtilFuns.isNotEmpty(entity.getCnumber())&& UtilFuns.isNotEmpty(entity.getPrice())) {
				amout = entity.getCnumber()* entity.getPrice();//货物总金额 
				entity.setAmount(amout);
			}	
			
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());//找到购销合同
			contract.setTotalAmount(contract.getTotalAmount()+amout);
			baseDao.saveOrUpdate(contract);
		}else {
			double amout = 0;
			double oldprice = entity.getAmount()==null?0:entity.getAmount();//这个货物的原有总金额
			if(UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice())){
				amout = entity.getCnumber()* entity.getPrice();//货物总金额 
				entity.setAmount(amout);
			}
			
			//更新购销合同的总金额
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());//找到购销合同
			contract.setTotalAmount(contract.getTotalAmount()-oldprice+amout);
			baseDao.saveOrUpdate(contract);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(ContractProduct.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(ContractProduct.class, ids);
	}



}
