<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/transport/customTime.vue"></script>
<script>
    customTime.api.save = '${save}';
    customTime.id = ${transportation.id};
    customTime.action = '${action}';
    if (customTime.action === 'in'){
        <c:if test="${not empty transportation.timeIn}">
            customTime.date = new Date('${transportation.timeIn}');
        </c:if>
    } else if (customTime.action === 'out') {
        <c:if test="${not empty transportation.timeOut}">
            customTime.date = new Date('${transportation.timeOut}');
        </c:if>
    }
    if (customTime.date == null){
        customTime.date = new Date();
    }
</script>
    <table id="custom">
        <tr>
            <td>
                <fmt:message key="date"/>
            </td>
            <td>
                <span v-if="date" class="mini-close" v-on:click="selectDate()">
                    {{date.toLocaleDateString()}}
                </span>
            </td>
        </tr>
        <tr>
            <td>
               <fmt:message key="time"/>
            </td>
            <td>
                <span v-if="date" class="mini-close" v-on:click="selectTime()">
                    {{date.toLocaleTimeString().substring(0, 5)}}
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="button.close"/>
                </button>
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
