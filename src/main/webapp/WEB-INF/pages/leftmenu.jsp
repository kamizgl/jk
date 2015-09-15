<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<ul>
			<c:forEach items="${_CURRENT_USER.roles }" var="role">
			       <c:forEach items="${role.modules }" var="module">
			            <c:if test="${(moduleName eq module.remark) and module.ctype==1    }">
			                  <li><a href="${ctx}/${module.curl}" onclick="linkHighlighted(this)" target="main" id="aa_1">${module.cpermission }</a></li>
			            </c:if>
			            
			       </c:forEach>
			</c:forEach>
</ul>