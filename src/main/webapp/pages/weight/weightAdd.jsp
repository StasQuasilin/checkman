<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/weightAdd.js"></script>
<script>
    editor.api.findOrganisation = '${findOrganisations}';
    editor.api.parseOrganisation = '${parseOrganisation}';
    editor.api.findDeals = '${findDeals}';
    editor.api.findVehicle = '${findVehicle}';
    editor.api.parseVehicle = '${parseVehicle}';
    editor.api.findDriver = '${findDriver}';
    editor.api.parseDriver = '${parseDriver}';
    editor.api.save = '${save}';
    <c:forEach items="${types}" var="type">
    editor.types['${type}'] = {
        id:'${type}',
        value:'<fmt:message key="${type}"/>'
    };
    </c:forEach>
    <c:forEach items="${products}" var="product">
    editor.products.push({
        id:${product.id},
        name:'${product.name}'
    });
    </c:forEach>
    <c:forEach items="${units}" var="unit">
    editor.units.push({
        id:${unit.id},
        value:'${unit.name}'
    });
    editor.plan.unit = editor.units[0].id;
    </c:forEach>
    <c:forEach items="${documentOrganisations}" var="d">
    editor.visibles.push(
        '${d.value}'
    );
    </c:forEach>
    editor.plan.from = editor.visibles[0];
    <c:forEach items="${customers}" var="customer">
    editor.customers['${customer}'] = {
        id:'${customer}',
        value:'<fmt:message key="${customer}"/>'
    };
    if(!editor.plan.customer){
        editor.plan.customer = '${customer}';
    }
    </c:forEach>
</script>
<table id="editor" class="editor">
    <tr>
        <td>
            <label for="type">
                <fmt:message key="deal.type"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="type" v-model="plan.type">
                <option v-for="type in typeList()" :value="type.id">{{type.value}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="date">
                <fmt:message key="date"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="date" readonly style="width: 7em" v-on:click="pickDate()" v-model="new Date(plan.date).toLocaleDateString()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="organisation">
                <fmt:message key="deal.organisation"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <%--!--%>
            <%--!--%>
            <%--ORGANISATION--%>
            <input id="organisation" v-model="input.organisation"
                   v-on:keyup="findOrganisation()"
                   v-on:keyup.enter="parseOrganisation()" onclick="this.select()">
            <div class="custom-data-list">
                <div v-for="organisation in foundOrganisations" class="custom-data-list-item" v-on:click="putOrganisation(organisation)">
                    {{organisation.value}}
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label for="deal">
                <fmt:message key="deal"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="deal" style="width: 100%" v-model="plan.deal">
                <option value="-1"><fmt:message key="deal.new"/></option>
                <optgroup  v-for="deal in deals" :label="new Date(deal.date).toLocaleDateString().substring(0, 5) +'-' + new Date(deal.date_to).toLocaleDateString().substring(0, 5)">
                    <option :value="deal.id">
                        {{types[deal.type].value}} {{deal.product.name}}
                    </option>
                </optgroup>
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
            :
        </td>
        <td>
            <select id="product" style="width: 200px" v-model="plan.product">
                <option v-if="plan.deal == -1" disabled value="-1"><fmt:message key="need.select"/></option>
                <option v-for="product in productList()" :value="product.id">{{product.name}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="quantity">
                <fmt:message key="load.plan"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="quantity" type="number" v-model="plan.plan">
            <c:set var="units"><fmt:message key="units"/></c:set>
            <select title="${units}" v-model="plan.unit">
                <option v-for="unit in units" :value="unit.id">{{unit.value}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label for="from">
                <fmt:message key="deal.from"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="from" v-model="plan.from">
                <option v-for="visible in visibleList()" :value="visible">{{visible}}</option>
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
            :
        </td>
        <td>
            <input id="price" v-if="plan.deal == -1" type="number" v-model="plan.price" autocomplete="off">
            <input :id="'price'" v-else type="number" v-model="getPrice()" readonly autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="customer">
                <fmt:message key="load.customer.title"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="customer" v-model="plan.customer">
                <option v-for="customer in customers" :value="customer.id">{{customer.value}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="vehicle">
                <fmt:message key="transportation.automobile"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="vehicle" v-model="input.vehicle"
                   v-on:keyup="findVehicle()"
                   v-on:keyup.enter="parseVehicle()"
                   :title="input.vehicle">
            <div class="custom-data-list">
                <div v-for="vehicle in foundVehicles" class="custom-data-list-item" v-on:click="putVehicle(vehicle)">
                    {{vehicle.model}}
                    '{{vehicle.number}}'
                    {{vehicle.trailer}}
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label for="driver">
                <fmt:message key="transportation.driver"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="driver" v-model="input.driver"
                   v-on:keyup="findDriver()"
                   v-on:keyup.enter="parseDriver()"
                   :title="input.driver">
            <div class="custom-data-list">
                <div v-for="driver in foundDrivers" class="custom-data-list-item" v-on:click="putDriver(driver)">
                    {{driver.person.value}}
                </div>
            </div>

        </td>
    </tr>

    <tr>
        <td colspan="3" align="center">
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