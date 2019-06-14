<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<table width="100%">
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
          Декларація віробника №
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
        14 червня 2019 р.
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <div style="padding-top: 12pt">
        <table>
          <tr>
            <td>
              Найменування підприємства - виробника<br>
              <b>
                ПАТ "Сумський завод продтоварів"
              </b>
            </td>
            <td rowspan="6">
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
              Масова частка загальної золи, % в перерахунку на аабсолютно суху речовину<br>
              Експертний висновок №000588 п/19 від 22.05.2019<br>
              виданий СРДЛ Держпродспоживслужби України<br>
              Експлуатаційний дозвіл №18-15-81 PF від 12.10.2018 р.<br>
              Відповідає ДСТУ 4638:2006<br>
              Підпис відповідальної особи
            </td>
          </tr>
          <tr>
            <td>
              Адреса виробника:<br>
              с. Бездрик Сумського району Сумської області
            </td>
          </tr>
          <tr>
            <td>
              № а/м ${plan.transportation.vehicle.number}
            </td>
          </tr>
          <tr>
            <td>
              Маса нетто
            </td>
          </tr>
          <tr>
            <td>
              Одержувач ${plan.deal.organisation.value}
            </td>
          </tr>
          <tr>
            <td>
              Дата виготовлення
            </td>
          </tr>
          <tr>
            <td>
              Термін зберігання шроту - 3 місяці
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
</html>
