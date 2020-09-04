<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 04.09.20
  Time: 14:03
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="header-content">
    <c:if test="${not empty title}">
        <fmt:message key="${title}"/>
    </c:if>
</div>
<jsp:include page="${content}"/>
