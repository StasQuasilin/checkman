<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    var references = new Vue({
        el :'#references',
        data:{
            tab:''
        }
    });
    references.tab = '${tab}'
</script>
<html>
    <div style="position: relative; padding: 18pt 0; width: 100%">
        <div id="references" style="position: absolute; top: 0; left: 0; padding: 0 8pt; border-bottom: solid gray 1pt">
            <b v-if="tab === 'drivers'">
                <fmt:message key="drivers"/>
            </b>
            <a v-else onclick="loadContent('${drivers}')"><fmt:message key="drivers"/></a>
            <b v-if="tab === 'vehicles'">
                <fmt:message key="vehicles"/>
            </b>
            <a v-else onclick="loadContent('${vehicles}')"><fmt:message key="vehicles"/></a>
            <b v-if="tab === 'organisations'">
                <fmt:message key="organisations"/>
            </b>
            <a v-else onclick="loadContent('${organisations}')"><fmt:message key="organisations"/></a>
            <b v-if="tab === 'products'">
                <fmt:message key="products"/>
            </b>
            <a v-else onclick="loadContent('${products}')"><fmt:message key="products"/></a>
        </div>
        <jsp:include page="${referenceContent}"/>
    </div>
</html>