<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.11.2019
  Time: 09:20
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/drop-menu.css">
<div id="container-header" class="container-header">
    <c:if test="${not empty edit}">
        <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
    </c:if>
    <%--<div class="drop-menu">--%>
        <%--<a class="drop-btn"><fmt:message key="document.print"/></a>--%>
        <%--<div class="drop-menu-content">--%>
            <%--<div class="drop-menu drop-menu-item" onclick="loadModal('${print}')">--%>
                <%--<fmt:message key="document.print"/>--%>
            <%--</div>--%>
            <%--<div class="drop-menu drop-menu-item" onclick="loadModal('${transportCarriages}')">--%>
                <%--<fmt:message key="transport.carriage.head"/>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
</div>
</html>
