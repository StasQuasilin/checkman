<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 25.02.2020
  Time: 9:57
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <style>
        .filter{
            overflow-y: visible;
        }
        .custom-data-list{
            width: 180px;
            font-size: 10pt;
            height: 150pt;
        }
        .object-block{
            background-color: transparent;
            border: none;
            border-radius: 0;
        }
    </style>
    <script src="${context}/vue/templates/vehicleInput.vue"></script>
    <script>
        <jsp:include page="/vue/filters/statisticFilter.vue"/>
        filter_control.api.find = '${find}';
        <c:forEach items="${products}" var="product">
        filter_control.products.push(${product.toJson()});
        </c:forEach>
        filter_control.organisationProps = {
            find:'${findOrganisation}',
            header:'<fmt:message key="counterparty.select"/>',
            put:function(organistion){
                filter_control.organisation = organistion;
            },
            show:['value']
        }
    </script>
    <table id="filter_view" style="width: 100%">
        <tr>
            <td>
                <label for="date">
                    <fm:message key="date"/>
                </label>
            </td>
        </tr>
        <tr>
            <td id="date" style="font-size: 10pt">
                <span v-on:click="selectDateFrom">{{new Date(from).toLocaleDateString()}}</span>
                -
                <span v-on:click="selectDateTo">{{new Date(to).toLocaleDateString()}}</span>
            </td>
        </tr>
        <tr>
            <td style="font-size: 10pt">
                <fmt:message key="deal.organisation"/>
            </td>
        </tr>
        <tr>
           <td>
               <object-input :props="organisationProps" :object="organisation"></object-input>
           </td>
        </tr>
        <tr>
            <td style="font-size: 10pt">
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <select id="product" v-model="product" style="width: 100%">
                    <option value="-1">
                        <fmt:message key="not.select"/>
                    </option>
                    <option v-for="product in products" :value="product.id">
                        {{product.name}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="center">
                <button v-on:click="find()">
                    <fmt:message key="find"/>
                </button>
            </td>
        </tr>
        <tr>
            <td align="center">
                <a>
                    <fmt:message key="button.clear"/>
                </a>
            </td>
        </tr>
    </table>
</html>
