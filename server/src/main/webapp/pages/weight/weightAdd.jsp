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
    editor.managers.push(
        ${manager.toJson()}
    );
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
    editor.transportation = ${transportation.toJson()}
    editor.transportation.deal = ${transportation.deal.toJson()}
    <c:forEach items="${deals}" var="deal">
    editor.deals.push(${deal.toJson()});
    </c:forEach>
    var managerFounded = false;
    for (let i in editor.managers){
        if (editor.managers.hasOwnProperty(i)){
            let manager = editor.managers[i];
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
    editor.transportation.deal.unit = editor.units[0];
    editor.transportation.deal.shipper = editor.shippers[0];
    </c:otherwise>
    </c:choose>
</script>
<style>
    .selected{
        font-weight: bold;
        background-color: #e3e3e3;
    }
</style>
<c:set var="editAddressTitle"><fmt:message key="edit.title"/></c:set>
<c:set var="type"><fmt:message key="deal.type"/></c:set>
    <table id="editor" class="editor">
<%--    DATE--%>
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

    <%--ORGANISATION--%>
    <tr :class="{error : errors.organisation}">
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            :
        </td>
        <td>
            <object-input :props="organisationProps" :object="transportation.deal.counterparty"></object-input>
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
            <select id="deal" style="width: 100%" v-model="transportation.deal.id"  v-on:change="setQuantity()">
                <option value="-1"><fmt:message key="deal.new"/></option>
                <option :value="deal.id" v-for="deal in deals">
                    <template v-if="deal.number">№ {{deal.number}}</template>
                    <template v-else>{{deal.id}}</template>
                    {{deal.product.name}}, {{(types[deal.type].value).toLowerCase()}}, {{deal.price.toLocaleString()}}, {{deal.shipper.name}}
                </option>
            </select>
        </td>
    </tr>
    <template v-if="transportation.deal.id === -1">
        <%--CONTRACT NUMBER--%>
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
        <%--PRODUCT--%>
        <tr :class="{error : errors.product}">
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
                    <option v-if="transportation.deal.id === -1" disabled value="-1"><fmt:message key="need.select"/></option>
                    <option v-for="product in productList()" :value="product.id">{{product.name}}</option>
                </select>
                <select v-if="transportation.deal.product.id != -1" id="type" title="${type}"
                        v-model="transportation.deal.type" :class="{error : errors.type}" v-on:click="errors.type = false">
                    <option v-for="type in typesByProduct()" :value="type">
                        {{typeNames[type]}}
                    </option>
                </select>
            </td>
        </tr>
    </template>
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
            <c:set var="units"><fmt:message key="units"/></c:set>
            <select title="${units}" v-model="transportation.deal.unit">
                <option v-for="unit in units" :value="unit">{{unit.name}}</option>
            </select>
            <%--{{transportation.deal.unit}}--%>
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
            <template v-if="transportation.deal.shipper">
                <select id="from" v-model="transportation.deal.shipper.id">
                    <option v-for="shipper in shipperList()" :value="shipper.id">{{shipper.name}}</option>
                </select>
            </template>
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
            <span v-if="transportation.deal.unit">
                {{transportation.deal.unit.name}}
            </span>
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
        <td>
            <object-input :props="vehicleProps" :object="transportation.vehicle"></object-input>
            <template v-if="transportation.vehicle && transportation.vehicle.id != -1">
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