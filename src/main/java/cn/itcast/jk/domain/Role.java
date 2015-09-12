package cn.itcast.jk.domain;

import java.util.Set;

public class Role extends BaseEntity {
	
	//private Set<Module> modules ;//角色与模块   多对多
	private Set<User> users;     //角色与用户   多对多
	private String id;          //编号 
	private String name;        //角色名 
	private String remark;      //说明 
	private Integer orderNo;    //排序号 
//	public Set<Module> getModules() {
//		return modules;
//	}
//	public void setModules(Set<Module> modules) {
//		this.modules = modules;
//	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	

}
