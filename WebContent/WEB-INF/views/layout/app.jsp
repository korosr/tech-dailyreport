<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>日報管理システム</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
        <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/' />">日報管理システム</a></h1>&nbsp;&nbsp;&nbsp;
                    <c:if test="${sessionScope.login_employee != null}">
                        <a href="<c:url value='/employees/index' />">従業員一覧</a>&nbsp;
                        <a href="<c:url value='/reports/index' />">日報管理</a>&nbsp;
                    </c:if>
                    <c:if test="${sessionScope.login_employee != null}">
	                    <div id="employee_name">
	                        <c:out value="${sessionScope.login_employee.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
	                        <a href="<c:url value='/logout' />">ログアウト</a>
	                    </div>
                	</c:if>
                </div>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by ko production.
            </div>
        </div>
    </body>
</html>