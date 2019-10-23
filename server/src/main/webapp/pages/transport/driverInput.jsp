<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/personEdit.vue"></script>
<script>
    editor.api.saveDriverAPI = '${saveDriverAPI}';
    editor.api.find = '${find}';
    editor.api.parse = '${parse}';
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

    editor.transportationId = '${transportation}'
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
        <td>
            <label for="transporter">
                <fmt:message key="transportation.transporter"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <span v-if="transporter != -1">
                <span>
                    {{input.transporter}}
                </span>
                <span class="mini-close">
                    E
                </span>
                <span class="mini-close">
                    &times;
                </span>
            </span>
            <div v-else v-on:blur="parseOrganisation()">
                <input id="transporter" v-model="input.transporter" autocomplete="off"
                    v-on:keyup="findOrganisation()" v-on:keyup.enter="parseOrganisation()">
                <div class="custom-data-list">
                    <div class="custom-data-list-item" v-for="organisation in arr.organisations"
                         v-on:click="setOrganisation(organisation)">
                        {{organisation.value}}
                    </div>
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
    <tr>
        <td colspan="3" style="font-size: 8pt; color: darkgray">
            <fmt:message key="transportation.quantity"/>:&nbsp;${transportations}
        </td>
    </tr>
</table>
</html>