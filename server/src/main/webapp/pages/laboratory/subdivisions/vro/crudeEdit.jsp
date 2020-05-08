<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/editor.css">
<script src="${context}/vue/laboratory/extractionCrude.vue"></script>
<script>
    editor.api.save = '${save}';
    editor.times=[
        {
            hour:'08',
            minute:30
        },
        {
            hour:'11',
            minute:30
        },
        {
            hour:'14',
            minute:30
        },
        {
            hour:'17',
            minute:30
        },
        {
            hour:'20',
            minute:30
        },
        {
            hour:'23',
            minute:30
        },
        {
            hour:'02',
            minute:30
        },
        {
            hour:'05',
            minute:30
        }
    ];
    <c:forEach items="${laborants}" var="l">
    editor.laborants.push({
        id:${l.id},
        value:'${l.person.value}'
    });
    </c:forEach>
    editor.forpress = [];
    <c:forEach items="${forpress}" var="f">
    editor.forpress.push({
        id:${f.id},
        value:'${f.name}'
    });
    </c:forEach>
    editor.actualForpress = function(){
        var forpress = [];
        forpress.push(editor.forpress);
        for (let i in editor.crude.forpressCake){
            if (editor.crude.forpressCake.hasOwnProperty(i)){
                const id = editor.crude.forpressCake[i].forpress;
                for (let j in forpress){
                    if (forpress.hasOwnProperty(j) && forpress[j].id === id) {
                        forpress.splice(j, 1);
                    }
                }
            }
        }
        return forpress
    };
    editor.addForpressCake = function(){
        editor.crude.forpressCake.push({
            forpress:editor.forpress[0].id,
            humidity:0,
            oiliness:0
        })
    };
    editor.removeForpressCake = function(key){
        editor.crude.forpressCake.splice(key, 1);
    };
    <c:choose>
    <c:when test="${not empty crude}">
    editor.crude={
        id:${crude.id},
        date : new Date('${crude.turn.turn.date}').toISOString().substring(0, 10),
        time : editor.currentTime(new Date('${crude.time}')),
        before:{
            humidity:${crude.humidityBefore},
            soreness:${crude.sorenessBefore}
        },
        after:{
            humidity:${crude.humidityAfter},
            soreness:${crude.sorenessAfter}
        },
        huskiness:${crude.huskiness},
        kernelOffset:${crude.kernelOffset},
        pulpHumidity1:${crude.pulpHumidity1},
        pulpHumidity2:${crude.pulpHumidity2},
        forpressCake:[
        <c:forEach items="${crude.forpressCakes}" var="fp">
            {
                id:${fp.id},
                forpress:${fp.forpress.id},
                humidity:${fp.humidity},
                oiliness:${fp.oiliness}
            },
        </c:forEach>
        ]
    };
    </c:when>
    <c:otherwise>
    editor.crude =
    {
        date : new Date().toISOString().substring(0, 10),
        time : editor.currentTime(),
        before:{
            humidity:0,
            soreness:0
        },
        after:{
            humidity:0,
            soreness:0
        },
        huskiness:0,
        kernelOffset:0,
        pulpHumidity1:0,
        pulpHumidity2:0,
        forpressCake:[]
    };
    </c:otherwise>
    </c:choose>
    editor.afterMidnight = function(){
        let target = new Date(editor.crude.date + ' ' + editor.crude.time);
        return target.getHours() >= 0 && target.getHours() < 8;
    };
    editor.prevDate = function(){
        let selected = new Date(editor.crude.date);
        selected.setDate(selected.getDate() - 1);
        return selected;
    };
