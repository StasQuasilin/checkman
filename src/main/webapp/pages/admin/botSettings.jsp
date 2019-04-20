<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var botSettings = new Vue({
        el: '#botSettings',
        data:{
            api:{
                settings:'',
                status:''
            },
            bot:{
                token:'',
                name:'',
                status:''
            },
            input:{
                token:'ololo',
                name:'123456'
            },
            upd:-1
        },
        methods:{
            needSave:function(){
                return this.bot.token !== this.input.token || this.bot.name !== this.input.name;
            },
            save:function(){
                const self = this;
                PostApi(this.api.settings, this.input, function(a){
                    if (a.status == 'success'){
                        self.bot.token = self.input.token;
                        self.bot.name = self.input.name;
                    }
                });
            },
            botStatus:function(){
                const self = this;
                PostApi(this.api.status, null, function(a){
                    self.bot = a;
                });
                this.upd = setTimeout(function(){
                    self.botStatus()
                }, 5000)
            }
        }

    });
    botSettings.api.settings = '${botSettings}';
    botSettings.api.status = '${botStatus}';
    botSettings.botStatus();
</script>
    <table id="botSettings">
        <tr>
            <th colspan="3">
                <fmt:message key="bot.settings"/>
            </th>
        </tr>
        <tr>
            <td>
                <fmt:message key="status"/>
            </td>
            <td>
                :
            </td>
            <td>
                {{bot.status}}
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <label for="token">
                    <fmt:message key="bot.token"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <textarea id="token" style="width: 100%; resize: none" v-model="input.token"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <label for="name">
                    <fmt:message key="person.forename"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="name" v-model="input.name">
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <button v-show="needSave()" v-on:click="save">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>