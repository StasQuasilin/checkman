<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <body>
        <link rel="stylesheet" href="${context}/css/reports.css">
        <div id="reports">
            <div ref="reportContainer" style="width: 100%; overflow-x: scroll" v-on:scroll="scrollListener()">
                <table border="1">
                    <tr style="height: 0">
                        <td>&nbsp;</td>
                        <th v-for="i in 14" style="padding: 0 16px">
                            {{dateOffset(nowDate(), i -1).toLocaleDateString()}}
                        </th>
                    </tr>
                    <tr v-for="user in users">
                       <td>
                           <div>
                               <user-cell style="position: absolute; left: 0" v-if="scroll > 10" :style="{'background-color' : backgroundColor()}" :user="user"></user-cell>
                               <user-cell :style="{visibility : scroll <= 10 ? 'visible' : 'hidden'}" :user="user"></user-cell>
                           </div>
                       </td>
                        <td v-for="column in getReportCells(user.id)" :colspan="getReportLength(user.id, column)">
                            <div style="background-color: red">
                                <cell :functions="functions" :data="getReportData(user.id, column)"></cell>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
    <script src="${context}/vue/templates/cell.vue"></script>
    <script src="${context}/vue/templates/userCell.vue"></script>
    <script src="${context}/vue/reports.vue"></script>
    <script>
        reports.api.show ='${show}';
        <c:forEach items="${users}" var="u">
        reports.users.push(${u.toJson()});
        </c:forEach>
        subscribe('${subscribe}', reports.handle);
        reports.functions = {
            open:function (id) {
                reports.open(id);
            }
        }
    </script>
</html>
