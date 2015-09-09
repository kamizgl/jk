<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <ul>
	<c:forEach items="${_CURRENT_USER.roles }" var="role">
	     <c:if test="${role.remark==moduleName }">
	         <c:forEach items="${role.modules }" var="module">
	               <c:if test="${module.layerNum==2 }">
	                   <li><a href="${ctx }/${module.curl }" onclick="linkHighlighted(this)" target="main" id="aa_1">${module.name }</a></li>
	               </c:if>
	         </c:forEach>
	     </c:if>
	</c:forEach>
</ul>