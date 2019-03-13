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
        <li class="menu-item"  onclick="loadContent('${buy_link}')">
          - <fmt:message key="deal.buy"/>
          <a onclick="loadContent('${context}${buy_archive}')"><fmt:message key="archive"/> </a>
        </li>
        <li class="menu-item"  onclick="loadContent('${sell_link}')">
          - <fmt:message key="deal.sell"/>
          <a onclick="loadContent('${context}${sell_archive}')" ><fmt:message key="archive"/> </a>
        </li>
      </ul>
      <li class="menu-item" onclick="loadContent('${logistic_link}')">
        <span>
          <fmt:message key="menu.logistic"/>
        </span>
        <a onclick="loadContent(${context}${logistic_archive})"><fmt:message key="archive"/> </a>
      </li>
      <li class="menu-item" onclick="loadContent('${security_link}')">
        <span>
          <fmt:message key="menu.transport"/>
        </span>
        <a onclick="loadContent(${context}${transport_archive})" ><fmt:message key="archive"/> </a>
      </li>
      <li class="menu-item" onclick="loadContent('${weight_link}')">
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
        <li class="menu-item" onclick="loadContent('${probe_url}')">
          - <fmt:message key="analyses.probe"/>
        </li>
        <li class="menu-item" onclick="loadContent('${department_url}')">
          - <fmt:message key="analyses.subdivision"/>
        </li>
        <li class="menu-item" onclick="loadContent('${laboratory_buy_url}')">
          - <fmt:message key="analyses.buy"/>
          <a onclick="loadContent(${context}${analyses_buy_archive})"><fmt:message key="archive"/> </a>
        </li>
        <li class="menu-item" onclick="loadContent('${laboratory_sell_url}')">
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
