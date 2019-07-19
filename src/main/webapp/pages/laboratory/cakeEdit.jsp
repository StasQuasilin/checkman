<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/laboratoryEdit.vue"></script>
<script>
    editor.api.save = '${save}';
    editor.api.print = '${print}';
    editor.plan = ${plan.id};
    editor.organisation = '${plan.deal.organisation.value}';
    <c:if test="${not empty plan.transportation.vehicle}">
    editor.vehicle.model = '${plan.transportation.vehicle.model}';
    editor.vehicle.number = '\'${plan.transportation.vehicle.number}\''
    editor.vehicle.trailer = ' \'${plan.transportation.vehicle.trailer}\''
    </c:if>
    <c:if test="${not empty plan.transportation.driver}">
    editor.driver = '${plan.transportation.driver.person.value}';
    </c:if>
    <c:choose>
    <c:when test="${not empty plan.transportation.mealAnalyses.id}">
    editor.analyses = {
        id:${plan.transportation.mealAnalyses.id},
        humidity:${plan.transportation.mealAnalyses.humidity},
        protein:${plan.transportation.mealAnalyses.protein},
        cellulose:${plan.transportation.mealAnalyses.cellulose},
        oiliness:${plan.transportation.mealAnalyses.oiliness},
        creator:${plan.transportation.mealAnalyses.createTime.creator.id}
    };
    </c:when>
    <c:otherwise>
    editor.analyses = {
        humidity:0,
        protein:0,
        cellulose:0,
        oiliness:0,
        creator:${worker.id}
    };
    </c:otherwise>
    </c:choose>
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
</script>
<table id="editor" class="editor">
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            :
        </td>
        <td>
            {{organisation}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.automobile"/>
        </td>
        <td>
            :
        </td>
        <td>
            <table>
                <tr>
                    <td rowspan="2">
                        <span style="font-size: 18pt">
                            {{vehicle.model}}
                        </span>
                    </td>
                    <td>
                        <span style="font-size: 10pt">
                            {{vehicle.number}}
                        </span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="font-size: 10pt">
                            {{vehicle.trailer}}
                        </span>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.driver"/>
        </td>
        <td>
            :
        </td>
        <td>
            {{driver}}
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
            <input id="humidity" type="number" step="0.01" v-model="analyses.humidity">
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
            <input id="protein" type="number" step="0.01" v-model="analyses.protein">
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
            <input id="cellulose" type="number" step="0.01" v-model="analyses.cellulose">
        </td>
    </tr>
    <tr>
        <td>
            <label for="oiliness">
                <fmt:message key="sun.oiliness"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="oiliness" type="number" step="0.01" v-model="analyses.oiliness">
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
            <select id="creator" style="width: 100%" v-model="analyses.creator">
                <option v-for="laborant in laborants" :value="laborant.id">{{laborant.value}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="right">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save" v-on:dblclick="save">
                <fmt:message key="button.save"/>
            </button>
            <span class="mini-close" v-on:click="print">
                <fmt:message key="document.print"/>
            </span>
        </td>
    </tr>

</table>
</html>