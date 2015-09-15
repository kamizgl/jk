/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.ModuleService;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class ModuleServiceImpl implements ModuleService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Module get( Serializable id) {
		return baseDao.get(Module.class, id);
	}

	@Override
	public Page<Module> findPage(String hql, Page<Module> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,Module.class, params);
	}

	@Override
	public void saveOrUpdate(Module entity) {
		
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Module> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(Module.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(Module.class, ids);
	}

}
