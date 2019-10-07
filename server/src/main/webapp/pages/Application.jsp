<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
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
    <script>
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
    <c:forEach items="${subscribe}" var="sub">
    subscribe('${sub}', function(a){
        chat.handle(a);
    });
    </c:forEach>
    </script>

</head>
    <body style="margin: 0;">
    <div class="coverlet" id="coverlet">
        <table style="width: 100%; height: 100%;">
            <tr>
                <td align="center">
                    <div class="lds-dual-ring"></div>
                </td>
            </tr>
        </table>
    </div>
    <div class="modal-layer" style="display: none" id="modal"></div>
    <jsp:include page="chat/chatHolder.jsp"/>
    <script>
        chat.api.send = '${sendMessage}';
        chat.api.get = '${getMessages}';
        chat.api.leave = '${leaveChat}';
        chat.api.rename = '${renameChat}';
        chat.api.remove = '${removeChat}';
    </script>

    <div class="datetime-picker" id="datePicker" v-show="onSelects.length" v-on:click="close">
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
                            {{hours[(i - 1) * 4 + (j - 1)]}}
                        </span>
                    </div>
                </div>
                <div style="display: inline-block; border-left: solid gray 1pt">
                    <div style="width: 100%; text-align: center">
                        <fmt:message key="minutes"/>
                    </div>
                    <div v-for="i in 6">
                        <span class="mini-close" v-for="j in 2" v-on:click="setMinutes(minutes[(i - 1) * 2 + (j - 1)])">
                            {{minutes[(i - 1) * 2 + (j - 1)]}}
                        </span>
                    </div>
                </div>
                <div style="width: 100%; text-align: center">
                    <span v-on:click="save">
                        &#10003;
                    </span>
                    <span v-on:click="close">
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
        <table border="1" class="body-table" style="width: 100%; height: 100%;">
            <tr>
                <td rowspan="2" valign="top" style="height: 100%; width: 1px; padding: 0; border-right: solid 2pt; background-color: #d3dbe2;">
                    <jsp:include page="NavigationMenu.jsp"/>
                    <div id="filter" class="filter" ></div>
                </td>
                <td colspan="2">
                    <div class="header-wrapper">
                        <div class="header" id="header">
                            Header
                        </div>
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
                </td>
            </tr>
            <tr>
                <td valign="top" height="100%">
                    <div class="wrapper" id="content"></div>
                </td>
                <td style="max-width: 200pt">
                    <div class="static-content" id="static"></div>
                </td>
            </tr>
        </table>
    </body>
</html>
