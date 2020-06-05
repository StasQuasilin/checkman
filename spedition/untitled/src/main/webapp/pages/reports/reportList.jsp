<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 02.06.20
  Time: 14:17
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <link rel="stylesheet" href="${context}/css/reports.css">
    <body>
        <div id="reports">
            <div v-for="(value, key) in reports">
                {{value.owner.person.surname}} {{value.owner.person.forename}}
                <div style="padding-left: 16pt">
                    <div v-for="report in value.reports" class="report" v-on:click="show(report)">
                        <div>
                            <span>
                                {{new Date(report.leave).toLocaleDateString()}}
                            </span>
                                <span if="report.product">
                                {{report.product.name}}
                            </span>
                        </div>
                        <div v-if="report.route">
                        <span v-for="(r, i) in report.route.split(',')">
                            {{r.toUpperCase()}}
                            <span v-if="i < report.route.split(',').length - 1">
                                &#10142;
                            </span>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="${context}/vue/reportList.vue"></script>
    <script>
        reports.api.show = '${show}';
        subscribe('${subscribe}', reports.handler);
    </script>
</html>
