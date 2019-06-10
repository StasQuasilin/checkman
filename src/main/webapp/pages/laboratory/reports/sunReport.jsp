<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .logo{
    width: 220px;
    height: 149px;
    background: url('../../../images/logo.png') no-repeat center;
    background-size: cover;
    display: table-cell;
    border: solid 1pt;
  }
  .header-text{
    font-size: 10pt;
    display: table-cell;
    text-align: left;
    vertical-align: middle;
    padding: 0 18pt;
  }
  table{
    border-collapse: collapse;
  }
  table th td{
    border: solid 1pt;
  }
  th td{
    padding: 0 4pt;
  }
</style>
<table width="100%" border="0">
  <tr>
    <td align="center">
      <div style="display: table-row">
        <span style="display: table-cell" class="header-text">
          Україна, 42350<br>
          Сумський район<br>
          с.Бездрик, вул. Зарічна, 1<br>
          olivija@olivija.ua<br>
          +38 0542 700 488
        </span>
        <img src="../../../images/logo.png" width="70%" height="70%">
        <span style="display: table-cell" class="header-text">
          <b>ПрАТ "Сумський завод<br>продтоварів"</b><br>
          http://olivija.ua
        </span>
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <div style="padding-top: 36pt">
        <b>
        <span>
          Акт №
        </span>
            1
        <span>
        від
        </span>
          <fmt:formatDate value="${now}" pattern="dd.MM.yyyy"/>
        </b>
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <i>
        Про перерахунок ваги насіння внаслідок невідповідності якісних показників базовим нормам
      </i>
    </td>
  </tr>
  <tr>
    <td style="font-size: 14pt;">
      <div style="padding-top: 24pt; text-indent: 25pt">
        Згідно вимог договору та нормативних документів базові показники насіння
        соняшника повинні відповідати ДСТУ 7011:2009
        "Соняшник. Технічні умови" та ДСТУ 4694:2006
        "Соняшник. Олійна сировина. Технічні умови".
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <p style="margin-left: 24pt; line-height: 24pt">
        Вологість, % не менше ніж 6,0, не більше 7,0<br>
        Сміттєві домішки, %, не більше ніж 3,0<br>
        Олійні домішки, %, не більше ніж 7,0<br>
        Кислотне число олії, мг КОН/г, не більше ніж 5,0<br>
        Зараженість шкідниками - не допускається
      </p>
    </td>
  </tr>
  <tr>
    <td>
      <div style="padding-top: 12pt; text-indent: 25pt">
        <fmt:formatDate value="${plan.date}" pattern="dd.MM.yyyy"/>
      <span>
        від постачальника
      </span>
        <b>
          ${plan.deal.organisation.value}
        </b>
      <span>
        прийняте насіння соняшника з такими якісними показниками:
      </span>
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <table border="1" style="font-size: 10pt">
        <tr>
          <th>
            п/н
          </th>
          <th>
            Прізвище водія
          </th>
          <th>
            Вологість, %
          </th>
          <th style="width: 6em">
            Сміттєві домішки, %
          </th>
          <th style="width: 6em">
            Олійна домішка, %
          </th>
          <th style="width: 8em">
            Кислотне число олії, мг КОН/г
          </th>
        </tr>
        <c:forEach items="${plan.transportation.sunAnalyses}" var="sun">
          <tr>
            <td align="center">
              1
            </td>
            <td>
              ${plan.transportation.driver.person.value}
            </td>
            <td>
              <div>
                М.-${sun.analyses.humidity1}
              </div>
              <div>
                Пр.-${sun.analyses.humidity2}
              </div>
            </td>
            <td align="center">
              ${sun.analyses.soreness}
            </td>
            <td align="center">
              ${sun.analyses.oilImpurity}
            </td>
            <td align="center">
              ${sun.analyses.acidValue}
            </td>
          </tr>
        </c:forEach>
      </table>
    </td>
  </tr>
  <c:choose>
    <c:when test="${humidity gt humidityBasis and soreness gt sorenessBasis}">
      <tr>
        <td>
          <div style="padding-top: 12pt">
            Убуток маси насіння від зменшення сміттєвої домішки
            внаслідок очистки та зменшення вологи
            внаслідок сушіння становить
            <fmt:formatNumber value="${percent}"/>%
          </div>
        </td>
      </tr>
      <tr>
        <td align="center">
          <div style="padding: 12pt 0">
            <table>
              <tr>
                <td rowspan="2">
                  100 -
                </td>
                <td style="border-bottom: solid 1pt">
                  (100 - В) &times; (100 - С) &times; 100
                </td>
                <td rowspan="2">
                  &nbsp;= 100 -
                </td>
                <td style="border-bottom: solid 1pt">
                  (100 - ${humidity}) &times; (100 - ${soreness}) &times; 100
                </td>
                <td rowspan="2">
                  &nbsp;= <fmt:formatNumber value="${percent}"/>%
                </td>
              </tr>
              <tr>
                <td align="center">
                  (100 - Вб)&times;(100 - Сб)
                </td>
                <td align="center">
                  (100 - ${humidityBasis})&times;(100 - ${sorenessBasis})
                </td>
              </tr>
            </table>
          </div>

        </td>
      </tr>
    </c:when>
    <c:when test="${humidity gt humidityBasis}">
      <tr>
        <td>
          <div style="padding-top: 12pt">
            Убуток маси насіння від зменшення вологості внаслідок сушіння становить
            <fmt:formatNumber value="${percent}"/>%
          </div>
        </td>
      </tr>
      <tr>
        <td align="center">
          <div style="padding: 12pt 0">
            <table>
            <tr>
              <td style="border-bottom: solid 1pt">
                ( ${humidity} - ${humidityBasis} ) &times; 100
              </td>
              <td rowspan="2">
                &nbsp;= <fmt:formatNumber value="${percent}"/>%
              </td>
            </tr>
            <tr>
              <td align="center">
                100 - ${humidityBasis}
              </td>
            </tr>
          </table>
          </div>
        </td>
      </tr>
    </c:when>
  </c:choose>

  <tr>
    <td>
      <div style="padding-top: 24pt">
        Проби відібрано згідно з ДСТУ 4601:2006 "Насіння олійних культур. Методи відбирання проб".
      </div>
    </td>
  </tr>
  <tr>
    <td>
      <table>
        <tr>
          <td>
            <fmt:message key="laboratory.response"/>
          </td>
          <td>

          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html>
