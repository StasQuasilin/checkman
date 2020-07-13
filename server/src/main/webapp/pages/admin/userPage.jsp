<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sc" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/admin/userPage.vue"></script>
<script>
    userPage.api.save = '${save}';
    userPage.api.delete = '${delete}';
    userPage.worker = ${worker.toJson()};
    userPage.worker.password = atob('${password}');
    <sc:forEach items="${roles}" var="role">
    userPage.roles.push({
        id:'${role}',
        value:'<fmt:message key="role.${role}"/>'
    });
    </sc:forEach>
</script>
    <table id="page" v-if="worker">
        <tr>
            <td>
                <label for="surname">
                    <fmt:message key="person.surname"/>
                </label>
            </td>
            <td>
                <input id="surname" v-model="worker.person.surname" onfocus="this.select()" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td>
                <label for="forename">
                    <fmt:message key="person.forename"/>
                </label>
            </td>
            <td>
                <input id="forename" v-model="worker.person.forename" onfocus="this.select()" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td>
                <label for="patronymic">
                    <fmt:message key="person.patronymic"/>
                </label>
            </td>
            <td>
                <input id="patronymic" v-model="worker.person.patronymic" onfocus="this.select()" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td>
                <label for="role">
                    <fmt:message key="role"/>
                </label>
            </td>
            <td>
                <select id="role" v-model="worker.role">
                    <option v-for="role in roles" :value="role.id">
                        {{role.value}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="password">
                    <fmt:message key="user.password"/>
                </label>
            </td>
            <td>
                <input id="password" v-model="worker.password" onfocus="this.select()" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="button.close"/>
                </button>
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <span class="mini-close" v-on:click="deleteUser()">
                    <fmt:message key="button.delete"/>
                    ( <fmt:message key="without.warning"/> )
                </span>
            </td>
        </tr>
    </table>
</html>
