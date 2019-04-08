<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/filters/transportFilter.js"></script>
<script>
    filter_controll.filters.types.push({
        id:'buy',
        value:'<fmt:message key="_buy"/>'
    });
    filter_controll.filters.types.push({
        id:'sell',
        value:'<fmt:message key="_sell"/>'
    });
</script>
<div id="filter_view">
    <table >
        <tr v-if="filters.types.length">
            <td>
                <label for="type">
                    <fmt:message key="deal.type"/>
                </label>
            </td>
            <td>
                <select id="type" v-model="type">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="(value, key) in filters.types" :value="value.id">{{value.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label for="organisation">
                    <fmt:message key="deal.organisation"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <select id="organisation" v-model="organisation">
                    <option value="-1"><fmt:message key="all"/> </option>
                    <option v-for="organisation in organisations()" value="organisation.id">
                        {{organisation.value}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <th colspan="2">
                <fmt:message key="deal.product"/>
            </th>
        </tr>
        <tr>
            <td colspan="2">
                <div>
                    <input type="radio" name="product_radio" id="product_" checked>
                    <label for="product_">
                        <fmt:message key="all"/>
                    </label>
                </div>
                <div v-for="product in products()">
                    <input type="radio" name="product_radio" :id="'product_'+product.id">
                    <label :for="'product_' + product.id">
                        {{product.name}}
                    </label>
                </div>
            </td>
        </tr>
    </table>
</div>

</html>