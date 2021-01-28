<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 20.02.2020
  Time: 16:34
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <body>
    <script>
        var find = new Vue({
            el:'#find',
            data:{
                api:{},
                input:'',
                result:[]
            },
            methods:{
                find:function(){
                    if (this.input) {
                        const self = this;
                        PostApi(this.api.find, {key: this.input}, function (a) {
                            self.result = a.result;
                        })
                    }
                }
            }
        });
        find.api.find = '${find}';
    </script>
        <table id="find">
            <tr>
                <td>
                    <label for="seal">
                        <fmt:message key="seals.number.find"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <input id="seal" v-model="input" autocomplete="off" onfocus="this.select()" v-on:keyup.enter="find()">
                    <button v-on:click="find()">
                        <fmt:message key="find"/>
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <div style="border: solid gray 1pt; height: 300px; overflow-x: hidden; overflow-y: scroll">
                        <div v-for="seal in result" style="border-bottom: solid gray 1pt">
                            <div>
                                {{seal.value}}
                            </div>
                            <div v-if="seal.transportation">
                                <div>
                                    {{new Date(seal.transportation.date).toLocaleDateString()}}
                                    {{seal.transportation.driver.value}}
                                </div>
                                <div>
                                    {{seal.transportation.counterparty.value}}
                                    {{seal.transportation.product.name}}
                                </div>
                            </div>
                            <div v-else>
                                <fmt:message key="not.used"/>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <button onclick="closeModal()">
                        <fmt:message key="button.close"/>
                    </button>
                </td>
            </tr>
        </table>
    </body>
</html>
