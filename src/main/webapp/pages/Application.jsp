<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vuetify/dist/vuetify.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900|Material+Icons" rel="stylesheet">

    <%--<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">--%>
    <title>
        <fmt:message key="application.title"/>.${lang}
    </title>
    <link rel="stylesheet" href="${context}/css/Application.css">
    <link rel="stylesheet" href="${context}/css/Coverlet.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="${context}/js/Core.js"></script>
    <script src="${context}/js/Application.js"></script>
    <script>
        context = '${context}';
        logoutAPI = '${logoutAPI}';
        welcome = '${welcome}';
    </script>
</head>
<body style="margin: 0">
<div class="coverlet" id="coverlet"></div>
<div class="modal-layer" style="display: none" id="modal"></div>
<link rel="stylesheet" href="${context}/css/datepick.css">

<div class="dateTime-picker" id="picker" v-show="onSelects.length" v-on:click="close">
    <div class="picker-content" :style="{top:y + 'px', left:x + 'px'}">
        <v-date-picker class="date-picker"
                       :no-title="true"
                       :locale="locale"
                       :color="color"
                       :first-day-of-week="1"
                       :type="type"
                       v-model="date" @input="put"></v-date-picker>
    </div>
</div>
<script src="${context}/vue/datetimePicker.vue"></script>
<script>
    <c:choose>
    <c:when test="${lang eq 'ua'}">
    datepicker.locale = 'uk';
    </c:when>
    <c:otherwise>
    datepicker.locale = '${lang}';
    </c:otherwise>
    </c:choose>
</script>

<table class="body-table" border="0" style="width: 100%; height: 100%">
    <tr>
        <td rowspan="2" valign="top" style="height: 40%; width: 1px; padding: 0; border-right: solid 2pt">
            <jsp:include page="NavigationMenu.jsp"/>
        </td>
        <td width="100%" style="background-color: #d3dbe2;">
            <table style="width: 100%; height: 100%">
                <tr>
                    <td>
                        <div class="header" id="header"></div>
                    </td>
                    <td>
                        <div class="header" style="font-size: 10pt; text-align: right">
                            <div style="padding: 0 4pt">
                                <a onclick="loadContent('${personal}')">
                                    ${worker.value}
                                </a>
                                <a onclick="logout()">
                                    (<fmt:message key="sign.out"/>)
                                </a>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
        <td rowspan="3">
            <%--<jsp:include page="ShortCuts.jsp"/>--%>
        </td>
    </tr>
    <tr>
        <td rowspan="2" height="100%" style="max-width: 1267px; width: 1px">
            <div class="wrapper" id="content"></div>
        </td>
    </tr>
    <tr>
        <td height="100%" valign="top" style="border-right: solid 2pt">
            <div id="filter" class="filter" ></div>
        </td>
    </tr>
</table>
</body>
</html>
