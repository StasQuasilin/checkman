<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratoryEdit.js"></script>
<script>
    editor.api.save = '${saveUrl}';
    editor.plan = ${plan.id};
    editor.organisation = '${plan.deal.organisation.value}';
    <c:if test="${not empty plan.transportation.vehicle}">
        editor.vehicle = '${plan.transportation.vehicle.model} \'${plan.transportation.vehicle.number}\' \'${plan.transportation.vehicle.trailer}\'';
    </c:if>
    <c:if test="${not empty plan.transportation.driver}">
        editor.driver = '${plan.transportation.driver.sun.value}';
    </c:if>
    editor.empty={
        oilines:0,
        humidity:0,
        soreness:0,
        oilImpurity:0,
        acidValue:0
    }
    <c:if test="${not empty plan.transportation.sunAnalyses}">
    <c:forEach items="${plan.transportation.sunAnalyses}" var="oil">
    editor.addAnalyses(
        {
            id:${oil.analyses.id},
            oiliness:${oil.analyses.oiliness},
            humidity:${oil.analyses.humidity},
            soreness:${oil.analyses.soreness},
            oilImpurity:${oil.analyses.oilImpurity},
            acidValue:${oil.analyses.acidValue}
        }
    );
    </c:forEach>
    </c:if>
    if (editor.analyses.length == 0){
        editor.newAnalyses();
    }
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
            {{vehicle}}
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
    <template v-for="item in analyses">
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
                <input id="humidity" step="0.01" type="number" v-model="item.humidity">
            </td>
        </tr>
        <tr>
            <td>
                <label for="soreness">
                    <fmt:message key="sun.soreness"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="soreness" step="0.01" type="number" v-model="item.soreness">
            </td>
        </tr>
        <tr>
            <td>
                <label for="oilImp">
                    <fmt:message key="sun.oil.impurity"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="oilImp" step="0.01" type="number" v-model="item.oilImpurity">
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
                <input id="oiliness" step="0.01" type="number" v-model="item.oiliness">
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
                <input id="acid" step="0.01" type="number" v-model="item.acidValue">
            </td>
        </tr>
    </template>

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