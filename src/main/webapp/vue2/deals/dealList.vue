dealList = new Vue({
    el:'#dealList',
    mixins:[bl, contextMenuView],
    mounted:function(){
        this.filter = dealFilter;
    },
    data:{
        attributes:[]
    },
    methods:{
        show:function (id) {
            let args = {
                id:id
            };
            args.back = getLastUrl();
            let a = getLastAttributes();
            if (a){
                args.attributes = a;
            }
            loadContent(this.api.show, args);
        },
        sort:function (a, b) {
            return new Date(b.date) - new Date(a.date);
        }
    }
});