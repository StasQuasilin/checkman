<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/daily.js"></script>
<script>
    editor.api.save = '${save}';
    <c:forEach items="${turns}" var="t">
    editor.turns.push({
        number:${t.number},
        begin:'${t.begin}',
        end:'${t.end}'
    });
    </c:forEach>
    editor.turns.sort(function(a, b){
        return a - b;
    });
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
    '!${daily}'
    <c:choose>
    <c:when test="${not empty daily.id}">
    editor.daily = {
        id:${daily.id},
        date:new Date(${daily.turn.date}).toISOString().substring(0, 10),
        kernelHumidity:${daily.kernelHumidity},
        huskHumidity:${daily.huskHumidity},
        huskSoreness:${daily.huskSoreness},
        kernelPercent:${daily.kernelPercent},
        huskPercent:${daily.huskPercent},
        creator:${daily.creator.id}
    };
    </c:when>
    <c:otherwise>
    editor.daily = {
        date:new Date().toISOString().substring(0, 10),
        kernelHumidity:0,
        huskHumidity:0,
        huskSoreness:0,
        kernelPercent:0,
        huskPercent:0,
        creator:${worker.id}
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
            <input id="date" readonly v-on:click="pickDate()" v-model="new Date(daily.date).toLocaleDateString()">
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <span v-if="turns.length > 0" style="font-size: 10pt">
                <fmt:message key="period"/>: {{info()}}
            </span>
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
            <input id="humidityCore" type="number" step="0.1" v-model="daily.kernelHumidity">
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
            <input id="humidityHusk" type="number" step="0.1" v-model="daily.huskHumidity">
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
            <input id="huskSoreness" type="number" step="0.1" v-model="daily.huskSoreness">
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
            <input id="kernelPercentage" type="number" step="0.01" v-model="daily.kernelPercent">
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
            <input id="huskPercentage" type="number" step="0.01" v-model="daily.huskPercent">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>