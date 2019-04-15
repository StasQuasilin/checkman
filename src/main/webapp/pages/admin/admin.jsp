<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var registrator = new Vue({
        el: '#registrator',
        data:{
            api:{
                find:'',
                registration:''
            },
            roles:[],
            foundPersons:[],
            user:{
                surname:'',
                forename:'',
                patronymic:'',
                role:'',
                email:''
            }
        },
        methods:{
            registration:function(){
                var users = [];
                users.push(this.user);
                var par = {};
                par.users = users;
                PostApi(this.api.registration, par, function(a){
                    alert(a.status);
                })
            }
        }
    });
    registrator.api.find = '${find}';
    registrator.api.registration = '${registration}';
    <c:forEach items="${roles}" var="role">
    registrator.roles.push({
        value:'${role}'
    });
    </c:forEach>
</script>

<table id="registrator">
    <tr>
        <th colspan="3">
            <fmt:message key="user.registration"/>
        </th>
    </tr>
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
            <input id="surname" v-model="user.surname" autocomplete="off">
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
            <input id="forename" v-model="user.forename" autocomplete="off">
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
            <input id="patronymic" v-model="user.patronymic" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="role">
                <fmt:message key="role"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="role" v-model="user.role">
                <option v-for="role in roles">{{role.value}}</option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="email">
                <fmt:message key="email"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="email" v-model="user.email" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button v-on:click="registration">
                <fmt:message key="button.registration"/>
            </button>
        </td>
    </tr>
</table>
</html>