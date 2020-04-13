<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.11.2019
  Time: 17:07
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="container-header" class="container-header">
  <c:if test="${not empty add}">
    <button onclick="loadModal('${add}')"><fmt:message key="button.add"/> </button>
  </c:if>
  <button onclick="loadModal('${report}')"><fmt:message key="do.report"/></button>
</div>
</html>
