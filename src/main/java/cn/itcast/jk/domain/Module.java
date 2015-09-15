/**
 * 
 */
package cn.itcast.jk.domain;

import java.util.Set;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月10日
 * @version 1.0
 */
public class Module extends BaseEntity {
	private String id;        //编号 
	
	private Set<Role> roles ;   //模块与角色   多对多
	
	private String parentId;  //父结点的id
	private String parentName;//父结点的名字
	private String name; 	  //模块名
	private Integer layerNum; //层数
	private Integer isLeaf;  //是否叶子节点
	private String ico;      //图标
	private String cpermission; //权限标识
	private String curl;    //链接

	private Integer ctype;  //类型
	private Integer state;  //状态，代表是否启用
	private String belong;  //从属
	private String cwhich;  //复用标识
	private Integer quoteNum;//引用次数
	private String remark;   //说明
	private Integer orderNo;  //排序号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLayerNum() {
		return layerNum;
	}
	public void setLayerNum(Integer layerNum) {
		this.layerNum = layerNum;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public String getCpermission() {
		return cpermission;
	}
	public void setCpermission(String cpermission) {
		this.cpermission = cpermission;
	}
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public Integer getCtype() {
		return ctype;
	}
	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getCwhich() {
		return cwhich;
	}
	public void setCwhich(String cwhich) {
		this.cwhich = cwhich;
	}
	public Integer getQuoteNum() {
		return quoteNum;
	}
	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
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
