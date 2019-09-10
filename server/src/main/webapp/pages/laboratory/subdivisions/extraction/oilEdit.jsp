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
        number:${turn.number},
        value:'<fmt:message key="turn"/> #${turn.number}',
        <c:choose>
        <c:when test="${turn.begin lt turn.end}">
        day:0
        </c:when>
        <c:otherwise>
        day:-1
        </c:otherwise>
        </c:choose>
    });
    </c:forEach>
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
    <c:choose>
    <c:when test="${not empty oil}">
    editor.oil = {
        date : new Date('${oil.turn.turn.date}').toISOString().substring(0, 10),
        turn : ${oil.turn.turn.number},
        humidity:${oil.humidity},
        acid:${oil.acid},
        peroxide:${oil.peroxide},
        phosphorus:${oil.phosphorus},
        explosion:${oil.explosionT}
    };
    </c:when>
    <c:otherwise>
    editor.oil = {
        date : new Date().toISOString().substring(0, 10),
        turn : -1,
        humidity:0,
        acid:0,
        peroxide:0,
        phosphorus:0,
        explosion:0
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
                <option v-for="turn in turns" :value="turn.number">
                    {{turnDate(turn.day)}}
                    {{turn.value}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidity">
                <fmt:message key="sun.humidity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity" type="number" step="0.01" autocomplete="off" v-model="oil.humidity">
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
            <input id="acid" type="number" step="0.01" autocomplete="off" v-model="oil.acid">
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
            <input id="peroxide" type="number" step="0.01" autocomplete="off" v-model="oil.peroxide">
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
            <input id="phosphorus" type="number" step="0.01" autocomplete="off" v-model="oil.phosphorus">
        </td>
    </tr>
    <tr>
        <td>
            <label for="explosion">
                <fmt:message key="extraction.oil.explosion"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="explosion" type="number" step="0.01" autocomplete="off" v-model="oil.explosion">
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