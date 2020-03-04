<%@ page import="utils.U" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/weightAdd.vue"></script>
<script>
    <%--<jsp:include page="/vue/weightAdd.vue"/>--%>
    editor.organisationProps = {
        find:'${findOrganisation}',
        edit:'${editOrganisation}',
        add:'${parseOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="counterparty.add"/>',
        put:editor.putOrganisation,
        show:['value']
    };
    editor.driverProps = {
        find:'${findDriver}',
        edit:'${editDriver}',
        add:'${parseDriver}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="driver.add"/>',
        put:editor.putDriver,
        show:['person/surname', 'person/forename', 'person/patronymic']
    };
    editor.vehicleProps = {
        find:'${findVehicle}',
        edit:'${editVehicle}',
        add:'${parseVehicle}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.vehicle"/>',
        put:editor.putVehicle,
        show:['model', 'number']
    };
    editor.trailerProps = {
        find:'${findTrailer}',
        add:'${parseTrailer}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.trailer"/>',
        put:editor.putTrailer,
        show:['number']
    };
    editor.transporterProps = {
        find:'${findOrganisation}',
        edit:'${editOrganisation}',
        add:'${parseOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.transporter"/>',
        put:editor.putTransporter,
        show:['value']
    };
    editor.api.findOrganisation = '${findOrganisations}';
    editor.api.parseOrganisation = '${parseOrganisation}';
    editor.api.findDeals = '${findDeals}';
    editor.api.findVehicle = '${findVehicle}';
    editor.api.parseVehicle = '${parseVehicle}';
    editor.api.editVehicle = '${editVehicle}';
    editor.api.findDriver = '${findDriver}';
    editor.api.parseDriver = '${parseDriver}';
    editor.api.editDriver = '${editDriver}';
    editor.api.save = '${save}';
    editor.api.editCounterparty = '${editOrganisation}';
    editor.api.editAddress = '${editAddress}';
    editor.api.findAddress = '${findLoadAddress}';
    editor.worker = '${worker.person.value}';
    
    <c:forEach items="${types}" var="type">
    editor.typeNames['${type}'] = '<fmt:message key="_${type}"/>';
    </c:forEach>
    
    <c:forEach items="${actions}" var="action">
    editor.addType(${action.toJson()});
    </c:forEach>
    
    <c:forEach items="${managers}" var="manager">
    editor.managers.push({
        ${manager.toJson()}
    });
    </c:forEach>
    
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
    editor.units.push(
        '${unit.name}'
    );
    </c:forEach>
    
    <c:forEach items="${shippers}" var="shipper">
    editor.shippers.push(
        '${shipper.value}'
    );
    </c:forEach>
    
    <c:forEach items="${customers}" var="customer">
    editor.customers['${customer}'] = {
        id:'${customer}',
        value:'<fmt:message key="${customer}"/>'
    };
    </c:forEach>
    
    <%--<c:choose>--%>
    <%--<c:when test="${not empty transportation }">--%>
    <%--editor.plan = {--%>
        <%--id:${transportation.id},--%>
        <%--type:'${deal.type}',--%>
        <%--date:'${transportation.date}',--%>
        <%--deal:${transportation.deal.id},--%>
        <%--number:'${deal.number}',--%>
        <%--organisation:{--%>
            <%--id:'${deal.organisation.id}',--%>
            <%--value:"${deal.organisation.value}"--%>
        <%--},--%>
        <%--address:-1,--%>
        <%--product:${deal.product.id},--%>
        <%--quantity:${deal.quantity},--%>
        <%--plan:${transportation.amount},--%>
        <%--from:'${deal.shipper.value}',--%>
        <%--price:${deal.price},--%>
        <%--unit:${deal.unit.id},--%>
        <%--customer:'${transportation.customer}',--%>
        <%--driver:{--%>
            <%--id:-1--%>
        <%--},--%>
        <%--vehicle:{--%>
            <%--id:-1--%>
        <%--},--%>
        <%--trailer:{--%>
            <%--id:-1--%>
        <%--},--%>
        <%--transporter:{--%>
            <%--id:-1--%>
        <%--},--%>
        <%--notes:[]--%>
    <%--};--%>
    <%--<c:if test="${not empty transportation.address}">--%>
    <%--editor.transportation.address = ${transportation.address.id}--%>
    <%--</c:if>--%>
    <%--<c:forEach items="${address}" var="a">--%>
    <%--editor.addressList.push(${a.address.toJson()});--%>
    <%--</c:forEach>--%>
    <%--<c:if test="${not empty transportation.vehicle.id}">--%>
    <%--editor.transportation.vehicle = {--%>
        <%--id:${transportation.vehicle.id},--%>
        <%--model:'${transportation.vehicle.model}',--%>
        <%--number:'${transportation.vehicle.number}'--%>
    <%--};--%>
    <%--<c:if test="${transportation.trailer ne null}">--%>
    <%--editor.transportation.trailer = {--%>
        <%--id:${transportation.trailer.id},--%>
        <%--number:'${transportation.trailer.number}'--%>
    <%--};--%>
    <%--</c:if>--%>
    <%--</c:if>--%>
    <%--<c:if test="${not empty transportation.driver.id}">--%>
    <%--editor.transportation.driver = {--%>
        <%--id:${transportation.driver.id},--%>
        <%--person:{--%>
            <%--surname:'${transportation.driver.person.surname}',--%>
            <%--forename:'${transportation.driver.person.forename}',--%>
            <%--patronymic:'${transportation.driver.person.patronymic}'--%>
        <%--}--%>
    <%--};--%>
    <%--</c:if>--%>
    <%--<c:if test="${not empty transportation.transporter.id}">--%>
    <%--editor.transportation.transporter = {--%>
        <%--id:${transportation.transporter.id},--%>
        <%--value:'${transportation.transporter.value}'--%>
    <%--};--%>
    <%--</c:if>--%>
    <%--<c:forEach items="${transportation.notes}" var="note">--%>
    <%--editor.transportation.notes.push(${note.toJson()});--%>
    <%--</c:forEach>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--editor.transportation.from = editor.shippers[0];--%>
    <%--editor.transportation.unit = editor.units[0].id;--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--editor.role = '${role}';--%>
    <%--<c:if test="${role ne 'weigher'}">--%>
    <%--editor.transportation.manager = ${worker.id};--%>
    <%--</c:if>--%>
    <%--<c:forEach items="${deals}" var="deal">--%>
    <%--editor.deals.push(${deal.toJson()});--%>
    <%--</c:forEach>--%>
