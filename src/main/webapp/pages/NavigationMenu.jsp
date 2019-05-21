<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/NavigationMenu.css"/>
  <div class="navigation-menu" id="nav-menu">
    <ul>
      <c:if test="${role eq 'admin' || role eq 'manager'}">
      <li class="menu-item">
        <span class="main" onclick="loadContent('${buyList}')">
          <fmt:message key="deal.buy"/>
        </span>
        <span class="archive">
          <a onclick="loadContent('${buyArchive}')">
            <fmt:message key="archive"/>
          </a>
        </span>
      </li>
      <li class="menu-item">
        <span class="main" onclick="loadContent('${sellList}')">
          <fmt:message key="deal.sell"/>
        </span>
        <span>
          <a onclick="loadContent('${sellArchive}')" ><fmt:message key="archive"/> </a>
        </span>
      </li>
      <li class="menu-item">
        <span class="main" onclick="loadContent('${railList}')">
          <fmt:message key="rail.shipment"/>
        </span>
        <span>
          <a onclick="loadContent('${railArchive}')" ><fmt:message key="archive"/> </a>
        </span>
      </li>
      <li class="menu-item">
        <span class="main" onclick="loadContent('${summaryList}')">
          <fmt:message key="consolidated.table"/>
        </span>
        <span>
          <a onclick="loadContent('${summaryArchive}')" ><fmt:message key="archive"/> </a>
        </span>
      </li>
      </c:if>
      <c:if test="${role eq 'admin' || role eq 'logistic'}">
      <li class="menu-item">
        <span class="main"  onclick="loadContent('${logisticList}')">
          <fmt:message key="menu.logistic"/>
        </span>
        <span>
          <a onclick="loadContent(${context}${logistic_archive})"><fmt:message key="archive"/> </a>
        </span>
      </li>
      </c:if>
      <c:if test="${role eq 'admin' || role eq 'security'}">
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
      </c:if>
      <c:if test="${role eq 'admin' || role eq 'weigher'}">
      <li class="menu-item">
        <span class="main" onclick="loadContent('${weightList}')">
          <fmt:message key="menu.weight"/>
        </span>
        <span>
          <a onclick="loadContent('${weightArchive}')"><fmt:message key="archive"/> </a>
        </span>
      </li>
      </c:if>
      <c:if test="${role eq 'admin' || role eq 'analyser'}">
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
        <li class="menu-item">
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
      </c:if>
      <li class="menu-item" onclick="loadContent('${referencesList}')">
        <span class="main">
          <fmt:message key="menu.references"/>
        </span>
      </li>
      <c:if test="${role eq 'admin'}">
        <li class="menu-item" onclick="loadContent('${admin}')">
          <span class="main">
            <fmt:message key="menu.admin"/>
          </span>
        </li>
      </c:if>
    </ul>
  </div>
</html>
