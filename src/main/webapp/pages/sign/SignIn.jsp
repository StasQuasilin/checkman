<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="${context}/images/sunflower_small.png" />
    <c:if test="${not empty token}">
        <script>
            location.href='${context}/app'
        </script>
    </c:if>
    <script src="${context}/ext/vue.js"></script>
    <title><fmt:message key="sign.in"/></title>
    <script src="${context}/js/Core.js"></script>
    <script src="${context}/vue/templates/vehicleInput.vue"></script>
    <script src="${context}/js/loginer.js?v=${now}"></script>
    <link rel="stylesheet" href="${context}/css/login.css">

</head>
<body>
<div id="login">
    <div class="coverlet" v-show="cover"></div>
    <div class="wrapper">
        <div class="content">
            <table border="0" style="width: 100%">
                <tr>
                    <th colspan="3" align="center">
                        <div class="header">
                            <fmt:message key="sign.in"/>
                        </div>
                    </th>
                </tr>
                <template v-if="state == 0">
                    <tr v-for="(value, key) in users">
                        <td colspan="3">
                            <span v-on:click="removeUserAccess(key)">
                                &times;
                            </span>
                            <span v-on:click="setUser(key, value)">
                                {{value}}
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <user-input :props="userProps" :object="user"></user-input>
                        </td>
                    </tr>
                </template>
                <template v-else>
                    <tr>
                        <td colspan="3">
                            <span>
                                {{user.value}}
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
<script src="${context}/ext/CryptoJS.js"></script>
<script src="${context}/js/utils.js?v=${now}"></script>
<script type="text/javascript" src="${context}/vue/SignIn.vue?v=${now}"></script>
<script>
    const context = '${context}';
    login.api.find = '${userApi}';
    loginer.api.signin = '${loginApi}';
    login.userProps = {
        header:'<fmt:message key="user.select.other"/>',
        find:'${userApi}',
        put:function(user){
            login.setUser(user.uid, user.person.value)
        },
        show:['person/value']
    }
</script>
</body>

</html>