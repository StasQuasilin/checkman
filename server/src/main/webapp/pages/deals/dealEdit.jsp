<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/dealEdit.vue"></script>
    <script>
        <c:forEach items="${types}" var="t">
        editor.types.push({
            id:'${t}',
            value:'<fmt:message key="${t}"/>'
        });</c:forEach>
        <c:forEach items="${products}" var="product">
        editor.products.push({
            id:${product.id},
            value:'${product.name}'
        });</c:forEach>
        <c:forEach items="${shippers}" var="shipper">
        editor.shippers.push({
            id:${shipper.id},
            value:'${shipper.value}'
        });</c:forEach>
        <c:forEach items="${units}" var="u">
        editor.units.push({
            id:${u.id},
            value:'${u.name}'
        });
        </c:forEach>

        editor.api.findOrganisation = '${findOrganisation}';
        editor.api.parseOrganisation = '${parseOrganisation}';
        editor.api.editCounterparty = '${editOrganisation}';
        editor.api.save = '${save}';
        editor.api.redirect = '${redirect}';
        <c:choose>
        <c:when test="${not empty deal}">
        editor.deal={
            id : ${deal.id},
            type : '${deal.type}',
            date : '${deal.date}',
            dateTo : '${deal.dateTo}',
            counterparty : ${deal.organisation.id},
            number:''
        };

        <c:if test="${not empty deal.unit}">
        editor.deal.unit = ${deal.unit.id};
        </c:if>
        editor.deal.price = ${deal.price};
        editor.counterpartyInput = editor.counterpartyName = '${deal.organisation.value}';
        </c:when>
        <c:otherwise>
        editor.deal.type = '${type}';
        if (editor.shippers.length > 0){
            editor.deal.realisation= editor.shippers[0].id;
        }
        if (editor.products.length > 0){
            editor.deal.product = editor.products[0].id;
        }
        if (editor.units.length > 0){
            editor.deal.unit = editor.units[0].id;
        }
        </c:otherwise>
        </c:choose>

    </script>
    <style>
        .invisible{
            background-color: transparent;
            border: solid gray 1px;
        }
        .invisible:focus{
            outline: none;
        }
    </style>
    <link rel="stylesheet" href="${context}/css/editor.css">
    <c:set var="findCunterparty"><fmt:message key="counterparty.find"/></c:set>
    <c:set var="addCunterparty"><fmt:message key="counterparty.add"/></c:set>
    <table id="editor" class="editor">
        <tr>
            <td>
                <label for="date">
                    <fmt:message key="period"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="date" readonly style="width: 7em" v-model="new Date(deal.date).toLocaleDateString()" v-on:click="showDatePicker">
                <span>-</span>
                <input readonly style="width: 7em" v-model="new Date(deal.dateTo).toLocaleDateString()" v-on:click="showDateToPicker">
            </td>
        </tr>
        <tr>
            <td>
                <label for="counterparty">
                    <fmt:message key="deal.organisation"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <div v-if="deal.counterparty == -1" v-on:blur="newCounterparty()">
                    <span  style="display: inline-block">
                        <input id="counterparty" autocomplete="off" style="width: 100%"
                               :class="{error : errors.organisation}"
                               onclick = "this.select()"
                               placeholder="${findCounterparty}"
                               v-on:keyup="findOrganisation()"
                               v-model="counterpartyInput"/>
                    </span>
                    <span class="mini-close" v-on:click="newCounterparty()" title="${newCounterparty}">+</span>
                    <div id="contragent-list" class="custom-data-list" v-if="foundOrganisations.length > 0">
                        <div class="custom-data-list-item" v-for="organisation in foundOrganisations"
                             v-on:click="setCounterparty(organisation)">{{organisation.value}}</div>
                    </div>
                </div>
                <div v-else>
                    <span>
                        {{counterpartyInput}}
                    </span>
                    <span>
                        <span class="mini-close flipY" style="padding: 0"
                              v-on:click="editOrganisation()"
                              style="-webkit-transform: scaleX(-1)">
                            &#9998;</span>
                        <span class="mini-close" style="padding: 0" v-on:click="cancelOrganisation()">
                            &times;</span>
                    </span>
                </div>

            </td>
        </tr>
        <tr>
            <td>
                <label for="number">
                    <fmt:message key="deal.number"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input id="number" autocomplete="off" v-model="deal.number">
            </td>
        </tr>
        <tr>
           <td colspan="3">
               <div style="border: solid gray 1pt; max-height: 140px; overflow-y: scroll; padding: 2pt">
                   <div v-for="(dealProduct, idx) in deal.dealProducts" style="border-bottom: dashed gray 1pt; padding-bottom: 1pt">
                       <div style="display: inline-block">
                           <div>
                               <span v-if="deal.dealProducts.length > 1" class="mini-close" v-on:click="removeDealProduct(idx)">
                                   &times;
                               </span>
                               <span v-else>
                                   &nbsp;
                               </span>
                               <select id="type" v-model="dealProduct.type" class="invisible">
                                   <option v-for="type in types" :value="type.id">{{type.value}}</option>
                               </select>
                               <select id="product" v-model="dealProduct.product" class="invisible">
                                   <option v-for="product in products" :value="product.id">
                                       {{product.value}}
                                   </option>
                               </select>
                               <input id="amount" type="number" v-model="dealProduct.amount"
                                      autocomplete="off" onfocus="this.select()">
                           </div>
                           <div>
                               <label for="price">
                                   <fmt:message key="deal.price"/>
                               </label>
                               <input id="price" v-model="dealProduct.price" type="number"
                                      autocomplete="off" onfocus="this.select()">
                               <fmt:message key="deal.from"/>
                               <select id="shipper" v-model="dealProduct.shipper" class="invisible">
                                   <option v-for="shipper in shippers" :value="shipper.id">
                                       {{shipper.value}}
                                   </option>
                               </select>
                               <fmt:message key="amount.total"/>:
                               <b>
                                   {{dealProduct.total().toLocaleString()}}
                               </b>
                           </div>
                       </div>

                   </div>
               </div>
               <div>
                   <span style="font-size: 10pt; color: gray">
                    <fmt:message key="amount.total"/>: {{deal.dealProducts.length}}
                    <fmt:message key="deal.amount"/>:{{total().toLocaleString()}}
                   </span>
                   <a v-on:click="newDealProduct" style="float: right">
                       + <fmt:message key="button.add"/>
                   </a>
               </div>
           </td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <button class="left-button close-button" onclick="closeModal()" ondblclick="">
                    <fmt:message key="button.cancel"/>
                </button>
                <button class="middle-button save-button" v-on:click="saveAndClose()" v-on:dblclick="">
                    <fmt:message key="button.save.and.close"/>
                </button>
                <button class="right-button save-button" v-on:click="saveAndRedirect()" v-on:dblclick="">
                    <fmt:message key="button.save.and.continue"/>
                </button>
            </td>
        </tr>
    </table>
</html>