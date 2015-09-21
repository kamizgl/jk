/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractService;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class ContractServiceImpl implements ContractService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Contract get( Serializable id) {
		return baseDao.get(Contract.class, id);
	}

	@Override
	public Page<Contract> findPage(String hql, Page<Contract> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,Contract.class, params);
	}

	@Override
	public void saveOrUpdate(Contract entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//添加操作
			entity.setState(0);//0代表草稿  1代表已提交 
			entity.setCreateTime(new Date());
			entity.setTotalAmount(0.0);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Contract> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(Contract.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(Contract.class, ids);
	}

	
}
