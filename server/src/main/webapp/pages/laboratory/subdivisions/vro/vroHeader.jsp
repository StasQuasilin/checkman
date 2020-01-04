<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<div id="container-header" style="display: inline">
    <link rel="stylesheet" href="${context}/css/drop-menu.css">
    <c:if test="${role eq 'analyser' or role eq 'admin'}">
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="analyses"/> &#9660;</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" style="width: 10em" onclick="loadModal('${crudeEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.crude"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${oilEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.oil"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${dailyEdit}')">
                <span style="padding: 0 2pt">
                    <fmt:message key="vro.turn.analyses"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${oilMassFraction}')">
                <span>
                    <fmt:message key="oil.mass.fraction"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${granules}')">
                <span>
                    <fmt:message key="granules"/>
                </span>
            </div>
            <div class="drop-menu-item" onclick="loadModal('${sunProtein}')">
                <fmt:message key="menu.vro.sun.protein"/>
            </div>
        </div>
    </div>
    </c:if>
    <div class="drop-menu">
        <a class="drop-btn"><fmt:message key="document.print"/>&nbsp;&#9660;</a>
        <div class="drop-menu-content">
            <div class="drop-menu-item" onclick="loadModal('${dailyPrint}')">
                <fmt:message key="print.daily.report"/>
            </div>
        </div>
    </div>
</div>