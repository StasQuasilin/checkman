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
    editor.vehicle.number = '\'${plan.transportation.vehicle.number}\'';
    editor.vehicle.trailer = ' \'${plan.transportation.vehicle.trailer}\'';
    </c:if>
    <c:if test="${not empty plan.transportation.driver}">
    editor.driver = '${plan.transportation.driver.person.value}';
    </c:if>
    <c:choose>
    <c:when test="${not empty plan.transportation.oilAnalyses.id}">
    editor.analyses =
    {
        id:${plan.transportation.oilAnalyses.id},
        organoleptic:${plan.transportation.oilAnalyses.organoleptic},
        color:${plan.transportation.oilAnalyses.color},
        acidValue:${plan.transportation.oilAnalyses.acidValue},
        peroxideValue:${plan.transportation.oilAnalyses.peroxideValue},
        phosphorus:${plan.transportation.oilAnalyses.phosphorus},
        humidity:${plan.transportation.oilAnalyses.humidity},
        soap:${plan.transportation.oilAnalyses.soap},
        wax:${plan.transportation.oilAnalyses.wax},
        creator:${plan.transportation.oilAnalyses.createTime.creator.id}

    };
    </c:when>
    <c:otherwise>
    editor.analyses =
    {
        organoleptic:false,
        color:0,
        acidValue:0,
        peroxideValue:0,
        phosphorus:0,
        humidity:0,
        soap:false,
        wax:0,
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
                    <td rowspan="2" style="font-size: 18pt">
                        {{vehicle.model}}
                    </td>
                    <td style="font-size: 8pt">
                        {{vehicle.number}}
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 8pt">
                        {{vehicle.trailer}}
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
            <fmt:message key="deal.product"/>
        </td>
        <td>
            :
        </td>
        <td>
            ${plan.deal.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <label for="organoleptic">
                <fmt:message key="oil.organoleptic"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td style="width: 9em">
            <input id="organoleptic" type="checkbox" v-model="analyses.organoleptic" style="width: auto">
                <span v-if="analyses.organoleptic">
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
            <input id="color" type="number" v-model="analyses.color"/>
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
            <input id="acid" type="number" step="0.01" v-model="analyses.acidValue"/>
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
            <input id="peroxide" type="number" step="0.01" v-model="analyses.peroxideValue"/>
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
            <input id="phosphorus" type="number" step="0.01" v-model="analyses.phosphorus"/>
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
            <input id="humidity" type="number" step="0.01" v-model="analyses.humidity"/>
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
            <input id="soap" type="checkbox" v-model="analyses.soap"/>
                <span v-if="analyses.soap">
                    <fmt:message key="notification.kpo.soap.yes"/>
                </span>
                <span v-else>
                    <fmt:message key="notification.kpo.soap.no"/>
                </span>
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
            <input id="wax" type="number" step="0.01" v-model="analyses.wax"/>
        </td>
    </tr>

    <tr>
        <td colspan="3" align="right">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
            <a class="mini-close" v-on:click="print">
                <fmt:message key="document.print"/>
            </a>
        </td>
    </tr>
</table>
</html>