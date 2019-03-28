<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<span id="header-content"><fmt:message key="${title}"/></span>
<div id="filter-content">
    <jsp:include page="${filter}"/>
    <c:forEach items="${filters}" var="f">
        <jsp:include page="${f}"/>
    </c:forEach>
</div>
<jsp:include page="${content}"/>
</html>
