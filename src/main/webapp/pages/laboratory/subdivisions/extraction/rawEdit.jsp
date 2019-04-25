<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/extractionCrude.js"></script>
<script>
    editor.api.save = '${saveUrl}';
    editor.times = [
        {
            hour:'08',
            minute:30
        },
        {
            hour:'10',
            minute:30
        },
        {
            hour:'12',
            minute:30
        },
        {
            hour:'14',
            minute:30
        },
        {
            hour:'16',
            minute:30
        },
        {
            hour:'18',
            minute:30
        },
        {
            hour:'20',
            minute:30
        },
        {
            hour:'22',
            minute:30
        },
        {
            hour:'00',
            minute:30
        },
        {
            hour:'02',
            minute:30
        },
        {
            hour:'04',
            minute:30
        },
        {
            hour:'06',
            minute:30
        }
    ]
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
    <c:choose>
    <c:when test="${not empty crude}">
    </c:when>
    <c:otherwise>
    editor.crude = {
        date : new Date().toISOString().substring(0, 10),
        time : editor.currentTime(),
        protein:0,
        cellulose:0,
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
            <input id="date" readonly style="width: 7em" v-on:click="datePicker"
                   v-model="new Date(crude.date).toLocaleDateString()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="time">
                <fmt:message key="time"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="time" v-model="crude.time">
                <option v-for="time in times">
                    {{time.hour}}:{{time.minute}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="protein">
                <fmt:message key="cake.protein"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="protein" type="number" step="0.1" autocomplete="off" v-model="crude.protein">
        </td>
    </tr>
    <tr>
        <td>
            <label for="cellulose">
                <fmt:message key="cake.cellulose"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="cellulose" type="number" step="0.1" autocomplete="off" v-model="crude.cellulose">
        </td>
    </tr>
    <tr>
        <td>
            <label for="creator">
                <fmt:message key="laboratory.creator"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="creator" v-model="crude.creator" style="width: 100%">
                <option v-for="laborant in laborants" :value="laborant.id">{{laborant.value}}</option>
            </select>
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