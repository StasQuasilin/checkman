<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css?v=${now}">
<script src="${context}/vue/laboratory/laboratoryEdit.vue?v=${now}"></script>
<script>
    editor.api.save = '${save}';
    editor.api.print = '${print}';
    editor.type = '${type}';
    editor.plan = ${plan.id};
    editor.organisation = '';
    <c:if test="${not empty plan.vehicle}">
    editor.vehicle.model = '${plan.vehicle.model}';
    editor.vehicle.number = '\'${plan.vehicle.number}\'';
    editor.vehicle.trailer = ' \'${plan.vehicle.trailer.number}\'';
    </c:if>
    <c:if test="${not empty plan.driver}">
    editor.driver = '${plan.driver.person.value}';
    </c:if>
    <c:choose>
    <c:when test="${not empty plan.oilAnalyses.id}">
    editor.analyses = {
        id:${plan.oilAnalyses.id},
        organoleptic:${plan.oilAnalyses.organoleptic},
        color:${plan.oilAnalyses.color},
        acidValue:${plan.oilAnalyses.acidValue},
        peroxideValue:${plan.oilAnalyses.peroxideValue},
        phosphorus:${plan.oilAnalyses.phosphorus},
        humidity:${plan.oilAnalyses.humidity},
        degreaseImpurity:${plan.oilAnalyses.degreaseImpurity},
        transparency:${plan.oilAnalyses.transparency},
        benzopyrene:${plan.oilAnalyses.benzopyrene},
        explosion:${plan.oilAnalyses.explosion}
    };
    </c:when>
    <c:otherwise>
    editor.analyses = {
        organoleptic:false,
        color:0,
        acidValue:0,
        peroxideValue:0,
        phosphorus:0,
        humidity:0,
        degreaseImpurity:0,
        transparency:0,
        benzopyrene:0,
        explosion:0
    };
    </c:otherwise>
    </c:choose>
    Vue.set(editor.helpers, 'types', [
        {
            id:1,
            value:'<fmt:message key="press.oil"/>'
        },
        {
            id:2,
            value:'<fmt:message key="mix.oil"/>'
        }
    ]);
    var t = editor.analyses.explosion > 0 ? 2 : 1;
    Vue.set(editor.helpers, 'oilType', t);
</script>
<table id="editor" class="editor">
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            :
        </td>
        <td class="secure">
            ${plan.counterparty.value}
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
                    <td style="font-size: 8pt" class="secure">
                        {{vehicle.number}}
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 8pt" class="secure">
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
            ${plan.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="oil.organoleptic"/>
        </td>
        <td>
            :
        </td>
        <td style="width: 9em">
            <b v-on:click="analyses.organoleptic = !analyses.organoleptic">
                <a v-if="analyses.organoleptic">
                    <fmt:message key="oil.organoleptic.match"/>
                </a>
                <a v-else>
                    <fmt:message key="oil.organoleptic.doesn't.match"/>
                </a>
            </b>
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
            <input id="color" type="number" v-model="analyses.color" onfocus="this.select()">
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
            <input id="acid" type="number" step="0.01" v-model="analyses.acidValue" onfocus="this.select()">
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
            <input id="peroxide" type="number" step="0.01" v-model="analyses.peroxideValue" onfocus="this.select()">
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
            <input id="phosphorus" type="number" step="0.01" v-model="analyses.phosphorus" onfocus="this.select()">
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
            <input id="humidity" type="number" step="0.01" v-model="analyses.humidity" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="degreaseImpurity">
                <fmt:message key="oil.degrease.impurity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="degreaseImpurity" type="number" step="0.01"
                   autocomplete="off" v-model="analyses.degreaseImpurity" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="transparency">
                <fmt:message key="oil.transparency"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="transparency" type="number" step="0.01" onfocus="this.select()"
                   autocomplete="off" v-model="analyses.transparency">
        </td>
    </tr>
    <tr>
        <td>
            <label for="benzopyrene">
                <fmt:message key="oil.benzopyrene"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="benzopyrene" type="number" step="0.001" onfocus="this.select()"
                autocomplete="off" v-model="analyses.benzopyrene">
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <select v-model="helpers.oilType" style="width: 100%">
                <option v-for="t in helpers.types" :value="t.id">
                    {{t.value}}
                </option>
            </select>
        </td>
    </tr>
    <tr v-if="helpers.oilType == 2">
        <td>
            <label for="explosion">
                <fmt:message key="extraction.oil.explosion"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="explosion" type="number" step="0.1"  onfocus="this.select()"
                   autocomplete="off" v-model="analyses.explosion">
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