
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 11.12.20
  Time: 15:30
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    sealId = ${seals.id};
    removeApi = '${context}${remove}';
    function remove(){
        PostApi(removeApi, {id: sealId}, function (a) {
            if (a.status === 'success'){
                closeModal();
            }
        })
    }
</script>
<table>
    <tr style="margin: 0 16pt">
        <td>
            <fmt:message key="button.delete"/>?
        </td>
    </tr>
    <tr>
        <td style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button onclick="remove()">
                <fmt:message key="button.delete"/>
            </button>
        </td>
    </tr>
</table>


</html>
