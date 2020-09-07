<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 04.09.20
  Time: 16:04
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script type="application/javascript" src="${context}/vue/editor.vue"></script>
<script type="application/javascript" src="${context}/vue/templates/search.vue"></script>
<script type="application/javascript" src="${context}/vue/dealEdit.vue"></script>
<script>
    dealEdit.api.save = '${save}';
    dealEdit.api.productEdit = '${productEdit}';
    dealEdit.organisationProps.findApi = '${findOrganisation}';
    <c:forEach items="${products}" var="product">
    dealEdit.products.push(${product.toJson()});
    </c:forEach>
    <c:forEach items="${types}" var="type">
    dealEdit.types.push('${type}');
    dealEdit.typeNames['${type}'] = '<fmt:message key="${type}"/>';
    </c:forEach>
    dealEdit.object = {
        id:-1,
        number:'',
        type:'${type}',
        date:new Date().toISOString().substring(0, 10),
        from:new Date().toISOString().substring(0, 10),
        to:new Date().toISOString().substring(0, 10),
        counterparty:{
            id:-1,
            name:''
        },
        products:[
            {
                id:-1,
                product:-1,
                amount:1,
                unit:-1,
                price:1
            }
        ]
    }
</script>
<table id="dealEdit">
    <tr>
        <td>
            <label for="number">
                <fmt:message key="deal.number"/>
            </label>
        </td>
        <td>
            <input id="number" v-model="object.number" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.date"/>
        </td>
        <td>
            <span class="text-button">
                {{new Date(object.date).toLocaleDateString()}}
            </span>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.period"/>
        </td>
        <td>
            <span>
                {{new Date(object.from).toLocaleDateString()}}
            </span>
            -
            <span>
                {{new Date(object.to).toLocaleDateString()}}
            </span>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.counterparty"/>
        </td>
        <td>
            <search :props="organisationProps" :object="object.counterparty" :field="'name'"></search>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <select v-model="object.type">
                <option v-for="type in types" :value="type">
                    {{typeNames[type]}}
                </option>
            </select>
        </td>
    </tr>
    <template v-for="(dp, idx) in object.products">
        <tr>
            <td>
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
            <td>
                <select id="product" v-model="dp.product">
                    <option v-if="dp.product === -1" disabled value="-1">
                        <fmt:message key="some.not.selected"/>
                    </option>
                    <option v-for="product in products" :value="product.id">
                        {{product.name}}
                    </option>
                </select>
                <span class="text-button" v-on:click="newProduct()">
                    +
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <label :for="'amount' + idx">
                    <fmt:message key="deal.amount"/>
                </label>
            </td>
            <td>
                <input :id="'amount' + idx" type="number" step="0.01" v-model="dp.amount" onfocus="this.select()" autocomplete="off">
                <select v-model="dp.unit">
                    <option v-if="dp.unit === -1" disabled value="-1">
                        <fmt:message key="some.not.selected"/>
                    </option>
                    <option v-for="unit in units" :value="unit.id">
                        {{unit.name}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label :for="'price ' + idx">
                    <fmt:message key="deal.price"/>
                </label>
            </td>
            <td>
                <input id="'price ' + idx" type="number" step="0.01" v-model="dp.price" onfocus="this.select()" autocomplete="off">
            </td>
        </tr>
    </template>
    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save()">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>
