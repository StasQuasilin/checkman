<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    var calendar = new Vue({
        el:'#calendar',
        data:{
            items:list.getItems(),
            calendar:{},
            refresh:0,
            opens:{}
        },
        methods:{
            getOpen:function(id){
                if (!this.opens[id]){
                    Vue.set(this.opens, id, false);
                }
                return this.opens[id];
            },
            getItems:function(){
                var calendar = {};
                var items = list.getItems();
                for (var i in items){
                    if (items.hasOwnProperty(i)){
                        var a = items[i].item;
                        var date = calendar[a.date];
                        if (!date){
                            date = Vue.set(calendar, a.date, {
                                values:{}
                            })
                        }
                        var product = date.values[a.product.name];
                        var productKey = a.date + '/' + a.product.name;
                        if (!product){

                            product = Vue.set(date.values, a.product.name, {
                                key:productKey,
                                open:this.getOpen(productKey),
                                count:0,
                                values:{}
                            })
                        }

                        product.count += a.plan;

                        var mn = a.manager.person ? a.manager.person.value : 'Unknown ' + a.id;
                        var manager = product.values[mn];
                        var managerKey = productKey + '/' + mn;
                        if (!manager){
                            manager = Vue.set(product.values, mn, {
                                key:managerKey,
                                open:this.getOpen(managerKey),
                                count:0,
                                values:{}
                            })
                        }

                        manager.count += a.plan;

                        var counterparty = manager.values[a.organisation.value];
                        var counterpartyKey = managerKey + '/' + a.organisation.value;
                        if (!counterparty){
                            counterparty = manager.values[a.organisation.value] = {
                                key:counterpartyKey,
                                open:this.getOpen(counterpartyKey),
                                count:0,
                                values:{}
                            };
//                            counterparty = Vue.set(manager.values, a.organisation.value, {
//                                open:false,
//                                count:0,
//                                values:{}
//                            })
                        }
                        counterparty.count += a.plan;

                        var driver = counterparty.values[a.id];
                        if (!driver){
                            var dn = a.driver.person ? a.driver.person.value : '--';
                            driver = Vue.set(counterparty.values, a.id, {
                                driver:dn,
                                count:a.plan
                            });
                        }
                    }
                }
                return calendar;
            },
            handle:function(t){
                for (var a in t.add){
                    if (t.add.hasOwnProperty(a)){
                        this.update(t.add[a]);
                    }
                }
                for (var u in t.update){
                    if (t.update.hasOwnProperty(u)){
                        this.update(t.update[u]);
                    }
                }
            },
            open:function(item){
                this.opens[item.key] = !this.opens[item.key];
            },
            getData:function(){
                return this.calendar;
            },
            remove:function(a){
                var date = this.calendar[a.date];
                if (date){
                    var product = date.values[a.product.name];
                    if (product){

                    }
                }
            },
            update:function(a){

            }
        }
    });
    <c:forEach items="${subscribe}" var="s">
    subscribe('${s}', function(a){
        calendar.handle(a);
    });
    </c:forEach>
    stopContent = function(){
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(a){
            unSubscribe('${s}');
        });
        </c:forEach>
    }
</script>
<style>
    .calendar{
        display: inline-block;
        font-size: 8pt;
        width: 100%;
    }
</style>
<html>
    <div id="calendar" class="calendar" style="width: 200pt">
        <table width="100%">
            <template v-for="(value, key) in getItems()">
                <tr>
                    <td colspan="2">
                        <span style="font-size: 10pt">
                            {{new Date(key).toLocaleDateString().substring(0, 5)}}
                        </span>
                    </td>
                </tr>
                <template v-for="(product, productName) in value.values">
                    <tr v-on:click="open(product)">
                        <td style="padding-left: 4pt">
                            <span v-if="product.open">
                                &#9207;
                            </span>
                            <span v-else>
                                &#9205;
                            </span>
                            <span :class="{bold : product.open}">
                                {{productName}}
                            </span>
                        </td>
                        <td>
                            {{product.count}}
                        </td>
                    </tr>
                    <template v-if="product.open" v-for="(manager, managerName) in product.values">
                        <tr v-on:click="open(manager)">
                            <td style="padding-left: 12pt">
                                <span v-if="manager.open">
                                    &#9207;
                                </span>
                                <span v-else>
                                    &#9205;
                                </span>
                                <span :class="{bold : manager.open}">
                                    {{managerName}}
                                </span>
                            </td>
                            <td>
                                {{manager.count}}
                            </td>
                        </tr>
                        <template v-if="manager.open" v-for="counterparty, counterpartyName) in manager.values">
                            <tr v-on:click="open(counterparty)">
                                <td style="padding-left: 20pt; overflow: hidden">
                                    <span v-if="counterparty.open">
                                        &#9207;
                                    </span>
                                    <span v-else>
                                        &#9205;
                                    </span>
                                    <span :class="{bold : counterparty.open}">
                                        {{counterpartyName}}
                                    </span>
                                </td>
                                <td>
                                    {{counterparty.count}}
                                </td>
                            </tr>
                            <tr v-if="counterparty.open" v-for="driver in counterparty.values">
                                <td style="padding-left: 28pt">
                                    {{driver.driver}}
                                </td>
                                <td>
                                    {{driver.count}}
                                </td>
                            </tr>
                        </template>
                    </template>
                </template>
            </template>
        </table>
    </div>
</html>