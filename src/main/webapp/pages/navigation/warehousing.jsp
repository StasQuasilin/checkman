<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<ul>
    <li class="menu-item">
        <span class="main" onclick="loadContent('${weightList}')">
          <fmt:message key="menu.transport"/>
        </span>
        <span>
          <a onclick="loadContent('${weightArchive}')"><fmt:message key="archive"/> </a>
        </span>
    </li>
<%--    <li class="menu-item">--%>
<%--        <span class="main" onclick="loadContent('${storages}')">--%>
<%--            <fmt:message key="menu.storages"/>--%>
<%--        </span>--%>
<%--    </li>--%>
</ul>