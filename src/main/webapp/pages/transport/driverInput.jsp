<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table>
    <tr>
        <td>
            <label for="find">
                <fmt:message key="find"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="find">
        </td>
    </tr>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<label for="surname">--%>
                <%--<fmt:message key="person.surname"/>*--%>
            <%--</label>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--:--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<input id="surname">--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<label for="forename">--%>
                <%--<fmt:message key="person.forename"/>*--%>
            <%--</label>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--:--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<input id="forename">--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<label for="patronymic">--%>
                <%--<fmt:message key="person.patronymic"/>*--%>
            <%--</label>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--:--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<input id="patronymic">--%>
        <%--</td>--%>
    <%--</tr>--%>
</table>
</html>