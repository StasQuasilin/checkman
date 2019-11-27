<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script src="${context}/vue/personEdit.vue"></script>
<script>

    editor.trailerProps = {
        find : '${findTrailer}',
        addHeade : '<fmt:message key="button.add"/>',
        header : '<fmt:message key="button.add.trailer"/>',
        show:['number'],
        put : editor.putTrailer,
    };
    editor.vehicleProps = {
        find : '${findVehicle}',
        edit:'${vehicleEdit}',
        addHeader : '<fmt:message key="button.add"/>',
        header : '<fmt:message key="button.add.vehicle"/>',
        show:['model', 'number'],
        put : function(vehicle){
            editor.putVehicle(vehicle);
            if (vehicle.trailer){
                editor.putTrailer(vehicle.trailer);
            }
        }
    };
    editor.transporterProps = {
        find:'${find}',
        edit:'${organisationEdit}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.transporter"/>',
        show:['value'],
        put:editor.setOrganisation
    };
    editor.api.save = '${save}';
    editor.api.find = '${find}';
    editor.api.parse = '${parse}';
    editor.api.editOrganisation = '${organisationEdit}';
    editor.person.id = '${driver.id}';
    editor.person.forename = '${driver.person.forename}';
    editor.person.surname = '${driver.person.surname}';
    editor.person.patronymic = '${driver.person.patronymic}';
    editor.license = '${driver.license}';
    editor.transporter = {};
    <c:if test="${driver.organisation ne null}">
    editor.transporter = {
        id:${driver.organisation.id},
        value:'${driver.organisation.value}'
    };
    </c:if>
    <c:if test="${driver.vehicle ne null}">
    editor.vehicle = {
        id:${driver.vehicle.id},
        model:'${driver.vehicle.model}',
        number:'${driver.vehicle.number}',
        value:'${driver.vehicle.model} ${driver.vehicle.number}'
    }
    <c:if test="${driver.vehicle.trailer ne null}">
    editor.trailer = {
        id:${driver.vehicle.trailer.id},
        number:'${driver.vehicle.trailer.number}',
        value:'${driver.vehicle.trailer.number}'

    }
    </c:if>
    </c:if>
    editor.transportationId = '${transportation}'
    editor.editOrganisation = function(id){
        const self = editor;
        loadModal(editor.api.editOrganisation, {id:id}, function(a){
            console.log(a);
            console.log(editor.setOrganisation)
            if (a.status === 'success'){
                self.setOrganisation(a.organisation);
            }
        });
    }
</script>

<table id="personEditor" width="100%">
    <tr>
        <td>
            <label for="surname">
                <fmt:message key="person.surname"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="surname" v-model="person.surname" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="forename">
                <fmt:message key="person.forename"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="forename" v-model="person.forename" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="patronymic">
                <fmt:message key="person.patronymic"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="patronymic" v-model="person.patronymic" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="license">
                <fmt:message key="driver.license"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="license" autocomplete="off" v-model="license">
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <fmt:message key="transportation.transporter"/>:
            <vehicle-input v-if="m" :props="transporterProps" :object="transporter"></vehicle-input>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <fmt:message key="transportation.automobile"/>/<fmt:message key="transportation.automobile.trailer"/>:
            <vehicle-input v-if="m" :props="vehicleProps" :object="vehicle"></vehicle-input>
            <vehicle-input v-if="m" :props="trailerProps" :object="trailer"></vehicle-input>
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