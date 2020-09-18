<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 16.09.20
  Time: 10:21
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script type="application/javascript" src="${context}/vue/editor.vue"></script>
    <script type="application/javascript" src="${context}/vue/templates/search.vue"></script>
    <script type="application/javascript" src="${context}/vue/transportEdit.vue"></script>
    <script type="application/javascript">
<%--        transportEdit.api.save = '${save}';--%>
        if (!transportEdit.products){
            transportEdit.products= [];
        }
        <c:forEach items="${products}" var="product">
        transportEdit.products.push(${product.toShortJson()});
        </c:forEach>
        <c:forEach items="${shippers}" var="shipper">
        transportEdit.shippers.push(${shipper.toJson()});
        </c:forEach>
        transportEdit.object = ${transportation.toJson()}
    </script>
    <div id="transportEdit">
        <table>
            <tr>
                <td>
                    <fmt:message key="transportation.date"/>
                </td>
                <td>
                    {{new Date(object.date).toLocaleDateString()}}
                </td>
            </tr>
            <template v-for="doc in object.documents">
                <tr>
                    <td colspan="2" style="border-top: solid gray 1px"></td>
                </tr>
                <tr>
                    <td>
                        <fmt:message key="deal.counterparty"/>
                    </td>
                    <td>
                        {{doc.counterparty}}
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <select v-model="doc.dealDocument" style="width: 100%">
                            <option v-for="deal in deals[doc.counterparty.id]" :value="deal.id">

                            </option>
                        </select>
                    </td>
                </tr>
                <template v-for="(dp, dpIndex) in doc.products">
                    <tr>
                        <td colspan="2">
                            <span class="text-button" v-on:click="removeProduct(doc, dpIndex)">
                                &times;
                            </span>
                            <select v-if="fixedProducts" v-model="dp.product.id" v-on:change="putProduct(dp, productMap[dp.product.id])">
                                <option v-for="product in products" :value="product.id">
                                    {{product.name}}
                                </option>
                                <option v-if="!productMap[dp.product.id]" :value="dp.product.id">
                                    {{dp.product.name}}
                                </option>
                            </select>
                            <span v-else>
                                {{dp.product.name}}
                            </span>
                            <input :id="'amount-' + dp.id" type="number" v-model="dp.amount" autocomplete="off" onfocus="this.select()">
                            {{dp.unit}}
                            <label :for="'price_' + dp.id">
                                <fmt:message key="deal.price"/>
                            </label>
                            <input :id="'price_' + dp.id" type="number" step="0.01" v-model="dp.price" autocomplete="off" onfocus="this.select()">
                            <select v-model="dp.shipper.id">
                                <option v-for="shipper in shippers" :value="shipper.id">
                                    {{shipper.name}}
                                </option>
                            </select>
                        </td>
                    </tr>
                </template>
                <tr>
                    <td colspan="2">
                        <span class="text-button" v-on:click="addTransportationProduct(doc)">
                            <fmt:message key="product.add"/>
                        </span>
                    </td>
                </tr>
            </template>
            <tr>
                <td colspan="2">
                    <span class="text-button" v-on:click="addDocument()">
                        <fmt:message key="document.add"/>
                    </span>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="transportation.driver"/>
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="transportation.vehicle"/>
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="transportation.trailer"/>
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="transportation.transporter"/>
                </td>
                <td>

                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right">
                    <button onclick="closeModal()">
                        <fmt:message key="button.cancel"/>
                    </button>
                    <button>
                        <fmt:message key="button.save"/>
                    </button>
                </td>
            </tr>
        </table>
        <div style="font-size: 8pt; color: gray">
            <span v-on:click="fixedProducts = !fixedProducts" class="text-button">
                <template v-if="fixedProducts">
                    Fixed
                </template>
                <template v-else>
                    Unfixed
                </template>
            </span>
        </div>
    </div>
</html>
