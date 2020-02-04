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
<link rel="stylesheet" href="${context}/css/editor.css"/>
<script>
    var printer = new Vue({
        el:'#printer',
        components:{
          'object-input':objectInput
        },
        data:{
            api:{},
            date:new Date().toISOString().substring(0, 10),
            number:'',
            transportation:{},
            legalAddress:{},
            address:-1,
            loadAddress:[],
            brutto:0,
            netto:0,
            price:0,
            weighter:-1,
            security:-1,
            warehousing:-1,
            weighters:[],
            securitys:[],
            warehousings:[],
            vehicleProps:{},
            trailerProps:{},
            transporterProps:{},
            driverProps:{},
            organisationProps:{}
        },
        methods:{
            changeDate:function(){
                const self = this;
              datepicker.show(function(date){
                  self.date = date;
              }, this.date)
            },
            editAddress:function(type, id, onSave){
                var data = {
                    counterparty:this.transportation.counterparty.id,
                    type:type,
                    id:id
                };
                loadModal(this.api.addressEdit, data, function(a){
                    onSave(a);

                })
            },
            editLoadAddress:function(id){
                const self = this;

                this.editAddress('load', id, function(a){
                    var found = false;
                    for(var i = 0; i < self.loadAddress.length; i++){
                        if (self.loadAddress[i].id == a.id){
                            self.loadAddress.splice(i, 1, a);
                            found = true;
                        }
                    }
                    if (!found){
                        self.loadAddress.push(a);
                    }
                });

            },
            editLegalAddress:function(){
                var id = -1;
                if (this.legalAddress.id){
                    id = this.legalAddress.id;
                }
                const self = this;
                this.editAddress('legal', id, function(a){
                    if (a.result){
                        self.legalAddress = a.result;
                    } else{
                        self.legalAddress = a;
                    }
                });
            },
            print:function(){
                var data = {
                    number:this.number,
                    date:this.date,
                    transportation:this.transportation.id,
                    address:this.address,
                    brutto:this.brutto,
                    netto:this.netto,
                    price:this.price,
                    booker:this.weighter,
                    allowed:this.security,
                    handedOver:this.warehousing
                };
                PostReq(this.api.print, data, function(a){
                    var print = window.open();
                    print.document.write(a);
                    print.document.close();
                    setTimeout(function(){
                        print.print();
//                        print.close();
                    }, 500);
                })
            }
        }
    });

    printer.api.print = '${print}';
    printer.api.addressEdit = '${editAddress}';
    printer.number = ${number};
    printer.transportation = ${transportation.toJson()};
    <c:if test="${not empty transportation.weight}">
    printer.brutto = ${transportation.weight.brutto};
    printer.netto = ${transportation.weight.netto};
    </c:if>
    printer.price = ${price};
    <c:forEach items="${weightres}" var="w">
    printer.weighters.push(${w.toJson()});
    </c:forEach>
    <c:forEach items="${securitys}" var="s">
    printer.securitys.push(${s.toJson()});
    </c:forEach>
    <c:forEach items="${warehousings}" var="wa">
    printer.warehousings.push(${wa.toJson()});
    </c:forEach>
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
                item.transporter = transporter;
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
            item.driver = driver;
        },
        show:['person/surname','person/forename','person/patronymic']
    };
    printer.organisationProps = {
        find:'${findOrganisation}',
        add:'${parseOrganisation}',
        edit:'${editOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.transporter"/>',
        put:function(counterparty, item){
            if(item) {
                item.counterparty = counterparty;
            }
        },
        show:['value']
    };
    <c:if test="${not empty legalAddress}">
    printer.legalAddress = ${legalAddress.toJson()};
    </c:if>
    <c:forEach items="${loadAddress}" var="address">
    printer.loadAddress.push(${address.toJson()});
    </c:forEach>

</script>
<style>
    .selected{
        background-color: #c2c2c2;
    }
</style>
<table style="width: 100%" id="printer" class="editor">
    <tr>
        <td>
            <label for="number">
                <fmt:message key="transportation.automobile.number"/>
            </label>
        </td>
        <td>
            <input id="number" v-model="number" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="date"/>
        </td>
        <td>
            <span class="mini-close" v-on:click="changeDate()">
                {{new Date(date).toLocaleDateString()}}
            </span>
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
            <span v-if="transportation.driver">
                {{transportation.driver.license}}
            </span>

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
            <a v-on:click="editLegalAddress()">
                <template v-if="!legalAddress.id">
                    <fmt:message key="add.address"/>
                </template>
                <template v-else>
                    {{legalAddress.city}}
                    {{legalAddress.street}}
                    {{legalAddress.build}}
                </template>
            </a>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="load.address"/>
        </td>
        <td>
            <a v-on:click="editLoadAddress(-1)">
                <fmt:message key="add.address"/>
            </a>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div style="width: 100%; height: 80pt; border: solid gray 1pt; overflow-y: scroll">
                <div v-for="a in loadAddress" style="padding: 1pt" :class="{'selected' : address == a.id}"
                     v-on:click="address = a.id" v-on:dblclick="editLoadAddress(a.id)">
                    {{a.city}}
                    {{a.street}}
                    {{a.build}}
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label for="brutto">
                <fmt:message key="weight.brutto"/>
            </label>
        </td>
        <td>
            <input id="brutto" type="number" step="0.01" v-model="brutto" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="netto">
                <fmt:message key="weight.netto"/>
            </label>
        </td>
        <td>
            <input id="netto" type="number" step="0.01" v-model="netto" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="price">
                <fmt:message key="deal.price"/>
            </label>
        </td>
        <td>
            <input id="price" type="number" step="0.01" v-model="price" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td>
            <label for="weighter">
                <fmt:message key="booker"/>
            </label>
        </td>
        <td>
            <select id="weighter" v-model="weighter" style="width: 100%">
                <option v-for="w in weighters" :value="w.id">
                    {{w.person.value}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="security">
                <fmt:message key="allowed"/>
            </label>
        </td>
        <td>
            <select id="security" v-model="security" style="width: 100%">
                <option v-for="s in securitys" :value="s.id">
                    {{s.person.value}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="warehousing">
                <fmt:message key="handed.over"/>
            </label>
        </td>
        <td>
            <select id="warehousing" v-model="warehousing" style="width: 100%">
                <option v-for="w in warehousings" :value="w.id">
                    {{w.person.value}}
                </option>
            </select>
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
