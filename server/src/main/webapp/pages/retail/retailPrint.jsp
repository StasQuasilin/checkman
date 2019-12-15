<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 11.12.2019
  Time: 10:35
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  table{
    border-collapse: collapse;
  }
  td, th{
    padding: 0 4pt;
  }
  .header{
    width: 100%;
    text-align: center;
  }
</style>
<table border="1">
  <c:set var="length">${fn:length(products)}</c:set>
  <c:forEach var="i" begin="0" end="1">
    <tr>
      <c:forEach var="j" begin="0" end="1">
        <td style="padding: 4pt">
          <div class="header">Накладна № <fmt:formatDate value="${contract.from}" pattern="dd.MM"/></div>
          <div class="header">від <fmt:formatDate value="${contract.from}" pattern="dd.MM.yyyy"/></div>
          <div>
            Від кого: ПАТ Сумський завод продтоварів
          </div>
          <div>
            Кому: ${contract.counterparty.getValue()}
          </div>

          <table border="1">
            <tr>
              <th>
                № п/п
              </th>
              <th>
                Назва
              </th>
              <th>
                Одиниці<br>виміру
              </th>
              <th>
                Кількість
              </th>
              <th>
                Ціна<br>без ПДВ
              </th>
              <th>
                Сума<br>без ПДВ
              </th>
            </tr>
            <c:set var="total" value="0"/>

            <c:forEach items="${products}" var="product" varStatus="status">
              <tr>
                <td align="right">
                    ${status.index + 1}
                </td>
                <td>
                    ${product.product.name}
                </td>
                <td align="center">
                    ${product.unit.name}
                </td>
                <td align="center">
                  <fmt:formatNumber value="${product.amount}"/>
                </td>
                <td align="center">
                  <fmt:formatNumber value="${product.price}"/>
                </td>
                <td align="center">
                  <c:set var="t" value="${product.amount * product.price}"/>
                  <fmt:formatNumber value="${t}"/>
                  <c:set var="total" value="${total + t}"/>
                </td>
              </tr>
            </c:forEach>
            <c:forEach var="k" begin="${length + 1}" end="10">
              <tr>
                <td>
                  &nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
                <td>
                  &nbsp;
                </td>
              </tr>
            </c:forEach>
            <tr>
              <td colspan="5" align="right">
                Всього без ПДВ
              </td>
              <td align="center">
                <fmt:formatNumber value="${total}"/>
              </td>
            </tr>
          </table>

          <div style="display: inline-flex; width: 100%; padding: 8pt 0">
            <div style="width: 50%; padding-left: 8pt">
              Керівник
            </div>
            <div style="width: 50%; padding-left: 8pt">
              Відпустив<br>
              Одержав
            </div>
          </div>
        </td>
      </c:forEach>
    </tr>
  </c:forEach>

</table>
</html>
