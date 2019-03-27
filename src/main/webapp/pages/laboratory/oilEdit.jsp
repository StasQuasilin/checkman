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
    editor.driver = '${plan.transportation.driver.person.value}';
    </c:if>
    editor.empty={
        organoleptic:false,
        color:0,
        acidValue:0,
        peroxideValue:0,
        phosphorus:0,
        humidity:0,
        soap:0,
        wax:0
    }
    <c:if test="${not empty plan.transportation.oilAnalyses}">
    <c:forEach items="${plan.transportation.oilAnalyses}" var="oil">
    editor.addAnalyses(
        {
            id:${oil.analyses.id},
            organoleptic:${oil.analyses.organoleptic},
            color:${oil.analyses.color},
            acidValue:${oil.analyses.acidValue},
            peroxideValue:${oil.analyses.peroxideValue},
            phosphorus:${oil.analyses.phosphorus},
            humidity:${oil.analyses.humidity},
            soap:${oil.analyses.soap},
            wax:${oil.analyses.wax}
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
                <label for="organoleptic">
                    <fmt:message key="oil.organoleptic"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="organoleptic" type="checkbox" v-model="item.organoleptic" style="width: auto">
                <span v-if="item.organoleptic">
                    <fmt:message key="oil.organoleptic.match"/>
                </span>
                <span v-else>
                    <fmt:message key="oil.organoleptic.doesn't.match"/>
                </span>
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
                <input id="color" type="number" v-model="item.color"/>
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
                <input id="acid" type="number" step="0.01" v-model="item.acidValue"/>
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
                <input id="peroxide" type="number" step="0.01" v-model="item.peroxideValue"/>
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
                <input id="phosphorus" type="number" step="0.01" v-model="item.phosphorus"/>
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
                <input id="humidity" type="number" step="0.01" v-model="item.humidity"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="soap">
                    <fmt:message key="oil.soap"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="soap" type="number" step="0.01" v-model="item.soap"/>
            </td>
        </tr>
        <tr>
            <td>
                <label for="wax">
                    <fmt:message key="oil.wax"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="wax" type="number" step="0.01" v-model="item.wax"/>
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