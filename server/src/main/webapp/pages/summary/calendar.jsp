<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    var calendar = new Vue({
        el:'#calendar',
        data:{
            items:{}
        },
        methods:{
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
            getData:function(){
                return this.calendar;
            },
            getCalendar:function(){
                var calendar = {};
                for (var i in this.items){
                    if (this.items.hasOwnProperty(i)){
                        var a = this.items[i];
                        var date = calendar[a.date];
                        if (!date){
                            date = Vue.set(calendar, a.date, {
                                values:{}
                            })
                        }
                        var product = date.values[a.product.name];
                        if (!product){
                            product = Vue.set(date.values, a.product.name, {
                                open:false,
                                count:0,
                                values:{}
                            })
                        }
                        product.count += a.plan;
                        var manager = product.values[a.manager.person.value];
                        if (!manager){
                            manager = Vue.set(product.values, a.manager.person.value, {
                                open:false,
                                count:0,
                                values:{}
                            })
                        }
                        manager.count += a.plan;

                        var counterparty = manager.values[a.organisation.value];
                        if (!counterparty){
                            counterparty = Vue.set(manager.values, a.organisation.value, {
                                open:false,
                                count:0,
                                values:{}
                            })
                        }
                        counterparty.count += a.plan;

                        if (!counterparty.values[a.id]){
                            var driver = a.driver.person ? a.driver.person.value : '--'
                            Vue.set(counterparty.values, a.id, {
                                driver:driver,
                                count: a.plan
                            })
                        }

                    }
                }
                return calendar;

            },
            update:function(a) {
                Vue.set(this.items, a.id, a);
            },
            openItem:function(a, idx){
                console.log(idx);
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
        border: solid orange 1pt;
        font-size: 8pt;
        width: 100%;
    }
</style>
<html>
    <div id="calendar" class="calendar">
        <table width="100%">
            <template v-for="(value, key) in getCalendar()">
                <tr>
                    <td>
                        {{new Date(key).toLocaleDateString().substring(0, 5)}}
                    </td>
                </tr>
                <template v-for="(product, productName) in value.values">
                    <tr v-on:click="product.open=!product.open">
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
                        <tr v-on:click="manager.open=!manager.open">
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
                            <tr v-on:click="counterparty.open=!counterparty.open">
                                <td style="padding-left: 20pt">
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