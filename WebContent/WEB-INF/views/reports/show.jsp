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
                	<a href="<c:url value="/reports/reaction?id=${report.id}&reaction_id=1"/>"><i class="far fa-thumbs-up fa-2x"></i></a>
	                <c:if test="${iine_count != 0}">
	                	<span data-toggle="tooltip" title="${iine_users[0]}さん<c:if test="${fn:length(iine_users) != 1}">、他${fn:length(iine_users)-1}人</c:if>がリアクションしました！">${iine_count}</span>
	                </c:if>
	                <a href="<c:url value="/reports/reaction?id=${report.id}&reaction_id=2"/>" class="ml-2"><i class="far fa-smile fa-2x"></i></a>
	                <c:if test="${smile_count != 0}">
	                	<span data-toggle="tooltip" title="${smile_users[0]}さん<c:if test="${fn:length(smile_users) != 1}">、他${fn:length(smile_users)-1}人</c:if>がリアクションしました！">${smile_count}</span>
	                </c:if>
	                <a href="<c:url value="/reports/reaction?id=${report.id}&reaction_id=3"/>" class="ml-2"><i class="far fa-laugh-squint fa-2x"></i></a>
	                <c:if test="${big_count != 0}">
	                	<span data-toggle="tooltip" title="${big_users[0]}さん<c:if test="${fn:length(big_users) != 1}">、他${fn:length(big_users)-1}人</c:if>がリアクションしました！">${big_count}</span>
	                </c:if>
	                <a href="<c:url value="/reports/reaction?id=${report.id}&reaction_id=4"/>" class="ml-2"><i class="far fa-grin-beam-sweat fa-2x"></i></a>
	                <c:if test="${sweat_count != 0}">
	                	<span data-toggle="tooltip" title="${sweat_users[0]}さん<c:if test="${fn:length(sweat_users) != 1}">、他${fn:length(sweat_users)-1}人</c:if>がリアクションしました！">${sweat_count}</span>
	                </c:if>
	                <a href="<c:url value="/reports/reaction?id=${report.id}&reaction_id=5"/>" class="ml-2"><i class="far fa-frown fa-2x"></i></a>
	                <c:if test="${bad_count != 0}">
	                	<span data-toggle="tooltip" title="${bad_users[0]}さん<c:if test="${fn:length(bad_users) != 1}">、他${fn:length(bad_users)-1}人</c:if>がリアクションしました！">${bad_count}</span>
	                </c:if>
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