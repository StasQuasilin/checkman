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
        <c:when test="${role eq 'admin' or role eq 'top'}">
          <ul class="nav-drop-menu">
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.deals"/>&nbsp;>
              </span>
              <div class="nav-drop-menu-content">
                <jsp:include page="navigation/deals.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.logistic"/>&nbsp;>
              </span>
              <div class="nav-drop-menu-content">
                <jsp:include page="navigation/logistic.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.transport"/>&nbsp;>
              </span>
              <div class="nav-drop-menu-content">
                <jsp:include page="navigation/transport.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.weight"/>&nbsp;>
              </span>
              <div class="nav-drop-menu-content">
                <jsp:include page="navigation/weight.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.warehousing"/>&nbsp;>
              </span>
              <div class="nav-drop-menu-content">
                <jsp:include page="navigation/warehousing.jsp"/>
              </div>
            </li>
            <li class="nav-menu-item nav-drop-menu-item">
              <span class="main">
                <fmt:message key="menu.laboratory"/>&nbsp;>
              </span>
              <div class="nav-drop-menu-content">
                <jsp:include page="navigation/laboratory.jsp"/>
              </div>
            </li>
            <c:if test="${role eq 'admin'}">
              <li class="menu-item" onclick="loadContent('${admin}')">
              <span class="main">
                <fmt:message key="menu.admin"/>
              </span>
              </li>
              <li class="menu-item" onclick="loadContent('${referencesList}')">
              <span class="main">
                <fmt:message key="menu.references"/>
              </span>
              </li>
            </c:if>
          </ul>
        </c:when>
        <c:when test="${role eq 'manager'}">
          <jsp:include page="navigation/deals.jsp"/>
          <jsp:include page="navigation/weight.jsp"/>
          <jsp:include page="navigation/transport.jsp"/>
          <jsp:include page="navigation/laboratoryTransport.jsp"/>
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

    </ul>
  </div>
</html>