</script>
<table id="editor" class="editor">
    <tr>
        <td>
            <table>
                <tr>
                    <td width="238px" align="right">
                        <label for="date">
                            <span style="font-size: 10pt; font-weight: bold; color: red" v-if="!crude.id && afterMidnight()">
                                <fmt:message key="date.will.be"/>
                                {{prevDate().toLocaleDateString()}}
                            </span>
                            <span v-else>
                                <fmt:message key="date"/>
                            </span>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="date" readonly style="width: 7em" v-on:click="datePicker"
                               v-model="new Date(crude.date).toLocaleDateString()">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="time">
                            <fmt:message key="time"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <select id="time" v-model="crude.time">
                            <option v-for="time in times">
                                {{time.hour}}:{{time.minute}}
                            </option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <b>
                            <fmt:message key="vro.sun.before"/>
                        </b>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="humidity1">
                            <fmt:message key="sun.humidity"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="humidity1" type="number" step="0.01" v-if="crude.before"
                               onfocus="this.select()" v-model="crude.before.humidity">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="soreness1">
                            <fmt:message key="sun.soreness"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="soreness1" type="number" step="0.01" v-if="crude.before" autocomplete="off"
                               onfocus="this.select()" v-model="crude.before.soreness">
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <b>
                            <fmt:message key="vro.sun.after"/>
                        </b>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="humidity2">
                            <fmt:message key="sun.humidity"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="humidity2" type="number" step="0.01" v-if="crude.after"
                               onfocus="this.select()" v-model="crude.after.humidity" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="soreness2">
                            <fmt:message key="sun.soreness"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="soreness2" type="number" step="0.01" v-if="crude.after"
                               onfocus="this.select()" v-model="crude.after.soreness"  autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="huskiness">
                            <fmt:message key="vro.huskiness"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="huskiness" type="number" step="0.01"
                               autocomplete="off" onfocus="this.select()" v-model="crude.huskiness">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="kernelOffset">
                            <fmt:message key="vro.kernel.offset"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="kernelOffset" type="number" step="0.01" autocomplete="off"
                               onfocus="this.select()" v-model="crude.kernelOffset">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="pulpHumidity1">
                            <fmt:message key="vro.pulp.humidity.1"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="pulpHumidity1" type="number" step="0.01" autocomplete="off"
                               onfocus="this.select()" v-model="crude.pulpHumidity1">
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="pulpHumidity2">
                            <fmt:message key="vro.pulp.humidity.2"/>
                        </label>
                    </td>
                    <td>
                        :
                    </td>
                    <td>
                        <input id="pulpHumidity2" type="number" step="0.01" autocomplete="off"
                               onfocus="this.select()" v-model="crude.pulpHumidity2">
                    </td>
                </tr>
            </table>
        </td>
        <td  valign="top">
            <table>
                <tr>
                    <td colspan="3" width="200px">
                        <fmt:message key="forpress.cake"/>
                        <button style="min-width: 0; padding: 0 5px; font-size: 12pt;"
                                v-on:click="addForpressCake()" v-if="crude.forpressCake"
                                v-show="crude.forpressCake.length < forpress.length">
                            +
                        </button>
                    </td>
                </tr>
                <template v-for="(value, key) in crude.forpressCake">
                    <tr >
                        <td>
                            <label for="fp">
                                <span class="mini-close" v-on:click="removeForpressCake(key)">&times;</span>
                                <b>
                                    <fmt:message key="forpress"/>
                                </b>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <select id="fp" v-model="value.forpress">
                                <option v-for="f in forpress" :value="f.id">{{f.value}}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label for="forpressHumidity">
                                <fmt:message key="sun.humidity"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input type="number" step="0.01" onfocus="this.select()"
                                   id="forpressHumidity" v-model="value.humidity" autocomplete="off">
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label for="forpressOiliness">
                                <fmt:message key="sun.oiliness"/>
                            </label>
                        </td>
                        <td>
                            :
                        </td>
                        <td>
                            <input id="forpressOiliness" step="0.01" onfocus="this.select()"
                                   type="number" v-model="value.oiliness" autocomplete="off">
                        </td>
                    </tr>
                </template>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-if="already">
                ...
            </button>
            <button v-on:click="save" v-else>
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>

</html>