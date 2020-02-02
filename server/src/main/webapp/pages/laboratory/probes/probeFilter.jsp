<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 30.01.2020
  Time: 9:36
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    var filter_control = new Vue({
        el:'#filter_view',
        data:{
            api:{

            },
            from:-1,
            to:-1,
            period:false,
            organisation:'',
            types:[],
            result:[],
            items:[],
            notFound:false
        },
        methods:{
            find:function(){
                const self = this;
                var data = {
                    organisation:this.organisation
                };
                if(this.from != -1){
                    data.from = this.from;
                    if (this.to != -1){
                        data.to = this.to;
                    }
                }
                PostApi(this.api.find, data, function(a){
                    self.result = [];
                    self.notFound = false;
                    if (a.length > 0) {
                        a.forEach(function (item) {
                            self.result.push({item: item});
                        });
                        self.result.sort(function(a, b){
                            return new Date(a.date) - new Date(b.date)
                        })
                    } else{
                        self.notFound = true;
                    }

                })
            },
            pickDateFrom:function(){
                const self = this;
                var from = this.from == -1 ? new Date().toISOString().substring(0, 10) : this.from;
                datepicker.show(function(date){
                    self.from = date;
                    if (!self.period){
                        self.to = date;
                    }
                }, from)
            },
            filteredItems:function(){
                if (this.result.length > 0){
                    return this.result;
                }else {
                    return this.items;
                }
            },
            clear:function(){
                this.from = -1;
                this.to = -1;
                this.organisation = "";
                this.result = [];
                this.notFound = false;
            }
        }
    });
    filter_control.api.find = '${find}';
</script>
<html>
<table width="100%" id="filter_view">
    <tr>
        <td style="font-size: 10pt">
            <fmt:message key="date"/>
        </td>
        <td>
            <span v-on:click="pickDateFrom()">
                <span v-if="from == -1">
                    <fmt:message key="select"/>
                </span>
                <span v-else>
                    {{new Date(from).toLocaleDateString()}}
                </span>
            </span>
            <span v-if="from != -1" v-on:click="from=-1" class="mini-close">
                &times;
            </span>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="font-size: 10pt">
            <label for="organisation">
                <fmt:message key="deal.organisation"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="organisation" v-model="organisation" autocomplete="off" style="width: 100%">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <a class="mini-close" v-on:click="find()"><fmt:message key="find"/></a>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <a class="mini-close" v-on:click="clear()"><fmt:message key="button.clear"/></a>
        </td>
    </tr>
    <tr v-if="notFound">
        <td colspan="2" align="center" style="color: orangered">
            <fmt:message key="not.found"/>
        </td>
    </tr>
</table>

</html>
