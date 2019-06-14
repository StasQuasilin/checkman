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
    editor.empty={
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
            wax:${oil.analyses.wax},
            creator:${oil.analyses.createTime.creator.id}

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
            <td style="width: 9em">
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
                <input id="soap" type="checkbox" v-model="item.soap"/>
                <span v-if="item.soap">
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
                <input id="wax" type="number" step="0.01" v-model="item.wax"/>
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
            <a class="mini-close" v-on:click="print">
                <fmt:message key="document.print"/>
            </a>
        </td>
    </tr>
</table>
</html>