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
        editor.realisations.push({
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
            realisation : ${deal.shipper.id},
            product : ${deal.product.id},
            quantity : ${deal.quantity},
            price: ${deal.price}
        };

        <c:if test="${not empty deal.unit}">
        editor.deal.unit = ${deal.unit.id};
        </c:if>
        editor.deal.price = ${deal.price};
        editor.counterpartyInput = editor.counterpartyName = '${deal.organisation.value}';
        </c:when>
        <c:otherwise>
        editor.deal.type = '${type}';
        if (editor.realisations.length > 0){
            editor.deal.realisation= editor.realisations[0].id;
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
    <link rel="stylesheet" href="${context}/css/editor.css">
    <c:set var="findCunterparty"><fmt:message key="counterparty.find"/></c:set>
    <c:set var="addCunterparty"><fmt:message key="counterparty.add"/></c:set>
    <table id="editor" class="editor">
        <tr>
            <td>
                <label for="type">
                    <fmt:message key="deal.type"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <select id="type" v-model="deal.type">
                    <option v-for="type in types" :value="type.id">{{type.value}}</option>
                </select>
            </td>
        </tr>
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
                <div v-if="deal.counterparty == -1">
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
                <label for="realisation">
                    <fmt:message key="deal.realisation"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <select id="realisation" v-model="deal.realisation">
                    <option v-for="realisation in realisations" :value="realisation.id">{{realisation.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="product">
                    <fmt:message key="deal.product"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <select id="product" v-model="deal.product">
                    <option v-for="product in products" :value="product.id">{{product.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="quantity">
                    <fmt:message key="deal.quantity"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input type="number" min="0" id="quantity" v-model="deal.quantity" :class="{error : errors.quantity}" autocomplete="off">
                <select v-model="deal.unit">
                    <option v-for="unit in units" :value="unit.id">{{unit.value}}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <label for="price">
                    <fmt:message key="deal.price"/>
                </label>
            </td>
            <td>
                :
            </td>
            <td>
                <input type="number" min="0" id="price" v-model="deal.price" :class="{error : errors.price}" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="deal.amount"/>
            </td>
            <td>
                :
            </td>
            <td>
                {{(deal.price * deal.quantity).toLocaleString()}}
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