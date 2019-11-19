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
        add : '<fmt:message key="button.add"/>',
        header : '<fmt:message key="button.add.trailer"/>',
        put : editor.putTrailer,
        open:false
    };
    editor.vehicleProps = {
        find : '${findVehicle}',
        edit:'${vehicleEdit}',
        add : '<fmt:message key="button.add"/>',
        header : '<fmt:message key="button.add.vehicle"/>',
        put : editor.putVehicle,
        trailerProps:editor.trailerProps
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
    editor.transporter = -1;
    <c:if test="${driver.organisation ne null}">
    editor.transporter = ${driver.organisation.id};
    editor.input.transporter = '${driver.organisation.value}';
    </c:if>
    <c:if test="${driver.vehicle ne null}">
    editor.vehicle = {
        id:${driver.vehicle.id},
        model:'${driver.vehicle.model}',
        number:'${driver.vehicle.number}'

    }
    <c:if test="${driver.vehicle.trailer ne null}">
    editor.trailer = {
        id:${driver.vehicle.trailer.id},
        number:'${driver.vehicle.trailer.number}'
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
<style>
    .vehicle-block{
        background-color: lightgray;
        border: solid 1pt;
        padding: 0 4pt;
        border-radius: 4pt;
        font-size: 10pt;
        margin: 0 2pt;
    }
</style>
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
            <label for="transporter">
                <fmt:message key="transportation.transporter"/>
            </label>
            <span v-if="transporter != -1" class="vehicle-block">
                <a v-on:click="editOrganisation(transporter)">
                    {{input.transporter}}
                </a>
                <span class="mini-close" v-on:click="cancelOrganisation()">
                    &times;
                </span>
            </span>
            <span v-else>
                <input id="transporter" v-model="input.transporter" autocomplete="off"
                    v-on:keyup="findOrganisation()">
                <div class="custom-data-list" v-if="arr.organisations.length > 0 || input.transporter">
                    <div class="custom-data-list-item" v-for="organisation in arr.organisations"
                         v-on:click="setOrganisation(organisation)">
                        {{organisation.value}}
                    </div>
                    <div class="custom-data-list-item" v-on:click="parseOrganisation()">
                        <b>
                            +<fmt:message key="button.add"/> {{input.transporter}}
                        </b>
                    </div>
                </div>
            </span>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <fmt:message key="transportation.automobile"/>/<fmt:message key="transportation.automobile.trailer"/>:
            <vehicle-input v-if="m" :props="vehicleProps" :vehicle="vehicle"></vehicle-input>
            <vehicle-input v-if="m" :props="trailerProps" :vehicle="trailer"></vehicle-input>
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