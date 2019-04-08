<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/weightEdit.js"></script>
<script>
    editor.api.saveWeightAPI = '${saveWeightAPI}';
    editor.id=${plan.id}
    <c:forEach items="${plan.transportation.weights}" var="weight">
    editor.addWeight(${weight.id}, ${weight.brutto}, ${weight.tara});
    </c:forEach>
    console.log(editor.weights)
    if (editor.weights.length == 0){
        editor.newWeight();
    }
    <c:forEach items="${plan.transportation.sunAnalyses}" var="sun">
    editor.analyses.sun.push(
            {
                id:'${sun.id}',
                humidity:${sun.humidity},
                soreness:${sun.soreness},
                oiliness:${sun.oiliness},
                oilImpurity:${sun.oilImpurity},
                acid:${sun.acidValue}
            }
    )

    </c:forEach>
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
<table border="1">
    <tr>
        <td rowspan="2">
            <table id="editor" class="editor" border="0">
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
                        ${plan.deal.documentOrganisation.value}
                    </td>
                </tr>

                <template v-for="(value, key) in weights">
                    <tr>
                        <td colspan="3">
                            <div class="custom-line">
                                <div style="display: inline-block; width: 20px">
                            <span v-show="length() > 1" class="mini-close" style="left: 0"
                                  v-on:click="removeWeight(key)">&times;</span>
                                </div>
                            </div>
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
                            <input id="brutto" v-model="value.brutto" v-on:change="value.checkBrutto()"
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
                            <input id="tara" v-model="value.tara" v-on:change="value.checkTara()"
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
                            {{netto(value.brutto, value.tara).toLocaleString()}}
                        </td>
                    </tr>
                </template>
                <template v-if="length() > 1">
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            &nbsp;
                        </td>
                        <td>
                            <b>Total: {{total().toLocaleString()}}</b>
                        </td>
                    </tr>

                </template>
                <tr>
                    <td colspan="3" align="right">
                        <span class="mini-close" v-on:click="newWeight">+</span>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="center">
                        <button onclick="closeModal()"><fmt:message key="button.cancel"/> </button>
                        <button v-on:click="save"><fmt:message key="button.save"/> </button>
                    </td>
                </tr>
            </table>
        </td>
        <td valign="top">
            <table border="0">
                <tr>
                    <th>
                        <fmt:message key="menu.seals"/>
                    </th>
                </tr>
                <c:forEach items="${plan.transportation.seals}" var="seal">
                    <tr>
                        <td>
                            ${seal.number}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <table>
                <tr>
                    <th>
                        <fmt:message key="analyses"/>
                    </th>
                </tr>

            </table>

        </td>
    </tr>
</table>


</html>