</script>
<style>
    .selected{
        font-weight: bold;
        background-color: #e3e3e3;
    }
</style>
<c:set var="editAddressTitle"><fmt:message key="edit.title"/></c:set>
<c:set var="type"><fmt:message key="deal.type"/></c:set>
<table id="editor" class="editor" border="0">
    <%--DATE--%>
    <tr>
        <td>
            <label for="date">
                <fmt:message key="date"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td style="width: 420px">
            <input id="date" readonly style="width: 7em" v-on:click="pickDate()"
                   v-model="new Date(transportation.date).toLocaleDateString()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="number">
                <fmt:message key="transportation.deal.number"/><br>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="number" v-model="transportation.deal.number" autocomplete="off">
        </td>
    </tr>
    <%--ORGANISATION--%>
    <tr :class="{error : errors.organisation}">
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            :
        </td>
        <td>
            <%--!--%>
            <%--!--%>
            <%--ORGANISATION--%>
            <object-input :props="organisationProps" :object="transportation.deal.counterparty"></object-input>
        </td>
    </tr>
    <%--<tr v-if="transportation.deal.organisation.id > 0">--%>
        <%--<td colspan="2" align="right" valign="top">--%>
            <%--<label for="address">--%>
                <%--<fmt:message key="address"/>--%>
            <%--</label>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<div id="address" v-if="addressList.length > 0" style="font-size: 10pt; max-height: 50pt; overflow-y: scroll; border: solid black 1pt; padding: 2pt">--%>
                <%--<div :class="{selected : transportation.address == -1}" v-on:click="transportation.address = -1" >--%>
                    <%--<fmt:message key="not.select"/>--%>
                <%--</div>--%>
                <%--<div title="${editAddressTitle}" v-for="address in addressList"--%>
                     <%--v-on:click="transportation.address = address.id" v-on:dblclick="editAddress(address.id)">--%>
                    <%--<span :class="{selected : transportation.address == address.id}">--%>
                        <%--<span v-if="!address.city">--%>
                            <%--<span v-if="address.region">--%>
                                <%--{{address.region}} <fmt:message key="address.region.short"/>,--%>
                            <%--</span>--%>
                            <%--<span v-if="address.district">--%>
                                <%--{{address.district}} <fmt:message key="address.district.short"/>,--%>
                            <%--</span>--%>
                        <%--</span>--%>
                        <%--{{address.city}}--%>
                        <%--{{address.street}}--%>
                        <%--{{address.build}}--%>
                    <%--</span>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<span class="mini-close" style="font-size: 10pt" v-on:click="editAddress(-1)">--%>
                <%--+ <fmt:message key="add.address"/>--%>
            <%--</span>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--DEAL--%>
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
            <select id="deal" style="width: 300px" v-model="transportation.deal.id"  v-on:change="setQuantity()">
                <option value="-1"><fmt:message key="deal.new"/></option>
                <option :value="deal.id" v-for="deal in deals">
                    <template v-if="deal.number">№ {{deal.number}}</template>
                    <template v-else>{{deal.id}}</template>
                    {{deal.product.name}}, {{(types[deal.type].value).toLowerCase()}}
                </option>
            </select>
        </td>
    </tr>
    <%--PRODUCT--%>

    <tr v-if="transportation.deal.id == -1" :class="{error : errors.product}">
        <td>
            <label for="product">
                <fmt:message key="deal.product"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="product" style="width: 200px" v-model="transportation.deal.product.id"
                    v-on:click="errors.product = false">
                <option v-if="transportation.deal.id == -1" disabled value="-1"><fmt:message key="need.select"/></option>
                <option v-for="product in productList()" :value="product.id">{{product.name}}</option>
            </select>
            <select v-if="transportation.deal.product.id != -1" id="type" title="${type}" v-model="transportation.deal.type" :class="{error : errors.type}" v-on:click="errors.type = false">
                <option v-for="type in typesByProduct()" :value="type">
                    {{typeNames[type]}}
                </option>
            </select>
        </td>
    </tr>
    <%--QUANTITY--%>
    <tr>
        <td>
            <label for="quantity">
                <fmt:message key="deal.quantity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="quantity" type="number" v-model="transportation.deal.quantity" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <%--PRICE--%>
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
            <input id="price" type="number" v-model="transportation.deal.price" onfocus="this.select()" autocomplete="off">
            <label for="from">
                <fmt:message key="deal.from"/>
            </label>
            <select id="from" v-model="transportation.from">
                <option v-for="shipper in shipperList()" :value="shipper">{{shipper}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="load">
                <fmt:message key="load.plan"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="load" type="number" v-model.number="transportation.plan" onfocus="this.select()" autocomplete="off">
            <c:set var="units"><fmt:message key="units"/></c:set>
            <select title="${units}" v-model="transportation.deal.unit">
                <option v-for="unit in units" :value="unit">{{unit}}</option>
            </select>
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
            <select id="customer" v-model="transportation.customer">
                <option v-for="customer in customers" :value="customer.id">{{customer.value}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.driver"/>
        </td>
        <td>
            :
        </td>
        <td>
            <object-input :props="driverProps" :object="transportation.driver" :item="transportation"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.automobile"/>/<fmt:message key="transportation.automobile.trailer"/>
        </td>
        <td>
            :
        </td>
        <td>
            <object-input :props="vehicleProps" :object="transportation.vehicle"></object-input>
            <template v-if="transportation.vehicle.id != -1">
                <object-input :props="trailerProps" :object="transportation.trailer"></object-input>
            </template>

        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.transporter"/>
        </td>
        <td>
            :
        </td>
        <td>
            <object-input :props="transporterProps" :object="transportation.transporter"></object-input>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <div>
                <span v-if="transportation.notes && transportation.notes.length > 0">
                    <label for="note">
                        <fmt:message key="notes"/>
                    </label>
                </span>
                <span class="mini-close" style="font-size: 10pt" v-on:click="editNote()">
                    <fmt:message key="note.add"/>
                </span>
            </div>
            <div>
                <div style="font-size: 10pt" v-for="(note, noteIdx) in transportation.notes">
                    <span class="mini-close" v-on:click="removeNote(noteIdx)">&times;</span>
                    <span v-on:click="editNote(note, noteIdx)">
                        <span v-if="note.creator" style="color: #8b8b8b">
                            {{note.creator}}:
                        </span>
                        <span style="color: #d06845">
                            {{note.note}}
                        </span>

                    </span>
                </div>
            </div>
            <div v-if="note.edit">
                <input id="note" ref="comment" style="border: none; width: 100%" v-model="note.input"
                       v-on:keyup.enter="saveNote()" v-on:blur="saveNote()">
            </div>
        </td>
    </tr>
    <%--<tr v-if="role === 'weigher' || role === 'logistic'">--%>
        <%--<td>--%>
            <%--<label for="manager">--%>
                <%--<fmt:message key="deal.manager"/>--%>
            <%--</label>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--:--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<select id="manager" v-model="transportation.manager" v-on:click="this.errors.manager = false"--%>
                    <%--:class="{error : errors.manager}">--%>
                <%--<option disabled value="-1">--%>
                    <%--<fmt:message key="need.select"/>--%>
                <%--</option>--%>
                <%--<option v-for="manager in managers" :value="manager.id">--%>
                    <%--{{manager.value}}--%>
                <%--</option>--%>
            <%--</select>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()" class="left-button close-button">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-if="!already" v-on:click="save" class="right-button save-button">
                <template v-if="transportation.id > 0">
                    <fmt:message key="button.save"/>
                </template>
                <template v-else>
                    <fmt:message key="button.create"/>
                </template>
            </button>
            <button v-else>
                ...
            </button>
        </td>
    </tr>
</table>
</html>