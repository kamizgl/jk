/**
 * 
 */
package cn.itcast.jk.domain;

/**
 * @description:部门实体
 * @author 传智.宋江
 * @date 2015年7月19日
 * @version 1.0
 */
public class Dept {
	
	private Dept parentDept;	//自关联  多个子部门可以对应同一个父部门
	private String id;			//编号
	private String deptName;	//部门名称
	private Integer state;   	//1启用0停用   新建的部门状态为1
	
	public Dept getParentDept() {
		return parentDept;
	}
	public void setParentDept(Dept parentDept) {
		this.parentDept = parentDept;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
