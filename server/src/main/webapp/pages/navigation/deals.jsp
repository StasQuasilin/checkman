<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul>
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
      <span class="main" onclick="loadContent('${summaryList}')">
        <fmt:message key="consolidated.table"/>
      </span>
      <span>
        <a onclick="loadContent('${summaryArchive}')" ><fmt:message key="archive"/> </a>
      </span>
    </li>
    <li class="menu-item">
        <span class="main" onclick="loadContent('${storages}')">
            <fmt:message key="menu.storages"/>
        </span>
    </li>
    <li class="menu-item" onclick="loadContent('${probeList}')">
        <span class="main">
          <fmt:message key="analyses.probe"/>
        </span>
    </li>
    <li class="menu-item" onclick="loadContent('${referencesList}')">
        <span class="main">
          <fmt:message key="menu.references"/>
        </span>
    </li>

</ul>
