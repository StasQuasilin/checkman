<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/daily.vue"></script>
<script>
    editor.api.save = '${save}';
//    TURNS
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
//    FORPRESS
    <c:forEach items="${forpress}" var="fp">
    editor.forpress.push({
        id:${fp.id},
        name:'${fp.name}'
    });
    </c:forEach>

//    BODY
    <c:choose>
    <c:when test="${not empty daily}">

    </c:when>
    <c:otherwise>
    editor.daily = {
        date:new Date().toISOString().substring(0, 10),
        turn:-1,
        seedWet:0,
        seedHumidity:0,
        seedDry:0,
        huskWet:0,
        huskHumidity:0,
        huskDry:0,
        forpress:[]
    };
    </c:otherwise>
    </c:choose>
</script>
<link rel="stylesheet" href="${context}/css/editor.css">
<table id="editor" class="editor">
    <c:set var="wetIndicator"><fmt:message key="wet.indicator"/></c:set>
    <c:set var="humidity"><fmt:message key="sun.humidity"/></c:set>
    <c:set var="dryIndicator"><fmt:message key="dry.indicator"/></c:set>
    <c:set var="forpress"><fmt:message key="forpress"/></c:set>
    <tr>
        <td>
            <label for="date">
                <fmt:message key="date"/>
            </label>
        </td>
        <td colspan="3">
            <input id="date" readonly v-on:click="pickDate()"
                   v-model="new Date(daily.date).toLocaleDateString()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="turn">
                <fmt:message key="turn"/>
            </label>
        </td>
        <td colspan="3">
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
            &nbsp;
        </td>
        <th style="font-size: 10pt">
            <fmt:message key="wet.indicator.br"/>
        </th>
        <th style="font-size: 10pt">
            <fmt:message key="sun.humidity"/>
        </th>
        <th style="font-size: 10pt">
            <fmt:message key="dry.indicator.br"/>
        </th>
    </tr>
    <tr>
        <td>
            <fmt:message key="oil.mass.fraction.seed"/>
        </td>
        <td>
            <input title="${wetIndicator}" type="number" step="0.01"
                   autocomplete="off" v-model="daily.seedWet"  onclick="this.select()">
        </td>
        <td>
            <input title="${humidity}" type="number" step="0.01"
                   autocomplete="off" v-model="daily.seedHumidity" onclick="this.select()">
        </td>
        <td>
            <input title="${dryIndicator}" type="number" step="0.01"
                   autocomplete="off" v-model="daily.seedDry" onclick="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="oil.mass.fraction.husk"/>
        </td>
        <td>
            <input title="${wetIndicator}" type="number" step="0.01"
                   autocomplete="off" v-model="daily.huskWet" onclick="this.select()">
        </td>
        <td>
            <input title="${humidity}" type="number" step="0.01"
                   autocomplete="off"  v-model="daily.huskHumidity" onclick="this.select()">
        </td>
        <td>
            <input title="${dryIndicator}" type="number" step="0.01"
                   autocomplete="off" v-model="daily.huskDry" onclick="this.select()">
        </td>
    </tr>
  <tr>
    <th align="right" colspan="4">
        <span v-if="daily.forpress.length < forpress.length" class="mini-close" v-on:click="addForpress">
        +
        </span>
      <fmt:message key="forpress"/>
    </th>
  </tr>
    <template v-for="(value, key) in daily.forpress">
        <tr>
            <td>
                <span class="mini-close" style="padding: 0" v-on:click="removeForpress(key)">
                    &times;
                </span>
                <select title="${forpress}" id="forpress" v-model="value.forpress">
                    <option v-for="f in forpress" :value="f.id">{{f.name}}</option>
                </select>
            </td>
            <td>
                <input title="${wetIndicator}" type="number" step="0.01"
                       autocomplete="off" v-model="value.wet" onclick="this.select()">
            </td>
            <td>
                <input title="${humidity}" type="number" step="0.01"
                       autocomplete="off" v-model="value.humidity" onclick="this.select()">
            </td>
            <td>
                <input title="${dryIndicator}" type="number" step="0.01"
                       autocomplete="off" v-model="value.dry" onclick="this.select()">
            </td>
        </tr>
    </template>
  <tr>
    <td colspan="4" align="center">
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
