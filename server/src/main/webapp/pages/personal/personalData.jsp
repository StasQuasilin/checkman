<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table>
    <tr>
        <th colspan="3">
            <fmt:message key="personal.data"/>
        </th>
    </tr>
    <tr>
        <td>
            <fmt:message key="person.surname"/>
        </td>
        <td>
            :
        </td>
        <td>
            ${worker.person.surname}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="person.forename"/>
        </td>
        <td>
            :
        </td>
        <td>
            ${worker.person.forename}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="person.patronymic"/>
        </td>
        <td>
            :
        </td>
        <td>
            ${worker.person.patronymic}
        </td>
    </tr>
</table>
</html>