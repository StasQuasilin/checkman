<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <link rel="stylesheet" href="${context}/css/NavigationMenu.css"/>
  <div class="navigation-menu" id="nav-menu">
    <ul>
      <c:choose>
        <c:when test="${role eq 'admin'}">
          <ul class="nav-drop-menu">
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.deals"/>&#9205;
              </span>
              <div class="nav-drop-menu-content" style="transform: rotateZ(15deg);">
                <jsp:include page="navigation/deals.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.logistic"/>&#9205;
              </span>
              <div class="nav-drop-menu-content" style="transform: rotateZ(-10deg);">
                <jsp:include page="navigation/logistic.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.transport"/>&#9205;
              </span>
              <div class="nav-drop-menu-content" style="transform: rotateZ(20deg);">
                <jsp:include page="navigation/transport.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.weight"/>&#9205;
              </span>
              <div class="nav-drop-menu-content" style="transform: rotateZ(12deg);">
                <jsp:include page="navigation/weight.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.laboratory"/>&#9205;
              </span>
              <div class="nav-drop-menu-content" style="transform: rotateZ(-8deg);">
                <jsp:include page="navigation/laboratory.jsp"/>
              </div>
            </li>
            <li class="menu-item" onclick="loadContent('${admin}')">
            <span class="main">
              <fmt:message key="menu.admin"/>
            </span>
            </li>
          </ul>
        </c:when>
        <c:when test="${role eq 'manager'}">
          <jsp:include page="navigation/deals.jsp"/>
        </c:when>
        <c:when test="${role eq 'logistic'}">
          <jsp:include page="navigation/logistic.jsp"/>
        </c:when>
        <c:when test="${role eq 'security'}">
          <jsp:include page="navigation/transport.jsp"/>
        </c:when>
        <c:when test="${role eq 'weigher'}">
          <jsp:include page="navigation/weight.jsp"/>
        </c:when>
        <c:when test="${role eq 'analyser'}">
          <jsp:include page="navigation/laboratory.jsp"/>
        </c:when>
      </c:choose>
      <li class="menu-item" onclick="loadContent('${referencesList}')">
        <span class="main">
          <fmt:message key="menu.references"/>
        </span>
      </li>
    </ul>
  </div>
</html>
