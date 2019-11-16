<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/referenceList.vue"></script>
<script>
    referenceList.sort = function(){
        this.items.sort(function(a, b){
            var aP = a.person;
            var bP = b.person;
            return aP.surname !== bP.surname ? aP.surname.localeCompare(bP.surname) : aP.forename.localeCompare(bP.forename);
        })
    };
    referenceList.api.update='${update}';
    referenceList.api.edit='${edit}';
    referenceList.api.collapse = '${collapse}';
    referenceList.update();
</script>
<style>
    .block{
        display: inline-flex;
        margin: 2pt 4pt;
        border: solid darkslategray 1pt;
        padding: 0 2pt;
        background-color: lightgray;
    }
    .selected{
        background-color: lightsalmon;
    }
</style>
<div id="referencesList" style="width: 100%; position: relative">
    <div style="position: fixed; background-color: white; padding: 2pt 4pt">
        <template v-if="selected.length > 1">
            <a v-if="api.collapse" v-on:click="collapse">
                <fmt:message key="collapse"/> ( {{selected.length}} )
            </a>

        </template>
    </div>
    <table width="100%">
        <template v-for="key in getKeys()">
            <tr>
                <td>
                    <span style="font-size: 18pt; font-weight: bold">
                        {{key}}
                    </span>
                </td>
            </tr>
            <tr>
                <td>
                    <div v-for="driver in items[key]" class="block" :class="{selected : driver.selected}">
                        <div v-on:click="onClick(driver)">
                            <div>
                                <b>
                                    {{driver.person.surname}}
                                    {{driver.person.forename}}
                                    {{driver.person.patronymic}}
                                </b>
                            </div>
                            <div style="font-size: 10pt" v-if="driver.license">
                                <fmt:message key="driver.license"/>:
                                <b>
                                    {{driver.license}}
                                </b>
                            </div>
                            <div v-if="driver.vehicle">
                                {{driver.vehicle.model}}
                                {{driver.vehicle.number}}
                                {{driver.vehicle.trailer}}
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </template>
    </table>
</div>
</html>