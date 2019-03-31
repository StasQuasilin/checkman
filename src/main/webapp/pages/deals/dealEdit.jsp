<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/dealEdit.js"></script>
    <script>
        <c:forEach items="${types}" var="t">
        editor.types.push({
            id:'${t}',
            value:'<fmt:message key="${t}"/>'
        })</c:forEach>
        <c:forEach items="${products}" var="product">
        editor.products.push({
            id:${product.id},
            value:'${product.name}'
        })</c:forEach>
        <c:forEach items="${documentOrganisations}" var="d">
        editor.realisations.push({
            id:${d.id},
            value:'${d.value}'
        })</c:forEach>
        <c:forEach items="${units}" var="u">
        editor.units.push({
            id:${u.id},
            value:'${u.name}'
        })
        </c:forEach>

        editor.api.findOrganisationUrl = '${findOrganisation}';
        editor.api.parseOrganisationUrl = '${parseOrganisation}';
        editor.api.saveUrl = '${saveUrl}'
        <c:choose>
        <c:when test="${not empty deal}">
        editor.deal.id = ${deal.id};
        editor.deal.type = '${deal.type}';
        editor.deal.date = '${deal.date}';
        editor.deal.dateTo = '${deal.dateTo}';
        editor.deal.contragent = ${deal.organisation.id};
        editor.deal.realisation = ${deal.documentOrganisation.id};
        editor.deal.product = ${deal.product.id};
        editor.deal.quantity = ${deal.quantity};
        <c:if test="${not empty deal.unit}">
        editor.deal.unit = ${deal.unit.id};
        </c:if>
        editor.deal.price = ${deal.price};
        editor.contragentInput = editor.contragentName = '${deal.organisation.value}';
        </c:when>
        <c:otherwise>
        editor.deal.type = '${type}';
        editor.init();
        </c:otherwise>
        </c:choose>

    </script>
    <table id="editor" class="editor">
        <tr>
            <td>
                <label for="type">
                    <fmt:message key="deal.type"/>
                </label>
            </td>
            <td>
                <select id="type" v-model="deal.type">
                    <option v-for="type in types" :value="type.id">{{type.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="date">
                    <fmt:message key="period"/>:
                </label>
            </td>
            <td>
                <input id="date" readonly autocomplete="off" v-model="new Date(deal.date).toLocaleDateString()" style="width: 7em">-
                <input id="date_to" readonly autocomplete="off" v-model="new Date(deal.dateTo).toLocaleDateString()" style="width: 7em">
            </td>
        </tr>
        <tr>
            <td>
                <label for="contragent">
                    <fmt:message key="deal.organisation"/>:
                </label>
            </td>
            <td>
                <input id="contragent" autocomplete="off" style="width: 100%"
                       v-on:keyup="findOrganisation()"
                       v-on:keyup.enter="parseOrganisation()"
                       v-model="contragentInput"/>
                <div id="contragent-list" class="custom-data-list">
                    <div class="custom-data-list-item" v-for="contragent in foundContragents" v-on:click="setContragent(contragent)">{{contragent.value}}</div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="realisation">
                    <fmt:message key="deal.realisation"/>:
                </label>
            </td>
            <td>
                <select id="realisation" v-model="deal.realisation">
                    <option v-for="realisation in realisations" :value="realisation.id">{{realisation.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
            <td>
                <select id="product" v-model="deal.product">
                    <option v-for="product in products" :value="product.id">{{product.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="quantity">
                    <fmt:message key="deal.quantity"/>
                </label>
            </td>
            <td>
                <input type="number" id="quantity" v-model="deal.quantity" autocomplete="off" style="width: 7em">
                <select v-model="deal.unit">
                    <option v-for="unit in units" :value="unit.id">{{unit.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="price">
                    <fmt:message key="deal.price"/>
                </label>
            </td>
            <td>
                <input type="number" id="price" v-model="deal.price" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button onclick="closeModal()"><fmt:message key="button.cancel"/> </button>
                <button v-on:click="save()"><fmt:message key="button.save"/> </button>
            </td>
        </tr>
    </table>
</html>