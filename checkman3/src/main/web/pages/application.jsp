<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 03.09.20
  Time: 10:36
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <link rel="stylesheet" href="${context}/css/main.css">
        <link rel="stylesheet" href="${context}/css/application.css">
        <link rel="stylesheet" href="${context}/css/modal.css">
        <script src="${context}/external/vue.js"></script>
        <script src="${context}/external/vuetify.js"></script>
        <script src="${context}/js/connection.js"></script>
        <script>
            if (typeof context === "undefined"){
                context = '${context}';
            }
        </script>
    </head>
<body>
    <div class="modal-view">

    </div>
    <div id="app" class="application-page">
        <div class="menu-bar">
            <button v-on:click="loadPage('/test')">
                Load
            </button>
        </div>
        <div class="content-page">
            <div class="header">
                <div class="header-wrapper">
                    <div class="title-holder">
                        TITLE
                    </div>
                    <div class="personal">
                        PERSONAL
                    </div>
                </div>
            </div>
            <div class="content-view">
                <div class="main-content">
                    CONTENT
                </div>
                <div class="static-content">
                    STATIC
                </div>
            </div>
        </div>
    </div>
</body>
    <script type="application/javascript" src="${context}/vue/application.vue"></script>
</html>
