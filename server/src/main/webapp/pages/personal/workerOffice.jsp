<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    var office = new Vue({
        el:'#office',
        data:{
            api:{},
            office:'',
            o:''
        },
        methods:{
            save:function(){
                const self = this;
                PostApi(this.api.save, {worker:Settings.worker, office:this.office},function(a){
                    console.log(a);
                    if (a.status === 'success') {
                        self.o = self.office;
                    }
                })
            }
        }
    });
    office.api.save = '${changeOffice}';
    office.office = office.o = '${worker.office}';
</script>
    <div id="office">
        <label for="officeInput">
            <fmt:message key="user.office"/>
        </label>
        <input id="officeInput" v-model="office" v-on:keyup.enter="save" v-on:keyup.escape="o = office" style="width: 65%" autocomplete="off">
        <span class="mini-close" v-show="o !== office" v-on:click="save" style="padding: 0">
            &#10003;
        </span>
        <span class="mini-close" v-show="o !== office" v-on:click="o = office" style="padding: 0">
            &times;
        </span>
    </div>
</html>