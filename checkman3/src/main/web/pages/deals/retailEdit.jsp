<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 08.09.20
  Time: 09:35
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script type="application/javascript" src="${context}/vue/editor.vue"></script>
    <script type="application/javascript" src="${context}/vue/templates/search.vue"></script>
    <script type="application/javascript" src="${context}/vue/retailEdit.vue"></script>
    <script type="application/javascript">
        retailEdit.api.productEdit = '${productEdit}';
        <c:forEach items="${products}" var="product">
        retailEdit.products.push(${product.toJson()});
        </c:forEach>
    </script>
    <table id="retailEdit">
        <tr>
            <td>
                <fmt:message key="transportation.date"/>
            </td>
            <td>
                00.00.00
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.driver"/>
            </td>
            <td>
                ########## ####### ######
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.vehicle"/>/<fmt:message key="transportation.trailer"/>
            </td>
            <td>
                ### ##00-00## #00-00##
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.transporter"/>
            </td>
            <td>
                ### #############
            </td>
        </tr>
        <tr>
            <td>
                <label for="shipper">
                    <fmt:message key="deal.shipper"/>
                </label>
            </td>
            <td>
                <select id="shipper">

                </select>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.counterparty"/>
            </td>
            <td>
                ##########################
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.address"/>
            </td>
            <td>

            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.product"/>
            </td>
            <td>
                <select>
                    <option v-for="product in products" :value="product.id">
                        {{product.name}}
                    </option>
                </select>
                <span class="text-button">
                    +
                </span>
            </td>
        </tr>
    </table>
</html>
