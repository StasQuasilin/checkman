<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<table>
  <tr>
    <td>
      <label for="surname">
        <fmt:message key="person.surname"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="surname">
    </td>
  </tr>
  <tr>
    <td>
      <label for="forename">
        <fmt:message key="person.forename"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="forename">
    </td>
  </tr>
  <tr>
    <td>
      <label for="patronymic">
        <fmt:message key="person.patronymic"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="patronymic">
    </td>
  </tr>
  <tr>
    <td>
      <label for="license">
        <fmt:message key="driver.license"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="license">
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <b>
        <fmt:message key="transportation.automobile"/>
      </b>
      <table style="font-size: 10pt">
        <tr>
          <td>
            <label for="model">
              <fmt:message key="transportation.automobile.model"/>
            </label>
          </td>
          <td>
            <input id="model">
          </td>
        </tr>
        <tr>
          <td>
            <label for="number">
              <fmt:message key="transportation.automobile.number"/>
            </label>
          </td>
          <td>
            <input id="number">
          </td>
        </tr>
        <tr>
          <td>
            <label for="trailer">
              <fmt:message key="transportation.automobile.trailer"/>
            </label>
          </td>
          <td>
            <input id="trailer">
          </td>
        </tr>
      </table>
    </td>
  </tr>

  <tr>
    <td>
      <label for="transporter">
        <fmt:message key="transportation.transporter"/>
      </label>
    </td>
    <td>
      :
    </td>
    <td>
      <input id="transporter">
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <div style="width: 100%; display: flex">
        <span>
          <fmt:message key="phones"/>:
        </span>

      </div>
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button>
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
</html>
