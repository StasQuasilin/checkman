<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 25.02.2020
  Time: 8:44
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    var statistic = new Vue({
        el:'#statistic',
        data:{
            result:[],
            opens:[]
        },
        mounted:function(){
            filter_control.result = this.result;
        },
        methods:{
            open:function(item){
                if (this.opens[item.id] === undefined){
                    console.log('add');
                    Vue.set(this.opens, item.id, true);
                } else {
                    Vue.set(this.opens, item.id, !this.opens[item.id]);
                }

            },
            isOpen:function(item){
                return this.opens[item.id]
            },
            getOpen:function(id){
                console.log('get open for ' + id);
                if (!this.opens[id]){
                    Vue.set(this.opens, id, false);
                }
                return this.opens[id];
            },
            sortByName:function(items){
                return Object.values(items).sort(function(a, b){
                    return a.name.localeCompare(b.name);
                })
            },
            sortByDate:function(items){
                return Object.values(items).sort(function(a, b){
                    return new Date(a.date) - new Date(b.date);
                })
            },
            getItems:function(){
                let res = {};
                let items = filter_control.getItems();
                for (let i in items){
                    if (items.hasOwnProperty(i)){
                        let item = items[i];
                        for (let j in item){
                            if (item.hasOwnProperty(j)){
                                let a = item[j];
                                let product = res[a.product.id];
                                if (!product){
                                    product = a.product;
                                    product.plus = 0;
                                    product.minus = 0;
                                    product.values = {};
                                    res[product.id] = product;
                                }
                                let counterparty = product.values[a.counterparty.id];
                                if (!counterparty){
                                    counterparty = a.counterparty;
                                    counterparty.values = {};
                                    counterparty.plus = 0;
                                    counterparty.minus = 0;
                                    counterparty.open = false;
                                    product.values[counterparty.id] = counterparty;
                                }
                                let key = a.scale + ' ' + a.date;
                                let scale = counterparty.values[key];
                                if (!scale){
                                    scale = {
                                        date: a.date,
                                        scale:a.scale,
                                        plus: 0,
                                        minus: 0
                                    };
                                    counterparty.values[key] = scale;
                                }

                                let amount = Math.round(parseFloat(a.amount) * 1000) / 1000;
                                if (amount > 0){
                                    product.plus += amount;
                                    counterparty.plus += amount;
                                    scale.plus += amount;
                                } else {
                                    product.minus += amount;
                                    counterparty.minus += amount;
                                    scale.minus += amount;
                                }
                            }
                        }

                    }
                }
                return Object.values(res);
            }
        }
    })
</script>
<style>
    .row{
        border-bottom: dotted gray 1pt;
    }
    .organisation-name{
        font-size: 14pt;
        display: inline-block;
        min-width: 280pt;
    }
    .plus-amount{
        color: green;
    }
    .minus-amount{
        color: orangered;
    }
</style>
<html>
    <body>
        <div id="statistic" style="padding: 4pt">
            <div v-for="i in getItems()">
                <div style="font-weight: bold">
                    <span>
                        {{i.name}}
                    </span>
                    <span v-if="i.plus != 0" class="plus-amount">
                        +{{i.plus.toLocaleString()}} {{i.unit.name}}
                    </span>
                    <span v-if="i.minus != 0" class="minus-amount">
                        {{i.minus.toLocaleString()}} {{i.unit.name}}
                    </span>
                </div>
                <template v-for="(counterparty) in sortByName(i.values)">
                    <div style="padding-left: 8pt" v-on:click="open(counterparty)" class="row">
                        <span class="organisation-name">
                            {{counterparty.value}}
                        </span>
                        <span v-if="counterparty.plus != 0" class="plus-amount">
                            +{{counterparty.plus.toLocaleString()}} {{i.unit.name}}
                        </span>
                        <span v-if="counterparty.minus != 0" class="minus-amount">
                            {{counterparty.minus.toLocaleString()}} {{i.unit.name}}
                        </span>
                    </div>
                    <template v-if="isOpen(counterparty)">
                        <div style="padding-left: 16pt" v-for="scale in sortByDate(counterparty.values)">
                        <span>
                            {{scale.scale}} {{new Date(scale.date).toLocaleDateString()}}
                        </span>
                        <span v-if="scale.plus != 0" class="plus-amount">
                            +{{scale.plus.toLocaleString()}}
                        </span>
                        <span v-if="scale.minus != 0" class="minus-amount">
                            {{scale.minus.toLocaleString()}}
                        </span>
                        </div>
                    </template>
                </template>
            </div>
        </div>
    </body>
</html>
