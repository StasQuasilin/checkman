<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var language = new Vue({
        el:'#languageBase',
        data:{
            api:{
                save:''
            },
            languages:[],
            language:'',
            lang:''
        },
        methods:{
            save:function(){
                PostApi(this.api.save, {lang : this.language}, function(a){
                    if (a.status === 'success'){
                        location.reload()
                    }
                })
            }

        }
    });
    language.api.save='${changeLanguage}';
    <c:forEach items="${languages}" var="language">
    language.languages.push({
        id:'${language}',
        value:'<fmt:message key="${language}"/>'
    });
    </c:forEach>
    language.language = '${lang}';
    language.lang = '${lang}'

</script>
<div style="padding: 18pt">
    <table border="0">
        <tr>
            <td colspan="3" id="languageBase">
                <label for="language">
                    <fmt:message key="language"/>:
                </label>
                <select id="language" v-model="language">
                    <option v-for="l in languages" :value="l.id">
                        {{l.value}}
                    </option>
                </select>
                <button v-on:click="save" v-show="lang !== language">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
        <tr>
            <td valign="top" align="center">
                <jsp:include page="personalData.jsp"/>
                <jsp:include page="workerOffice.jsp"/>
                <jsp:include page="contacts.jsp"/>
                <button onclick="loadModal('${changePassword}')">
                    <fmt:message key="password.change"/>
                </button>
            </td>
            <td valign="top">
                <jsp:include page="telegramBotSettings.jsp"/>
            </td>
            <td valign="top">

            </td>
        </tr>
    </table>
</div>

</html>