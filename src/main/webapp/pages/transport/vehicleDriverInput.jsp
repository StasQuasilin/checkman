<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
<table width="100%">
    <tr>
        <th>
            <fmt:message key="transportation.automobile"/>
        </th>
        <th>
            <fmt:message key="transportation.driver"/>
        </th>
    </tr>
    <tr>
        <td>
            <jsp:include page="vehicleInput.jsp"/>
        </td>
        <td>
            <jsp:include page="driverInput.jsp"/>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <label for="transporter">
                <fmt:message key="transportation.transporter"/>:
            </label>
            <input id="transporter">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button><fmt:message key="button.cancel"/> </button>
            <button><fmt:message key="button.save"/> </button>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="color: orangered" align="center">
            * - <fmt:message key="label.requaired"/>
        </td>
    </tr>
</table>
</html>