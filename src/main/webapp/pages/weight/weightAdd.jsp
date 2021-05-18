<%@ page import="utils.U" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css?v=${now}">
<style>
    .deal-attention{
        display: inline-block;
        position: absolute;
        background: aliceblue;
        border: solid 1pt;
        margin: 0 2pt;
        padding: 2pt;
        width: 150pt;
    }
    .deal-attention-title{
        width: 100%;
        text-align: center  ;
        font-weight: bold;
        color: orangered;
    }
</style>
<script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
<script src="${context}/vue/weightAdd.vue?v=${now}"></script>
<script>
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
        edit:'${editTrailer}',
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
    editor.api.selectCounterparty = '${selectCounterparty}';
    editor.api.dealEdit = '${dealEdit}';
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
    editor.managers.push(
        ${manager.toJson()}
    );
    </c:forEach>
    editor.managers.sort(function (a, b) {
        return a.person.value.localeCompare(b.person.value);
    });
    
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
    editor.products.sort(function (a, b) {
        return a.name.localeCompare(b.name);
    });
    
    <c:forEach items="${units}" var="unit">
    editor.units.push({
        id:${unit.id},
        name:'${unit.name}'
    });
    </c:forEach>
    
    <c:forEach items="${shippers}" var="shipper">
    editor.shippers.push({
        id:${shipper.id},
        name:'${shipper.value}'
    });
    </c:forEach>
    
    <c:forEach items="${customers}" var="customer">
    editor.customers['${customer}'] = {
        id:'${customer}',
        value:'<fmt:message key="${customer}"/>'
    };
    </c:forEach>
    <c:choose>
    <c:when test="${not empty transportation}">

    editor.transportation = ${transportation.toJson()};
    editor.transportation.address = -1;
    <c:if test="${not empty transportation.address}">
    editor.transportation.address = ${transportation.address.id};
    </c:if>
    editor.deal = ${transportation.deal.toShortJson()};
    editor.setQuantity();
    editor.findAddress(editor.transportation.deal.counterparty.id);
    editor.initDealsLists();
    managerFounded = false;
    for (let i in editor.managers){
        if (editor.managers.hasOwnProperty(i)){
            let manager = editor.managers[i], managerFounded;
            if (manager.id === editor.transportation.manager.id){
                managerFounded = true;
            }
        }
    }
    if(!managerFounded){
        editor.transportation.manager = {id:-1}
    }
    </c:when>
    <c:otherwise>
    editor.deal = '-1';
    </c:otherwise>
    </c:choose>
</script>
<c:set var="editAddressTitle"><fmt:message key="edit.title"/></c:set>
<c:set var="type"><fmt:message key="deal.type"/></c:set>
<div id="editor" class="editor">
    <div>
        <span style="font-weight: bold; font-size: 12pt">
            <fmt:message key="deals"/>:{{transportation.products.length.toLocaleString()}}
        </span>
    </div>
    <div class="deal-field">
        <table v-for="(product, productId) in transportation.products">
            <tr :class="{error : errors.organisation}">
                <td>
                    <fmt:message key="deal.organisation"/>
                </td>
                <td v-if="product.counterparty.value" class="secure">
                    {{product.counterparty.value}}
                </td>
            </tr>
            <tr v-if="transportation.deal.counterparty && transportation.deal.counterparty.id != -1">
                <td>
                    <label for="address">
                        <fmt:message key="address"/>
                    </label>
                </td>
                <td>
<%--                    <select id="address" v-if="addressList.length > 0" v-model="product.addressId" style="width: 220pt">--%>
<%--                        <option value="-1"><fmt:message key="not.select"/></option>--%>
<%--                        <option v-for="address in addressList" :value="address.id">{{address.city}}<template v-if="address.street">, {{address.street}}</template><template v-if="address.build">, {{address.build}}</template>--%>
<%--                        </option>--%>
<%--                    </select>--%>
<%--                    <span v-if="product.address !== -1" v-on:click="editAddress(transportation.address)">--%>
<%--                        <img style="width: 11pt;" src="${context}/images/smallpensil.svg" alt=""/>--%>
<%--                    </span>--%>
                    <button class="mini-close" v-on:click="editAddress(-1)">
                        <fmt:message key="add.address"/>
                    </button>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="deal">
                        <fmt:message key="deal"/>
                    </label>
                </td>
                <td>
                    <select id="deal" style="width: 100%" v-model="product.dealProduct" v-on:change="setQuantity()" class="secure">
                        <template v-for="deal in product.deals">
                            <option :value="product.id" v-for="product in deal.products">
                                <template v-if="deal.number">№ {{deal.number}}</template>
                                {{product.productName}}, {{(types[deal.type].value).toLowerCase()}}
                                <template v-if="product.price > 0">
                                    {{product.price.toLocaleString()}}
                                </template>
                                {{product.shipperName}}
<%--                            {{deal.product.name}}, {{(types[deal.type].value).toLowerCase()}}, {{deal.price.toLocaleString()}}, {{deal.shipper.name}}--%>
                            </option>
                        </template>
                    </select>
                </td>
            </tr>
            <template v-if="product.dealId === '-1'">
                <%--PRODUCT--%>
                <tr :class="{error : errors.product}">
                    <td>
                        <label for="product">
                            <fmt:message key="deal.product"/>
                        </label>
                    </td>
                    <td>
                        <select id="product" style="width: 200px" v-model="product.productId"
                            v-on:click="errors.product = false" v-on:change="checkDeal()">
                            <option v-if="deal === '-1'" disabled value="-1"><fmt:message key="need.select"/></option>
                            <option v-for="product in productList()" :value="product.id">{{product.name}}</option>
                        </select>
