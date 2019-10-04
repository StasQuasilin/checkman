<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    var calendar = new Vue({
        el:'#calendar',
        data:{
            calendar:[]
        },
        methods:{
            handle:function(t){
                for (var a in t.add){
                    if (t.add.hasOwnProperty(a)){
                        this.add(t.add[a]);
                    }
                }
            },
            add:function(a){
                console.log(a);
                var has = false;
                for (var i in this.calendar){
                    if (this.calendar.hasOwnProperty(i)){
                        if (this.calendar[i].date === a.date){
                            has = true;
                            var product = this.calendar[i].values[a.product.name];
                            if (!product){
                                product = this.calendar[i].values[a.product.name] = {
                                    open:false,
                                    load:0,
                                    values:{}
                                };
                            }
                            product.load += a.plan;
                            var manager = product.values[a.manager.person.value];

                            if (!manager){
                                manager = product.values[a.manager.person.value] = {
                                    open:false,
                                    load:0,
                                    values:{}
                                }
                            }

                            manager.load += a.plan;

                            var counterparty = manager.values[a.organisation.value];
                            if (!counterparty){
                                counterparty = manager.values[a.organisation.value] = {
                                    open:false,
                                    load:0,
                                    values:{}
                                }
                            }
                            counterparty.load += a.plan;

                            var driver = counterparty.values[a.id];
                            var driverName = a.driver.person ? a.driver.person.value : null;
                            if (!driver){
                                driver = counterparty.values[a.id] = {
                                    load: a.plan,
                                    name: driverName
                                }
                            }

                        }
                    }
                }
                if (!has) {
                    this.calendar.push({
                        date: a.date,
                        values: {}
                    });
                    this.add(a);
                }
            },
            update:function(a) {
                var has = false;
                for (var i in this.calendar){
                    if (this.calendar.hasOwnProperty(i)){

                    }
                }
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
<html>
    <div id="calendar" style="display: inline-block; border: solid orange 1pt; font-size: 8pt;">
        <table>
            <template v-for="(value, key) in calendar">
                <%--<tr>--%>
                    <%--<td>--%>
                        <%--{{key}} {{value}}--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <td colspan="4">
                        {{new Date(value.date).toLocaleDateString().substring(0, 5)}}
                    </td>
                </tr>
                <template  v-for="(product, productName) in value.values">
                    <tr>
                        <td>
                            {{productName}}
                        </td>
                        <td>
                            0
                        </td>
                        <td>
                            0
                        </td>
                        <td>
                            {{product.load}}
                        </td>
                    </tr>
                    <template v-for="(manager, managerName) in product.values">
                        <tr>
                            <td colspan="3" style="padding-left: 8pt">
                                {{managerName}}
                            </td>
                            <td>
                                {{manager.load}}
                            </td>
                        </tr>
                        <template v-for="(counterparty, counterpartyName) in manager.values">
                            <tr>
                                <td colspan="3" style="padding-left: 16pt">
                                    {{counterpartyName}}
                                </td>
                                <td>
                                    {{counterparty.load}}
                                </td>
                            </tr>
                            <tr v-for="(driver) in counterparty.values">
                                <td colspan="3" style="padding-left: 24pt">
                                    <span v-if="driver.name">
                                        {{driver.name}}
                                    </span>
                                    <span v-else>
                                        --
                                    </span>
                                </td>
                                <td>
                                {{driver.load}}
                                </td>
                            </tr>
                        </template>
                    </template>
                </template>
            </template>
        </table>
    </div>
</html>