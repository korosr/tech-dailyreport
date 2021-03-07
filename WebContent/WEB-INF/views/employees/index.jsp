<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                    	<th>操作</th>
                    </c:if>
                    <th></th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <c:if test="${sessionScope.login_employee.admin_flag == 1}">
	                    <td>
                           <c:choose>
                               <c:when test="${employee.delete_flag == 1}">
                                   （削除済み）
                               </c:when>
                               <c:otherwise>
                                   <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                               </c:otherwise>
                           </c:choose>
	                    </td>
	                    </c:if>
	                    <td>
	                    	<c:forEach var="relationship" items="${relationships}" varStatus="status">
			                    <c:choose>
			                    	<c:when test="${relationship.followed_id != employee.id}">
			                    		<a href="<c:url value='/employees/follow?id=${employee.id}' />"><button type="button" <c:if test="${sessionScope.login_employee.id == employee.id}">disabled</c:if>>フォロー</button></a>
			                    	</c:when>
			                    	<c:otherwise>
			                    		<a href="<c:url value='/employees/follow?id=${employee.id}' />"><button type="button" <c:if test="${sessionScope.login_employee.id == employee.id}">disabled</c:if>>フォロー解除</button></a>
			                    	</c:otherwise>
			                    </c:choose>
			                 </c:forEach>
			                 <c:if test="${relationships.size() == 0}">
			                 	<a href="<c:url value='/employees/follow?id=${employee.id}' />"><button type="button" <c:if test="${sessionScope.login_employee.id == employee.id}">disabled</c:if>>フォロー</button></a>
			                 </c:if>
	                    </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${employees_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>

    </c:param>
</c:import>