<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul>
    <li class="menu-item">
        <span class="main" onclick="loadContent('${transportList}')">
          <fmt:message key="menu.transport"/>
        </span>
        <span>
          <a onclick="loadContent('${transportArchive}')"><fmt:message key="archive"/> </a>
        </span>
    </li>
    <li class="menu-item"  onclick="loadContent('${sealList}')">
      <span class="main">
        <fmt:message key="menu.seals"/>
      </span>
    </li>
</ul>