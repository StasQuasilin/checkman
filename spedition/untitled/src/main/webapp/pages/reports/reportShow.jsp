<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 04.06.20
  Time: 09:44
--%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <body>
        <table>
            <tr>
                <td colspan="2">
                    ${report.owner.person.value}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="driver"/>
                </td>
                <td>
                    <c:if test="${not empty report.driver}">
                        ${fn:toUpperCase(report.driver.person.value)}
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="route"/>
                </td>
                <td>
                    ${fn:toUpperCase(report.route)}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="date.leave"/>
                </td>
                <td>
                    <fmt:formatDate value="${report.leaveTime}" pattern="dd.MM.yyyy hh:mm"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="product"/>
                </td>
                <td>
                    ${report.product.name}
                </td>
            </tr>
            <tr>
                <td colspan="2" style="max-height: 200pt; padding-left: 8pt">
                    <div>
                        <table>
                            <c:forEach items="${fields}" var="field" varStatus="status">
                                <tr>
                                    <td colspan="2">
                                        <fmt:message key="report.point"/> # ${status.index + 1}
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        ${field.counterparty}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="date.arrive"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${field.arriveTime}" pattern="dd.MM.yy HH:mm"/>
                                    </td>
                                </tr>
                                <c:if test="${field.money != 0}">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${field.money > 0}">
                                                    <fmt:message key="money.get"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <fmt:message key="money"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${field.money < 0 ? -field.money : field.money}"/>
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td colspan="2" style="border-bottom: solid gray 1pt; "></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </td>
            </tr>
            <c:if test="${not empty report.done}">
                <tr>
                    <td colspan="2">
                        <fmt:message key="date.done"/>:
                        <fmt:formatDate value="${report.leaveTime}" pattern="dd.MM"/> -
                        <fmt:formatDate value="${report.done}" pattern="dd.MM"/>
                        <c:set var="length" value="${report.length()}"/>
                        ( ${length}
                            <c:choose>
                                <c:when test="${length == 1}">
                                    <fmt:message key="day.1"/>
                                </c:when>
                                <c:when test="${length < 5}">
                                    <fmt:message key="day.2"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="day.5"/>
                                </c:otherwise>
                            </c:choose>
                        )
                    </td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2">
                    <fmt:message key="money.spending"/>:
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="fare"/>
                </td>
                <td>
                    <fmt:formatNumber value="${report.fare}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="expenses"/>
                </td>
                <td>
                    <fmt:formatNumber value="${report.expenses}"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center">
                    <button onclick="closeModal()">
                        <fmt:message key="button.close"/>
                    </button>
                </td>
            </tr>
        </table>
    </body>
</html>
