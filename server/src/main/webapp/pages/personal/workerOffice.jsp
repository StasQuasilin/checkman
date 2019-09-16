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
        <div>
            <label for="officeInput">
                <fmt:message key="user.office"/>
            </label>
            <input id="officeInput" v-model="office">
        </div>
        <div style="font-size: 10pt">
            &nbsp;
            <span class="mini-close"  v-show="o !== office" v-on:click="save">
                <fmt:message key="button.save"/>
            </span>
        </div>
    </div>

</html>