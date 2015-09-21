package cn.itcast.jk.action.cargo;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.util.DownloadUtil;
import cn.itcast.util.UtilFuns;

public class outProductAction extends BaseAction {
	private String inputDate;
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	//业务逻辑层
	private ContractProductService contractProductService;
	public void setContractProductService(
			ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	public String print() throws Exception {
		//1.创建工作簿
		Workbook wb = new HSSFWorkbook();
		//2.创建工作表
		Sheet sheet = wb.createSheet();
		
		Row nRow =null;
		Cell nCell = null;
		
		int rowNo=0;
		int colNo = 1;//代表从第二列开始
		
		
		sheet.setColumnWidth(0, 1*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(1, 26*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(2, 12*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(3, 29*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(4, 12*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(5, 15*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(6, 10*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(7, 10*256);//0代表的是第一列，1代表第一列的宽度
		sheet.setColumnWidth(8, 8*256);//0代表的是第一列，1代表第一列的宽度
		
		
		//3.创建行
		nRow = sheet.createRow(rowNo++);
		
		nRow.setHeightInPoints((short)26);//设置行高
		nCell = nRow.createCell(colNo);
		
		nCell.setCellValue(inputDate.replace("-0", "-").replace("-", "年")+"月份出货表");  //2015-11

		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
		
		String title[] = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
		
		//创建标题行
		Row titleRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints((short)26);//设置行高
		
		
		//7.保存，关闭
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		baos.flush();
		baos.close();
		
		//8.下载(web测试)
		DownloadUtil downloadUtil = new DownloadUtil();
		HttpServletResponse response = ServletActionContext.getResponse();
		downloadUtil.download(baos, response, "出货表.xls");
		return NONE;
	}
	
	public String list() throws Exception {
		return SUCCESS;
	}
}
