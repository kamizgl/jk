<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('exportAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增出口报运
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="exportId" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="inputDate" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">ID集合串
            
            x,y,z：</td>
	            <td class="tableContent"><input type="text" name="contractIds" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">客户的合同号,可选择多个合同：</td>
	            <td class="tableContent"><input type="text" name="customerContract" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">L/C T/T：</td>
	            <td class="tableContent"><input type="text" name="lcno" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="consignee" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="marks" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="shipmentPort" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="destinationPort" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">SEA/AIR：</td>
	            <td class="tableContent"><input type="text" name="transportMode" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">FBO/CIF：</td>
	            <td class="tableContent"><input type="text" name="priceCondition" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="remark" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">冗余，为委托服务，一个报运的总箱数：</td>
	            <td class="tableContent"><input type="text" name="boxNums" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">冗余，为委托服务，一个报运的总毛重：</td>
	            <td class="tableContent"><input type="text" name="grossWeights" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">冗余，为委托服务，一个报运的总体积
            
            =长x高x宽/100 00 00
            
            cm转换为m3 立方米
            ：</td>
	            <td class="tableContent"><input type="text" name="measurements" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务：</td>
	            <td class="tableContent"><input type="text" name="state" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="createBy" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="createDept" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="createTime" value=""/></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

