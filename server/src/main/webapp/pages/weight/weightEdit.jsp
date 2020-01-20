<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/weightEdit.vue"></script>
<script>
    editor.api.save = '${save}';
    editor.api.print = '${print}';
    editor.id=${transportation.id};
    <c:forEach items="${transportation.documents}" var="doc">
    <c:forEach items="${doc.products}" var="product">
    var w = JSON.parse('${product.toJson()}');
    <c:if test="${empty product.weight}">
    w.weight = {
        id:-1,
        brutto:0,
        tara:0
    };
    </c:if>
    w.weight.netto = function(){
        return editor.netto(w.weight);
    };
    editor.products.push(w);
    </c:forEach>
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
                        <fmt:formatDate value="${transportation.date}" pattern="dd.MM.yyyy"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <fmt:message key="deal.product"/>:
                    </td>
                </tr>
                <template v-for="product in products">
                    <tr>
                        <td colspan="3">
                            <b>
                                {{product.product.name}}
                            </b>
                            <fmt:message key="deal.plan"/>
                            {{product.amount.toLocaleString()}} {{product.unit.name}}
                            <fmt:message key="deal.from"/>
                            {{product.shipper.name}}
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label for="brutto">
                                <fmt:message key="weight.brutto"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="brutto" type="number" step="0.01" v-model="product.weight.brutto"
                                   autocomplete="off" onfocus="this.select()">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label for="tara">
                                <fmt:message key="weight.tara"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="tara" type="number" step="0.01" v-model="product.weight.tara"
                                   autocomplete="off" onfocus="this.select()">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label for="netto">
                                <fmt:message key="weight.netto"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="netto" type="number" readonly v-model="product.weight.netto()">
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
    </tr>
</table>
</html>