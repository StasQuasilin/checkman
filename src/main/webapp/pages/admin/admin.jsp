<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>

<div style="padding: 18pt">
    <table>
        <tr>
            <td align="center">
                <div style="padding: 4pt">
                    <button onclick="loadModal('${changeView}')">
                        <fmt:message key="change.view"/>
                    </button>
                </div>
                <div style="padding: 4pt">
                    <button onclick="loadModal('${userRegistration}')">
                        <fmt:message key="user.registration"/>
                    </button>
                </div>
                <div style="padding: 4pt">
                    <button onclick="loadModal('${userList}', {})">
                        <fmt:message key="admin.user.list"/>
                    </button>
                </div>
                <div style="padding: 4pt">
                    <button onclick="loadModal('${organisationCollapse}', {})">
                        <fmt:message key="admin.collapse.organisations"/>
                    </button>
                </div>
            </td>
            <td valign="top">
                <jsp:include page="telegramBotSettings.jsp"/>
            </td>
        </tr>
    </table>
</div>

</html>