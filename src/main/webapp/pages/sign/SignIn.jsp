<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
<head>
</head>
<body>
    <div>
        <table border="1">
            <tr>
                <td colspan="3" align="center">
                    <fmt:message key="sign.in"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="login">
                        <fmt:message key="user-name"/>
                    </label>
                </td>
                <td>
                    <input id="login">
                </td>
                <td rowspan="2" align="center">
                    <div>
                        <button><fmt:message key="sign.in"/> </button>
                    </div>
                    <div>
                        <button><fmt:message key="registration"/> </button>
                    </div>
                    <div>
                        <a href=""><fmt:message key="forgot"/> </a>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="password">
                        <fmt:message key="user-passwors"/>
                    </label>
                </td>
                <td>
                    <input id="password" type="password">
                </td>
            </tr>

        </table>
    </div>
</body>
</html>