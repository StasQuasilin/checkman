<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratoryEdit.js"></script>
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
    <c:when test="${not empty plan.transportation.sunAnalyses.id}">
    editor.analyses = {
        id:${plan.transportation.sunAnalyses.id},
        oiliness:${plan.transportation.sunAnalyses.oiliness},
        humidity1:${plan.transportation.sunAnalyses.humidity1},
        humidity2:${plan.transportation.sunAnalyses.humidity2},
        soreness:${plan.transportation.sunAnalyses.soreness},
        oilImpurity:${plan.transportation.sunAnalyses.oilImpurity},
        acidValue:${plan.transportation.sunAnalyses.acidValue},
        contamination:${plan.transportation.sunAnalyses.contamination},
    }    
    </c:when>
    <c:otherwise>
    editor.analyses = {
        oiliness:0,
        humidity1:0,
        humidity2:0,
        soreness:0,
        oilImpurity:0,
        acidValue:0,
        contamination:false,
    }
    </c:otherwise>
    </c:choose>
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
    
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
                <fmt:message key="sun.humidity.1"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity1" step="0.01" type="number" v-model.number="analyses.humidity1">
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
            <label for="humidity2">
                <fmt:message key="sun.humidity.2"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity2" step="0.01" type="number" v-model.number="analyses.humidity2">
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
        <td colspan="2">
            <input id="soreness" step="0.01" type="number" v-model="analyses.soreness">
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
        <td colspan="2">
            <input id="oilImp" step="0.01" type="number" v-model="analyses.oilImpurity">
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
        <td colspan="2">
            <input id="oiliness" step="0.01" type="number" v-model="analyses.oiliness">
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
        <td colspan="2">
            <input id="acid" step="0.01" type="number" v-model="analyses.acidValue">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="right">
            <label for="contamination">
                <fmt:message key="sun.contamination"/>
            </label>
            <input id="contamination" type="checkbox" v-model="analyses.contamination">
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
        <tr>
            <td>
                <label for="creator">
                    <fmt:message key="laboratory.creator"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td colspan="2">
                <select id="creator" v-model="analyses.creator">
                    <option v-for="lab in laborants" :value="lab.id">
                        {{lab.value}}
                    </option>
                </select>
            </td>
        </tr>
    </template>
    <tr>
        <td colspan="3" align="right">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
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