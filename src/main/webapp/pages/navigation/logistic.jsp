<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul>
    <li class="menu-item">
      <span class="main"  onclick="loadContent('${logisticList}')">
        <fmt:message key="menu.logistic"/>
      </span>
      <span>
        <a onclick="loadContent(${context}${logistic_archive})"><fmt:message key="archive"/> </a>
      </span>
    </li>
</ul>