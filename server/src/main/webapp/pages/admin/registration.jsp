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
                role:''-1,
                email:''
            },
            err:{
                surname:false,
                forename:false,
                patronymic:false,
                role:false,
                email:false
            },
            already:false
        },
        methods:{
            registration:function(){
                if (!this.already) {
                    var e1 = this.err.surname = this.user.surname === '';
                    var e2 = this.err.forename = this.user.forename == '';
                    var e3 = this.err.patronymic = this.user.patronymic == '';
                    var e4 = this.err.role = this.user.role == -1;
                    var e5 = this.err.email = this.user.email === '';
                    if (!e1 && !e2 && !e3 && !e4 && !e5) {
                        this.already = true;
                        var users = [];
                        users.push(this.user);
                        var par = {};
                        par.users = users;
                        const self = this;
                        PostApi(this.api.registration, par, function (a) {
                            alert(a.status);
                            self.already = false;
                            self.clear();
                        }, function (e) {
                            console.log(e);
                            self.already = false;
                        })
                    }
                }
            },
            clear:function(){
                this.user.surname='';
                this.user.forename='';
                this.user.patronymic='';
                this.user.role=-1;
                this.user.email='';
            }
        }
    });
    registrator.api.find = '${find}';
    registrator.api.registration = '${registration}';
    <c:forEach items="${roles}" var="r">
    <c:if test="${r ne 'guest'}">
    registrator.roles.push({
        id:'${r}',
        value: '<fmt:message key="role.${r}"/>'
    });
    </c:if>
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
            <input id="surname" v-model="user.surname" v-on:click="err.surname = false"
                   onclick="this.select()" autocomplete="off" :class="{error : err.surname}">
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
            <input id="forename" v-model="user.forename" v-on:click="err.forename = false"
                   onclick="this.select()" autocomplete="off" :class="{error : err.forename}">
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
            <input id="patronymic" v-model="user.patronymic" v-on:click="err.patronymic = false"
                   onclick="this.select()" autocomplete="off" :class="{error : err.patronymic}">
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <label for="role">
                <fmt:message key="user.group"/>
            </label>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;
        </td>
        <td>
            &nbsp;
        </td>
        <td>
            <select id="role" v-model="user.role" style="width: 100%" v-on:click="err.role = false" :class="{error : err.role}">
                <option disabled value="-1"><fmt:message key="need.select"/></option>
                <option v-for="role in roles" :value="role.id">{{role.value}}</option>
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
            <input id="email" v-model="user.email" autocomplete="off" v-on:click="err.email = false"
                onclick="this.select()" :class="{error : err.email}">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button v-on:click="registration">
                <span v-if="already">
                    <fmt:message key="button.registration.process"/>...
                </span>
                <span v-else>
                    <fmt:message key="button.registration"/>
                </span>
            </button>
        </td>
    </tr>
</table>
</html>