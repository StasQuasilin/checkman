<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by Kvasik
  Date: 02.02.2020
  Time: 15:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  *{
    font-family: Arial, serif;
  }
  .label{
    display: inline-block;
    height: 100%;
    vertical-align: top;
    font-size: 9pt;
  }
  .master-cell{
    display: inline-block;
    padding-left: 4pt;
    margin-right: 8pt;
  }
  .cell{
    border-bottom: solid black 1pt;
    padding-left: 4pt;
  }
  .sub-cell{
    width: 100%;
    text-align: center;
    font-size: 8pt;
  }
  table{
    border-collapse: collapse;
  }
</style>
<html>
  <table width="100%" style="font-size: 9.5pt" border="0">
    <tr>
      <td>
        <div style="margin-left: 70%">
          <div>
            Додаток 7<br>
            до Правил перевезень вантажів<br>
            автомобільним транспортом в Україні
            пункт 11.1 глави 11
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td align="center">
        <b>
          ТОВАРО-ТРАНСПОРТНА НАКЛАДНА
        </b>
      </td>
    </tr>
    <tr>
      <td align="right">
        <b>
          Форма N 1-TH
        </b>
      </td>
    </tr>
    <tr>
      <td align="center" valign="top">
        №
        <span class="cell" style="margin-right: 38pt">
         P${number}
        </span>
        <span class="cell">
          ${date}
        </span>
      </td>
    </tr>
    <tr>
      <td valign="top">
        <span class="label">
          Автомобіль
        </span>
        <div class="master-cell" style="width: 37%">
          <div class="cell">
            ${transportation.vehicle.model}
            ${transportation.vehicle.number}
          </div>
          <div class="sub-cell">
            (марка, модель, тип, реєстраційний номер)
          </div>
        </div>
        <span class="label">
          Причіп/напівпричіп
        </span>
        <div class="master-cell" style="width: 20%">
          <div class="cell">
            ${transportation.trailer.number}
          </div>
          <div class="sub-cell">
            (марка, модель, тип, реєстраційний номер)
          </div>
        </div>
        <div class="label">
          Вид перевезень
        </div>
        <div class="master-cell" style="width: 13%">
          <div class="cell">
            авто
          </div>
          <div class="sub-cell">
            &nbsp;
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Автомобільний перевізник
        </span>
        <div class="master-cell" style="width: 46%">
          <div class="cell">
            ${transportation.transporter.value}
          </div>
          <div class="sub-cell">
            (найменування / П.І.Б)
          </div>
        </div>
        <span class="label">
          Водій
        </span>
        <div class="master-cell" style="width: 32%">
          <div class="cell">
            ${transportation.driver.person.value}
            ${transportation.driverLicense}
          </div>
          <div class="sub-cell">
            (П.І.Б., номер посвідчення водія)
          </div>
        </div>

      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Замовник (платник)
        </span>
        <div class="master-cell" style="width: 87%">
          <div class="cell">
            ${organisationType.fullName} "${transportation.counterparty.name}"
          </div>
          <div class="sub-cell">
            (найменування / П.І.Б.)
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Вантажовідправник
        </span>
        <div class="master-cell" style="width: 87%">
          <div class="cell">
            ПРИВАТНЕ АКЦІОНЕРНЕ ТОВАРИСТВО "СУМСЬКИЙ ЗАВОД ПРОДОВОЛЬЧИХ ТОВАРІВ",
            адреса: Сумська область, Сумський район, село Бездрик, вул. Зарічна, буд. 1
          </div>
          <div class="sub-cell">
            (повне найменування, місцезнаходження / П.І.Б., місце проживання)
          </div>
        </div>

      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Вантажоодержувач
        </span>
        <div class="master-cell" style="width: 87%">
          <div class="cell">
            ${organisationType.fullName} "${transportation.counterparty.name}",
              <c:if test="${not empty legalAddress}">
                юр. адреса:
                <c:if test="${not empty legalAddress.address.index}">
                  ${legalAddress.address.index},
                </c:if>
                <c:if test="${not empty legalAddress.address.region}">
                  ${legalAddress.address.region} область,
                </c:if>
                <c:if test="${not empty legalAddress.address.district}">
                  ${legalAddress.address.district} район,
                </c:if>
                ${legalAddress.address.city},
                ${legalAddress.address.street},
                ${legalAddress.address.build},
              </c:if>
          </div>
          <div class="sub-cell">
            (повне найменування, місцезнаходження / П.І.Б., місце проживання)
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Пункт навантаження
        </span>
        <div class="master-cell">
          <div class="cell">
            Сумська обл., Сумський район, село Бездрик, вул. Зарічна, буд. 1
          </div>
          <div class="sub-cell">
            (місцезнаходження)
          </div>
        </div>
        <span class="label">
          Пункт розвантаження
        </span>
        <div class="master-cell" style="width: 43%">
          <div class="cell">
            <c:if test="${not empty address.index}">
              ${address.index},
            </c:if>
            <c:if test="${not empty address.region}">
              ${address.region} область,
            </c:if>
            <c:if test="${not empty address.district}">
              ${address.district} район,
            </c:if>
            ${address.city},
            ${address.street},
            ${address.build},
          </div>
          <div class="sub-cell">
            (місцезнаходження)
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Переадресування вантажу
        </span>
        <div class="master-cell" style="width: 85%">
          <div class="cell">
            &nbsp;
          </div>
          <div class="sub-cell">
            (найменування, місцезнаходження /
            П.І.Б., місце проживання нового вантажоодержувача;
            П.І.Б., посада та підпис відповідальної особи)
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          відпуск за довіреністю вантажоодержувача: серія
        </span>
        <div class="master-cell" style="width: 4%">
          <div class="cell">

          </div>
        </div>
        <span class="label">
          №
        </span>
        <div class="master-cell" style="width: 6%">
          <div class="cell">

          </div>
        </div>
        <span class="label">
          від
        </span>
        <div class="master-cell" style="width: 12%">
          <div class="cell">

          </div>
        </div>
        <span class="label">
          виданою
        </span>
        <div class="master-cell" style="width: 40%">
          <div class="cell">

          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Вантаж наданий для перевезення у стані, що
        </span>
        <div class="master-cell">
          <div class="cell">
            Відповідає
          </div>
          <div class="sub-cell">
            (відповідає / не відповідає)
          </div>
        </div>
        <span class="label">
          правилам перевезень відповідних вантажів, номер пломби (за наявності)
        </span>
        <div class="master-cell">
          <div class="cell" style="font-size: 9pt">
            <c:forEach items="${seals}" var="seal" varStatus="status">
            <span>
              ${seal.value}
              <c:if test="${!status.last}">
                ,
              </c:if>
            </span>
            </c:forEach>
          </div>
          <div class="sub-cell">
            &nbsp;
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          кількість місць
        </span>
        <div class="master-cell" style="width: 21%">
          <div class="cell">
            ${netto}
          </div>
          <div class="sub-cell">
            (словами)
          </div>
        </div>
        <span class="label">
          , масою брутто, т
        </span>
        <div class="master-cell" style="width: 21%">
          <div class="cell">
            ${brutto}
          </div>
          <div class="sub-cell">
            (словами)
          </div>
        </div>
        <span class="label">
          , отримав водій/експедитор
        </span>
        <div class="master-cell" style="width: 24%">
          <div class="cell">
            ${transportation.driver.person.value}, водій
          </div>
          <div class="sub-cell">
            (П.І.Б., посада, підпис)
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Бухгалтер (відповідальна особа вантажовідправника)
        </span>
        <div class="master-cell" style="width: 28%">
          <div class="cell">
            ${booker.person.value}, вагар-обліковець
          </div>
          <div class="sub-cell">
            (П.І.Б., посада, підпис)
          </div>
        </div>
        <span class="label">
          Відпуск дозволив
        </span>
        <div class="master-cell" style="width: 35%">
          <div class="cell">
            ${allowed.person.value}, старший охоронник
          </div>
          <div class="sub-cell">
            (П.І.Б., посада, підпис)
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Усього відпущено на загальну суму
        </span>
        <div class="master-cell" style="width: 48%">
          <div class="cell">
            <c:if test="${not empty price}">
              ${sumWords}
            </c:if>
            &nbsp;
          </div>
          <div class="sub-cell">
            (словами, з урахуванням ПДВ)
          </div>
        </div>
        <span class="label">
          , у т.ч. ПДВ
        </span>
        <div class="master-cell" style="width: 26%">
          <div class="cell">
            ${tax}
          </div>
          <div class="sub-cell">
            &nbsp;
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td>
        <span class="label">
          Супровідні документи на вантаж
        </span>
        <div class="master-cell" style="width: 82%">
          <div class="cell">
            Декларація виробника
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <td align="center">
        <b>
          ВІДОМОСТІ ПРО ВАНТАЖ
        </b>
      </td>
    </tr>
    <tr>
      <td>
        <table width="100%" border="1" style="font-size: 8pt">
          <tr>
            <td align="center" style="width: 2em">
              №<br>
              з/п
            </td>
            <td align="center" style="width: 36em">
              Найменування вантажу (номер контейнера), у разі перевезення
              небезпечних вантажів: клас небезпечних речовин, до якого віднесено вантаж
            </td>
            <td align="center">
              Один. виміру
            </td>
            <td align="center">
              Кількість місць
            </td>
            <td align="center">
              Ціна без ПДВ за одиницю, грн
            </td>
            <td align="center">
              Загальна сума з ПДВ, грн
            </td>
            <td align="center">
              Вид пакування
            </td>
            <td align="center">
              Документи з вантажем
            </td>
            <td align="center">
              Маса брутто, т
            </td>
          </tr>
          <tr>
            <td align="center">1</td>
            <td align="center">2</td>
            <td align="center">3</td>
            <td align="center">4</td>
            <td align="center">5</td>
            <td align="center">6</td>
            <td align="center">7</td>
            <td align="center">8</td>
            <td align="center">9</td>
          </tr>
          <tr>
            <td align="center">1</td>
            <td align="center">
              ${transportation.product.name}
            </td>
            <td align="center">
              ${transportation.product.unit.name}
            </td>
            <td align="center">
              ${transportation.weight.netto}
            </td>
            <td align="center">
              ${price}
            </td>
            <td align="center">
              ${sum}
            </td>
            <td align="center"></td>
            <td align="center">
              Декларація виробника
            </td>
            <td align="center">
              ${transportation.weight.brutto}
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="center" style="padding: 8pt 0">
        <table style="font-size: 9pt">
          <tr>
            <td align="center">
              Здав (відповідальна особа вантажовідправника)
            </td>
            <td align="center">
              Прийняв водій/експедитор
            </td>
            <td align="center">
              Здав водій/експедитор
            </td>
            <td align="center">
              Прийняв (відповідальна особа вантажоодержувача)
            </td>
          </tr>
          <tr>
            <td style="width: 25%">
              <div style="border-bottom: solid black 1pt; width: 90%">
                ${handedOver.person.value}, приймальник-здавальник
              </div>
            </td>
            <td style="width: 25%">
              <div style="border-bottom: solid black 1pt; width: 90%">
                ${transportation.driver.person.value}, водій
              </div>
            </td>
            <td style="width: 25%">
              <div style="border-bottom: solid black 1pt; width: 90%">
                ${transportation.driver.person.value}, водій
              </div>
            </td>
            <td style="width: 25%">
              <div style="border-bottom: solid black 1pt; width: 90%">
                &nbsp;
              </div>
            </td>
          </tr>
          <tr>
            <td align="center">
              (П.І.Б., посада, підпис)
            </td>
            <td align="center">
              (П.І.Б., посада, підпис)
            </td>
            <td align="center">
              (П.І.Б., посада, підпис)
            </td>
            <td align="center">
              (П.І.Б., посада, підпис)
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td align="center">
        <b>
          ВАНТАЖНО-РОЗВАНТАЖУВАЛЬНІ ОПЕРАЦІЇ
        </b>
      </td>
    </tr>
    <tr>
      <td align="center">
        <table width="100%" border="1" style="font-size: 8pt">
          <tr>
            <td rowspan="2" align="center">
              Операція
            </td>
            <td rowspan="2" align="center">
              Маса брутто, т
            </td>
            <td colspan="3" align="center">
              Час (год., хв.)
            </td>
            <td rowspan="2" align="center">
              Підпис відповідальної особи
            </td>
          </tr>
          <tr>
            <td align="center" style="width: 15em">
              прибуття
            </td>
            <td align="center" style="width: 15em">
              вибуття
            </td>
            <td align="center" style="width: 15em">
              простою
            </td>
          </tr>
          <tr>
            <td align="center">10</td>
            <td align="center">11</td>
            <td align="center">12</td>
            <td align="center">13</td>
            <td align="center">14</td>
            <td align="center">15</td>
          </tr>
          <tr>
            <td>
              Навантаження
            </td>
            <td align="center">
              ${transportation.weight.brutto}
            </td>
            <td align="center">
              <fmt:formatDate value="${transportation.timeIn.time}" pattern="dd.MM.yyyy HH:mm"/>
            </td>
            <td align="center">
              <fmt:formatDate value="${transportation.timeOut.time}" pattern="dd.MM.yyyy HH:mm"/>
            </td>
            <td>
              &nbsp;
            </td>
            <td>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td>Ровантаження</td>
            <td align="center"></td>
            <td align="center"></td>
            <td align="center"></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</html>
