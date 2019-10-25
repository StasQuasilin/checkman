<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="${context}/ext/vue.js"></script>
    <title><fmt:message key="sign.in"/></title>
    <script src="${context}/js/Core.js"></script>
    <link rel="stylesheet" href="${context}/css/login.css">
</head>
<body>
<div id="login">
    <div class="coverlet" v-show="cover"></div>
    <div class="wrapper">
        <div class="content">
            <table border="0">
                <tr>
                    <th colspan="3" align="center">
                        <div class="header">
                            <fmt:message key="sign.in"/>
                        </div>
                    </th>
                </tr>
                <tr v-if="state == 0">
                    <td>
                        <label for="worker">
                            <fmt:message key="user.name"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <div>
                            <input id="worker" autocomplete="off" v-model="worker" :class="{error : errors.user}"
                                   v-on:keyup.enter="check()" v-on:keyup="findUser()" onfocus="this.select()"
                                ref="worker">
                            <div class="custom-data-list" v-show="foundUsers.length > 0">
                                <div class="custom-data-list-item" v-for="user in foundUsers" v-on:click="setUser(user)">
                                    {{user.person.surname}}&nbsp;{{user.person.forename}}&nbsp;{{user.person.patronymic}}
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <template v-else>
                    <tr>
                        <td colspan="3">
                            <span>
                                {{worker}}
                            </span>
                            <a class="mini-close" style="font-size: 10pt" v-on:click="back">
                                <fmt:message key="sign.in.no.me"/>
                            </a>
                        </td>
                    </tr>
                    <tr >
                        <td>
                            <label for="key">
                                <fmt:message key="user.password"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="key" ref="password" type="password" v-on:keyup.enter="signIn"
                                   v-model="user.password" :class="{error : errors.password}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <a onclick="location.href='${context}${forgot}'"><fmt:message key="forgot"/>...</a>
                            <button v-on:click="signIn"><fmt:message key="sign.in"/></button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <span v-if="err" class="error">{{err}}</span>
                            <span v-else>&nbsp;</span>
                        </td>
                    </tr>
                </template>

            </table>
        </div>
    </div>
</div>

<script type="text/javascript" src="${context}/vue/SignIn.vue"></script>
<script>
    const context = '${context}';
    login.api.find = '${userApi}';
    login.api.signin = '${loginApi}';
</script>
</body>

</html>