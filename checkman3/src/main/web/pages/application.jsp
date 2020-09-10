<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 03.09.20
  Time: 10:36
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html lang="${locale}">
    <head>
        <link rel="stylesheet" href="${context}/css/main.css">
        <link rel="stylesheet" href="${context}/css/application.css">
        <link rel="stylesheet" href="${context}/css/modal.css">
        <script type="application/javascript" src="${context}/external/jquery.min.js"></script>
        <script type="application/javascript" src="${context}/external/vue.js"></script>
        <script type="application/javascript" src="${context}/external/vuetify.js"></script>
        <script type="application/javascript" src="${context}/js/utils.js"></script>
        <script type="application/javascript" src="${context}/js/connection.js"></script>
        <script type="application/javascript" src="${context}/js/application.js"></script>
        <script type="application/javascript" src="${context}/js/subscriber.js"></script>
        <script type="application/javascript" src="${context}/vue/list.vue"></script>
        <script>
            if (typeof context === "undefined"){
                context = '${context}';
            }
            if (typeof worker === "undefined"){
                worker = '${worker}'
            }
            subscriber.address = 'ws://' + window.location.host + context + '${subscriber}'
        </script>
        <title><fmt:message key="application.title"/></title>
    </head>
<body>
    <div id="coverlet" class="coverlet"></div>
    <div id="modal" class="modal-view">

    </div>
    <div id="app" class="application-page">
        <div class="menu-bar">
            <div class="menu-holder">
                <jsp:include page="navigation/menu.jsp"/>
            </div>
            <div id="filter" class="filter-holder">
                FILTER
            </div>
        </div>
        <div class="content-page">
            <div class="header">
                <div class="header-wrapper">
                    <div id="header" class="title-holder">
                        TITLE
                    </div>
                    <div class="personal">
                        PERSONAL
                    </div>
                </div>
            </div>
            <div class="content-view">
                <div id="content" class="main-content">
                    CONTENT
                </div>
                <div id="static" class="static-content">
                    STATIC
                </div>
            </div>
        </div>
    </div>
</body>
    <script type="application/javascript" src="${context}/vue/application.vue"></script>
</html>
