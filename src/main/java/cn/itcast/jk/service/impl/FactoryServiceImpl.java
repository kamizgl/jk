/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.common.SysConstant;
import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.FactoryService;
import cn.itcast.util.Encrypt;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class FactoryServiceImpl implements FactoryService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Factory get( Serializable id) {
		return baseDao.get(Factory.class, id);
	}

	@Override
	public Page<Factory> findPage(String hql, Page<Factory> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,Factory.class, params);
	}

	@Override
	public void saveOrUpdate(Factory entity) {
		
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Factory> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(Factory.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(Factory.class, ids);
	}

	

}
