<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
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
<table width="100%">
  <tr>
    <td align="center">
      <jsp:include page="header.jsp"/>
    </td>
  </tr>
  <tr>
    <td align="center">
      <div style="padding-top: 36pt">
        <b>
        <span>
          Акт №
        </span>
            ${number}
        <span>
        від
        </span>
          <fmt:formatDate value="${date}" pattern="dd.MM.yyyy"/>
        </b>
      </div>
    </td>
  </tr>
  <tr>
    <td align="center">
      <div style="padding: 18pt 12pt">
        <i>
          Про перерахунок ваги насіння внаслідок невідповідності якісних показників базовим нормам
        </i>
      </div>
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
        <fmt:formatDate value="${product.transportation.timeIn.time}" pattern="dd.MM.yyyy"/>
      <span>
        від постачальника сировини
      </span>
        <b>
          ${product.dealProduct.deal.organisation.value}
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
        <c:set value="${product.sunAnalyses}" var="sun"/>
        <tr>
          <td align="center">
            1
          </td>
          <td>
            ${product.transportation.driver.person.value}
          </td>
          <td>
            <c:choose>
              <c:when test="${sun.humidity1 > 0 && sun.humidity2 > 0}">
                <table style="font-size: 10pt">
                  <tr>
                    <td>
                      М.&nbsp;-&nbsp;${sun.humidity1}
                    </td>
                    <td rowspan="2">
                      Сер.&nbsp;<fmt:formatNumber value="${sun.middleHumidity()}"/>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      Пр.&nbsp;-&nbsp;${sun.humidity2}
                    </td>
                  </tr>
                </table>
              </c:when>
              <c:otherwise>
                М.&nbsp;-&nbsp;${sun.humidity1}
              </c:otherwise>
            </c:choose>

          </td>
          <td align="center">
            ${sun.soreness}
          </td>
          <td align="center">
            ${sun.oilImpurity}
          </td>
          <td align="center">
            ${sun.acidValue}
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <c:choose>
    <c:when test="${sun.middleHumidity() > humidityBasis and sun.soreness > sorenessBasis}">
      <tr>
        <td>
          <div style="padding-top: 12pt">
            Убуток маси насіння від зменшення сміттєвої домішки
            внаслідок очистки та зменшення вологи
            внаслідок сушіння становить
            <fmt:formatNumber value="${product.weight.correction}"/>%
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
                  (100 - <fmt:formatNumber value="${sun.middleHumidity()}"/>) &times; (100 - ${sun.soreness}) &times; 100
                </td>
                <td rowspan="2">
                  &nbsp;= <fmt:formatNumber value="${product.weight.correction}"/>%
                </td>
              </tr>
              <tr>
                <td align="center">
                  (100 - Вб)&times;(100 - Сб)
                </td>
                <td align="center">
                  (100 - ${humidityBasis}) &times; (100 - ${sorenessBasis})
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
    </c:when>
    <c:when test="${sun.middleHumidity() > humidityBasis}">
      <tr>
        <td>
          <div style="padding-top: 12pt">
            Убуток маси насіння від зменшення вологості внаслідок сушіння становить
            <fmt:formatNumber value="${product.weight.correction}"/>%
          </div>
        </td>
      </tr>
      <tr>
        <td align="center">
          <div style="padding: 12pt 0">
            <table>
              <tr>
                <td style="border-bottom: solid 1pt">
                  ( ${sun.middleHumidity()} - ${humidityBasis} ) &times; 100
                </td>
                <td rowspan="2">
                  &nbsp;= <fmt:formatNumber value="${product.weight.correction}"/>%
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
    <c:when test="${sun.soreness gt sorenessBasis}">
      <tr>
        <td>
          <div style="padding-top: 12pt">
            Убуток маси насіння від зменшення вологи
            внаслідок сушіння становить
            <fmt:formatNumber value="${product.weight.correction}"/>%
          </div>
        </td>
      </tr>
      <tr>
        <td align="center">
          <div style="padding: 12pt 0">
            <table>
              <tr>
                <td style="border-bottom: solid 1pt">
                  ( ${sun.soreness} - ${sorenessBasis} ) &times; 100
                </td>
                <td rowspan="2">
                  &nbsp;= <fmt:formatNumber value="${product.weight.correction}"/>%
                </td>
              </tr>
              <tr>
                <td align="center">
                  100 - ${sorenessBasis}
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
      <div style="padding: 24pt;">
        <table width="100%">
          <tr>
            <td>
              Відповідальна особа
            </td>
            <td>
              М.П.
            </td>
            <td align="right">
              ${responsible.value}
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
</html>
