<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul>
  <li class="menu-item" onclick="loadContent('${probeList}')">
    <span class="main">
      <fmt:message key="analyses.probe"/>
    </span>
  </li>
  <c:forEach items="${subdivisions}" var="s">
    <li class="menu-item" onclick="loadContent('${subdivisionList}?sub=${s.key}')">
      <span class="main">
        <fmt:message key="analyses.subdivision.${s.key}"/>
      </span>
    </li>
  </c:forEach>
  <li class="menu-item" onclick="loadContent('${storages}')">
    <span class="main">
      <fmt:message key="menu.volumes"/>
    </span>
  </li>
  <li class="menu-item">
    <span class="main" onclick="loadContent('${laboratoryBuyList}')">
      <fmt:message key="analyses.buy"/>
    </span>
    <span>
      <a onclick="loadContent('${laboratoryBuyArchive}')"><fmt:message key="archive"/> </a>
    </span>
  </li>
  <li class="menu-item">
    <span class="main" onclick="loadContent('${laboratorySellList}')">
      <fmt:message key="analyses.sell"/>
    </span>
    <span>
      <a onclick="loadContent('${laboratorySellArchive}')"><fmt:message key="archive"/> </a>
    </span>
  </li>
  <li class="menu-item">
    <span class="main" onclick="loadContent('${laboratoryTurns}')">
      <fmt:message key="laboratory.turns"/>
    </span>
  </li>
</ul>

