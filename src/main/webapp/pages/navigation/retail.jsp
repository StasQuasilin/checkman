<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 11.12.2019
  Time: 11:38
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <jsp:include page="deals.jsp"/>
<%--    <ul>--%>
<%--      <li class="menu-item" onclick="loadContent('${retailList}')">--%>
<%--          <span class="main">--%>
<%--            <fmt:message key="menu.retail.list"/>--%>
<%--          </span>--%>
<%--      </li>--%>
<%--    </ul>--%>
</html>
