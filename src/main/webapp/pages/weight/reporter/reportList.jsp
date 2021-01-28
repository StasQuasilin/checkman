<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 13.04.20
  Time: 10:24
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/templates/laboratoryViewPlug.vue"></script>
    <script src="${context}/vue/templates/pricePlug.vue"></script>
    <script src="${context}/vue/templates/commentatorPlug.vue"></script>
    <script src="${context}/vue/dataList.vue"></script>
    <script>
        list.sort = function () {
            list.items.sort(function (a, b) {
                let aD = new Date(a.item.timestamp);
                let bD = new Date(b.item.timestamp);
                return  bD - aD;
            })
        }
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(a){
            list.handler(a);
        });
        </c:forEach>
        stopContent = function(){
            <c:forEach items="${subscribe}" var="s">
            unSubscribe('${s}');
            </c:forEach>
        };
    </script>
    <jsp:include page="reportListHeader.jsp"/>
    <div id="container">
        <div v-for="item in items">
            <div>
                {{new Date(item.item.timestamp).toLocaleDateString()}}
                {{new Date(item.item.timestamp).toLocaleTimeString().substring(0, 5)}}
            </div>
            <div style="padding-left: 8pt" v-for="r in item.item.reports">
                <span>
                    {{r.subdivision.name}}:
                </span>
                <span v-if="r.serviceability" style="color: green">
                    <fmt:message key="serviceability"/>
                </span>
                <span v-else style="color: orangered">
                    <fmt:message key="not.serviceability"/>
                </span>
                <template v-if="r.subdivision.teh">
                    <span v-if="r.adherence" style="color: green">
                        <fmt:message key="adherence"/>
                    </span>
                    <span v-else style="color: orangered">
                        <fmt:message key="no.adherence"/>
                    </span>
                </template>
                <div style="padding-left: 8pt; "
                     v-if="!r.serviceability || !r.adherence">
                    <span style="border-bottom: solid gray 1pt;">
                        {{r.note}}
                    </span>

                </div>
            </div>
        </div>
    </div>
</html>
