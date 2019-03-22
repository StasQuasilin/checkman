<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
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
        width: 80%
    }
</style>
    <table id="editor">
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
                ${plan.deal.product.name}
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
                            <span v-show="length() > 1" class="mini-close" style="left: 0" v-on:click="removeWeight(key)">&times;</span>
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
                    <input id="brutto" v-model="value.brutto">
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
                    <input id="tara" v-model="value.tara">
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
                <button><fmt:message key="button.cancel"/> </button>
                <button v-on:click="save"><fmt:message key="button.save"/> </button>
            </td>
        </tr>
    </table>
</html>