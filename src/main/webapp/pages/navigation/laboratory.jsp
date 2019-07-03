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
  <jsp:include page="laboratoryTransport.jsp"/>

</ul>

