<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 31.01.2020
  Time: 10:51
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/templates/vehicleInput.vue"></script>
<script>
    var printer = new Vue({
        el:'#printer',
        components:{
          'object-input':objectInput
        },
        data:{
            api:{},
            transportation:{},
            legalAddress:{},
            loadAddress:[],
            vehicleProps:{},
            trailerProps:{},
            transporterProps:{},
            driverProps:{},
            organisationProps:{}
        },
        methods:{
            print:function(){

            }
        }
    });

    printer.transportation = ${transportation.toJson()};

    printer.vehicleProps = {
        find:'${findVehicle}',
        add:'${parseVehicle}',
        edit:'${editVehicle}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.vehicle"/>',
        put:function(vehicle, item){
            if (item) {
                item.vehicle = vehicle;
            }
        },
        show:['model', 'number']
    };
    printer.trailerProps = {
        find:'${findTrailer}',
        add:'${parseTrailer}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.trailer"/>',
        put:function(trailer, item){
            if (item) {
                item.trailer = trailer;
            }
        },
        show:['number']
    };
    printer.transporterProps = {
        find:'${findOrganisation}',
        add:'${parseOrganisation}',
        edit:'${editOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.transporter"/>',
        put:function(transporter, item){
            if(item) {
                PostApi(save, {id: item.id, transporter: transporter.id});
            }
        },
        show:['value']
    };
    printer.driverProps = {
        find:'${findDriver}',
        add:'${parseDriver}',
        edit:'${editDriver}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="transportation.driver.insert.info"/>',
        put:function(driver, item){
            if (item) {
                PostApi(save, {id: item.id, driver: driver.id});
            }
        },
        show:['person/surname','person/forename','person/patronymic']
    };
    printer.organisationProps = {
        find:'${findOrganisation}',
        add:'${parseOrganisation}',
        edit:'${editOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.transporter"/>',
        put:function(transporter, item){
            if(item) {
                PostApi(save, {id: item.id, transporter: transporter.id});
            }
        },
        show:['value']
    };
    printer.legalAddress = ${legalAddress.toJson()};
    <c:forEach items="${loadAddress}" var="address">
    printer.loadAddress.push(${address.toJson()});
    </c:forEach>

</script>
<table style="width: 100%" id="printer">
    <tr>
        <td>
            <fmt:message key="transportation.automobile.number"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="date"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.automobile"/>
        </td>
        <td>
            <object-input :props="vehicleProps" :object="transportation.vehicle" :item="transportation"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.automobile.trailer"/>
        </td>
        <td>
            <object-input :props="trailerProps" :object="transportation.trailer" :item="transportation"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.transporter"/>
        </td>
        <td>
            <object-input :props="transporterProps" :object="transportation.transporter" :item="transportation"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transportation.driver"/>
        </td>
        <td>
            <object-input :props="driverProps" :object="transportation.driver" :item="transportation"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="driver.license"/>
        </td>
        <td>
            {{transportation.license}}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="deal.organisation"/>
        </td>
        <td>
            <object-input :props="organisationProps" :object="transportation.counterparty" :item="transportation"></object-input>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="legal.address"/>
        </td>
        <td>
            <a>
                {{legalAddress}}
            </a>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="address"/>
        </td>
        <td>

        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="weight.brutto"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="weight.netto"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="print">
                <fmt:message key="document.print"/>
            </button>
        </td>
    </tr>
</table>
</html>
