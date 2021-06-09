<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/laboratoryEdit.vue?v=${now}"></script>
<script src="${context}/vue/laboratory/sunAnalysesEdit.vue?v=${now}"></script>
<script>
    sunEditor.api.save = '${save}';
    sunEditor.api.print = '${print}';
    sunEditor.type = '${type}';
    sunEditor.plan = ${plan.id};
    sunEditor.organisation = '${plan.counterparty.value}';
    <c:if test="${not empty plan.vehicle}">
        sunEditor.vehicle.model = '${plan.vehicle.model}';
        sunEditor.vehicle.number = '\'${plan.vehicle.number}\'';
        sunEditor.vehicle.trailer = ' \'${plan.vehicle.trailer.number}\'';
    </c:if>
    <c:if test="${not empty plan.driver}">
        sunEditor.driver = '${plan.driver.person.value}';
    </c:if>
    <c:forEach items="${plan.products}" var="p">
    sunEditor.addProducts(${p.toJson()});
    </c:forEach>
</script>
<table id="editor" class="editor">
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
            <fmt:message key="transportation.automobile"/>
        </td>
        <td>
            :
        </td>
        <td colspan="2">
            <table>
                <tr>
                    <td rowspan="2" style="font-size: 14pt">
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
    <template v-for="p in products">
<%--        COUNTERPARTY--%>
        <tr>
            <td>
                <fmt:message key="deal.organisation"/>
            </td>
            <td>
                :
            </td>
            <td colspan="2" class="secure">
                {{p.counterparty.value}}
            </td>
        </tr>
<%--    PRODUCT--%>
        <tr>
            <td>
                <fmt:message key="deal.product"/>
            </td>
            <td>
                :
            </td>
            <td colspan="2">
                {{p.productName}}, {{p.shipperName}}
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
                <input id="humidity1" step="0.01" type="number" v-model.number="p.sun.humidity1" onfocus="this.select()">
            </td>
            <td rowspan="2" style="border-left: solid darkgray 1pt; width: 7em" align="center">
                <div style="font-size: 8pt">
                    <fmt:message key="mean.short"/>
                </div>
                <div>
                    {{((p.sun.humidity1 > 0 || p.sun.humidity2 > 0 ? (p.sun.humidity1 + p.sun.humidity2) /
                    ((p.sun.humidity1 > 0 ? 1 : 0) + (p.sun.humidity2 > 0 ? 1 : 0)) : 0)).toLocaleString()}} %
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="humidity2">
                    <fmt:message key="sun.humidity.2.short"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="humidity2" step="0.01" type="number" v-model.number="p.sun.humidity2" onfocus="this.select()">
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
                <input id="soreness" step="0.01" type="number" v-model="p.sun.soreness" onfocus="this.select()">
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
                <input id="oilImp" step="0.01" type="number" v-model="p.sun.oilImpurity" onfocus="this.select()">
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
                <input id="oiliness" step="0.01" type="number" v-model="p.sun.oiliness" onfocus="this.select()">
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
                <input id="acid" step="0.01" type="number" v-model="p.sun.acidValue" onfocus="this.select()">
            </td>
        </tr>
        <tr>
            <td colspan="3" align="right">
                <label for="contamination">
                    <fmt:message key="sun.contamination"/>
                </label>
                <input id="contamination" type="checkbox" v-model="p.sun.contamination">
            </td>
        </tr>
        <template v-if="recount(p.sun) > 0">
            <tr>
                <td>
                    <fmt:message key="recount.percentage"/>
                </td>
                <td>
                    :
                </td>
                <td colspan="2">
                    <span style="background-color: white; padding: 0 6pt; border: inset 2px lightgray; border-radius: 8pt; font-size: 10pt">
                        {{p.sun.recount + ' %'}}
                    </span>
                    <a style="float: right" class="mini-close" v-on:click="print" v-if="p.sun.recount > 0">
                        <fmt:message key="document.print"/>
                    </a>
                </td>
            </tr>
        </template>
    </template>
    <tr>
        <td colspan="4" align="center">
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