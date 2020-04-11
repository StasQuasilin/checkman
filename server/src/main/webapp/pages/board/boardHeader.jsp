<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 24.03.20
  Time: 10:50
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div id="container-header" class="container-header">
        <c:if test="${not empty edit}">
            <button onclick="loadModal('${edit}')"><fmt:message key="button.add"/> </button>
        </c:if>
    </div>
</html>
