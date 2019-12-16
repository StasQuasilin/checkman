<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 06.12.2019
  Time: 11:55
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  table{
    border-collapse: collapse;
  }
</style>
  <table border="1" width="100%" style="margin-bottom: 32pt">
    <c:forEach items="${transportation.documents}" var="document" varStatus="status">
      <tr>
        <td colspan="7">
          &nbsp;
        </td>
      </tr>
      <tr>
        <td colspan="2" style="background-color: darkgray; font-weight: bold">
          ${status.index + 1}.
          ${document.getCounterparty().value}
            <c:if test="${not empty document.address}">
              <span>
                &nbsp;
              ${document.address.city}
              ${document.address.street}
              ${document.address.build}
              </span>
            </c:if>
        </td>
        <th>
          <fmt:message key="once.weight"/>
        </th>
        <th>
          <fmt:message key="on.pallet.amount"/>
        </th>
        <th>
          <fmt:message key="seal.quantity"/>
        </th>
        <th>
          <fmt:message key="weight"/>
        </th>
        <th>
          <fmt:message key="pallets"/>
        </th>
      </tr>
      <c:set var="totalAmount" value="0"/>
      <c:set var="ttoalWeight" value="0"/>
      <c:set var="totalPallets" value="0"/>
      <c:forEach items="${document.products}" var="product">
        <tr>
          <td align="center">
            Ф${product.contractProduct.shipper.id}
          </td>
          <td>
            ${product.contractProduct.product.name}
          </td>
          <td align="center">
            <fmt:formatNumber value="${product.contractProduct.product.weight}"/>

          </td>
          <td align="center">
            <fmt:formatNumber value="${product.contractProduct.product.pallet}"/>
          </td>
          <td align="center">
            <fmt:formatNumber value="${product.contractProduct.amount}"/>
            <c:set var="totalAmount" value="${totalAmount + product.contractProduct.amount}"/>
          </td>
          <td align="center">
            <c:set var="weight" value="${product.contractProduct.amount * product.contractProduct.product.weight}"/>
            <c:set var="totalWeight" value="${totalWeight + weight}"/>
            <fmt:formatNumber value="${weight}"/>
          </td>
          <td align="center">
            <c:set var="amount" value="${product.contractProduct.amount / product.contractProduct.product.pallet}"/>
            <c:set var="totalPallets" value="${totalPallets + amount}"/>
            <fmt:formatNumber value="${amount}"/>
          </td>
        </tr>
      </c:forEach>
      <tr>
        <th colspan="4" align="right">
          <fmt:message key="amount.total"/>:
        </th>
        <th>
          <fmt:formatNumber value="${totalAmount}"/>
        </th>
        <th>
          <fmt:formatNumber value="${totalWeight}"/>
        </th>
        <th>
          <fmt:formatNumber value="${totalPallets}"/>
        </th>
      </tr>
    </c:forEach>
  </table>
</html>
