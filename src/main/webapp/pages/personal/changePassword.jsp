<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var change = new Vue({
        el: '#change',
        data:{
            api:{
                change:''
            },
            password:{
                current:'',
                password:''
            },
            repeat:'',
            err:'',
            success:false
        },
        computed:{
            doesntMatch:function(){
                return this.password.password & this.repeat & this.password.password !== this.repeat;
            }
        },
        methods:{
            change:function(){
                const self = this;
                if (this.password.current && this.password.password) {
                    this.password.current = btoa(this.password.current);
                    this.password.password = btoa(this.password.password);
                    PostApi(this.api.change, this.password, function (a) {
                        if (a.status == 'success') {
                            self.success = true;
                            self.password.current = '';
                            self.password.password = '';
                            self.repeat= '';
                            self.err ='';
                        } else {
                            self.err = a.msg;
                            self.success = false;
                        }
                    })
                }
            }
        }
    });
    change.api.change='${changePassword}'
</script>
<table id="change">
    <tr>
        <th colspan="3">
            <fmt:message key="password.change"/>
        </th>
    </tr>
    <tr>
        <td>
            <label for="currentPassword">
                <fmt:message key="pasword.change.current"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input type="password" id="currentPassword" autocomplete="off" v-model="password.current">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="right">
            <span class="error" v-if="err">
                {{err}}
            </span>
            <span v-else class="error" >
                &nbsp;
            </span>
        </td>
    </tr>
    <tr>
        <td>
            <label for="newPassword">
                <fmt:message key="password.change.new"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input type="password" id="newPassword" autocomplete="off" v-model="password.password">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="right">
            <span class="error" v-if="doesntMatch">
                <fmt:message key="pasword.doesnt.match"/>
            </span>
            <span v-else class="error" >
                &nbsp;
            </span>
        </td>
    </tr>
    <tr>
        <td>
            <label for="repeatPassword">
                <fmt:message key="password.change.repeat"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input type="password" id="repeatPassword" autocomplete="off" v-model="repeat">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button v-on:click="change">
                <fmt:message key="password.change"/>
            </button>
            <span v-if="success" style="color: green">
                &#10003;
            </span>
        </td>
    </tr>
</table>
</html>