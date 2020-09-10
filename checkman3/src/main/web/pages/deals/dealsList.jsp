
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 04.09.20
  Time: 14:04
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script type="application/javascript" src="${context}/vue/dealList.vue"></script>
<script>
    dealList.api.edit = '${edit}';
    dealList.api.show = '${show}';
    subscriber.subscribe('${subscriber}', dealList.handler);

</script>
<div id="header-buttons">
    <button onclick="dealList.edit()">
        <fmt:message key="deal.add"/>
    </button>
</div>
<div id="deals" class="item-container">
    <div v-for="deal in items" class="list-item" v-on:click="show(deal.id)">
        <div style="display: flex; flex-direction: row">
            <div style="border-right: solid gray 1px; font-size: 10pt">
                <div>
                    <fmt:message key="deal.date"/>
                </div>
                <div>
                    {{new Date(deal.date).toLocaleDateString()}}
                </div>
                <div>
                    <fmt:message key="deal.period"/>
                </div>
                <div v-if="deal.from">
                    <fmt:message key="deal.from"/>
                    {{new Date(deal.from).toLocaleDateString()}}
                </div>
                <div v-if="deal.to">
                    <fmt:message key="deal.to"/>
                    {{new Date(deal.to).toLocaleDateString()}}
                </div>
            </div>
            <div style="flex: 1 0 auto; display: flex; flex-direction: column">
                <div>
                    <template v-for="doc in deal.documents">
                        <fmt:message key="deal.counterparty"/>: <b>{{doc.counterparty.name.toUpperCase()}}</b>
                        <template v-for="(dp, pIndex) in doc.products">
                            <div style="border-bottom: solid gray 1px; width: 100%" v-if="pIndex == 0">
                                <fmt:message key="deal.product"/>:
                                <b>{{dp.product.name}} {{dp.amount.toLocaleString()}} {{dp.unit}}</b>
                                <fmt:message key="deal.price"/>:
                                <b>
                                    {{dp.price.toLocaleString()}}
                                </b>
                                <fmt:message key="deal.shipper"/>:
                                <b>
                                    {{dp.shipper}}
                                </b>
                            </div>
                            <div>
                                <fmt:message key="deal.done"/>:
                                {{dp.done.toLocaleString()}} / {{dp.amount.toLocaleString()}} {{dp.unit}}
                            </div>
                        </template>
                        <span v-if="doc.products.length > 1">
                                + {{(doc.products.length - 1).toLocaleString()}}
                            </span>
                    </template>
                </div>

            </div>

        </div>
    </div>
</div>
</html>
