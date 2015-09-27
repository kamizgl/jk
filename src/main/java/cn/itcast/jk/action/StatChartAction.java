/**
 * 
 */
package cn.itcast.jk.action.stat;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.common.springdao.SqlDao;
import cn.itcast.jk.action.BaseAction;
import cn.itcast.util.file.FileUtil;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月22日
 * @version 1.0
 */
public class StatChartAction extends BaseAction {
	private SqlDao sqlDao;//省掉了service
	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	//生产厂家的销售情况统计
	public String factorySale() throws Exception {
		//1.查询生产厂家的销售情况
		List<String> dataSource =this.execSQL("SELECT factory_name ,IFNULL (SUM(cnumber),0)FROM contract_product_c GROUP BY factory_name");
		
		//2.封装数据
		StringBuilder sb = genPieChart(dataSource); 
		
		//3.写入数据到xml文件中
		String sPath = ServletActionContext.getServletContext().getRealPath("/")+"stat\\chart\\factorysale";
		writeXML(sb, sPath);
		
		//4进入页面
		return "factorySale";
	}
	
	//产品销售排行
	public String productSale() throws Exception {
		//1.查询产品的销售情况
		List<String> dataSource = this.execSQL("select product_NO,sum(cnumber) as  productNumber from contract_product_c group by product_NO order by productNumber desc limit 15");
		
		//2.封装数据
		StringBuilder sb = genBarChart(dataSource); 
		
		//3.写入数据到xml文件中
		String sPath = ServletActionContext.getServletContext().getRealPath("/")+"stat\\chart\\productsale";
		writeXML(sb, sPath);
		
		return "productSale";
	}
	
	
	//系统访问压力图
	public String onlineInfo() throws Exception {
		//1.查询产品的销售情况
		List<String> dataSource = this.execSQL("select a.a1,ifnull(p.loginCount,0) from  (select a1 from online_info_t) a left join  (select substring(login_time,12,2) a1, count(*) as loginCount from login_log_p group by substring(login_time,12,2)) p on(a.a1=p.a1)");
		
		//2.封装数据
		StringBuilder sb = genBarChart(dataSource); 
		
		//3.写入数据到xml文件中
		String sPath = ServletActionContext.getServletContext().getRealPath("/")+"stat\\chart\\onlineinfo";
		writeXML(sb, sPath);
		
		return "onlineInfo";
	}
	
	/**
	 * 执行sql语句 
	 */
	public List<String> execSQL(String sql){
		return  sqlDao.executeSQL(sql);
	}

	/**
	 * 按照指定的数据源，专门用于生成饼图
	 */
	private StringBuilder genPieChart(List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<pie>");
		for (int i = 0; i < list.size(); ) {
			sb.append("<slice title=\""+list.get(i++)+"\" pull_out=\"true\">"+list.get(i++)+"</slice>");
		}
		sb.append("</pie>");
		return sb;
	}
	
	
	/**
	 *  生成柱状图
	 */
	private StringBuilder genBarChart(List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<chart><series>");
		int j=0;//用于作序号
		for (int i = 0; i < list.size(); ) {
			sb.append("<value xid=\""+(j++)+"\">"+list.get(i++)+"</value>");
			i++;
		}
		sb.append("</series>");	
        sb.append("<graphs><graph gid=\"30\" color=\"#C3F3C3\" gradient_fill_colors=\"#111111, #F3C3F3\">");
        
        j=0;
		for(int i = 0; i < list.size();){
			i++;
			sb.append("<value xid=\""+(j++)+"\" description=\"\" url=\"\">"+list.get(i++)+"</value>");
		}	
		sb.append("</graph></graphs></chart>");

		return sb;
	}

	/**
	 * 用于写XML文件
	 */
	private void writeXML(StringBuilder sb, String sPath)
			throws FileNotFoundException {
		FileUtil fileUitl = new FileUtil();
		fileUitl.createTxt(sPath, "data.xml", sb.toString(), "UTF-8");
	}
}
