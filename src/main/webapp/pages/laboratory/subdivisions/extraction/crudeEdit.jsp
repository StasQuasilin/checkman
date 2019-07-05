<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/datepick.vue"></script>
<link rel="stylesheet" href="${context}/css/date-picker.css">
<script src="${context}/vue/laboratory/extractionCrude.vue"></script>
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
    <c:choose>
    <c:when test="${not empty crude}">
    editor.crude = {
        id : ${crude.id},
        date : new Date('${crude.turn.turn.date}').toISOString().substring(0, 10),
        time : editor.currentTime(new Date('${crude.time}')),
        humidityIncome:${crude.humidityIncome},
        fraction:${crude.fraction},
        miscellas:${crude.miscellas},
        humidity:${crude.humidity},
        dissolvent:${crude.dissolvent},
        grease:${crude.grease},
        creator:${crude.creator.id}
    };
    </c:when>
    <c:otherwise>
    editor.crude = {
        date : new Date().toISOString().substring(0, 10),
        time : editor.currentTime(),
        humidityIncome:0,
        fraction:0,
        miscellas:0,
        humidity:0,
        dissolvent:0,
        grease:0,
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
            <label for="humidityIncome">
                <fmt:message key="extraction.crude.humidity.income"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidityIncome" type="number" v-model="crude.humidityIncome" step="0.1" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="smallFraction">
                <fmt:message key="extraction.crude.small.fraction"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="smallFraction" type="number" v-model="crude.fraction" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="miscellas">
                <fmt:message key="extraction.crude.miscellas"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="miscellas" type="number" v-model="crude.miscellas" step="0.01" autocomplete="off">
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
            <input id="humidity" type="number" v-model="crude.humidity" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="dissolvent">
                <fmt:message key="extraction.crude.dissolvent"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="dissolvent" type="number" v-model="crude.dissolvent" step="0.0001" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="grease">
                <fmt:message key="extraction.crude.grease"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="grease" type="number" v-model="crude.grease" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="right">
            <a class="mini-close" v-if="crude.id">
                <fmt:message key="button.delete"/>
            </a>
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