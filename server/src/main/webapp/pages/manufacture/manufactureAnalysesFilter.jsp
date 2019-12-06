<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script>
        var filter_view = new Vue({
            el:'#filter_view',
            data:{
                month:new Date().toISOString().substring(0, 7),
                already:false

            },
            methods:{
                selectDate:function(){
                    const self = this;
                    datepicker.show(function(date){
                        self.already = false;
                        self.month = date;
                    }, this.month, 'month')
                },
                build:function(){
                    this.already = true;
                    const self = this;
                    analyse.update(this.month, function(){
                        self.already = false;
                    });
                }
            }
        })
    </script>
    <table id="filter_view" width="100%">
        <tr>
            <td>
                <fmt:message key="month"/>
            </td>
            <td style="width: 100%" align="center">
                <a v-on:click="selectDate">
                    {{new Date(month).toLocaleDateString().substring(3)}}
                </a>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button v-if="already">
                    --
                </button>
                <button v-else v-on:click="build()">
                    <fmt:message key="report.build"/>
                </button>
            </td>
        </tr>
    </table>
</html>