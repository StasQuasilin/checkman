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
            uid:''
        },
        methods:{
            generate:function(){
                const self = this;
                PostApi(this.api.generate, null, function(a){
                    self.uid = a.uid;
                })
            }
        }
    });
    botSettings.api.generate = '${uidGenerator}';
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
            <span style="color: red">
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
        <th colspan="2">
            <fmt:message key="transport"/>
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <input type="radio" name="transport" id="transportNone">
            <label for="transportNone">
                <fmt:message key="none"/>
            </label>
            <input type="radio" name="transport" id="transportMy">
            <label for="transportMy">
                <fmt:message key="my"/>
            </label>
            <input type="radio" name="transport" id="transportAll">
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
            <input type="radio" name="weight" id="weightNone">
            <label for="weightNone">
                <fmt:message key="none"/>
            </label>
            <input type="radio" name="weight" id="weightMy">
            <label for="weightMy">
                <fmt:message key="my"/>
            </label>
            <input type="radio" name="weight" id="weightAll">
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
            <input type="radio" name="analyse" id="analyseNone">
            <label for="analyseNone">
                <fmt:message key="none"/>
            </label>
            <input type="radio" name="analyse" id="analyseMy">
            <label for="analyseMy">
                <fmt:message key="my"/>
            </label>
            <input type="radio" name="analyse" id="analyseAll">
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
            <input type="checkbox" name="subdivisionAnalyse" id="subdivisionAnalyseNone">
            <label for="subdivisionAnalyseNone">
                <fmt:message key="extraction"/>
            </label>
        </td>
    </tr>
    <tr>
        <td>
            <input type="checkbox" name="subdivisionAnalyse" id="subdivisionAnalyseMy">
            <label for="subdivisionAnalyseMy">
                <fmt:message key="vro"/>
            </label>
        </td>
    </tr>
    <tr>
        <td>
            <input type="checkbox" name="subdivisionAnalyse" id="subdivisionAnalyseAll">
            <label for="subdivisionAnalyseAll">
                <fmt:message key="kpo"/>
            </label>
        </td>
    </tr>
</table>
</html>