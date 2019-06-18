<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/referenceList.js"></script>
<script>
    referenceList.sort = function(){
        this.items.sort(function(a, b){
            return a.name.localeCompare(b.name);
        })
    };
    referenceList.api.update='${update}';
    referenceList.api.edit = '${edit}';
    referenceList.update();
</script>
<div id="referencesList" style="width: 100%">
    <table>
        <tr v-for="organisation in items" v-on:click="edit(organisation.id)">
            <td>
                {{organisation.name}}
            </td>
            <td>
                {{organisation.type}}
            </td>
        </tr>
    </table>
</div>
</html>