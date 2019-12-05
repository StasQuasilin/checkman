<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/DataContainer.css">
    <link rel="stylesheet" href="${context}/css/TransportList.css">
    <script>
        subscribe('${subscribe}', function(a){
            list.handler(a);
        })
    </script>
    <div id="container">
        <div v-for="(value, idx) in getItems()">
            <div style="display: inline-block; max-width: 98%; width: 94%">
                <div class="upper-row" style="font-size: 11pt">
                    <span>
                        {{new Date(value.item.date).toLocaleDateString()}}
                    </span>
                    <product-view :products="value.item.products"></product-view>
                </div>
            </div>
        </div>
    </div>
</html>