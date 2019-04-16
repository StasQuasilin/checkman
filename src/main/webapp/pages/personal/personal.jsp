<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<table border="1">
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
</html>