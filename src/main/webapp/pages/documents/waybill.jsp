<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html>
<style type="text/css" media="print">
    @page {
        size: landscape;
    }
    .data-container{
        text-align: center;
        display: inline-block;
    }
    .data-container .main{
        border-bottom: solid black 1pt;
        width: 100%;
        display: inline-block;
        font-style: italic;
    }
    .data-container .note{
        font-size: 8pt;
        display: block;
    }
</style>
<table border="0" width="100%" style="font-size: 10pt">
    <tr>
        <th align="center">
            <div style="padding-bottom: 10pt">
                <span>
                    <fmt:message key="waybill.title"/>&nbsp;0
                </span>
            </div>
        </th>
    </tr>
    <tr>
        <td>
            <span style="vertical-align: top">
                <fmt:message key="waybill.vehicle"/>
            </span>
            <div class="data-container" style="width: 24%">
                <span class="main">
                    ${vehicle.model}
                    ${vehicle.number}
                </span>
                <span class="note">
                    (<fmt:message key="waybill.vehicle.description"/>)
                </span>
            </div>
            <span style="vertical-align: top; margin-left: 15pt">
                <fmt:message key="waybill.trailer"/>
            </span>
            <div class="data-container" style="width: 24%">
                <span class="main">
                    ${vehicle.trailer}
                </span>
                <span class="note">
                    (<fmt:message key="waybill.vehicle.description"/>)
                </span>
            </div>
            <span style="vertical-align: top; margin-left: 15pt">
                <fmt:message key="waybill.transportation.type"/>
            </span>
            <div class="data-container" style="width: 20%">
                <span class="main" style="text-align: left">
                    aвто
                </span>
                <span class="note">
                    &nbsp;
                </span>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <span style="vertical-align: top;">
                <fmt:message key="waybill.transporter"/>
            </span>
            <div class="data-container" style="width: 55%">
                <span class="main">
                    &nbsp;
                </span>
                <span class="note">
                    (<fmt:message key="waybill.transporter.description"/>)
                </span>
            </div>
            <span style="vertical-align: top; margin-left: 15pt">
                <fmt:message key="waybill.driver"/>
            </span>
            <div class="data-container" style="width: 20%">
                <span class="main">
                    ${driver.person.value}
                </span>
                <span class="note">
                    (<fmt:message key="waybill.driver.description"/>)
                </span>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <span style="vertical-align: top;">
                <fmt:message key="waybill.shipper"/>
            </span>
            <div class="data-container" style="width: 80%">
                <span class="main" style="text-align: left">
                    ${from.value}
                </span>
                <span class="note">
                    <fmt:message key="waybill.shipper.description"/>
                </span>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <span style="vertical-align: top;">
                <fmt:message key="waybill.consignee"/>
            </span>
            <div class="data-container" style="width: 80%">
                <span class="main" >
                    &nbsp;
                </span>
                <span class="note">
                    <fmt:message key="waybill.shipper.description"/>
                </span>
            </div>

        </td>
    </tr>

</table>
</html>