<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title><fmt:message key="sign.in"/></title>
    <script src="${context}/js/Logic.js"></script>
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
                <tr>
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
                            <input id="worker" autocomplete="off" v-model="worker" :class="{error : errors.user}" v-on:keyup="findUser()" onclick="this.select()">
                            <div class="custom-data-list" v-show="foundUsers.length > 0">
                                <div class="custom-data-list-item" v-for="user in foundUsers" v-on:click="setUser(user)">
                                    {{user.person.value}}
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="key">
                            <fmt:message key="user.password"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="key" type="password" v-model="user.password" :class="{error : errors.password}">
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
            </table>
        </div>
    </div>
</div>

<script src="${context}/vue/SignIn.js"></script>
<script>
    const context = '${context}';
    login.api.find = '${userApi}';
    login.api.signin = '${loginApi}';
</script>
</body>

</html>