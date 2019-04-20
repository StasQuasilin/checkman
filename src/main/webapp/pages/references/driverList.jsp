<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/referenceList.js"></script>
<script>
    referenceList.sort = function(){
        this.items.sort(function(a, b){
            var aP = a.person;
            var bP = b.person;
            return aP.surname !== bP.surname ? aP.surname.localeCompare(bP.surname) : aP.forename.localeCompare(bP.forename);
        })
    };
    referenceList.api.update='${update}';
    referenceList.update();
</script>
<style>
    .selectable:hover{
        background-color: coral;
    }
</style>
<div id="referencesList" style="width: 100%">
    <table width="100%">
        <tr>
            <th width="20%">
                <fmt:message key="person.surname"/>
            </th>
            <th width="20%">
                <fmt:message key="person.forename"/>
            </th>
            <th width="20%">
                <fmt:message key="person.patronymic"/>
            </th>
            <th width="40%">
                <fmt:message key="transportation.automobile"/>
            </th>
        </tr>
        <tr v-for="driver in items" class="selectable">
            <td>
                {{driver.person.surname}}
            </td>
            <td>
                {{driver.person.forename}}
            </td>
            <td>
                {{driver.person.patronymic}}
            </td>
            <td>
                <template v-if="driver.vehicle.id">
                    {{driver.vehicle.model}}
                    '{{driver.vehicle.number}}'
                    {{driver.vehicle.trailer}}
                </template>

            </td>
        </tr>
    </table>
</div>
</html>