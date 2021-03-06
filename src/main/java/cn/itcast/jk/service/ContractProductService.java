/**
 * 
 */
package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.pagination.Page;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public interface ContractProductService {
    
	/**
	 * 添加一个部门
	 */
	public  List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params);
	//获取一条记录
	public  ContractProduct get(Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public  Page<ContractProduct> findPage(String hql, Page<ContractProduct> page,  Object[] params);
	
	//新增和修改保存
	public  void saveOrUpdate(ContractProduct entity);
	//批量新增和修改保存
	public  void saveOrUpdateAll(Collection<ContractProduct> entitys);
	
	//单条删除，按id
	public  void deleteById(Serializable id);
	//批量删除
	public  void delete(Serializable[] ids);
	
	

}
