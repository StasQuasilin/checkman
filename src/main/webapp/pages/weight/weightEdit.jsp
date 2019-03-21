<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<script src="${context}/vue/weightEdit.js"></script>
<script>
    <c:forEach items="${plan.transportation.weights}" var="weight">
    editor.addWeight(${weight.id}, ${weight.brutto}, ${weight.tara});
    </c:forEach>
    console.log(editor.weights)


</script>
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
        <template v-for="weight in weights">
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
                    <input id="brutto" v-model="weight.brutto">
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
                    <input id="tara" v-model="weight.tara">
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
                    {{netto(weight.brutto, weight.tara)}}
                </td>

            </tr>
        </template>
        <tr>
            <td colspan="3" align="center">
                <button><fmt:message key="button.cancel"/> </button>
                <button><fmt:message key="button.save"/> </button>
            </td>
        </tr>
    </table>
<script>
    if (editor.weights.length == 0){
        editor.newWeight();
    }
</script>
</html>