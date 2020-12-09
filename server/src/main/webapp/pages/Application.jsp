
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="${context}/images/sunflower_small.png" />
    <title>
        <fmt:message key="application.title"/>.${lang}
    </title>
    <link rel="stylesheet" href="${context}/css/Application.css">
    <link rel="stylesheet" href="${context}/css/Coverlet.css">
    <link rel="stylesheet" href="${context}/css/chat/chat-holder.css">
    <link rel="stylesheet" href="${context}/css/date-picker.css">
    <script src="${context}/ext/vue.js"></script>
    <script src="${context}/ext/vuetify.js"></script>
    <script src="${context}/ext/jquery.min.js"></script>
    <script src="${context}/js/Core.js"></script>
    <script src="${context}/js/Application.js"></script>
    <script src="${context}/js/Settings.js"></script>
    <script src="${context}/vue/templates/transportationDataView.vue"></script>
    <script src="${context}/vue/templates/retailProductView.vue"></script>
    <script src="${context}/vue2/baseList.vue"></script>
    <script>
        Vue.component('product-view', productView);
    context = '${context}';
    logoutAPI = '${logoutAPI}';
    welcome = '${welcome}';
    Settings.address=window.location.host;
    Settings.context='${context}';
    Settings.api='${SUBSCRIBER}';
    Settings.worker = ${worker.id};
    </script>
    <script src="${context}/js/Subscriber.js"></script>
    <script>
        subscribe('NOTIFICATIONS', function(a){
            notificator.notify(a);
        });
        // subscribe('SESSION_TIMER', function(a){
        //     lockSession(a);
        // })
    </script>
</head>
<%--oncontextmenu="return false;"--%>
<body style="margin: 0;" >
    <div class="modal-layer" id="sessionLocker" style="background-color: #5e5e5e; z-index: 101; display: none">
        <table style="width: 100%; height: 100%;">
            <tr>
                <td align="center">
                    <div class="content">
                        <div style="padding: 8pt">
                            <span id="reason" style="color: gainsboro; font-size: 16pt"></span>
                        </div>
                        <div>
                            <button onclick="location.reload()">
                                OK
                            </button>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="coverlet" id="coverlet">
        <table style="width: 100%; height: 100%;">
            <tr>
                <td align="center">
                    <div class="lds-dual-ring"></div>
                </td>
            </tr>
        </table>
    </div>
    <jsp:include page="notifications/notificator.jsp"/>

    <div class="modal-layer" style="display: none" id="modal"></div>
    <div class="datetime-picker" id="datePicker" v-show="onSelects.length" v-on:click="close">
        <div class="picker-content" ref="date" :style="{top:y + 'px', left:x + 'px'}">
            <v-date-picker class="date-picker"
                           :no-title="true"
                           :locale="locale"
                           :color="color"
                           :first-day-of-week="1"
                           :type="type"
                           v-model="date" @input="put"></v-date-picker>
        </div>
    </div>
    <script src="${context}/vue/datetime/datePicker.vue"></script>
    <div id="timepicker" class="datetime-picker" v-on:click="saveAndClose"
        v-show="onSave.length">
        <div class="picker-content" :style="{top:y + 'px', left:x + 'px'}">
            <div style="background-color: aliceblue;">
                <div style="width: 100%; text-align: center">
                    {{hh}}:{{mm}}
                </div>
                <div style="display: inline-block">
                    <div style="width: 100%; text-align: center">
                        <fmt:message key="hours"/>
                    </div>
                    <div v-for="i in 6">
                        <span class="mini-close" v-for="j in 4" v-on:click="setHours(hours[(i - 1) * 4 + (j - 1)])">
                            <span style="font-size: 24pt">
                                {{hours[(i - 1) * 4 + (j - 1)]}}
                            </span>
                        </span>
                    </div>
                </div>
                <div style="display: inline-block; border-left: solid gray 1pt">
                    <div style="width: 100%; text-align: center">
                        <fmt:message key="minutes"/>
                    </div>
                    <div v-for="i in 6">
                        <span class="mini-close" v-for="j in 2" v-on:click="setMinutes(minutes[(i - 1) * 2 + (j - 1)])">
                            <span style="font-size: 24pt">
                                {{minutes[(i - 1) * 2 + (j - 1)]}}
                            </span>
                        </span>
                    </div>
                </div>
                <div style="width: 100%; text-align: center">
                    <span v-on:click="save" style="font-size: 18pt">
                        &#10003;
                    </span>
                    <span v-on:click="close" style="font-size: 18pt">
                        &times;
                    </span>
                </div>
            </div>
        </div>
    </div>
    <script src="${context}/vue/datetime/timePicker.vue"></script>
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
    <div class="body-table">
        <div class="navigation-panel">
            <jsp:include page="NavigationMenu.jsp"/>
            <div id="filter" class="filter"></div>
        </div>
        <div class="content">
            <div class="title-holder">
                <div class="header-wrapper">
                    <div class="header" id="header"></div>
                    <div style="font-size: 10pt; text-align: right; float: right;">
                        <div style="padding: 6.5pt 4pt">
                            <a onclick="loadContent('${personal}')">
                                ${worker.person.surname}&nbsp;${worker.person.forename}&nbsp;${worker.person.patronymic}
                            </a>
                            <a onclick="logout()">
                                (<fmt:message key="sign.out"/>)
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="content-holder">
                <div class="wrapper" id="content"></div>
                <div class="static-content" id="static"></div>
            </div>
        </div>
    </div>
</body>
</html>
