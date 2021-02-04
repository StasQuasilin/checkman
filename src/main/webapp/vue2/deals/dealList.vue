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
            loadModal(this.api.show, {id: id});
        },
        sort:function (a, b) {
            return new Date(b.date) - new Date(a.date);
        }
    }
});