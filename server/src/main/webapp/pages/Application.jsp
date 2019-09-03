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
    <body style="margin: 0; max-width: 1390px">
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

    <div class="datetime-picker" id="picker" v-show="onSelects.length" v-on:click="close">
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

        <table class="body-table" border="1" style="width: 100%; height: 100%; max-width: 1280px">
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
                                            ${worker.person.surname}&nbsp;${worker.person.forename}&nbsp;${worker.person.patronymic}
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
            </tr>
            <tr>
                <td rowspan="2" height="100%" style="width: 1px">
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