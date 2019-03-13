<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <%--<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>--%>
    <link rel="stylesheet" href="${context}/css/Application.css">

</head>
<body style="margin: 0">
<table border="1" style="width: 100%; height: 100%">
    <tr>
        <td rowspan="2">
            <div class="navigation-menu" id="nav-menu" dynamic>
                <ul>
                    <li>
                        <b>
                            <fmt:message key="menu.deals"/>
                        </b>
                    </li>
                    <ul style="padding-left: 20px">
                        <li class="menu-item"  onclick="loadContent('${buy_url}')">
                            - <fmt:message key="deal.buy"/>
                            <a onclick="loadContent('${context}${buy_archive}')"><fmt:message key="archive"/> </a>
                        </li>
                        <li class="menu-item"  onclick="loadContent('${sell_url}')">
                            - <fmt:message key="deal.sell"/>
                            <a onclick="loadContent('${context}${sell_archive}')" ><fmt:message key="archive"/> </a>
                        </li>
                    </ul>
                    <li class="menu-item" onclick="loadContent('${logistic_url}')">
                      <span>
                        <fmt:message key="menu.logistic"/>
                      </span>
                        <a onclick="loadContent(${context}${logistic_archive})"><fmt:message key="archive"/> </a>
                    </li>
                    <li class="menu-item" onclick="loadContent('${security_url}')">
                      <span>
                        <fmt:message key="menu.transport"/>
                      </span>
                        <a onclick="loadContent(${context}${transport_archive})" ><fmt:message key="archive"/> </a>
                    </li>
                    <li class="menu-item" onclick="loadContent('${weight_url}')">
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
        </td>
        <td>
            2
        </td>
        <td rowspan="2">

        </td>
    </tr>
    <tr>
        <td height="100%" style="max-width: 1266px" width="100%">
            Content
        </td>
    </tr>
</table>
</body>
</html>
