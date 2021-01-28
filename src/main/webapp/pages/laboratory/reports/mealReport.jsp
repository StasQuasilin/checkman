<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<table width="100%" border="0">
  <tr>
    <td align="center">
      <jsp:include page="header.jsp"/>
    </td>
  </tr>
  <tr>
    <td>
      <jsp:include page="laboratoryAccreditation.jsp"/>
    </td>
  </tr>
  <tr>
    <td align="center">
      <div style="padding-top: 12pt">
        <b>
          Декларація виробника № ${number}
        </b>
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <div style="padding-top: 12pt">
        на шрот соняшника негранульований
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <div style="padding-top: 12pt">
        <fmt:formatDate value="${date}" pattern="dd MMMM yyyy"/>
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <div style="padding-top: 12pt">
        <table border="0">
          <tr>
            <td style="padding-bottom: 8pt">
              Найменування підприємства - виробника<br>
              <b>
                ПАТ "Сумський завод продтоварів"
              </b>
            </td>
            <td width="50%" rowspan="5" style="padding-left: 2pt">
              Зовнішній вигляд - <b>однорідна сипка маса</b><br>
              Колір - <b>сірий різних відтінків</b><br>
              Запах - <b>характерний соняшниковому шроту без стороннього запаху</b><br>
              Масова частка вологи та летких речовин<br>
              Масова частка металічних домішок не більше<br>
              Зараженість шкідниками або наявність слідів зараження<br>
              Масова частка сирого протеїну в абсолютно сухій речовині<br>
              Масова частка сирої клітковини в абсолютно сухій речовині<br>
              Масова частка жиру і екстрактивних речовин в абсолютно сухій речовині<br>
              Масова частка залишкової кількості розчинника (бензину, неф расу)<br>
              Масова частка загальної золи, % в перерахунку на аабсолютно суху речовину<br><br>
              Експертний висновок №${protocol.number} від <fmt:formatDate value="${protocol.date}" pattern="dd.MM.yyyy"/><br>
              виданий СРДЛ Держпродспоживслужби України<br><br>
              Експлуатаційний дозвіл №18-15-81 PF від 10.10.2019 р.<br><br>

              <b>Відповідає ДСТУ 4638:2006</b><br>
              <b>Без ГМО</b><br><br>
              Відповідальна особа
              <span style="float: right">
                ${responsible.value}
              </span>
            </td>
          </tr>
          <tr>
            <td style="padding-bottom: 8pt">
              Адреса виробника:<br>
              с. Бездрик Сумського району Сумської області
            </td>
          </tr>
          <tr>
            <td style="padding-bottom: 8pt">
              Одержувач: ${plan.counterparty.value}<br>
              № а/м: ${plan.vehicle.number}<br>
              Маса нетто:
              <c:if test="${not empty plan.weight}">
                <fmt:formatNumber value="${plan.weight.netto}"/>  т.
              </c:if><br>
              Дата виготовлення: <fmt:formatDate value="${manufacture}" pattern="dd.MM.yyyy"/>
            </td>
          </tr>
          <tr>
            <td height="100%" valign="top">
              Термін зберігання шроту - 3 місяці
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
</html>
