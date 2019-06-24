<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/personEdit.js"></script>
<script>
    editor.api.saveDriverAPI = '${saveDriverAPI}';
    editor.person.id = '${driver.id}';
    editor.person.forename = '${driver.person.forename}';
    editor.person.surname = '${driver.person.surname}';
    editor.person.patronymic = '${driver.person.patronymic}';
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
            <input id="surname" v-model="person.surname" autocomplete="off" <c:if test="${transportations gt 1}">readonly</c:if>>
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
            <input id="forename" v-model="person.forename" autocomplete="off"  <c:if test="${transportations gt 1}">readonly</c:if>>
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
            <input id="patronymic" v-model="person.patronymic" autocomplete="off" <c:if test="${transportations gt 1}">readonly</c:if>>
        </td>
    </tr>
    <tr>
        <td colspan="3" style="font-size: 8pt; color: darkgray">
            <fmt:message key="transportation.quantity"/>:&nbsp;${transportations}
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