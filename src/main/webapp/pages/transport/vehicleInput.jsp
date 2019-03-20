<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table>
    <tr>
        <td>
            <label for="model">
                <fmt:message key="transportation.automobile.model"/>*
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="model">
        </td>
    </tr>
    <tr>
        <td>
            <label for="number">
                <fmt:message key="transportation.automobile.number"/>*
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="number">
        </td>
    </tr>
    <tr>
        <td>
            <label for="trailer">
                <fmt:message key="transportation.automobile.trailer"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="trailer">
        </td>
    </tr>
</table>
</html>