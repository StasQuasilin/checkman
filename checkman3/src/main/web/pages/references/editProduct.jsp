
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 07.09.20
  Time: 12:02
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script type="application/javascript" src="${context}/vue/editor.vue"></script>
<script type="application/javascript" src="${context}/vue/productEdit.vue"></script>
<script type="application/javascript">
    productEdit.api.save = '${save}';
    <c:forEach items="${actions}" var="action">
    productEdit.actions.push('${action}');
    productEdit.actionNames['${action}'] = '<fmt:message key="${action}"/>';
    </c:forEach>
    productEdit.object = {
        id:-1,
        name:'',
        actions:{},
        group:false
    }
</script>
<table id="productEdit">
    <tr>
        <td>
            <label for="name">
                <fmt:message key="product.name"/>
            </label>
        </td>
        <td>
            <input id="name" v-model="object.name" onfocus="this.select()" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <template v-for="a in actions">
                <input :id="a" type="checkbox" v-model="object.actions[a]">
                <label :for="a">
                    {{actionNames[a]}}
                </label>
            </template>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="isGroup" v-model="object.group">
            <label for="isGroup">
                <fmt:message key="product.group"/>
            </label>
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
