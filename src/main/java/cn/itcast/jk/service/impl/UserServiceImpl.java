/**
 * 
 */
package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.UserService;
import cn.itcast.util.UtilFuns;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public User get( Serializable id) {
		return baseDao.get(User.class, id);
	}

	@Override
	public Page<User> findPage(String hql, Page<User> page,
			 Object[] params) {
		return baseDao.findPage(hql, page,User.class, params);
	}

	@Override
	public void saveOrUpdate(User entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//说明是添加
			String id = UUID.randomUUID().toString();
			entity.setId(id);//给user对象的id属性赋值    一对一关联（基于主键的一对一    基于外键的一对一）
			entity.getUserinfo().setId(id);   //基于主键的一对一
			
			//如果要加入细粒度权限控制的话，还要设置 

			if(UtilFuns.isEmpty(entity.getUserinfo().getManager().getId())){
				entity.getUserinfo().setManager(null);
			}
		}else{
			//说明是修改
			//entity.setUpdateBy(updateBy);
			//entity.setUpdateTime(updateTime);

		}
		if(UtilFuns.isEmpty(entity.getDept().getId())){
			entity.setDept(null);
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<User> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	//删除的如果是一个指定的父部门时，考虑递归
	public void deleteById(Serializable id) {
		baseDao.deleteById(User.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		baseDao.delete(User.class, ids);
	}

	@Override
	public User findUserByUsername(String username) {
		List<User> list = baseDao.find("from User u where u.userName=?", User.class, new Object[]{username});
		if(list!=null&&list.isEmpty()==false) {
			return list.get(0);
		}
		
		return null;
	}

	
}
