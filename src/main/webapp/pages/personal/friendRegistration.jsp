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
                registration:''
            },
            roles:[],
            user:{
                forename:'',
                surname:'',
                patronymic:'',
                email:'',
                role:''
            }
        },
        methods:{
            registration:function(){
                PostApi(this.api.registration, this.user, function(a){
                    console.log(a);
                })
            }
        }
    });
    registrator.api.registration = '${registration}';
    <c:forEach items="${roles}" var="role">
    registrator.roles.push({
        id:'${role}',
        name:'<fmt:message key="${role}"/>'
    });
    </c:forEach>
</script>
<table id="registrator">
    <tr>
        <th colspan="2" align="center">
            <fmt:message key="friend.registration"/>
        </th>
    </tr>
    <tr>
        <td>
            <label for="surname">
                <fmt:message key="person.surname"/>
            </label>
        </td>
        <td>
            <input id="surname" v-model="user.surname">
        </td>
    </tr>
    <tr>
        <td>
            <label for="forename">
                <fmt:message key="person.forename"/>
            </label>
        </td>
        <td>
            <input id="forename" v-model="user.forename">
        </td>
    </tr>
    <tr>
        <td>
            <label for="patronymic">
                <fmt:message key="person.patronymic"/>
            </label>
        </td>
        <td>
            <input id="patronymic" v-model="user.patronymic">
        </td>
    </tr>
    <tr>
        <td>
            <label for="email">
                <fmt:message key="email"/>
            </label>
        </td>
        <td>
            <input id="email" v-model="user.email">
        </td>
    </tr>
    <tr>
        <td>
            <label for="subdivision">
                <fmt:message key="subdivision"/>
            </label>
        </td>
        <td>
            <select id="subdivision" style="width: 100%" v-model="user.role">
                <option v-for="role in roles" :value="role.id">
                    {{role.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button v-on:click="registration">
                <fmt:message key="button.registration"/>
            </button>
        </td>
    </tr>
</table>
</html>