dealList = new Vue({
    el:'#deals',
    mixins:[list],
    methods:{
        show:function (id) {
            loadModal(this.api.show, {id:id});
        }
    }
});