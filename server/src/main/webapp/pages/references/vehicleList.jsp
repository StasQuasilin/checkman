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
    {{items.length}}
    <div v-for="v in items" v-on:click="edit(v)">
        {{v.model}} '{{v.number}}'
        <template v-if="v.trailer">
            '{{v.trailer.number}}'
        </template>
    </div>
</div>
</html>