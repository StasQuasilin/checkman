<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .header-text{
        font-size: 10pt;
        display: table-cell;
        text-align: left;
        vertical-align: middle;
        padding: 0 18pt;
    }
</style>
<c:set var="logoUrl" value="${context}/images/logo1.png"/>
<div style="display: table-row; width: 100%" >
    <span style="display: table-cell" class="header-text">
      Україна, 42350<br>
      Сумський район<br>
      с.Бездрик, вул. Зарічна, 1<br>
      olivija@olivija.ua<br>
      +38 0542 700 488
    </span>
    <img src="${logoUrl}" style="width: 70%; height: 70%">
    <span style="display: table-cell" class="header-text">
      <b>ПАТ "Сумський завод<br>продтоварів"</b><br>
      http://olivija.ua
    </span>
</div>