<%--                        <select v-if="transportation.deal.product.id != -1" id="type" title="${type}" v-on:change="checkDeal()"--%>
<%--                                v-model="transportation.deal.type" :class="{error : errors.type}" v-on:click="errors.type = false">--%>
<%--                            <option v-for="type in typesByProduct()" :value="type">--%>
<%--                                {{typeNames[type]}}--%>
<%--                            </option>--%>
<%--                        </select>--%>
                    </td>
                </tr>
            </template>
            <%--QUANTITY--%>
            <tr>
                <td>
                    <fmt:message key="deal.quantity"/>
                </td>
                <td>
                    {{product.amount.toLocaleString()}}
                    {{product.unitName}}
                </td>
            </tr>
            <%--PRICE--%>
            <tr>
                <td>
                    <fmt:message key="deal.price"/>
                </td>
                <td class="secure">
                    <template v-if="product.price > 0">
                        {{product.price.toLocaleString()}}
                    </template>
                    <fmt:message key="deal.from"/>
                    {{product.shipperName}}
                </td>
            </tr>
            <tr>
                <td>
                    <label for="load">
                        <fmt:message key="load.plan"/>
                    </label>
                </td>
                <td>
                    <input id="load" type="number" v-model.number="product.plan" onfocus="this.select()" autocomplete="off">
                    {{product.unitName}}
                </td>
            </tr>
            <tr>
                <td colspan="2" style="width: 100%; text-align: right; font-size: 10pt">
                    <span class="mini-close" v-on:click="selectCounterparty(productId)">
                &#8634;<fmt:message key="transportation.counterparty.change"/>
            </span>
                    <span class="mini-close" v-on:click="dealCopy()">
                <fmt:message key="deal.copy"/>
            </span>
                    <span class="mini-close" v-on:click="dealEdit()">
                <fmt:message key="deal.edit"/>
            </span>
                </td>
            </tr>
        </table>
    </div>
    <div v-if="transportation.products.length === 0">
        <span class="mini-close" v-on:click="selectCounterparty(-1)">
            +<fmt:message key="transportation.counterparty.add"/>
        </span>
    </div>
    <table>
        <tr>
            <td>
                <label for="date">
                    <fmt:message key="date"/>
                </label>
            </td>
            <td>:</td>
            <td>
                <input id="date" readonly style="width: 7em" v-on:click="pickDate()"
                       v-model="new Date(transportation.date).toLocaleDateString()">
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
<%--    DRIVER--%>
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
<%--    VEHICLE + TRAILER--%>
    <tr>
        <td>
            <fmt:message key="transportation.automobile"/>/<fmt:message key="transportation.automobile.trailer"/>
        </td>
        <td>
            :
        </td>
        <td class="secure">
            <object-input :props="vehicleProps" :object="transportation.vehicle"></object-input>
            <object-input :props="trailerProps" :object="transportation.trailer"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.transporter"/>
        </td>
        <td>
            :
        </td>
        <td class="secure">
            <object-input :props="transporterProps" :object="transportation.transporter"></object-input>
        </td>
    </tr>
    <%--MANAGER--%>
    <tr>
        <td>
            <label for="manger">
                <fmt:message key="deal.manager"/>
            </label>
        </td>
        <td>:</td>
        <td>
            <select id="manger" v-model="transportation.manager.id">
                <option disabled value="-1"><fmt:message key="can.select"/></option>
                <option v-for="manager in managers" :value="manager.id">
                    {{manager.person.value}}
                </option>
            </select>
        </td>
    </tr>
    <%--NOTES--%>
    <tr>
        <td colspan="3">
            <div>
                <span v-if="transportation.notes && transportation.notes.length > 0">
                    <label for="note">
                        <fmt:message key="notes"/>
                    </label>
                </span>
                <span v-if="!note.edit" class="mini-close" style="font-size: 10pt" v-on:click="editNote()">
                    <fmt:message key="note.add"/>
                </span>
            </div>
            <div>
                <span style="font-size: 10pt" v-for="(note, noteIdx) in transportation.notes"  class="secure">
                    <template v-if="note.creator" >
                        <span class="mini-close" v-on:click="removeNote(noteIdx)">&times;</span>
                        <span v-on:click="editNote(note, noteIdx)" class="mini-close">
                            <span style="color: #8b8b8b">
                                {{note.creator}}:
                            </span>
                            <span style="color: #d06845">
                                {{note.note}}
                            </span>
                        </span>
                    </template>
                    <template v-else>
                        <span style="color: #d06845">
                                {{note.note}}
                            </span>
                    </template>
                </span>
            </div>
            <c:set var="notePlaceHolder"><fmt:message key="note.placeholder"/> </c:set>
            <div v-if="note.edit" v-on:blur="saveNote()">
                <input id="note" ref="comment" placeholder="${notePlaceHolder}" v-model="note.input" v-on:keyup.escape="clearNote()" v-on:keyup.enter="saveNote()">
                <span class="mini-close" v-on:click="clearNote()">
                    &times;
                </span>
            </div>
        </td>
    </tr>

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
</div>
</html>