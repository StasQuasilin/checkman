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
                                plan:0,
                                count:0,
                                values:{}
                            })
                        }
                        let plan = a.plan;
                        let amount = 0;
                        let done = false;
                        if (a.weight && a.weight.netto > 0){
                            amount = a.weight.netto;
                            done = true;
                        }

                        product.count += amount;
                        product.plan += plan;

                        var mn = a.manager.person ? a.manager.person.value : 'Unknown ' + a.id;
                        var manager = product.values[mn];
                        var managerKey = productKey + '/' + mn;
                        if (!manager){
                            manager = Vue.set(product.values, mn, {
                                key:managerKey,
                                open:this.getOpen(managerKey),
                                count:0,
                                plan:0,
                                values:{}
                            })
                        }

                        manager.count += amount;
                        manager.plan += plan;

                        var counterparty = manager.values[a.organisation.value];
                        var counterpartyKey = managerKey + '/' + a.organisation.value;
                        if (!counterparty){
                            counterparty = manager.values[a.organisation.value] = {
                                key:counterpartyKey,
                                open:this.getOpen(counterpartyKey),
                                count:0,
                                plan:0,
                                values:{}
                            };
                        }
                        counterparty.count += amount;
                        counterparty.plan += plan;

                        var driver = counterparty.values[a.id];
                        if (!driver){
                            var dn = a.driver.person ? a.driver.person.value : '--';
                            driver = Vue.set(counterparty.values, a.id, {
                                driver:dn,
                                plan:plan,
                                count:amount,
                                done:done
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
            }
        }
    });
</script>
<style>
    .calendar{
        display: inline-block;
        font-size: 8pt;
        width: 100%;
        border-bottom: dashed gray 1pt;
    }
    .calendar table{
        border-collapse: collapse;
    }
</style>
<html>
    <div id="calendar" class="calendar" style="width: 200pt; border-bottom: dotted gray 1pt">
        <table width="100%" style="font-size: 10pt">
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
                        <td style="padding-left: 4pt" width="100%">
                            <span v-if="product.open">
                                -
                            </span>
                            <span v-else>
                                +
                            </span>
                            <span :class="{bold : product.open}" style="font-size: 10pt">
                                {{productName}}
                            </span>
                        </td>
                        <td class="data-cell">
                            {{product.count.toLocaleString()}}/{{product.plan.toLocaleString()}}
                        </td>
                    </tr>
                    <template v-if="product.open" v-for="(manager, managerName) in product.values">
                        <tr v-on:click="open(manager)">
                            <td style="padding-left: 12pt">
                                <span v-if="manager.open">
                                    -
                                </span>
                                <span v-else>
                                    +
                                </span>
                                <span :class="{bold : manager.open}" style="font-size: 10pt">
                                    {{managerName}}
                                </span>
                            </td>
                            <td  class="data-cell">
                                {{manager.count.toLocaleString()}}/{{manager.plan.toLocaleString()}}
                            </td>
                        </tr>
                        <template v-if="manager.open" v-for="counterparty, counterpartyName) in manager.values">
                            <tr v-on:click="open(counterparty)">
                                <td style="padding-left: 20pt; overflow: hidden">
                                    <span v-if="counterparty.open">
                                        -
                                    </span>
                                    <span v-else>
                                        +
                                    </span>
                                    <span :class="{bold : counterparty.open}">
                                        {{counterpartyName}}
                                    </span>
                                </td>
                                <td class="data-cell">
                                    {{counterparty.count.toLocaleString()}}/{{counterparty.plan.toLocaleString()}}
                                </td>
                            </tr>
                            <tr v-if="counterparty.open" v-for="driver in counterparty.values">
                                <td style="padding-left: 28pt">
                                    {{driver.driver}}
                                </td>
                                <td class="data-cell" :class="{green : driver.done}">
                                    {{driver.count.toLocaleString()}}/{{driver.plan.toLocaleString()}}
                                </td>
                            </tr>
                        </template>
                    </template>
                </template>
            </template>
        </table>
    </div>
</html>