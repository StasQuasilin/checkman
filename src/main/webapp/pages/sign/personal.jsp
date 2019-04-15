<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var editor = new Vue({
        el: '#editor',
        data:{
            person:{
                forename:'',
                surname:'',
                patronymic:'',
                phones:[],
                newForename:'',
                newSurname:'',
                newPatronymic:'',
                newPhones:[]
            }
        },
        computed:{
            ne:function(){
                return this.person.forename != this.person.newForename ||
                        this.person.surname != this.person.newSurname ||
                        this.person.patronymic != this.person.newPatronymic;
            }
        },
        methods:{
            editNumber:function(id, edit){
                this.person.phones[id].edit = edit;
            }
        }
    });
    editor.person.surname = editor.person.newSurname = '${worker.person.surname}';
    editor.person.forename = editor.person.newForename = '${worker.person.forename}';
    editor.person.patronymic = editor.person.newPatronymic = '${worker.person.patronymic}';
    <c:forEach items="${worker.person.phones}" var="phone">
    editor.person.phones.push({
        id:${phone.id},
        number:'${phone.number}',
        edit:false
    });
    </c:forEach>
</script>
<table id="editor">
    <tr>
        <td>
            <fmt:message key="password.change"/>
        </td>
        <td valign="top">
            <table>
                <tr>
                    <th colspan="3">
                        <fmt:message key="personal.data"/>
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
                        <input id="surname" v-model="person.newSurname">
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
                        <input id="forename" v-model="person.newForename">
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
                        <input id="patronymic" v-model="person.newPatronymic">
                    </td>
                </tr>
                <tr>
                    <th colspan="3">
                        <fmt:message key="phones"/>
                        <span class="mini-close">+</span>

                    </th>
                </tr>
                <tr v-for="(value, key) in person.phones">
                    <td colspan="3" align="right">
                        <span class="mini-close">&times</span>
                        <span v-if="value.edit">
                            <input v-model="value.number" v-on:keyup.enter="editNumber(key, false)">
                        </span>
                        <span v-else v-on:click="editNumber(key, true)">
                            {{value.number}}
                        </span>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="center">
                        <template v-if="ne">
                            <button>
                                <fmt:message key="button.cancel"/>
                            </button>
                            <button>
                                <fmt:message key="button.save"/>
                            </button>
                        </template>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</html>