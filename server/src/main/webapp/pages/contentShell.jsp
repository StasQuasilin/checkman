<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<span id="header-content" class="header-content">
    <fmt:message key="${title}"/>
</span>
<div id="filter-content">
    <jsp:include page="${filter}"/>
</div>
<jsp:include page="${content}"/>
</html>
