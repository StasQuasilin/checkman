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
        editor.vehicle.model = '${plan.transportation.vehicle.model}';
        editor.vehicle.number = '\'${plan.transportation.vehicle.number}\''
        editor.vehicle.trailer = ' \'${plan.transportation.vehicle.trailer}\''
    </c:if>
    <c:if test="${not empty plan.transportation.driver}">
        editor.driver = '${plan.transportation.driver.person.value}';
    </c:if>
    editor.empty={
        oiliness:0,
        humidity:0,
        soreness:0,
        oilImpurity:0,
        acidValue:0,
        contamination:false,
        creator:${worker.id}
    };
    <c:if test="${not empty plan.transportation.sunAnalyses}">
    <c:forEach items="${plan.transportation.sunAnalyses}" var="sun">
    editor.addAnalyses(
        {
            id:${sun.analyses.id},
            oiliness:${sun.analyses.oiliness},
            humidity:${sun.analyses.humidity},
            soreness:${sun.analyses.soreness},
            oilImpurity:${sun.analyses.oilImpurity},
            acidValue:${sun.analyses.acidValue},
            contamination:${sun.analyses.contamination},
            creator:${sun.analyses.createTime.creator.id}
        }
    );
    </c:forEach>
    </c:if>
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
    if (editor.analyses.length == 0){
        editor.newAnalyses();
    }
    editor.recount=function(){
        const basis = {
            humidity:7,
            soreness:3
        }
        var humidity = 0;
        var soreness = 0;
        var count = 0;
        for (var a in this.analyses){
            if (this.analyses.hasOwnProperty(a)){
                var analyses = this.analyses[a];
                humidity += analyses.humidity;
                soreness += analyses.soreness;
                count++;
            }
        }

        humidity /= count;
        soreness /= count;
        var recount = 0;
        if (humidity > basis.humidity && soreness > basis.soreness){
            recount = 100-((100-humidity)*(100-soreness)*100)/((100-basis.humidity)*(100-basis.soreness));
        } else if (humidity > basis.humidity) {
            recount = (humidity - basis.humidity)/(100 - basis.humidity)
        } else if (soreness > basis.soreness){
            recount = (soreness - basis.soreness) / (100 - basis.soreness);
        }
        return '-' + (Math.round(recount * 1000) / 1000) + '%';
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
            <div>
                <span>
                    {{vehicle.model}}
                </span>
                <div style="display: inline-block; font-size: 8pt">
                    <div>
                        {{vehicle.number}}
                    </div>
                    <div>
                        {{vehicle.trailer}}
                    </div>

                </div>
            </div>
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
        <tr>
            <td>
                <label for="contamination">
                    <fmt:message key="sun.contamination"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="contamination" type="checkbox" v-model="item.contamination">
            </td>
        </tr>
    </template>
    <template v-if="recount">
    <tr v-if="typeof recount === 'function'">
        <td>
            <label for="recount">
                <fmt:message key="recount.percentage"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="recount" readonly style="width: 7em" v-model="recount()">
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