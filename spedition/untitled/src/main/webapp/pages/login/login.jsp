<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="uk"/>
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 02.06.20
  Time: 14:20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <link rel="stylesheet" href="${context}/css/main.css">
        <title>LOGIN</title>
        <script>
            if (typeof context === "undefined"){
                context = '${context}';
            }
        </script>
        <script src="${context}/external/vue.js"></script>
        <script src="${context}/js/utils.js"></script>
    </head>
    <body>
        <div id="signIn">
            <div>
                <div>
                    <table>
                        <tr>
                            <td>
                                <label for="login">
                                    <fmt:message key="phone"/>
                                </label>
                            </td>
                            <td>
                                <input id="login" v-model="login" autocomplete="off">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="pass">
                                    <fmt:message key="password"/>
                                </label>
                            </td>
                            <td>
                                <input id="pass" type="password" v-model="password" autocomplete="off">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <button v-on:click="signIn">
                                    <fmt:message key="sign.in"/>
                                </button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
    <script src="${context}/vue/login.vue"></script>
    <script>
        login.api.login = '${login}';
    </script>
</html>
