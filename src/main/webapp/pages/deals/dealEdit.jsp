<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
    <script src="${context}/vue/dealEdit.vue?v=${now}"></script>
    <script>
        <c:forEach items="${products}" var="product">
        dealEdit.products[${product.id}] = ({
            id:${product.id},
            value:'${product.name}',
            group:${product.group}
        });</c:forEach>

        <c:forEach items="${shippers}" var="shipper">
        dealEdit.shippers.push({
            id:${shipper.id},
            value:'${shipper.value}'
        });</c:forEach>
        <c:forEach items="${customers}" var="customer">
        dealEdit.customers.push('${customer}');
        dealEdit.customerNames['${customer}'] = '<fmt:message key="delivery.${customer}"/>';
        </c:forEach>
        <c:forEach items="${units}" var="u">
        dealEdit.units.push({
            id:${u.id},
            value:'${u.name}'
        });
        </c:forEach>

        dealEdit.api.save = '${save}';
        dealEdit.api.redirect = '${redirect}';
        <c:forEach items="${types}" var="type">
        dealEdit.typeNames['${type}'] = '<fmt:message key="${type}"/>';
        </c:forEach>
        <c:forEach items="${actions}" var="action">
        dealEdit.addType(${action.toJson()});
        </c:forEach>
        dealEdit.organisationProps = {
            find:'${findOrganisation}',
            add:'${parseOrganisation}',
            edit:'${editOrganisation}',
            addHeader:'<fmt:message key="button.add"/>',
            header:'<fmt:message key="counterparty.select"/>',
            put:function(organisation){
                dealEdit.setCounterparty(organisation);
            },
            show:['value']
        };
        <c:choose>
        <c:when test="${not empty deal}">
        dealEdit.deal=${deal.toJson()};
        // dealEdit.deal.shipper = dealEdit.deal.shipper.id;
        // dealEdit.deal.product = dealEdit.deal.product.id;
        <c:if test="${not empty deal.unit}">
        dealEdit.deal.unit = ${deal.unit.id};
        </c:if>
        dealEdit.deal.price = ${deal.price};
        </c:when>
        <c:otherwise>
        dealEdit.deal.type = '${type}';
        // if (dealEdit.shippers.length > 0){
        //     dealEdit.deal.shipper= dealEdit.shippers[0].id;
        // }
        // dealEdit.selectProduct();
        dealEdit.addProduct();

        if (dealEdit.units.length > 0){
            dealEdit.deal.unit = dealEdit.units[0].id;
        }
        </c:otherwise>
        </c:choose>

    </script>
    <link rel="stylesheet" href="${context}/css/editor.css">
    <c:set var="findCunterparty"><fmt:message key="counterparty.find"/></c:set>
    <c:set var="addCunterparty"><fmt:message key="counterparty.add"/></c:set>
    <c:set var="type"><fmt:message key="deal.type"/></c:set>
    <table id="editor" class="editor">
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
                <input id="number" v-model="deal.number" autocomplete="off">
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
                <input readonly style="width: 7em" v-model="new Date(deal.to).toLocaleDateString()" v-on:click="showDateToPicker">
            </td>
        </tr>
        <tr :class="{error : errors.organisation}">
            <td>
                <fmt:message key="deal.organisation"/>
            </td>
            <td>
                :
            </td>
            <td v-on:click="errors.organisation = false" >
                <object-input :props="organisationProps" :object="deal.counterparty"></object-input>
            </td>
        </tr>
        <template v-for="(dp, dpIdx) in deal.products">
            <tr v-if="deal.products.length > 1">
               <td colspan="3">
                   <span class="mini-close" v-on:click="deal.products.splice(dpIdx, 1)" >
                       &times;
                   </span> {{(dpIdx + 1).toLocaleString()}} {{dp.id}}
               </td>
            </tr>
            <tr>
                <td colspan="3" style="border-bottom: solid gray 1pt"></td>
            </tr>
            <tr>
                <td colspan="2">
                    <label :for="'dpProduct_' + dpIdx">
                        <fmt:message key="deal.product"/>
                    </label>
                </td>
                <td>
                    <select :id="'dpProduct_' + dpIdx" v-model="dp.productId">
                        <option v-for="product in productList()" :value="product.id">{{product.value}}</option>
                    </select>
                    <select id="type" v-model="deal.type">
                        <option v-for="type in typesByProduct(dp.productId)" :value="type">{{typeNames[type]}}</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label :for="'shipper_' + dpIdx">
                        <fmt:message key="deal.shipper"/>
                    </label>
                </td>
                <td>
                    <select :id="'shipper_' + dpIdx" v-model="dp.shipperId">
                        <option v-for="shipper in shippers" :value="shipper.id">{{shipper.value}}</option>
                    </select>
                </td>
            </tr>
            <template v-if="!products[dp.productId].group">
                <tr>
                    <td colspan="2">
                        <label :for="'quantity_' + dpIdx">
                            <fmt:message key="deal.quantity"/>
                        </label>
                    </td>
                    <td>
                        <input type="number" min="0" :id="'quantity_' + dpIdx" v-model="dp.quantity" autocomplete="off" onfocus="this.select()">
                        <select v-model="dp.unitId">
                            <option v-for="unit in units" :value="unit.id">{{unit.value}}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label :for="'price_' + dpIdx">
                            <fmt:message key="deal.price"/>
                        </label>
                    </td>
                    <td>
                        <input type="number" min="0" :id="'price_' + dpIdx" v-model="dp.price" autocomplete="off" onfocus="this.select()">
                    </td>
                </tr>
            </template>
        </template>
<%--        <tr>--%>
<%--            <td colspan="3" style="text-align: right">--%>
<%--                <span class="mini-close" v-on:click="addProduct()">--%>
<%--                    +<fmt:message key="button.add.product"/>--%>
<%--                </span>--%>
<%--            </td>--%>
<%--        </tr>--%>
        <tr v-if="totalSum > 0">
            <td>
                <fmt:message key="deal.amount"/>
            </td>
            <td>
                :
            </td>
            <td>
                {{totalSum.toLocaleString()}}
            </td>
        </tr>
        <tr v-for="(cost, cIdx) in deal.costs">
            <td colspan="3">
                <span class="mini-close" v-on:click="removeCost(cIdx)">
                    &times;
                </span>
                <label :for="'customer_' + cIdx">
                    <fmt:message key="load.customer.title"/>
                </label>
                <select :id="'customer_' + cIdx" v-model="cost.customer">
                    <option v-for="customer in customers" :value="customer">
                        {{customerNames[customer]}}
                    </option>
                </select>
                <label :for="'cost_' + cIdx">
                    <fmt:message key="delivery.cost"/>
                </label>
                <input :id="'cost_' + cIdx" v-model="cost.cost" type="number" autocomplete="off" onfocus="this.select()">
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <span class="mini-close" v-on:click="addCost()">
                    <fmt:message key="deal.add.delivery.cost"/>
                </span>
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