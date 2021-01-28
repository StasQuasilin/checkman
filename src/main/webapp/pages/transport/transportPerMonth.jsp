<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 29.01.2020
  Time: 14:58
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var printer = new Vue({
        el:'#printer',
        data:{
            api:{},
            from:new Date().toISOString().substring(0, 10),
            to:new Date().toISOString().substring(0, 10),
            result:[],
            wait:false
        },
        methods:{
            pickDateFrom:function(){
                const self = this;
                datepicker.show(function(d){
                    self.from = d;
                }, this.from)
            },
            pickDateTo:function(){
                const self = this;
                datepicker.show(function(d){
                    self.to = d;
                }, this.to)
            },
            print:function(){
                if (!this.wait) {
                    this.wait = true;
                    const self = this;
                    PostApi(this.api.print, {from: this.from, to: this.to}, function (a) {
                        self.wait = false;
                        self.result = a;
                    })
                }
            },
            total:function(){
                var total = 0;
                this.result.forEach(function(item){
                   total += item.amount;
                });
                return total;
            }
        }
    });
    printer.api.print = '${print}';
</script>
<table id="printer">
    <tr>
        <td>
            <fmt:message key="date"/>
        </td>
        <td>
            <span class="mini-close" v-on:click="pickDateFrom">
                {{new Date(from).toLocaleDateString()}}
            </span>
            <span class="mini-close" v-on:click="pickDateTo">
                {{new Date(to).toLocaleDateString()}}
            </span>
        </td>
    </tr>
    <tr v-for="product in result">
        <td colspan="2" align="right">
            {{product.name}}: {{product.amount}}
        </td>
    </tr>
    <tr v-if="result.length > 0">
        <th colspan="2" align="right">
           <fmt:message key="amount.total"/>: {{total()}}
        </th>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.close"/>
            </button>
            <button v-on:click="print()">
                <span v-if="wait">...</span>
                <span v-else>
                    <fmt:message key="document.print"/>
                </span>
            </button>
        </td>
    </tr>
</table>
</html>
