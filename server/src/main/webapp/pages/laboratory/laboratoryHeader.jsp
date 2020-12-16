<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.11.2019
  Time: 17:07
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/drop-menu.css">
<div id="container-header" class="container-header">
    <button onclick="loadModal('${laboratoryCarriages}')">
        <fmt:message key="transport.carriage.head"/>
    </button>
</div>
</html>
