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
    <c:forEach items="${managers}" var="manager">
    editor.managers.push({
        id:${manager.id},
        value:'${manager.person.value}'
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
    editor.units.push({
        id:${unit.id},
        value:'${unit.name}'
    });
    </c:forEach>
    <c:forEach items="${shippers}" var="shipper">
    editor.visibles.push(
        '${shipper.value}'
    );
    </c:forEach>
    <c:forEach items="${customers}" var="customer">
    editor.customers['${customer}'] = {
        id:'${customer}',
        value:'<fmt:message key="${customer}"/>'
    };
    </c:forEach>
    <c:choose>
    <c:when test="${not empty plan }">
    editor.plan = {
        id:${plan.id},
        type:'${plan.deal.type}',
        date:'${plan.date}',
        deal:${plan.deal.id},
        number:'${plan.deal.number}',
        organisation:{
            id:'${plan.deal.organisation.id}',
            value:"${plan.deal.organisation.value}"
        },
        address:-1,
        product:${plan.deal.product.id},
        quantity:${plan.deal.quantity},
        plan:${plan.plan},
        from:'${plan.deal.shipper.value}',
        price:${plan.deal.price},
        unit:${plan.deal.unit.id},
        customer:'${plan.customer}',
        driver:{
            id:-1
        },
        vehicle:{
            id:-1
        },
        trailer:{
            id:-1
        },
        transporter:{
            id:-1
        },
        notes:[]
    };
    <c:if test="${not empty plan.transportation.address}">
    editor.plan.address = ${plan.transportation.address.id}
    </c:if>
    <c:forEach items="${address}" var="a">
    editor.addressList.push(${a.address.toJson()});
    </c:forEach>
    editor.deals.push({
        id:${plan.deal.id},
        type:'${plan.deal.type}',
        date:'${plan.deal.date}',
        date_to:'${plan.deal.dateTo}',
        product:{
            id:${plan.deal.product.id},
            name:'${plan.deal.product.name}'
        },
        visibility:'${plan.deal.shipper.value}',
        unit:${plan.deal.unit.id},
        price:${plan.deal.price}
    });

    <c:if test="${not empty plan.transportation.vehicle.id}">
    editor.plan.vehicle = {
        id:${plan.transportation.vehicle.id},
        model:'${plan.transportation.vehicle.model}',
        number:'${plan.transportation.vehicle.number}'
    };
    <c:if test="${plan.transportation.trailer ne null}">
    editor.plan.trailer = {
        id:${plan.transportation.trailer.id},
        number:'${plan.transportation.trailer.number}'
    };
    </c:if>
    </c:if>
    <c:if test="${not empty plan.transportation.driver.id}">
    editor.plan.driver = {
        id:${plan.transportation.driver.id},
        person:{
            surname:'${plan.transportation.driver.person.surname}',
            forename:'${plan.transportation.driver.person.forename}',
            patronymic:'${plan.transportation.driver.person.patronymic}'
        }
    };
    </c:if>
    <c:if test="${not empty plan.transportation.transporter.id}">
    editor.plan.transporter = {
        id:${plan.transportation.transporter.id},
        value:'${plan.transportation.transporter.value}'
    };
    </c:if>

    <c:forEach items="${plan.transportation.notes}" var="note">
    editor.plan.notes.push(${note.toJson()});
    </c:forEach>
    </c:when>
    <c:otherwise>
    editor.plan = {
        id:-1,
        type:-1,
        date:new Date().toISOString().substring(0, 10),
        number:'',
        deal:-1,
        quantity:0,
        organisation:{
            id:-1
        },
        address:-1,
        product:-1,
        plan:20,
        from:editor.visibles[0],
        price:0,
        unit:editor.units[0].id,
        customer:'szpt',
        vehicle:{
            id:-1
        },
        trailer:{
            id:-1
        },
        driver:{
            id:-1
        },
        transporter:{
            id:-1
        },
        manager:-1,
        notes:[]
    };
    </c:otherwise>
    </c:choose>
    editor.role = '${role}';
    <c:if test="${role ne 'weigher'}">
    editor.plan.manager = ${worker.id}
    </c:if>
</script>
<table id="editor" class="editor" style="width: 480pt" border="0">
    <%--DEAL TYPE--%>
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
            <select id="type" v-model="plan.type" :class="{error : errors.type}" v-on:click="errors.type = false">
                <option disabled value="-1">
                    <fmt:message key="need.select"/>
                </option>
                <option v-for="type in typeList()" :value="type.id">{{type.value}}</option>
            </select>
        </td>
    </tr>
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
        <td>
            <input id="date" readonly style="width: 7em" v-on:click="pickDate()"
                   v-model="new Date(plan.date).toLocaleDateString()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="number">
                <fmt:message key="transportation.automobile.number"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="number" v-model="plan.number" autocomplete="off">
        </td>
    </tr>
    <%--ORGANISATION--%>
    <tr>
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
            <object-input :props="organisationProps" :object="plan.organisation"></object-input>
        </td>
    </tr>
    <tr v-if="plan.organisation.id > 0">
        <td>
            <label for="address">
                <fmt:message key="address"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="address" style="width: 300px" v-on:dblclick="editAddress(plan.address)"
                    v-if="addressList.length > 0" v-model="plan.address">
                <option value="-1" disabled><fmt:message key="can.select"/></option>
                <option v-for="address in addressList" :value="address.id">
                    {{address.city}}
                    {{address.street}}
                    {{address.build}}
                </option>
            </select>
            <span class="mini-close" style="font-size: 10pt" v-on:click="editAddress(-1)">
                <fmt:message key="add.address"/>
            </span>
        </td>
    </tr>
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
            <select id="deal" style="width: 300px" v-model="plan.deal"  v-on:change="setQuantity()">
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
    <tr v-if="plan.deal == -1">
        <td>
            <label for="product">
                <fmt:message key="deal.product"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="product" style="width: 200px" v-model="plan.product" :class="{error : errors.product}"
                    v-on:click="errors.product = false">
                <option v-if="plan.deal == -1" disabled value="-1"><fmt:message key="need.select"/></option>
                <option v-for="product in productList()" :value="product.id">{{product.name}}</option>
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
            :
        </td>
        <td>
            <input id="quantity" type="number" v-model="plan.quantity" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <%--QUANTITY--%>

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
            <input id="price" type="number" v-model="plan.price" onfocus="this.select()" autocomplete="off">
            <label for="from">
                <fmt:message key="deal.from"/>
            </label>
            <select id="from" v-model="plan.from">
                <option v-for="visible in visibleList()" :value="visible">{{visible}}</option>
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
            <input id="load" type="number" v-model.number="plan.plan" onfocus="this.select()" autocomplete="off">
            <c:set var="units"><fmt:message key="units"/></c:set>
            <select title="${units}" v-model="plan.unit">
                <option v-for="unit in units" :value="unit.id">{{unit.value}}</option>
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
            <select id="customer" v-model="plan.customer">
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
                <object-input :props="driverProps" :object="plan.driver" :item="plan"></object-input>
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
            <object-input :props="vehicleProps" :object="plan.vehicle"></object-input>
            <object-input :props="trailerProps" :object="plan.trailer"></object-input>
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
            <object-input :props="transporterProps" :object="plan.transporter"></object-input>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <div>
                <span v-if="plan.notes && plan.notes.length > 0">
                    <label for="note">
                        <fmt:message key="notes"/>
                    </label>
                </span>
                <span class="mini-close" style="font-size: 10pt" v-on:click="editNote()">
                    <fmt:message key="note.add"/>
                </span>
            </div>
            <div>
                <div style="font-size: 10pt" v-for="(note, noteIdx) in plan.notes">
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
    <tr v-if="role === 'weigher'">
        <td>
            <label for="manager">
                <fmt:message key="deal.manager"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="manager" v-model="plan.manager" v-on:click="this.errors.manager = false"
                    :class="{error : errors.manager}">
                <option disabled value="-1">
                    <fmt:message key="need.select"/>
                </option>
                <option v-for="manager in managers" :value="manager.id">
                    {{manager.value}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()" class="left-button close-button">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-if="!already" v-on:click="save" class="right-button save-button">
                <fmt:message key="button.save"/>
            </button>
            <button v-else>
                ...
            </button>
        </td>
    </tr>
</table>
</html>