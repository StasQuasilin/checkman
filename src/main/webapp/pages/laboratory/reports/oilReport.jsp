<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="uk_UK"/>
<html>
<body style="padding: 0 0 0 32pt">
  <div>
    <table style="width: 100%" border="0">
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
        <td align="center" style="font-size: 14pt">
          <b>
            Декларація виробника № ${number}
          </b>
        </td>
      </tr>
      <tr>
        <td align="center">
          <b>
            на олію
          </b>
        </td>
      </tr>
      <tr>
        <td align="center">
          <b>
            <fmt:formatDate value="${date}" pattern="dd MMMM yyyy"/>
          </b>
        </td>
      </tr>
      <tr>
        <td style="padding-top: 18pt">
          <table border="0">
            <tr>
              <td valign="top" style="height: fit-content">
                <jsp:include page="manufactoryLabel.jsp"/>
              </td>
              <td rowspan="7" style="font-style: italic; border-left: solid 1pt; padding-left: 4pt">
                <p>
                  <c:if test="${analyses.explosion == 0}">
                    Рід - <b>соняшникова</b><br>
                    Вид - <b>${properties['form']}</b><br>
                  </c:if>
                  <c:if test="${analyses.explosion > 0}">
                    <b>Олія соняшникова не рафінована (суміш пресової з екстракційною)</b><br>
                  </c:if>
                  <c:if test="${not empty properties['brand']}">
                    Гатунок - <b>${properties['brand']}</b><br>
                  </c:if>
                  Прозорість - <b>${properties['transparency']}</b><br>
                  Запах та смак - <b>${properties['smell']}</b><br>
                  Колірне число, мг йоду - <b><fmt:formatNumber value="${analyses.color}"/></b><br>
                  Кислотне число, мг КОН/г - <b><fmt:formatNumber value="${analyses.acidValue}"/></b><br>
                  Масова частка фосфоровміснх речовн в перерахунку<br>
                  на стеароолеолецитин - <b><fmt:formatNumber value="${analyses.phosphorus}"/></b><br>
                  Масова частка вологи та летких речовин - <b><fmt:formatNumber value="${analyses.humidity}"/></b><br>
                  Масова частка нежирових домішок - <b><fmt:formatNumber value="${analyses.degreaseImpurity}"/></b><br>
                  Ступінь прозорості, фем - <b><fmt:formatNumber value="${analyses.transparency}"/></b><br>
                  Перекисне число, &#189;O ммоль/кг - <b><fmt:formatNumber value="${analyses.peroxideValue}"/></b><br>
                  Бенз(а)пірен, мкг/кг - <b><fmt:formatNumber value="${analyses.benzopyrene}"/></b><br>
                </p>
                <p>
                  Залишковий вміст хлорорганічних пестицидів, токсичних елементів,<br>
                  мікотоксинів відповідає вимогам МБТ 5061-89<br>
                  Радіологічні показникик не перевищують<br>
                  допустимих рівнів згідно з ГН 6.6.1.1  - 130 - 2006<br>
                </p>
                <p>
                  <b>
                    Протокол випробовувань №${protocol.number} від <fmt:formatDate value="${protocol.date}" pattern="dd.MM.yyyy"/><br>
                    виданий ДП "Сумистандарт метрологія"<br><br>
                    <b>
                      Відповідає ДСТУ 4492:2017<br>
                    </b>
                  </b>
                </p>
                <p>
                  <b>
                    Сертифікат відповідності в державній системі<br>
                    сертифікації УкрСЕПРО №UA1.036.X002563-17<br>
                  </b>
                </p>
                <p style="margin-top: 24pt">
                    <span>
                      Змінний хімік
                    </span>
                    <span style="float: right">
                      ${responsible.value}
                    </span>
                </p>
              </td>
            </tr>
            <tr>
              <td>
                Одержувач: ${product.dealProduct.deal.organisation.value}
              </td>
            </tr>
            <tr>
              <td>
                № а/ц: ${product.transportation.vehicle.trailer.number}
              </td>
            </tr>
            <tr>
              <td>
                Маса нетто - <b><fmt:formatNumber value="${weight.netto}"/> т.</b>
              </td>
            </tr>
            <tr>
              <td>
                <span>
                  Дата виготовлення:
                  <fmt:formatDate value="${manufacture}" pattern="dd.MM.yyyy"/>
                </span>
              </td>
            </tr>
            <tr>
              <td style="height: 100%; padding-top: 24pt" valign="top">
                <span>
                  Термін зберігання - ${properties['shelfLife']} від дати виготовлення
                </span>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
</body>
</html>
