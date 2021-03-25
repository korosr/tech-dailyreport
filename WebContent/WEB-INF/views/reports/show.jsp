<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>出勤時間</th>
                            <td>
                                <fmt:formatDate value="${report.timecard.in_time}" pattern="HH:mm" />
                            </td>
                        </tr>
                        <tr>
                            <th>退勤時間</th>
                            <td>
                            	<fmt:formatDate value="${report.timecard.out_time}" pattern="HH:mm" />
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div id="reactionBtn" class="m-2">
	                <form method="post" action="<c:url value='/reports/reaction' />">
	                	<input type="hidden" name="report_id" value="${report.id}">
	                	<c:if test="${!good}">
							<button type="submit" value="on" name="good_btn" class="btn p-0 border-0 text-primary"><i class="far fa-heart fa-2x" style="color: indianred;"></i></button>
						</c:if>
						<c:if test="${good}">
							<button type="submit" value="off" name="good_btn" class="btn p-0 border-0 text-primary"><i class="fas fa-heart fa-2x" style="color: indianred;"></i></button>
	                	</c:if>
	                </form>
                </div>
                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>