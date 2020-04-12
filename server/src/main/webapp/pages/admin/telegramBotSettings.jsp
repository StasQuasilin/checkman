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
            save:function(chatId){
                const self = this;
                const id = chatId;
                PostApi(this.api.status, this.bots[chatId], function(a){
                    //self.bots.splice(id, 1, a);
                })
            },
            openSettings:function(index, open){
                for (let i in this.bots){
                    if (this.bots.hasOwnProperty(i)){
                        if (i == index){
                            this.bots[i].open = open;
                        } else {
                            this.bots[i].open = false;
                        }
                    }
                }
            },
            deleteSetting:function(key){
                const self = this;
                const id = key;
                PostApi(this.api.delete, {id:this.bots[key].id}, function(a){
                    if (a.status === 'success'){
                        self.bots.splice(id, 1);
                    }
                });
            }
        }
    });
    botSettings.api.generate = '${uidGenerator}';
    botSettings.api.status = '${botStatus}';
    botSettings.api.delete = '${botDelete}';
    <c:forEach items="${botSettings}" var="bot">
    botSettings.bots.push({
        id:${bot.id},
        telegramId:${bot.telegramId},
        title:'${bot.title}',
        transport:'${bot.transport}',
        weight:'${bot.weight}',
        analyses:'${bot.analyses}',
        extraction:${bot.extraction},
        vro:${bot.vro},
        kpo:${bot.kpo},
        show:${bot.show},
        manufactureReports:${bot.manufactureReports},
        roundReports:${bot.roundReport},
        open:false
    });
    </c:forEach>
</script>
<style>
    .bold{
        font-weight: bold;
        font-size: 12pt;
    }
</style>
<table id="settings" border="0">
    <tr>
        <th colspan="2">
            <fmt:message key="bot.settings"/>
        </th>
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
    <template v-for="(bot, key) in bots">
        <tr v-on:click="openSettings(key, !bot.open)">
            <td align="right" colspan="2" style="border: dashed orangered 1pt;
             background-color: lightgray; font-size: 10pt" :class="{bold : bot.open}">
                <span v-if="bot.title">
                    {{bot.title}}
                </span>
                <span v-else>
                    <fmt:message key="personal.chat"/>
                </span>
                <span v-if="bot.open">
                    &#9206;
                </span>
                <span v-else>
                    &#9207;
                </span>
            </td>
        </tr>
        <template v-if="bot.open">
            <tr>
                <td colspan="2">
                    <input id="show" type="checkbox" v-model="bot.show" v-on:change="save(key)">
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
                <th align="left">
                    <label for="transport">
                        <fmt:message key="transport"/>
                    </label>
                </th>
                <td>
                    <select id="transport" v-model="bot.transport" v-on:change="save(key)">
                        <option value="off">
                            <fmt:message key="none"/>
                        </option>
                        <option value="my">
                            <fmt:message key="my"/>
                        </option>
                        <option value="all">
                            <fmt:message key="all"/>
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <th align="left">
                    <label for="weight">
                        <fmt:message key="weights"/>
                    </label>
                </th>
                <td>
                    <select id="weight" v-model="bot.weight" v-on:change="save(key)">
                        <option value="off">
                            <fmt:message key="none"/>
                        </option>
                        <option value="my">
                            <fmt:message key="my"/>
                        </option>
                        <option value="all">
                            <fmt:message key="all"/>
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <th align="left">
                    <label for="analyses">
                        <fmt:message key="analyses"/>
                    </label>
                </th>
                <td>
                    <select id="analyses" v-model="bot.analyses" v-on:change="save(key)">
                        <option value="off">
                            <fmt:message key="none"/>
                        </option>
                        <option value="my">
                            <fmt:message key="my"/>
                        </option>
                        <option value="all">
                            <fmt:message key="all"/>
                        </option>
                    </select>
                </td>
            </tr>
            <tr>
                <th colspan="2">
                    <fmt:message key="subdivisionAnalyses"/>
                </th>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" name="subdivisionAnalyse" id="subdivisionExtraction"
                           v-model="bot.extraction" v-on:change="save(key)">
                    <label for="subdivisionExtraction">
                        <fmt:message key="extraction"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" name="subdivisionAnalyse" id="subdivisionVRO"
                           v-model="bot.vro" v-on:change="save(key)">
                    <label for="subdivisionVRO">
                        <fmt:message key="vro"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" name="subdivisionAnalyse" id="subdivisionKPO"
                           v-model="bot.kpo" v-on:change="save(key)">
                    <label for="subdivisionKPO">
                        <fmt:message key="kpo"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" name="manufactureReports" id="manufactureReports"
                           v-model="bot.manufactureReports" v-on:change="save(key)">
                    <label for="manufactureReports">
                        <fmt:message key="manufacture.reports"/>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="checkbox" name="roundReports" id="roundReports"
                           v-model="bot.roundReports" v-on:change="save(key)">
                    <label for="roundReports">
                        <span class="mini-close">
                            <fmt:message key="round.reports"/>
                        </span>
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <span class="mini-close" v-on:click="deleteSetting(key)">
                        &times; <fmt:message key="settings.delete"/>
                    </span>
                </td>
            </tr>
        </template>

    </template>

</table>
    <a onclick="loadModal('${formattingTest}')">Formatting test</a>
</html>