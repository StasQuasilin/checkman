<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/vehicleEdit.vue"></script>
<script>
    editor.api.findOrganisation = '${findOrganisationAPI}';
    editor.api.saveVehicleAPI = '${saveVehicleAPI}';
    editor.transportationId = ${transportation}
    editor.vehicleId = ${vehicle.id}
    editor.vehicleModel = '${vehicle.model}';
    editor.vehicleNumber = '${vehicle.number}';
    <c:if test="${not empty vehicle.trailer}">
    editor.vehicleTrailer = '${vehicle.trailer.number}';
    </c:if>

    <c:if test="${not empty vehicle.transporter}">
        editor.transporterId = ${vehicle.transporter.id};
        editor.transporterInput = ${vehicle.transporter.value}
    </c:if>

</script>
<table id="vehicleEditor">
    <tr>
        <td>
            <label for="model">
                <fmt:message key="transportation.automobile.model"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="model" v-model="vehicleModel" v-on:keyup.enter="save" autocomplete="off" onfocus="this.select()" autofocus>
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
            <input id="number" v-model="vehicleNumber" v-on:keyup.enter="save" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="trailer">
                <fmt:message key="transportation.automobile.trailer"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="trailer" v-model="vehicleTrailer" v-on:keyup.enter="save" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center" v-on:click="close">
            <c:if test="${role eq 'admin' or transportationsCount == 0}">
                <a>
                    <fmt:message key="button.delete"/>
                </a>
            </c:if>
            <button class="left-button close-button">
                <fmt:message key="button.cancel"/>
            </button>
            <button class="right-button" v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>