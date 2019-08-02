<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <table>
        <tr>
            <td colspan="3">
                <fmt:message key="deal.delete.?sure"/>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="period"/>
            </td>
            <td>
                :
            </td>
            <td>
                <fmt:formatDate value="${deal.date}" pattern="dd.MM.yyyy"/>
                <c:if test="${deal.date ne deal.dateTo}">
                    -<fmt:formatDate value="${deal.dateTo}" pattern="dd.MM.yyyy"/>
                </c:if>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.organisation"/>
            </td>
            <td>
                :
            </td>
            <td>
                ${deal.organisation.value}
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.product"/>
            </td>
            <td>
                :
            </td>
            <td>
                ${deal.product.name}
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.quantity"/>
            </td>
            <td>
                :
            </td>
            <td>
                <fmt:formatNumber value="${deal.quantity}"/>
            </td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button>
                    <fmt:message key="button.delete"/>
                </button>
            </td>
        </tr>
    </table>
</html>