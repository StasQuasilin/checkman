<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-06-09
  Time: 13:47
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/transportAnalysesEdit.vue?v=${now}"></script>
<script>
    analysesEdit.api.save = '${save}';
    analysesEdit.api.print = '${print}';
    <c:forEach items="${transportation.products}" var="p">
    analysesEdit.addProduct(${p.toJson()});
    </c:forEach>
    analysesEdit.helpers.types.push(
        {
            id:1,
            value:'<fmt:message key="press.oil"/>'
        }
    );
    analysesEdit.helpers.types.push(
        {
            id:2,
            value:'<fmt:message key="mix.oil"/>'
        }
    )
</script>
<div id="editor" class="editor">
    <table>
        <c:if test="${not empty transportation.driver}">
            <tr>
                <td>
                    <fmt:message key="transportation.driver"/>
                </td>
                <td>
                    :
                </td>
                <td colspan="2">
                    ${transportation.driver.person.value}
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty transportation.vehicle}">
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
                                ${transportation.vehicle.model}
                            </td>
                            <td style="font-size: 8pt" class="secure">
                                ${transportation.vehicle.number}
                            </td>
                        </tr>
                        <tr>
                            <td style="font-size: 8pt" class="secure">
                                <c:if test="${not empty transportation.trailer}">
                                    ${transportation.trailer.number}
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:if>
        <template v-for="p in products">
            <tr>
                <td colspan="4" style="border-bottom: dashed #2b2b2b 1pt"></td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="deal.organisation"/>
                </td>
                <td>
                    :
                </td>
                <td colspan="2" class="secure" style="white-space: nowrap">
                    {{p.counterparty.value}}
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="deal.product"/>
                </td>
                <td>
                    :
                </td>
                <td colspan="2" style="white-space: nowrap">
                    {{p.productName}}, {{p.shipperName}}
                </td>
            </tr>
            <template v-if="p.product.analysesType === 'sun'">
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
                        <label for="sunAcid">
                            <fmt:message key="sun.acid.value"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td colspan="2">
                        <input id="sunAcid" step="0.01" type="number" v-model="p.sun.acidValue" onfocus="this.select()">
                    </td>
                </tr>
                <tr>
                    <td colspan="4" align="right">
                        <label for="contamination">
                            <fmt:message key="sun.contamination"/>
                        </label>
                        <input id="contamination" type="checkbox" v-model="p.sun.contamination">
                    </td>
                </tr>
                <tr v-if="recount(p.sun) > 0">
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
                        <a style="float: right" class="mini-close" v-on:click="print(p)">
                            <fmt:message key="document.print"/>
                        </a>
                    </td>
                </tr>
            </template>
            <template v-else-if="p.product.analysesType === 'oil' || p.product.analysesType === 'raf'">
                <tr>
                    <td>
                        <fmt:message key="oil.organoleptic"/>
                    </td>
                    <td>
                        :
                    </td>
                    <td style="width: 9em">
                        <b v-on:click="p.oil.organoleptic = !p.oil.organoleptic">
                            <a v-if="p.oil.organoleptic" style="color: forestgreen">
                                <fmt:message key="oil.organoleptic.match"/>:)
                            </a>
                            <a v-else style="color: orangered">
                                <fmt:message key="oil.organoleptic.doesn't.match"/>:(
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
                        <input id="color" type="number" v-model="p.oil.color" onfocus="this.select()">
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
                        <input id="acid" type="number" step="0.01" v-model="p.oil.acidValue" onfocus="this.select()">
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
                        <input id="peroxide" type="number" step="0.01" v-model="p.oil.peroxideValue" onfocus="this.select()">
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
                        <input id="phosphorus" type="number" step="0.01" v-model="p.oil.phosphorus" onfocus="this.select()">
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
                        <input id="humidity" type="number" step="0.01" v-model="p.oil.humidity" onfocus="this.select()">
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
                               autocomplete="off" v-model="p.oil.degreaseImpurity" onfocus="this.select()">
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
                               autocomplete="off" v-model="p.oil.transparency">
                    </td>
                </tr>
                <template v-if="p.product.analysesType === 'raf'">
                    <tr>
                        <td>
                            <fmt:message key="oil.soap"/>
                        </td>
                        <td>
                            :
                        </td>
                        <td>

                            <b v-on:click="p.oil.soap = !p.oil.soap">
                                <a v-if="p.oil.soap">
                                    <fmt:message key="notification.kpo.soap.yes"/>
                                </a>
                                <a v-else>
                                    <fmt:message key="notification.kpo.soap.no"/>
                                </a>
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <fmt:message key="oil.wax"/>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <b v-on:click="p.oil.wax = !p.oil.wax">
                                <a v-if="p.oil.wax">
                                    <fmt:message key="notification.kpo.wax.yes"/>
                                </a>
                                <a v-else>
                                    <fmt:message key="notification.kpo.wax.no"/>
                                </a>
                            </b>
                        </td>
                    </tr>
                </template>

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
                               autocomplete="off" v-model="p.oil.benzopyrene">
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <select v-model="p.oil.type" style="width: 100%">
                            <option v-for="t in helpers.types" :value="t.id">
                                {{t.value}}
                            </option>
                        </select>
                    </td>
                </tr>
                <tr v-if="p.oil.type == 2">
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
                               autocomplete="off" v-model="p.oil.explosion">
                    </td>
                </tr>
            </template>
        </template>
        <tr>
            <td colspan="4" align="center">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</div>
</html>
