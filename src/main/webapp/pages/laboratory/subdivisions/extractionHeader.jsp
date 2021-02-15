<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div id="container-header" style="display: inline">
        <link rel="stylesheet" href="${context}/css/drop-menu.css?v=${now}">
        <c:if test="${role eq 'analyser' or role eq 'admin'}">
        <div class="drop-menu">
            <a class="drop-btn-header"><fmt:message key="analyses"/>&nbsp;+</a>
            <div class="drop-menu-content">
                <div class="drop-menu drop-sub-btn drop-menu-item">
                    <span>
                        <fmt:message key="storage.analyses"/>
                    </span>
                    <div class="drop-menu-content sub-menu">
                        <div class="drop-menu-item" onclick="loadModal('${storageProtein}')">
                            <fmt:message key="menu.extraction.storage.protein"/>
                        </div>
                        <div class="drop-menu-item" onclick="loadModal('${storageGrease}')">
                            <fmt:message key="menu.extraction.storage.raw.grease"/>
                        </div>
                    </div>
                </div>
                <div class="drop-menu drop-sub-btn drop-menu-item">
                     <span>
                        <fmt:message key="vro.turn.analyses"/>
                    </span>
                    <div class="drop-menu-content sub-menu">
                        <div class="drop-menu-item" onclick="loadModal('${turnProtein}')">
                            <fmt:message key="menu.extraction.turn.protein"/>
                        </div>
                        <div class="drop-menu-item" onclick="loadModal('${turnGrease}')">
                            <fmt:message key="menu.extraction.turn.raw.grease"/>
                        </div>
                        <div class="drop-menu-item" onclick="loadModal('${turnCellulose}')">
                            <fmt:message key="menu.extraction.turn.cellulose"/>
                        </div>
                    </div>
                </div>
                <div class="drop-menu-item"  onclick="loadModal('${oilEdit}')">
                    <fmt:message key="extraction.oil"/>
                </div>
                <div class="drop-menu-item"  onclick="loadModal('${mealGranules}')">
                    <fmt:message key="extraction.meal.granules"/>
                </div>
            </div>
        </div>
        </c:if>
        <div class="drop-menu">
            <a class="drop-btn-header"><fmt:message key="document.print"/></a>
            <div class="drop-menu-content">
                <div class="drop-menu-item" onclick="loadModal('${dailyPrint}')">
                    <fmt:message key="print.daily.roundReport"/>
                </div>
            </div>
        </div>
    </div>
</html>