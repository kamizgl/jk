/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.RoleService;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class RoleServiceImpl implements RoleService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Role get( Serializable id) {
		return baseDao.get(Role.class, id);
	}

	@Override
	public Page<Role> findPage(String hql, Page<Role> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,Role.class, params);
	}

	@Override
	public void saveOrUpdate(Role entity) {
		
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Role> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(Role.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(Role.class, ids);
	}

}
