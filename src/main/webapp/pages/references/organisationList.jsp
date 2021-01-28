<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/referenceList.vue"></script>
<script>
    referenceList.api.update='${update}';
    referenceList.api.edit = '${edit}';
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
</style>
<div id="referencesList" style="width: 100%">
    <template v-for="key in getKeys()">
        <div>
            <h2>
                {{key}}
            </h2>
        </div>
        <div>
            <div v-for="organisation in items[key]" class="block" v-on:click="edit(organisation)">
                {{organisation.value}}
            </div>
        </div>
    </template>
</div>
</html>