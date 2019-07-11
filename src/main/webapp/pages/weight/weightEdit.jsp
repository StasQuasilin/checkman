<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/weightEdit.vue"></script>
<script>
    editor.api.saveWeightAPI = '${saveWeightAPI}';
    editor.api.print = '${print}';
    editor.id=${plan.id}
    <c:choose>
    <c:when test="${not empty plan.transportation.weight.id}">
    editor.weight={
        id:${plan.transportation.weight.id},
        brutto:${plan.transportation.weight.brutto},
        tara:${plan.transportation.weight.tara}
    }
    </c:when>
    <c:otherwise>
    editor.weight={
        brutto:0,
        tara:0
    }
    </c:otherwise>
    </c:choose>
    <%--<c:forEach items="${plan.transportation.sunAnalyses}" var="sun">--%>
    <%--editor.analyses.sun.push(--%>
        <%--{--%>
            <%--id:'${sun.id}',--%>
            <%--humidity1:${sun.analyses.humidity1},--%>
            <%--humidity2:${sun.analyses.humidity2},--%>
            <%--soreness:${sun.analyses.soreness},--%>
            <%--oiliness:${sun.analyses.oiliness},--%>
            <%--oilImpurity:${sun.analyses.oilImpurity},--%>
            <%--acid:${sun.analyses.acidValue}--%>
        <%--}--%>
    <%--)--%>
    <%--</c:forEach>--%>
    <%--<c:forEach items="${plan.transportation.oilAnalyses}" var="oil">--%>
    <%--editor.analyses.oil.push(--%>
        <%--{--%>
            <%--id:'${oil.id}',--%>
            <%--organoleptic:${oil.analyses.organoleptic},--%>
            <%--color:${oil.analyses.color},--%>
            <%--acid:${oil.analyses.acidValue},--%>
            <%--peroxide:${oil.analyses.peroxideValue},--%>
            <%--phosphorus:${oil.analyses.phosphorus},--%>
            <%--humidity:${oil.analyses.humidity},--%>
            <%--soap:${oil.analyses.soap},--%>
            <%--wax:${oil.analyses.wax}--%>
        <%--}--%>
    <%--)--%>
    <%--</c:forEach>--%>
    <%--<c:forEach items="${plan.transportation.cakeAnalyses}" var="cake">--%>
    <%--editor.analyses.cake.push(--%>
        <%--{--%>
            <%--id:${cake.id},--%>
            <%--humidity:${cake.analyses.humidity},--%>
            <%--protein:${cake.analyses.protein},--%>
            <%--cellulose:${cake.analyses.cellulose},--%>
            <%--oiliness:${cake.analyses.oiliness}--%>
        <%--}--%>
    <%--)--%>
    <%--</c:forEach>--%>
</script>
<style>
    .custom-line{
        line-height: 0.5;
        height: 10px;

    }
    .custom-line:after{
        content: "";
        position: absolute;
        height: 4px;
        border-bottom: 1px solid gray;
        width: 50%
    }
</style>
<table border="1" id="editor" >
    <tr>
        <td rowspan="2">
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
                    <td>
                        ${plan.deal.organisation.value}
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
                        ${plan.deal.product.name},
                        <c:set var="type"><fmt:message key="_${plan.deal.type}"/> </c:set>
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
                        <fmt:formatNumber value="${plan.plan}"/>
                        ${plan.deal.unit.name}
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
                        ${plan.deal.shipper.value}
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
                        ${plan.transportation.vehicle.value}
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
                        ${plan.transportation.driver.person.value}
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="brutto">
                            <fmt:message key="weight.brutto"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="brutto" v-model="weight.brutto" v-on:change="checkBrutto"
                               onclick="this.select()" type="number" step="0.01" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="tara">
                            <fmt:message key="weight.tara"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="tara" v-model="weight.tara" v-on:change="checkTara"
                               onclick="this.select()" type="number" step="0.01" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="netto">
                            <fmt:message key="weight.netto"/>
                        </label>
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
                        <button onclick="closeModal()"><fmt:message key="button.cancel"/> </button>
                        <button v-on:click="save"><fmt:message key="button.save"/> </button>
                        <button v-on:click="print()">
                            <fmt:message key="document.print"/>
                        </button>
                    </td>
                </tr>
            </table>
        </td>
        <td valign="top" align="center" width="200px">
            <%--<table border="0" style="width: 100%">--%>
                <%--<tr>--%>
                    <%--<th>--%>
                        <%--<fmt:message key="menu.seals"/>--%>
                    <%--</th>--%>
                <%--</tr>--%>
                <%--<c:forEach items="${plan.transportation.seals}" var="seal">--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--${seal.number}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</c:forEach>--%>
            <%--</table>--%>
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
                            <%--<fmt:message key="sun.humidity.1"/>--%>
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
                            <%--<fmt:message key="sun.humidity.2"/>--%>
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
                            <%--<fmt:message key="sun.soreness"/>--%>
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