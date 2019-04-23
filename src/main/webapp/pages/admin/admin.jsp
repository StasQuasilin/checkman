<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<div style="padding: 18pt">
    <table>
        <tr>
            <td>
                <jsp:include page="registration.jsp"/>
            </td>
            <td>
                <jsp:include page="botSettings.jsp"/>
            </td>
        </tr>
    </table>

</div>

</html>