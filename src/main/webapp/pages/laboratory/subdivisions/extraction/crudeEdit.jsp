<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/datepick.js"></script>
<link rel="stylesheet" href="${context}/css/date-picker.css">
<script>
    new Vue({
        el: '#editor',
        data: {
            times: [
                {
                    hour:8,
                    minute:30
                },
                {
                    hour:10,
                    minute:30
                },
                {
                    hour:12,
                    minute:30
                },
                {
                    hour:14,
                    minute:30
                },
                {
                    hour:16,
                    minute:30
                },
                {
                    hour:18,
                    minute:30
                },
                {
                    hour:20,
                    minute:30
                },
                {
                    hour:22,
                    minute:30
                },
                {
                    hour:00,
                    minute:30
                },
                {
                    hour:02,
                    minute:30
                },
                {
                    hour:04,
                    minute:30
                },
                {
                    hour:06,
                    minute:30
                },
            ],
            date:'',
            time:'',
            picker: false
        },
        methods:{
            now:function(){
                return '' + new Date().toLocaleDateString();
            },
            currentTime:function(){
                var now = new Date();
                var min = Number.MAX_VALUE;
                var current = '';
                for (var t in this.times){
                    var time = this.times[t];
                    var date = new Date();
                    date.setHours(time.hour);
                    date.setMinutes(time.minute);

                    var d = Math.abs((date.getHours() * 60 + date.getMinutes()) - (now.getHours() * 60 + now.getMinutes()));
                    if (d < min){
                        min = d;
                        current = time.hour + ':' + time.minute
                    }
                }
                return current;
            },
            save:function(){
                console.log(this.date + '; ' + this.time)
            }
        },
        mounted:function(){
            this.time = this.currentTime()
            this.date = this.now()
        }
    })
</script>

<table id="editor" class="editor">
    <tr>
        <td>
            <label for="date">
                <fmt:message key="date"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <v-menu class="date-picker"
                    v-model="picker"
                    :close-on-content-click="false"
                    :nudge-right="40"
                    lazy
                    transition="scale-transition"
                    offset-y
                    full-width
                    min-width="290px"
                    >
                <template v-slot:activator="{ on }">
                    <input style="width: 7em"
                           v-model="new Date().toLocaleDateString()"
                           readonly
                           v-on="on"
                            />
                </template>
                <v-date-picker v-model="date" @input="picker = false"></v-date-picker>
            </v-menu>
        </td>
    </tr>
    <tr>
        <td>
            <label for="time">
                <fmt:message key="time"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <select id="time" v-model="time">
                <option v-for="time in times">
                    {{time.hour}}:{{time.minute}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidityIncome">
                <fmt:message key="extraction.crude.humidity.income"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidityIncome" type="number" step="0.1" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="smallFraction">
                <fmt:message key="extraction.crude.small.fraction"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="smallFraction" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="miscellas">
                <fmt:message key="extraction.crude.miscellas"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="miscellas" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="humidity">
                <fmt:message key="extraction.crude.humidity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="dissolvent">
                <fmt:message key="extraction.crude.dissolvent"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="dissolvent" type="number" step="0.0001" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="grease">
                <fmt:message key="extraction.crude.grease"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="grease" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>