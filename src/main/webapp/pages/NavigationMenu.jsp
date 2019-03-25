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
      <li>
        <b>
          <fmt:message key="menu.deals"/>
        </b>
      </li>
      <ul style="padding-left: 20px">
        <li class="menu-item"  onclick="loadContent('${buyList}')">
          - <fmt:message key="deal.buy"/>
          <a onclick="loadContent('${buyArchive}')"><fmt:message key="archive"/> </a>
        </li>
        <li class="menu-item"  onclick="loadContent('${sellList}')">
          - <fmt:message key="deal.sell"/>
          <a onclick="loadContent('${sellArchive}')" ><fmt:message key="archive"/> </a>
        </li>
      </ul>
      <li class="menu-item" onclick="loadContent('${logisticList}')">
        <span>
          <fmt:message key="menu.logistic"/>
        </span>
        <a onclick="loadContent(${context}${logistic_archive})"><fmt:message key="archive"/> </a>
      </li>
      <li class="menu-item" onclick="loadContent('${transportList}')">
        <span>
          <fmt:message key="menu.transport"/>
        </span>
        <a onclick="loadContent(${context}${transport_archive})" ><fmt:message key="archive"/> </a>
      </li>
      <li class="menu-item" onclick="loadContent('${weightList}')">
        <span>
          <fmt:message key="menu.weight"/>
        </span>
        <a onclick="loadContent(${context}${weight_archive})"><fmt:message key="archive"/> </a>
      </li>
      <li>
        <span>
          <b>
            <fmt:message key="menu.laboratory"/>
          </b>
        </span>
      </li>
      <ul style="padding-left: 20px">
        <li class="menu-item" onclick="loadContent('${probeList}')">
          - <fmt:message key="analyses.probe"/>
        </li>
        <li class="menu-item" onclick="loadContent('${departmentList}')">
          - <fmt:message key="analyses.subdivision"/>
        </li>
        <li class="menu-item" onclick="loadContent('${laboratoryBuyList}')">
          - <fmt:message key="analyses.buy"/>
          <a onclick="loadContent(${context}${analyses_buy_archive})"><fmt:message key="archive"/> </a>
        </li>
        <li class="menu-item" onclick="loadContent('${laboratorySellList}')">
          - <fmt:message key="analyses.sell"/>
          <a onclick="loadContent(${context}${analyses_sell_archive})"><fmt:message key="archive"/> </a>
        </li>
      </ul>
      <li class="menu-item" onclick="loadContent('${references_url}')">
        <span>
          <fmt:message key="menu.references"/>
        </span>
      </li>
      <c:if test="${role eq 'admin'}">
        <li class="menu-item" onclick="loadContent('${admin_url}')">
                            <span>
                              <fmt:message key="menu.admin"/>
                            </span>
        </li>
      </c:if>

    </ul>
  </div>
</html>
