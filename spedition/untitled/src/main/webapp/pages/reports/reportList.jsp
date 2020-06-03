<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 02.06.20
  Time: 14:17
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <body>
        <div id="reports">
            <div v-for="(value, key) in reports">
                {{value.owner.person.surname}} {{value.owner.person.forename}}
                <div style="padding-left: 8pt" v-for="report in value.reports">
                    <div>
                        <span>
                            {{new Date(report.leave).toLocaleDateString()}}
                        </span>
                        <span if="report.product">
                            {{report.product.name}}
                        </span>
                    </div>
                    <div>
                        {{report.route}}
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="${context}/vue/reportList.vue"></script>
    <script>
        subscribe('${subscribe}', reports.handler);
    </script>
</html>
