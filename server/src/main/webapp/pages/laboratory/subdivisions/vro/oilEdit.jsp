<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/extractionOil.vue"></script>
<script>
    editor.api.save = '${save}';
    <c:forEach items="${turns}" var="turn">
    editor.turns.push({
        id:${turn.id},
        value:'<fmt:message key="turn"/> #${turn.number}',
        day:-${turn.begin gt turn.end}
    });
    </c:forEach>
    <c:choose>
    <c:when test="${not empty oil}">
    var date = new Date('${oil.turn.turn.date}');
    var num = ${oil.turn.turn.number};
    if (num == 2){
        date.setDate(date.getDate()  + 1);
    }
    editor.oil = {
        id:${oil.id},
        date: date.toISOString().substring(0, 10),
        turn: ${oil.turn.turn.number},
        acid:${oil.acid},
        peroxide:${oil.peroxide},
        phosphorus:${oil.phosphorus},
        color:${oil.color},
        impurity:${oil.impurity},
        humidity:${oil.humidity}
    };
    </c:when>
    <c:otherwise>
    editor.oil = {
        date : new Date().toISOString().substring(0, 10),
        turn : 1,
        acid:0,
        peroxide:0,
        phosphorus:0,
        color:0,
        impurity:0,
        humidity:0
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
            <input id="date" readonly style="width: 7em" v-on:click="datePicker"
                   v-model="new Date(oil.date).toLocaleDateString()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="time">
                <fmt:message key="turn"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="time" v-model="oil.turn">
                <option v-for="turn in turns" :value="turn.id">
                    {{turnDate(turn.day)}}
                    {{turn.value}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="acid">
                <fmt:message key="sun.acid.value"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="acid" type="number" step="0.01" v-model="oil.acid" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="peroxide">
                <fmt:message key="oil.peroxide"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="peroxide" type="number" step="0.01" v-model="oil.peroxide" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="phosphorus">
                <fmt:message key="oil.phosphorus"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="phosphorus" type="number" step="0.01" v-model="oil.phosphorus" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="color">
                <fmt:message key="oil.color.value"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="color" type="number" step="1" v-model="oil.color" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="impurity">
                <fmt:message key="oil.degrease.impurity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="impurity" type="number" step="0.01" v-model="oil.impurity" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidity">
                <fmt:message key="extraction.crude.humidity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity" type="number" step="0.01" v-model="oil.humidity" onfocus="this.select()" autocomplete="off">
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