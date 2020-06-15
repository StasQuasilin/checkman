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
<div id="container-header" class="container-header">
  <c:if test="${not empty add}">
    <button onclick="loadModal('${add}')"><fmt:message key="button.add"/> </button>
  </c:if>
  <c:if test="${role == 'weigher' or role == 'admin'}">
    <c:if test="${not empty report}">
      <button onclick="loadModal('${report}')"><fmt:message key="do.roundReport"/></button>
    </c:if>
    <c:if test="${not empty manufactureReport}">
      <button onclick="loadModal('${manufactureReport}')">
        <fmt:message key="turn.roundReport.add"/>
      </button>
    </c:if>
  </c:if>
</div>
</html>
