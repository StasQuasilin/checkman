<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div style="padding: 18pt">
    <table border="0">
        <tr>
            <td colspan="3">
                <label for="language">
                    <fmt:message key="language"/>:
                </label>
                <select id="language">
                    <c:forEach items="${languages}" var="language">
                        <option value="${language}"><fmt:message key="${language}"/> </option>
                    </c:forEach>
                </select>
                <button>
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <jsp:include page="changePassword.jsp"/>
            </td>
            <td valign="top">
                <jsp:include page="personalData.jsp"/>
            </td>
            <td valign="top">
                <jsp:include page="telegramBotSettings.jsp"/>
            </td>
        </tr>
    </table>
</div>

</html>