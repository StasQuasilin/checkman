<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 23.03.20
  Time: 11:43
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/editor.css">
    <script src="${context}/vue/templates/vehicleInput.vue"></script>
    <script src="${context}/vue/transport/fastTransportation.vue"></script>
    <script>
        edit.api.save = '${save}';
        edit.api.findDeals = '${findDeals}';
        <c:forEach items="${products}" var="product">
        edit.products.push(${product.toJson()});
        </c:forEach>
        <c:forEach items="${workers}" var="worker">
        edit.managers.push(${worker.toJson()});
        </c:forEach>
        <c:forEach items="${actions}" var="action">
        edit.addAction(${action.toShortJson()});
        </c:forEach>
        edit.organisationProps = {
            find:'${findOrganisation}',
            edit:'${editOrganisation}',
            add:'${parseOrganisation}',
            addHeader:'<fmt:message key="button.add"/>',
            header:'<fmt:message key="counterparty.add"/>',
            put:edit.putOrganisation,
            show:['value']
        };
        edit.driverProps = {
            find:'${findDriver}',
            edit:'${editDriver}',
            add:'${parseDriver}',
            addHeader:'<fmt:message key="button.add"/>',
            header:'<fmt:message key="driver.add"/>',
            put:edit.putDriver,
            show:['person/surname', 'person/forename', 'person/patronymic']
        };
        edit.vehicleProps = {
            find:'${findVehicle}',
            edit:'${editVehicle}',
            add:'${parseVehicle}',
            addHeader:'<fmt:message key="button.add"/>',
            header:'<fmt:message key="button.add.vehicle"/>',
            put:edit.putVehicle,
            show:['model', 'number']
        };
        edit.trailerProps = {
            find:'${findTrailer}',
            add:'${parseTrailer}',
            addHeader:'<fmt:message key="button.add"/>',
            header:'<fmt:message key="button.add.trailer"/>',
            put:edit.putTrailer,
            show:['number']
        };
        edit.transporterProps = {
            find:'${findOrganisation}',
            edit:'${editOrganisation}',
            add:'${parseOrganisation}',
            addHeader:'<fmt:message key="button.add"/>',
            header:'<fmt:message key="button.add.transporter"/>',
            put:edit.putTransporter,
            show:['value']
        }
    </script>
    <table id="editor" width="404px">
        <tr>
            <td>
                <fmt:message key="date"/>
            </td>
            <td style="width: 72%">
                <span>
                    {{new Date(transportation.date).toLocaleDateString()}}
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.organisation"/>
            </td>
            <td>
                <object-input :props="organisationProps" :object="transportation.counterparty" :item="transportation"></object-input>
            </td>
        </tr>
        <tr>
            <td>
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
            <td>
                <select id="product" v-model="transportation.product" v-on:change="dealByProduct()">
                    <option disabled value="-1"><fmt:message key="need.select"/></option>
                    <option v-for="p in products" :value="p.id">
                        {{p.name}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.driver"/>
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
                <object-input :props="vehicleProps" :object="transportation.vehicle" :item="transportation"></object-input>
                <object-input v-if="transportation.vehicle" :props="trailerProps"
                              :object="transportation.trailer" :item="transportation"></object-input>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.transporter"/>
            </td>
            <td>
                <object-input :props="transporterProps" :object="transportation.transporter" :item="transportation"></object-input>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.manager"/>
            </td>
            <td>
                <select id="manager" v-model="transportation.manager">
                    <option value="-1"><fmt:message key="can.select"/></option>
                    <option v-for="m in managers" :value="m.id">
                        {{m.person.value}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="2">
                <button onclick="closeModal()">
                    <fmt:message key="button.close"/>
                </button>
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
