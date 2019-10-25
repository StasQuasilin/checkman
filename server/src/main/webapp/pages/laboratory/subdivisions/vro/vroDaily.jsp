<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/daily.vue"></script>
<script>
    editor.api.save = '${save}';
    <c:forEach items="${turns}" var="t">
    editor.turns.push({
        number:${t.number},
        begin:'${t.begin}',
        end:'${t.end}',
        name:'<fmt:message key="turn"/> #${t.number}'
    });
    </c:forEach>
    editor.turns.sort(function(a, b){
        return a - b;
    });
    <c:choose>
    <c:when test="${not empty daily.id}">
    editor.daily = {
        id:${daily.id},
        date:new Date('${daily.turn.turn.date}').toISOString().substring(0, 10),
        turn:${daily.turn.turn.number},
        kernelHumidity:${daily.kernelHumidity},
        huskHumidity:${daily.huskHumidity},
        huskSoreness:${daily.huskSoreness},
        kernelPercent:${daily.kernelPercent},
        huskPercent:${daily.huskPercent},
    };
    </c:when>
    <c:otherwise>
    editor.daily = {
        date:new Date().toISOString().substring(0, 10),
        turn:-1,
        kernelHumidity:0,
        huskHumidity:0,
        huskSoreness:0,
        kernelPercent:0,
        huskPercent:0
    };
    </c:otherwise>
    </c:choose>

</script>
<table id="editor" class="editor">
    <tr>
        <td>
            <label for="date">
                <fmt:message key="date"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="date" readonly v-on:click="pickDate()"
                   v-model="new Date(daily.date).toLocaleDateString()" style="width: 7em">
        </td>
    </tr>
    <tr>
        <td>
            <label for="turn">
                <fmt:message key="turn"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="turn" style="width: 100%" v-model="daily.turn">
                <option disabled value="-1"><fmt:message key="need.select"/></option>
                <option v-for="turn in turns" :value="turn.number">
                    {{turn.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidityCore">
                <fmt:message key="kernel.humidity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidityCore" type="number" step="0.1" v-model="daily.kernelHumidity" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidityHusk">
                <fmt:message key="husk.humidity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidityHusk" type="number" step="0.1" v-model="daily.huskHumidity" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="huskSoreness">
                <fmt:message key="husk.soreness"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="huskSoreness" type="number" step="0.1" v-model="daily.huskSoreness" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="kernelPercentage">
                <fmt:message key="kernel.percent"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="kernelPercentage" type="number" step="0.01" v-model="daily.kernelPercent" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="huskPercentage">
                <fmt:message key="husk.percent"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="huskPercentage" type="number" step="0.01" v-model="daily.huskPercent" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()" class="close-button left-button">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save" class="save-button right-button">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>