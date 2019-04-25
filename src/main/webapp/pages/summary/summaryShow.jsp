<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table border="0">
  <tr>
    <td valign="top">
      <div class="page-container">
        <table border="0">
          <tr>
            <th colspan="3">
              <a><fmt:message key="deal"/></a>
            </th>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.type"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:message key="${plan.deal.type}"/>
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.organisation"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.deal.organisation.value}
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.product"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.deal.product.name}
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.quantity"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:formatNumber value="${plan.deal.quantity}"/>&nbsp;${plan.deal.unit.name}
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="deal.price"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:formatNumber value="${plan.deal.price}"/>
            </td>
          </tr>
        </table>
      </div>
    </td>
    <td valign="top">
      <div class="page-container">
        <table border="0">
          <tr>
            <th colspan="3">
              <fmt:message key="transportation"/>
            </th>
          </tr>
          <tr>
            <th colspan="3" align="left">
              <fmt:message key="transportation.automobile"/>
            </th>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="transportation.automobile.model"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.transportation.vehicle.model}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="transportation.automobile.number"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.transportation.vehicle.number}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="transportation.automobile.trailer"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.transportation.vehicle.trailer}
            </td>
          </tr>
          <tr>
            <th colspan="3" align="left">
              <fmt:message key="transportation.driver"/>
            </th>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="person.surname"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.transportation.driver.person.surname}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="person.forename"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.transportation.driver.person.forename}
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;<fmt:message key="person.patronymic"/>
            </td>
            <td>
              :
            </td>
            <td>
              ${plan.transportation.driver.person.patronymic}
            </td>
          </tr>

        </table>
        <table>
          <tr>
            <td>
              <fmt:message key="load.plan"/>
            </td>
            <td>
              :
            </td>
            <td>
              <fmt:formatNumber value ="${plan.plan}"/>&nbsp;${plan.deal.unit.name}
            </td>
          </tr>
          <tr>
            <td>
              <fmt:message key="load.fact"/>
            </td>
            <td>
              :
            </td>
            <td>

            </td>
          </tr>
        </table>
      </div>
    </td>
    <td valign="top">
      <div class="page-container">
        <table border="1">
          <tr>
            <th>
              <fmt:message key="transportation.log"/>
            </th>
          </tr>
          <tr>
            <td>

            </td>
          </tr>
        </table>
      </div>

    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.close"/>
      </button>
    </td>
  </tr>
</table>

</html>
