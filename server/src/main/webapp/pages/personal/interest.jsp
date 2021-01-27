<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 20.01.21
  Time: 09:05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<script src="${context}/vue/templates/vehicleInput.vue?v=${now}"></script>
<script src="${context}/vue/personal/interestPage.vue?v=${now}"></script>
<script src="${context}/vue/personal/interestEdit.vue?v=${now}"></script>
<script>
    interestEdit.api.save = '${interestSave}';
    interestEdit.globalProps = {
        height:50,
        i:'In',
        w:'Weight',
        a:'Analyses',
        o:'Out'
    };
    interestEdit.counterpartyProps = {
        title:'Counterparty',
        all:'All',
        addItemProps:{
            find:'${findOrganisation}',
            header:'+Counterparty',
            show:['value']
        },
        show:['value']
    };
    interestEdit.productProps = {
        title:'Products',
        all:'All',
        addItemProps:{
            header:'+Driver'
        }
    };
    let json = ${interests};
    interestEdit.all = json.all;
    interestEdit.counterpartyItems = json.counterparty;
</script>
<div id="interestEdit">
    <input type="checkbox" v-model="all">All
    <span class="mini-close" v-on:click="save">
        Save
    </span>
    <interest-item :global="globalProps" :props="counterpartyProps" :all="all" :items="counterpartyItems" ></interest-item>
<%--    <interest-item :global="globalProps" :props="productProps" :all="all" ></interest-item>--%>
</div>

</html>
