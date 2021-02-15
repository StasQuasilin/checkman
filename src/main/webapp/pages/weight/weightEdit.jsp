<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css?v=${now}">
<script src="${context}/vue/weightEdit.vue?v=${now}"></script>
<script>
    editor.api.saveWeightAPI = '${save}';
    editor.api.print = '${print}';
    editor.id=${plan.id}
    <c:choose>
    <c:when test="${not empty plan.weight.id}">
    editor.weight={
        id:${plan.weight.id},
        brutto:${plan.weight.brutto},
        tara:${plan.weight.tara}
    };
    </c:when>
    <c:otherwise>
    editor.weight={
        brutto:0,
        tara:0
    };
    </c:otherwise>
    </c:choose>
</script>
<table border="0" id="editor" >
    <tr>
        <td>
            <table class="editor" border="0">
                <tr>
                    <td>
                        <fmt:message key="date"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <fmt:formatDate value="${plan.date}" pattern="dd.MM.yyyy"/>
                    </td>
                </tr>
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

                        <fmt:message key="deal.product"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        ${plan.product.name},
                        <c:set var="type"><fmt:message key="_${plan.type}"/> </c:set>
                        ${fn:toLowerCase(type)}
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="deal.quantity"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <fmt:formatNumber value="${plan.amount}"/>
                        <%--${plan.deal.unit.name}--%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="deal.from"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        ${plan.shipper.value}
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="transportation.automobile"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td class="secure">
                        ${plan.vehicle.value}
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
                        ${plan.driver.person.value}
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="brutto">
                            <fmt:message key="weight.gross"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="brutto" v-model="weight.brutto" v-on:change="checkBrutto"
                               onfocus="this.select()" type="number" step="0.01" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="tara">
                            <fmt:message key="weight.tare"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="tara" v-model="weight.tara" v-on:change="checkTara"
                               onfocus="this.select()" type="number" step="0.01" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="weight.net"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        {{netto(weight.brutto, weight.tara).toLocaleString()}}
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="center">
                        <button onclick="closeModal()" class="close-button left-button">
                            <fmt:message key="button.cancel"/>
                        </button>
                        <button v-on:click="save" class="save-button right-button">
                            <fmt:message key="button.save"/>
                        </button>
                        <span v-on:click="print()" class="mini-close">
                            <fmt:message key="document.print"/>
                        </span>
                    </td>
                </tr>
            </table>
        </td>
        <td valign="top" align="center">
            <div style="overflow-y: scroll">
                <c:if test="${fn:length(seals) > 0}">
                    <fmt:message key="seals"/>
                    <c:forEach items="${seals}" var="seal">
                        <div>
                            ${seal.value}
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${plan.sunAnalyses ne null}">
                    <fmt:message key="analyses"/>
                    <div style="width:100%; text-align: left">
                        <div>
                            <fmt:message key="sun.humidity.1.short"/>:
                            ${plan.sunAnalyses.humidity1}
                        </div>
                        <div>
                            <fmt:message key="sun.humidity.2.short"/>:
                            ${plan.sunAnalyses.humidity2}
                        </div>
                        <div>
                            <fmt:message key="sun.soreness"/>:
                            ${plan.sunAnalyses.soreness}
                        </div>
                        <div>
                            <fmt:message key="sun.oil.impurity"/>:
                            ${plan.sunAnalyses.oilImpurity}
                        </div>
                        <div>
                            <fmt:message key="sun.oiliness"/>:
                            ${plan.sunAnalyses.oiliness}
                        </div>
                        <c:if test="${plan.sunAnalyses.contamination}">
                        <div>
                            <fmt:message key="sun.contamination"/>
                        </div>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top" align="center">
            <%--<table width="100%">--%>
                <%--<tr>--%>
                    <%--<th colspan="3">--%>
                        <%--<fmt:message key="analyses"/>--%>
                    <%--</th>--%>
                <%--</tr>--%>
                <%--<template v-for="sun in analyses.sun">--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.humidity.1.short"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{sun.humidity1}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.humidity.2.short"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{sun.humidity2}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>

                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{sun.soreness}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.oiliness"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{sun.oiliness}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.oil.impurity"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{sun.oilImpurity}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.acid.value"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{sun.acid}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>

                <%--</template>--%>
                <%--<template v-for="oil in analyses.oil">--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="oil.organoleptic"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--<input type="checkbox" readonly v-model="oil.organoleptic" onclick="return false;">--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="oil.color.value"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.color}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.acid.value"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.acid}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="oil.peroxide"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.peroxide}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="oil.phosphorus"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.phosphorus}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.humidity"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.humidity}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="oil.soap"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.soap}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="oil.wax"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{oil.wax}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</template>--%>
                <%--<template v-for="cake in analyses.cake" style="font-size: 9pt">--%>
                    <%--<tr >--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.humidity"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{cake.humidity}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="cake.protein"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{cake.protein}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="cake.cellulose"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{cake.cellulose}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<fmt:message key="sun.oiliness"/>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--:--%>
                        <%--</td>--%>
                        <%--<td>--%>
                            <%--{{cake.oiliness}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</template>--%>
            <%--</table>--%>

        </td>
    </tr>
</table>
</html>