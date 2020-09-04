dealEdit = new Vue({
    el:'#dealEdit',
    mixins:[editor],
    components:{
        search:search
    },
    data:function () {
        return{
            types:[],
            typeNames:{}
        }
    }
});