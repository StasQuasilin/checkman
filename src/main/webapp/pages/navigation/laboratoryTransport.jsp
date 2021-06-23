<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<li class="menu-item">
    <span class="main" onclick="loadContent('${weightList}?type=buy')">
      <fmt:message key="analyses.buy"/>
    </span>
    <span>
      <a onclick="loadContent('${laboratoryBuyArchive}')"><fmt:message key="archive"/> </a>
    </span>
</li>
<li class="menu-item">
    <span class="main" onclick="loadContent('${weightList}?type=sell')">
      <fmt:message key="analyses.sell"/>
    </span>
    <span>
      <a onclick="loadContent('${laboratorySellArchive}')"><fmt:message key="archive"/> </a>
    </span>
</li>
