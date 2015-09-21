/**
 * 
 */
package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.Export;
import cn.itcast.jk.pagination.Page;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public interface ExportService {
    
	/**
	 * 添加一个部门
	 */
	public  List<Export> find(String hql, Class<Export> entityClass, Object[] params);
	//获取一条记录
	public  Export get(Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public  Page<Export> findPage(String hql, Page<Export> page,  Object[] params);
	
	//新增和修改保存
	public  void saveOrUpdate(Export entity);
	//批量新增和修改保存
	public  void saveOrUpdateAll(Collection<Export> entitys);
	
	//单条删除，按id
	public  void deleteById(Serializable id);
	//批量删除
	public  void delete(Serializable[] ids);
	
	public  void save(String[] ids,Export model);
	
	

}
