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
    editor.type = '${type}';
    editor.plan = ${plan.id};
    editor.organisation = '${plan.counterparty.value}';
    <c:if test="${not empty plan.vehicle}">
        editor.vehicle.model = '${plan.vehicle.model}';
        editor.vehicle.number = '\'${plan.vehicle.number}\'';
        editor.vehicle.trailer = ' \'${plan.vehicle.trailer.number}\'';
    </c:if>
    <c:if test="${not empty plan.driver}">
        editor.driver = '${plan.driver.person.value}';
    </c:if>

    <c:choose>
    <c:when test="${not empty plan.sunAnalyses.id}">
    editor.analyses = {
        id:${plan.sunAnalyses.id},
        oiliness:${plan.sunAnalyses.oiliness},
        humidity1:${plan.sunAnalyses.humidity1},
        humidity2:${plan.sunAnalyses.humidity2},
        soreness:${plan.sunAnalyses.soreness},
        oilImpurity:${plan.sunAnalyses.oilImpurity},
        acidValue:${plan.sunAnalyses.acidValue},
        contamination:${plan.sunAnalyses.contamination}
    };
    </c:when>
    <c:otherwise>
    editor.analyses = {
        oiliness:0,
        humidity1:0,
        humidity2:0,
        soreness:0,
        oilImpurity:0,
        acidValue:0,
        contamination:false
    };
    </c:otherwise>
    </c:choose>
    editor.recount=function(){
        const basis = {
            humidity:7,
            soreness:3
        };
        var humidity = (editor.analyses.humidity1 > 0 || editor.analyses.humidity2 > 0 ? (
            (editor.analyses.humidity1 + editor.analyses.humidity2) / ((editor.analyses.humidity1 > 0 ? 1 : 0) + (editor.analyses.humidity2 > 0 ? 1 : 0))
        ) : 0);
        var soreness = editor.analyses.soreness;
        

        var recount = 0;
        if (humidity > basis.humidity && soreness > basis.soreness){
            recount = 100-((100-humidity)*(100-soreness)*100)/((100-basis.humidity)*(100-basis.soreness));
        } else if (humidity > basis.humidity) {
            recount = (humidity - basis.humidity)*100 / (100 - basis.humidity)
        } else if (soreness > basis.soreness){
            recount = (soreness - basis.soreness)*100 / (100 - basis.soreness);
        }
        return (Math.round(recount * 1000) / 1000);
    }
</script>
<table id="editor" class="editor" border="0">
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            {{organisation}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.product"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            ${plan.product.name}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.automobile"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            <table border="0">
                <tr>
                    <td rowspan="2" style="font-size: 14pt">
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
        <td colspan="2">
            {{driver}}
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidity1">
                <fmt:message key="sun.humidity.1.short"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity1" step="0.01" type="number" v-model.number="analyses.humidity1" onfocus="this.select()">
        </td>
        <td rowspan="2" style="border-left: solid darkgray 1pt; width: 7em" align="center">
            <div style="font-size: 8pt">
                <fmt:message key="mean.short"/>
            </div>
            <div>
                {{(
                (analyses.humidity1 > 0 || analyses.humidity2 > 0 ?
                (analyses.humidity1 + analyses.humidity2) /
                ((analyses.humidity1 > 0 ? 1 : 0) + (analyses.humidity2 > 0 ? 1 : 0)) : 0)
                ).toLocaleString()}} %
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="sun.humidity.2.short"/>
        </td>
        <td>
            :
        </td>
        <td>
            {{analyses.humidity2.toLocaleString()}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="sun.soreness"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            {{analyses.soreness.toLocaleString()}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="sun.oil.impurity"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            {{analyses.oilImpurity.toLocaleString()}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="sun.oiliness"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            {{analyses.oiliness.toLocaleString()}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="sun.acid.value"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            {{analyses.acidValue.toLocaleString()}}
        </td>
    </tr>
    <tr v-if="analyses.contamination">
        <td colspan="3" align="right">
            <span>
                <fmt:message key="sun.contamination"/>
            </span>
        </td>
    </tr>
    <template v-if="recount()">
        <tr>
            <td>
                <fmt:message key="recount.percentage"/>
            </td>
            <td>
                :
            </td>
            <td colspan="2">
                <span style="background-color: white; padding: 0 6pt; border: inset 2px lightgray; border-radius: 8pt; font-size: 10pt">
                    {{recount() + ' %'}}
                </span>
            </td>
        </tr>
    </template>
    <tr>
        <td colspan="3" align="right">
            <button onclick="closeModal()">
                <fmt:message key="button.close"/>
            </button>
        </td>
        <td align="right">
            <a class="mini-close" v-on:click="print" v-if="recount()">
                <fmt:message key="document.print"/>
            </a>
        </td>
    </tr>
</table>
</html>