<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css?v=${now}">
<script src="${context}/vue/weightEdit.vue?v=${now}"></script>
<script>
    editor.api.save = '${save}';
    editor.api.print = '${print}';
    <c:forEach items="${plan.products}" var="p">
    editor.addWeight(${p.toJson()});
    </c:forEach>
    <c:forEach items="${types}" var="t">
    editor.typeNames['${t}']='<fmt:message key="_${t}"/>';
    </c:forEach>

</script>
<table id="editor" >
    <tr>
        <td>
            <table class="editor">
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
                <template v-for="(w, wIdx) in weights">
                    <tr>
                        <td colspan="3" style="border-bottom: solid gray 1pt"></td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="deal.organisation"/>
                        </td>
                        <td>
                            :
                        </td>
                        <td class="secure">
                            {{w.counterparty.value}}
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
                            {{w.productName}},
                            {{typeNames[w.type].toLowerCase()}}
                        </td>
                    </tr>
                    <tr v-if="!w.product.group">
                        <td>
                            <fmt:message key="deal.quantity"/>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            {{w.amount.toLocaleString()}} {{w.unitName}} <fmt:message key="deal.from"/> {{w.shipperName}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label :for="'gross_' + wIdx">
                                <fmt:message key="weight.gross"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input :id="'gross_' + wIdx" v-model="w.weight.gross" v-on:change="checkBrutto(w.weight)"
                                   onfocus="this.select()" type="number" step="0.01" autocomplete="off">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label :for="'tara_' + wIdx">
                                <fmt:message key="weight.tare"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input :id="'tara_' + wIdx" v-model="w.weight.tare" v-on:change="checkTara(w.weight)"
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
                            <b v-if="w.weight.gross > 0 && w.weight.tare > 0">
                                {{(w.weight.gross - w.weight.tare).toLocaleString()}}
                            </b>
                            <span v-else>
                                0
                            </span>
                        </td>
                    </tr>
                </template>
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