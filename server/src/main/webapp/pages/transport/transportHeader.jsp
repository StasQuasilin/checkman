<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/drop-menu.css">
<div id="container-header" class="container-header">
    <c:if test="${not empty check}">
        <button onclick="loadModal('${check}')"><fmt:message key="button.check"/></button>
    </c:if>
</div>
</html>
