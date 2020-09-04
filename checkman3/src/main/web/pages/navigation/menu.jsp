<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>

<div class="navigation-menu">
    <c:forEach items="${dealTypes}" var="type">
        <div class="menu-item" onclick="loadContent('${deals}?type=${type}')">
            <fmt:message key="menu.deals.${type}"/>
        </div>
    </c:forEach>
</div>