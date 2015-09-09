/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年7月19日
 * @version 1.0
 */
public class DeptServiceImpl implements DeptService {
	private BaseDao baseDao ;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}


	public Dept get(Class<Dept> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}


	public Page<Dept> findPage(String hql, Page<Dept> page,
			Class<Dept> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}


	public void saveOrUpdate(Dept entity) {
		baseDao.saveOrUpdate(entity);

	}


	public void saveOrUpdateAll(Collection<Dept> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}


	public void deleteById(Class<Dept> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);

	}

	
	public void delete(Class<Dept> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
