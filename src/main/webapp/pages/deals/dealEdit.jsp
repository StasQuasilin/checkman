<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" type="text/css" href="${context}/datetimepicker/jquery.datetimepicker.css"/>
    <script src="${context}/datetimepicker/jquery.js"></script>
    <script src="${context}/datetimepicker/build/jquery.datetimepicker.full.min.js"></script>
    <script src="${context}/vue/dealEdit.js"></script>
    <script>
        findOrganisationUrl = '${find_organisation}';
        saveDealUrl = '${save_url}';
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
                <input type="hidden" id="deal_id" value="${deal.id}">
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
                <label for="date_to">-</label>
                <input id="date_to" readonly autocomplete="off" style="width: 65pt">
            </td>
        </tr>
        <tr>
            <td>
                <label for="contragent">
                    <fmt:message key="deal.organisation"/>:
                </label>
            </td>
            <td>
                <input type="hidden" id="contragent_id" value="${deal.organisation.id}">
                <input id="contragent" autocomplete="off" style="width: 100%" value="${deal.organisation.value}">
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
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
            <td>
                <select id="product">
                    <c:forEach items="${products}" var="p">
                        <option value="${p.id}"
                                <c:if test="${dealProduct.product.id eq p.id}">selected</c:if> >
                                ${p.name}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="quantity">
                    <fmt:message key="deal.quantity"/>
                </label>
            </td>
            <td>
                <input type="number" id="quantity" value="${dealProduct.quantity}" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td>
                <label for="price">
                    <fmt:message key="deal.price"/>
                </label>
            </td>
            <td>
                <input type="number" id="price" value="${dealProduct.price}" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button onclick="close()"><fmt:message key="button.cancel"/> </button>
                <button onclick="save()"><fmt:message key="button.save"/> </button>
            </td>
        </tr>
    </table>
</html>