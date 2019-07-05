<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/vehicleEdit.vue"></script>
<script>
    editor.api.findOrganisation = '${findOrganisationAPI}'
    editor.api.saveVehicleAPI = '${saveVehicleAPI}'
    editor.transportationId = ${transportation}
    editor.vehicleId = ${vehicle.id}
    editor.vehicleModel = '${vehicle.model}';
    editor.vehicleNumber = '${vehicle.number}';
    editor.vehicleTrailer = '${vehicle.trailer}';
    <c:if test="${not empty vehicle.transporter}">
        editor.transporterId = ${vehicle.transporter.id};
        editor.transporterInput = ${vehicle.transporter.value}
    </c:if>

</script>
<table id="vehicleEditor">
    <tr>
        <td>
            <label for="model">
                <fmt:message key="transportation.automobile.model"/>*
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="model" v-model="vehicleModel">
        </td>
    </tr>
    <tr>
        <td>
            <label for="number">
                <fmt:message key="transportation.automobile.number"/>*
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="number" v-model="vehicleNumber">
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
            <input id="trailer" v-model="vehicleTrailer">
        </td>
    </tr>
    <tr>
        <td>
            <label for="transporter">
                <fmt:message key="transportation.transporter"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <div style="display: inline-block">
                <input id="transporter" v-model="transporterInput">
                <div class="custom-data-list"></div>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center" v-on:click="close">
            <button>
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>