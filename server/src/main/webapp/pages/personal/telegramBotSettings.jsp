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
            bots:[],
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
                var bots = [];
                for (var i in this.bots){
                    if (this.bots.hasOwnProperty(i)){
                        bots.push(this.bots[i].id);
                    }
                }
                PostApi(this.api.status, {bots:bots}, function(a){
                    self.bot.id= a.id;
                    self.bot.transport = a.transport;
                    self.bot.weight = a.weight;
                    self.bot.analyses = a.analyses;
                    self.bot.extraction = a.extraction;
                    self.bot.vro = a.vro;
                    self.bot.kpo = a.kpo;
                    self.bot.show = a.show;
                    self.online = true;
                })
            },
            save:function(chatId){

            }
        }
    });
    botSettings.api.generate = '${uidGenerator}';
    botSettings.api.status = '${botStatus}';
    <c:forEach items="${botSettings}" var="bot">
    botSettings.bots.push({
        id:${bot.telegramId},
        title:'${bot.title}',
        transport:'${bot.transport}',
        weight:'${bot.weight}',
        analyses:'${bot.analyses}',
        extraction:${bot.extraction},
        vro:${bot.vro},
        kpo:${bot.kpo},
        show:${bot.show},
        reports:${bot.reports}
    });
    </c:forEach>
    <%--<c:set var="object" value="${botSettings}" />--%>
    <%--<c:if test="${not empty object['class'].declaredFields}">--%>
        <%--<c:forEach var="field" items="${object['class'].declaredFields}">--%>
            <%--'${field.name}: ${object[field.name]}'--%>
        <%--</c:forEach>--%>
    <%--</c:if>--%>
//    botSettings.status();
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
    <template v-for="bot in bots">
        <tr v-if="bot.title">
            <th colspan="2" style="border-bottom: dashed orangered 2pt;
            color: orangered">
               {{bot.title}}
            </th>
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
    </template>
</table>
</html>