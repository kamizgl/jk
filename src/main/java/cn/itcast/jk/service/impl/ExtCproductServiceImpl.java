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
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExtCproductService;
import cn.itcast.util.Encrypt;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class ExtCproductServiceImpl implements ExtCproductService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ExtCproduct get( Serializable id) {
		return baseDao.get(ExtCproduct.class, id);
	}

	@Override
	public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,ExtCproduct.class, params);
	}

	@Override
	public void saveOrUpdate(ExtCproduct entity) {
		
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(ExtCproduct.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(ExtCproduct.class, ids);
	}


}
