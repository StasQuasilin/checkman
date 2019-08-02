<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var botSettings = new Vue({
        el: '#settings',
        data:{
            api:{
                generate:'',
                status:''
            },
            uid:'',
            bot:{
                transport:'off',
                weight:'off',
                analyses:'off',
                extraction:false,
                vro:false,
                kpo:false,
                show:false
            },
            online:false
        },
        methods:{
            generate:function(){
                const self = this;
                PostApi(this.api.generate, null, function(a){
                    self.uid = a.uid;
                })
            },
            status:function(){
                const self = this;
                PostApi(this.api.status, this.bot, function(a){
                    self.bot.id= a.id;
                    self.bot.transport = a.transport;
                    self.bot.weight = a.weight;
                    self.bot.analyses = a.analyses;
                    self.bot.extraction = a.extraction;
                    self.bot.vro = a.vro;
                    self.bot.kpo = a.kpo;
                    self.bot.show = a.show;
                    self.online = true;
                    setTimeout(function(){
                        self.status();
                    }, 1000)
                })
            }
        }
    });
    botSettings.api.generate = '${uidGenerator}';
    botSettings.api.status = '${botStatus}';
    <c:if test="${not empty botSettings}">
    botSettings.bot={
        id:${botSettings.telegramId},
        transport:'${botSettings.transport}',
        weight:'${botSettings.weight}',
        analyses:'${botSettings.analyses}',
        extraction:${botSettings.extraction},
        vro:${botSettings.vro},
        kpo:${botSettings.kpo},
        show:${botSettings.show}
    };
    </c:if>
    botSettings.status();
</script>
<table id="settings">
    <tr>
        <th colspan="2">
            <fmt:message key="bot.settings"/>
        </th>
    </tr>
    <tr>
        <td>
            <fmt:message key="status"/>:
        </td>
        <td>
            <span v-if="online" style="color: green">
                Online
            </span>
            <span v-else style="color: red">
                Offline
            </span>
        </td>
    </tr>
    <tr>
        <td>
            <label for="token">
                Access token
            </label>
        </td>
        <td>
            <input id="token" readonly v-model="uid" style="width: 7em">
            <span style="color: green" class="mini-close" v-on:click="generate">&#8635;</span>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="show" type="checkbox" v-model="bot.show">
            <label for="show">
                <span v-if="bot.show">
                    <fmt:message key="bot.show"/>
                </span>
                <span v-else>
                    <fmt:message key="bot.dont.show"/>
                </span>
            </label>
        </td>
    </tr>
    <tr>
        <th colspan="2">
            <fmt:message key="transport"/>
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <input type="radio" name="transport" id="transportNone" value="off" v-model="bot.transport">
            <label for="transportNone">
                <fmt:message key="none"/>
            </label>
            <input type="radio" name="transport" id="transportMy" value="my" v-model="bot.transport">
            <label for="transportMy">
                <fmt:message key="my"/>
            </label>
            <input type="radio" name="transport" id="transportAll" value="all" v-model="bot.transport">
            <label for="transportAll">
                <fmt:message key="all"/>
            </label>
        </td>
    </tr>
    <tr>
        <th colspan="2">
            <fmt:message key="weights"/>
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <input type="radio" name="weight" id="weightNone" value="off" v-model="bot.weight">
            <label for="weightNone">
                <fmt:message key="none"/>
            </label>
            <input type="radio" name="weight" id="weightMy" value="my" v-model="bot.weight">
            <label for="weightMy">
                <fmt:message key="my"/>
            </label>
            <input type="radio" name="weight" id="weightAll" value="all" v-model="bot.weight">
            <label for="weightAll">
                <fmt:message key="all"/>
            </label>
        </td>
    </tr>
    <tr>
        <th colspan="2">
            <fmt:message key="analyses"/>
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <input type="radio" name="analyse" id="analyseNone" value="off" v-model="bot.analyses">
            <label for="analyseNone">
                <fmt:message key="none"/>
            </label>
            <input type="radio" name="analyse" id="analyseMy" value="my" v-model="bot.analyses">
            <label for="analyseMy">
                <fmt:message key="my"/>
            </label>
            <input type="radio" name="analyse" id="analyseAll" value="all" v-model="bot.analyses">
            <label for="analyseAll">
                <fmt:message key="all"/>
            </label>
        </td>
    </tr>
    <tr>
        <th colspan="2">
            <fmt:message key="subdivisionAnalyses"/>
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <input type="checkbox" name="subdivisionAnalyse" id="subdivisionExtraction" v-model="bot.extraction">
            <label for="subdivisionExtraction">
                <fmt:message key="extraction"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="checkbox" name="subdivisionAnalyse" id="subdivisionVRO" v-model="bot.vro">
            <label for="subdivisionVRO">
                <fmt:message key="vro"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="checkbox" name="subdivisionAnalyse" id="subdivisionKPO" v-model="bot.kpo">
            <label for="subdivisionKPO">
                <fmt:message key="kpo"/>
            </label>
        </td>
    </tr>
    
</table>
</html>