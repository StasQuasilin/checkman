<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 13.01.21
  Time: 15:59
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
    <script src="${context}/vue/transport/counterpartySelect.vue?v=${now}"></script>
    <script>
        select.api.deals = '${findDeals}';
        select.organisationProps = {
            find:'${findOrganisation}',
            edit:'${editOrganisation}',
            add:'${parseOrganisation}',
            addHeader:'<fmt:message key="button.create"/>',
            header:'<fmt:message key="counterparty.add"/>',
            put:select.putOrganisation,
            show:['value']
        };
        <c:forEach items="${types}" var="type">
        select.typeNames['${type}'] = '<fmt:message key="${type}"/>';
        </c:forEach>
    </script>
    <table id="select">
        <tr>
            <td colspan="2" style="word-wrap: break-spaces; padding: 16pt; max-width: 0; text-align: center">
                <fmt:message key="deal.attention.title"/><br/>
                <fmt:message key="counterparty.select.hello.text"/>
            </td>
        </tr>
        <tr>
            <td>
                <template v-if="organisation.id !== -1">
                    <fmt:message key="deal.organisation"/>
                </template>
                <template v-else>
                    <fmt:message key="counterparty.input"/>
                </template>
            </td>
            <td>
                <object-input :props="organisationProps" :object="organisation" :is_open="true"></object-input>
            </td>
        </tr>
        <template v-if="organisation.id !== -1">
            <tr>
                <td colspan="2">
                    <fmt:message key="deal.select.text"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div style=" max-height: 200pt; overflow-y: scroll">
                        <template v-for="d in deals" >
                            <div v-for="product in d.products">
                                <input :id="product.id" type="radio" name="dealId" v-model="deal" :value="product.id"/>
                                <label :for="product.id">
                                    {{d.id}} {{product.productName}},
                                    <template v-if="product.price> 0">
                                        {{product.price.toLocaleString()}}
                                    </template>
                                    <fmt:message key="deal.from"/>
                                    {{product.shipperName}}
                                </label>
                            </div>
                        </template>
                        <div>
                            <span v-if="deal === -1" disabled value="-1">
                                <fmt:message key="nothing.choose"/>
                            </span>
                            <input id="newDeal" type="radio" name="dealId" v-model="deal" value="-2"/>
                            <label for="newDeal">
                                <fmt:message key="new.deal"/>
                            </label>
                        </div>
                    </div>
                </td>
            </tr>
        </template>
        <tr>
            <td colspan="2" style="text-align: center; padding-top: 16pt">
                <button onclick="closeModal()">
                    <fmt:message key="button.cancel"/>
                </button>
                <button v-on:click="save()" :disabled="organisation.id === -1">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
