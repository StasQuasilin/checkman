<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" style="display: inline">
    <c:forEach items="${analysesTypes}" var="a">
        <button onclick="loadModal('${editLink}?type=${a}')">
            <fmt:message key="button.do"/>
            <fmt:message key="${a}"/>
        </button>
    </c:forEach>
</div>
</html>