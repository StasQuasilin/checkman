<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 24.03.20
  Time: 10:57
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
    .message-area{
        width: 100%;
        resize: vertical;
    }
</style>
<script>
    var edit = new Vue({
        el:'#edit',
        data:{
            api:{},
            board:{
                title:'',
                text:''
            }
        },
        methods:{
            save:function(){
                PostApi(this.api.save, this.board, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        }
    });
    edit.api.save = '${save}';
    <c:if test="${not empty item}">
    edit.board = {
        id:${item.id},
        title:'${item.title}',
        text:'${item.text}'
    };
    </c:if>
</script>
<table id="edit">
    <tr>
        <td>
            <label for="title">
                <fmt:message key="board.title"/>
            </label>
        </td>
        <td>
            <input id="title" v-model="board.title" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <label for="text">
                <fmt:message key="board.text"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <textarea id="text" v-model="board.text" autocomplete="off" class="message-area"></textarea>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save()">
                <fmt:message key="button.publich"/>
            </button>
        </td>
    </tr>
</table>
</html>
