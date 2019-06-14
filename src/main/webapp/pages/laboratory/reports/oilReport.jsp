<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<table>
  <tr>
    <td>
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
        Декларація виробника №
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
        14 червня 2019
      </b>
    </td>
  </tr>
  <tr>
    <td>
      <table>
        <tr>
          <td>
            <jsp:include page="manufactoryLabel.jsp"/>
          </td>
          <td rowspan="5" style="font-style: italic; border-left: solid 1pt">
            Рід - <b>соняшникова</b><br>
            Вид -
            Гатунок - <b>перший</b><br>
            Прозорість - <b>легке помутніння</b><br>
            Запах та смак - <b>властивий соняшниковій олії, без гіркоти</b><br>
            Колірне число, мг йоду<br>
            Кислотне число, мг КОН/г<br>
            Масова частка фосфоровміснх речовн в перерахунку на стеароолеолецитин<br>
            Масова частка вологи та летких речовин<br>
            Масова частка нежирових домішок<br>
            Ступінь прозорості, фем<br>
            Перекисне число, &#189;O ммоль/кг<br>
            Бенз(а)пірен, мкг/кг<br>
            Залишковий вміст хлорорганічних пестицидів, токсичних елементів,<br>
            мікотоксинів відповідає вимогам МБТ 5061-89<br>
            Радіологічні показникик не перевищують<br>
            допустимих рівнів згідно з ГН 6.6.1.1  - 130 - 2006<br>
            <b>
            Протокол випробовувань №469 від 02.04.2019<br>
            виданий ДП "Сумистандарт метрологія"<br>
            </b>
            <b>
            Відповідає ДСТУ 4492:2017<br>
            Сертифікат відповідності в державній<br>
            системі сертифікації УкрСЕПРО №UA1.036.X002563-17<br>
            </b>
          </td>
        </tr>
        <tr>
          <td>
            Одержувач
          </td>
        </tr>
        <tr>
          <td>
            № а/ц
          </td>
        </tr>
        <tr>
          <td>
            Маса нетто
          </td>
        </tr>
        <tr>
          <td>
            Дата виготовлення
          </td>
        </tr>
        <tr>
          <td>
            Термін зберігання - з місяці від дати виготовлення
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html>
