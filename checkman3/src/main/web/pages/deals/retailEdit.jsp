<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 08.09.20
  Time: 09:35
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mft" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script type="application/javascript" src="${context}/vue/editor.vue"></script>
    <script type="application/javascript" src="${context}/vue/templates/search.vue"></script>
    <script type="application/javascript" src="${context}/vue/retailEdit.vue"></script>
    <script type="application/javascript">
        retailEdit.api.productEdit = '${productEdit}';
        <c:forEach items="${products}" var="product">
        retailEdit.products.push(${product.toJson()});
        </c:forEach>
        <c:forEach items="${units}" var="unit">
        retailEdit.units.push(${unit.toJson()});
        </c:forEach>
        <c:forEach items="${shippers}" var="shipper">
        retailEdit.shippers.push(${shipper.toJson()});
        </c:forEach>

        retailEdit.counterpartyProps.holder = '<fmt:message key="deal.counterparty"/>';
        retailEdit.counterpartyProps.findApi = '${findOrganisation}';

        retailEdit.object = {
            id:-1,
            date:new Date().toISOString().substring(0, 10),
            shipper:retailEdit.shippers[0].id,
            documents:[]
        };
        retailEdit.newDocument();
    </script>
    <table id="retailEdit">
        <tr>
            <td>
                <fmt:message key="transportation.date"/>
            </td>
            <td>
                {{new Date(object.date).toLocaleDateString()}}
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.driver"/>
            </td>
            <td>
                ########## ####### ######
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.vehicle"/>/<fmt:message key="transportation.trailer"/>
            </td>
            <td>
                ### ##00-00## #00-00##
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transportation.transporter"/>
            </td>
            <td>
                ### #############
            </td>
        </tr>
        <tr>
            <td>
                <label for="shipper">
                    <fmt:message key="deal.shipper"/>
                </label>
            </td>
            <td>
                <select id="shipper" v-model="object.shipper">
                    <option v-for="shipper in shippers" :value="shipper.id">
                        {{shipper.name}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="border: solid gray 1px; max-height: 300px; overflow-y: scroll">
                <transition-group name="flip-list" tag="div" >
                    <div v-for="(doc, docIndex) in object.documents" :key="doc.key" style="display: flex; flex-direction: row; border-bottom: dashed gray 1px">
                        <div style="border-right: solid gray 1px; padding: 2pt">
                            <div>
                                {{docIndex + 1}}.
                            </div>
                            <div v-if="docIndex > 0" v-on:click="moveItem(docIndex, -1)">
                                &#9650;
                            </div>
                            <div v-if="docIndex < object.documents.length - 1" v-on:click="moveItem(docIndex, 1)">
                                &#9660;
                            </div>
                        </div>
                        <div>
                            <div>
                                <fmt:message key="deal.counterparty"/>:
                                <search :props="counterpartyProps" :object="doc.counterparty" :item="doc"></search>
                                <fmt:message key="transportation.address"/>
                            </div>
                            <div v-for="(dp, dpIndex) in doc.products">
                                <span>{{docIndex + 1}}.{{dpIndex + 1}}</span>
                                <span v-if="dp.product.id === -1">
                                    <fmt:message key="retail.insert.product"/>
                                </span>
<%--                                <search :props="productProps" :object="dp.product"></search>--%>
                                <template v-if="dp.product.id !== -1">
                                    <input type="number" v-model="dp.amount" autocomplete="off" onfocus="this.select()">
                                    <select v-model="dp.unit">
                                        <option v-if="dp.unit === -1" disabled value="-1">
                                            <mft:message key="some.not.selected"/>
                                        </option>
                                        <option v-for="unit in units" :value="unit.id">
                                            {{unit.name}}
                                        </option>
                                    </select>
                                    <input type="number" v-model="dp.price">
                                    <select v-model="dp.shipper">
                                        <option v-for="shipper in shippers" :value="shipper.id">
                                            {{shipper.name}}
                                        </option>
                                    </select>
                                </template>
                            </div>
                            <div>
                            <span class="text-button" v-on:click="newProduct(doc)" style="font-size: 10pt; color: gray">
                                + <fmt:message key="retail.new.product"/>
                            </span>
                            </div>
                        </div>
                    </div>
                </transition-group>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <span class="text-button" v-on:click="newDocument()" style="font-size: 10pt; color: gray">
                    + <fmt:message key="retail.new.document"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button v-on:click="save()">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
