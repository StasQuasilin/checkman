<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by Kvasik
  Date: 02.02.2020
  Time: 15:05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  *{

  }
  .label{
    display: inline-block;
    height: 100%;
    vertical-align: top
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
</style>
<html style="width: 320mm; height: 210mm; border: solid grey 1pt">
  <table width="100%" style="font-size: 10pt" border="1">
    <tr>
      <td>
        <div style="margin-left: 70%">
          <div>
            Додаток 7<br>
            до Правил перевезень вантажів<br>
            автомобільним транспортом в Україні
          </div>
          <div style="margin-top: 8pt">
            Форма N 1-TH
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
      <td align="center" valign="top">
        № ${number}
        ${date}
      </td>
    </tr>
    <tr>
      <td valign="top">
        <span class="label">
          Автомобіль
        </span>
        <div class="master-cell" style="width: 37%">
          <div class="cell">
            ${transportation.vehicle.value}
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
        <div class="label" style="width: 10%">
          <span>
            Вид перевезень
          </span>
          <span class="cell">
            авто
          </span>
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
            "${transportation.counterparty.name}"
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
            "${transportation.counterparty.name}", юр. адреса:
          </div>
          <div class="sub-cell">
            (повне найменування, місцезнаходження / П.І.Б., місце проживання)
          </div>
        </div>

      </td>
    </tr>
    <tr>
      <td>
        Пункт навантаження
        <span class="cell">
          Сумська обл., Сумський район, село Бездрик, вул. Зарічна, буд. 1
        </span>
        Пункт розвантаження
        <span class="cell">
          ${address.city}
          ${address.street}
          ${address.build}
        </span>
      </td>
    </tr>
    <tr>
      <td>
        Переадресування вантажу
      </td>
    </tr>

  </table>
</html>
