/**
 * 
 */
package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.Module;
import cn.itcast.jk.pagination.Page;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public interface ModuleService {
    
	/**
	 * 添加一个部门
	 */
	public  List<Module> find(String hql, Class<Module> entityClass, Object[] params);
	//获取一条记录
	public  Module get(Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public  Page<Module> findPage(String hql, Page<Module> page,  Object[] params);
	
	//新增和修改保存
	public  void saveOrUpdate(Module entity);
	//批量新增和修改保存
	public  void saveOrUpdateAll(Collection<Module> entitys);
	
	//单条删除，按id
	public  void deleteById(Serializable id);
	//批量删除
	public  void delete(Serializable[] ids);

}
