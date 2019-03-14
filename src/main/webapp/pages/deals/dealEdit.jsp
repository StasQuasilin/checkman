<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" type="text/css" href="${context}/datetimepicker/jquery.datetimepicker.css"/>
    <script src="${context}/datetimepicker/jquery.js"></script>
    <script src="${context}/datetimepicker/build/jquery.datetimepicker.full.min.js"></script>
    <script src="${context}/vue/dealEdit.js"></script>
    <script>
    <c:forEach items="${types}" var="t">
        editor.addType('${t}', '<fmt:message key="${t}"/>');
    </c:forEach>
    </script>
    <script>
        $(function(){
            $.datetimepicker.setLocale('${language}');
            $('#date').datetimepicker({
                lazyInit:true,
                format:'d.m.Y',
                timepicker: false,
                dayOfWeekStart:1
            });
            $('#dateTo').datetimepicker({
                lazyInit:true,
                format:'d.m.Y',
                timepicker: false,
                dayOfWeekStart:1
            });
        });
    </script>
    <table id="form">
        <tr>
            <td>
                <label for="type">
                    <fmt:message key="deal.type"/>
                </label>
            </td>
            <td>
                <select id="type">
                    <c:forEach items="${types}" var="t">
                        <option value="${t}" <c:if test="${t eq type}">selected</c:if> >
                            <fmt:message key="${t}"/> </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="date">
                    <fmt:message key="period"/>:
                </label>
            </td>
            <td>
                <input id="date" readonly autocomplete="off" style="width: 65pt">
                <label for="dateTo">-</label>
                <input id="dateTo" readonly autocomplete="off" style="width: 65pt">
            </td>
        </tr>
        <tr>
            <td>
                <label for="contragent">
                    <fmt:message key="deal.organisation"/>:
                </label>
            </td>
            <td>
                <input id="contragent" autocomplete="off" style="width: 100%">
                <div id="contragent-list" class="custom-data-list"></div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="realisation">
                    <fmt:message key="deal.realisation"/>:
                </label>
            </td>
            <td>
                <select id="realisation">
                    <c:forEach items="${documentOrganisations}" var="dO">
                        <option value="${dO.id}">${dO.value}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>

            </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <fmt:message key="deal.product"/>
                <button><fmt:message key="button.add"/> </button>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div style="width: 100%; height: 100pt; border: solid gray 1pt; overflow-y: scroll"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button><fmt:message key="button.cancel"/> </button>
                <button><fmt:message key="button.save"/> </button>
            </td>
        </tr>
    </table>
</html>