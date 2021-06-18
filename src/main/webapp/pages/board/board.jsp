<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 24.03.20
  Time: 10:49
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <jsp:include page="boardHeader.jsp"/>

    <script>
        var board = new Vue({
            el:'#board',
            data:{
                api:{},
                items:[],
                worker:-1,
            },
            methods:{
                handle:function(data){
                    for (let a in data.add){
                        if (data.add.hasOwnProperty(a)){
                            let add = data.add[a];
                            this.update(add);
                        }
                    }
                    for (let u in data.update){
                        if (data.update.hasOwnProperty(u)){
                            let update = data.update[u];
                            this.update(update);
                        }
                    }
                    this.items.sort(function (a, b) {
                        return new Date(b.created.time) - new Date(a.created.time);
                    })
                },
                update:function(item){
                    let founded = false;
                    for (let i in this.items){
                        if(this.items.hasOwnProperty(i)){
                            let itm = this.items[i];
                            if (itm.id===item.id){
                                founded = true;
                                this.items.splice(i, 1, item);
                            }
                        }
                    }
                    if (!founded){
                        this.items.push(item);
                    }
                },
                remove:function(id){
                    console.log('Remove ' + id);
                },
                edit:function(id){
                    loadModal(this.api.edit, {id:id});
                }
            }
        });
        board.api.edit = '${edit}';
        board.api.remove = '${remove}';
        <c:forEach items="${subscribe}" var="s">
        subscribe('${s}', function(a){
            board.handle(a);
        });
        </c:forEach>

        board.worker = ${worker.id}
    </script>
    <style>
        .border-item-created{
            font-size: 8pt;
            color: gray;
            width: 100%;
            text-align: right;
        }
        .board{
        }
        .board-item{
            border: solid gray 1pt;
            margin: 2pt;
        }
        .board-item *{
            padding: 0 2pt;
        }
        .board-item-title{
            font-weight: bold;
            font-size: 11pt;
        }
        .border-item-control{
            float: right;
        }
    </style>
    <div id="board" class="board">
        <div v-for="item in items" class="board-item">
            <div>
                <span class="board-item-title">
                    {{item.title}}
                </span>
                <div v-if="item.created.creator.id === worker" class="border-item-control">
                    <span v-on:click="edit(item.id)">
                        <img style="width: 7pt" src="${context}/images/pencil.svg" alt="">
                    </span>
                    <span v-on:click="remove(item.id)">
                        &times;
                    </span>

                </div>
            </div>
            <div>
                <p v-html="item.text"></p>
            </div>
            <div class="border-item-created">
                <span>
                    {{item.created.creator.person.value}}
                </span>
                <span>
                    {{new Date(item.created.time).toLocaleDateString()}}
                    {{new Date(item.created.time).toLocaleTimeString().substring(0, 5)}}
                </span>
            </div>
        </div>
    </div>
</html>
