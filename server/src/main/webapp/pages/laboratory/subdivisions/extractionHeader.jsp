<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div id="container-header" style="display: inline">
        <link rel="stylesheet" href="${context}/css/drop-menu.css">
        <c:if test="${role eq 'analyser' or role eq 'admin'}">
        <div class="drop-menu">
            <a class="drop-btn"><fmt:message key="analyses"/>&nbsp;+</a>
            <div class="drop-menu-content">
                <div class="drop-menu-item" onclick="loadModal('${crudeEdit}')">
                    <fmt:message key="extraction.crude"/>
                </div>
                <div class="drop-menu drop-menu-item">
                    <span>
                        <fmt:message key="storage.analyses"/>
                    </span>
                    <div class="drop-menu-content" style="top: 0; left: 100%; width: 10em">
                        <div class="drop-menu-item" onclick="loadModal('${storageProtein}')">
                            <fmt:message key="menu.extraction.storage.protein"/>
                        </div>
                        <div class="drop-menu-item" onclick="loadModal('${storageGrease}')">
                            <fmt:message key="menu.extraction.storage.raw.grease"/>
                        </div>
                    </div>
                </div>
                <div class="drop-menu drop-menu-item">
                     <span>
                        <fmt:message key="vro.turn.analyses"/>
                    </span>
                    <div class="drop-menu-content" style="top: 0; left: 100%; width: 10em">
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
            <a class="drop-btn"><fmt:message key="document.print"/>&nbsp;&#9660;</a>
            <div class="drop-menu-content">
                <div class="drop-menu-item" onclick="loadModal('${dailyPrint}')">
                    <fmt:message key="print.daily.report"/>
                </div>
            </div>
        </div>
    </div>
</html>