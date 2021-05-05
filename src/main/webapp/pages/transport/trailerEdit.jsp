<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-05-05
  Time: 14:13
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    editor = new Vue({
        el:'#trailerEdit',
        data:{
            api:{},
            trailer:{
                id:-1,
                number:''
            }
        },
        methods:{
            save:function () {
                PostApi(this.api.save, this.trailer, function (a) {
                    if (a.status === 'success'){
                        closeModal();
                        if (a.result) {
                            saveModal(a);
                        }
                    }
                })
            }
        }
    });
    editor.api.save = '${save}';
    <c:if test="${not empty trailer}">
    editor.trailer = ${trailer.toJson()}
    </c:if>
</script>
    <table id="trailerEdit">
        <tr>
            <td>
                <label for="number">
                    <fmt:message key="transportation.automobile.number"/>
                </label>
            </td>
            <td>
                <input id="number" v-model="trailer.number" autocomplete="off" onfocus="this.select()">
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
