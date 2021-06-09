
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-06-08
  Time: 09:56
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/changeView.vue?v=${now}"></script>
<div id="changeView">

</div>
<c:forEach items="${roles}" var="r">
    <div>
        <c:if test="${view eq r}">
            &times;
        </c:if>
        <fmt:message key="role.${r}"/>
    </div>
</c:forEach>
</html>
