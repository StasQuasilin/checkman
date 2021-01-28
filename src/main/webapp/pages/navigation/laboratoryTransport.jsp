<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
