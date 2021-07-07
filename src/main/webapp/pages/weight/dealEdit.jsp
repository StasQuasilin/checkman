<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 13.01.21
  Time: 16:10
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css?v=${now}">
<script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
<script src="${context}/vue/transport/dealEdit.vue?v=${now}"></script>
<script>
    dealEdit.api.save = '${save}';
    dealEdit.organisationProps = {
        find:'${findOrganisation}',
        edit:'${organisationEdit}',
        add:'${parseOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="counterparty.add"/>',
        put:dealEdit.putOrganisation,
        show:['value']
    };
    <c:forEach items="${products}" var="product">
    dealEdit.products.push(${product.toJson()});
    </c:forEach>

    <c:forEach items="${actions}" var="action">
    dealEdit.addType(${action.toJson()});
    </c:forEach>
    <c:forEach items="${shippers}" var="shipper">
    dealEdit.shippers.push(${shipper.toJson()});
    </c:forEach>
    <c:forEach items="${units}" var="unit">
    dealEdit.units.push(${unit.toJson()});
    </c:forEach>

    <c:forEach items="${types}" var="type">
    dealEdit.typeNames['${type}'] = '<fmt:message key="${type}"/>';
    dealEdit.types.push('${type}');
    </c:forEach>
    <c:if test="${not empty deal}">
    dealEdit.deal = ${deal.toJson()}
    </c:if>
    <c:if test="${not empty pre}">
    dealEdit.deal = ${pre};
    </c:if>
    if (dealEdit.deal.products.length === 0){
        dealEdit.deal.products.push({
            id:-1,
            productId:dealEdit.products[0].id,
            shipperId:dealEdit.shippers[0].id,
            price:0,
            quantity:0,
            unitId:dealEdit.units[0].id
        })
    }
    dealEdit.deal.type = dealEdit.currentActions(dealEdit.products[0].id)[0];
</script>
<table id="dealEdit" class="editor">
    <tr v-if="deal.id > 0">
        <td colspan="2" style="text-align: center; word-wrap: break-spaces; max-width: 0">
            <fmt:message key="deal.attention.title"/><br>
            <fmt:message key="deal.edit.attention"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            <object-input :props="organisationProps" :object="deal.organisation"></object-input>
        </td>
    </tr>
    <template v-for="product in deal.products">
        <tr>
            <td>
                <label for="products">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
            <td v-if="currentActions">
                <select id="products" v-model="product.productId" v-on:change="check(product.productId)">
                    <option v-if="product.product == -1" disabled value="-1">
                        <fmt:message key="not.select"/>
                    </option>
                    <option v-for="p in products" :value="p.id">
                        {{p.name}}
                    </option>
                </select>
                <select v-model="deal.type">
                    <option v-for="a in currentActions(product.productId)" :value="a">
                        {{typeNames[a]}}
                    </option>
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
                <input id="price" type="number" v-model="product.price" autocomplete="off" onfocus="this.select()">
                <label for="shipper">
                    <fmt:message key="deal.from"/>
                </label>
                <select id="shipper" v-model="product.shipperId">
                    <option v-for="s in shippers" :value="s.id">
                        {{s.name}}
                    </option>
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
                <input id="quantity" type="number" v-model="product.quantity" autocomplete="off" onfocus="this.select()">
                <select v-model="product.unitId">
                    <option v-for="u in units" :value="u.id">
                        {{u.name}}
                    </option>
                </select>
            </td>
        </tr>
    </template>

    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>
