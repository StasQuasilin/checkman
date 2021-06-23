changeView = new Vue({
    el:'#changeView',
    mixins:[editor],
    data:{
        items:[],
        itemNames:{},
        current:''
    },
    methods:{
        saveView:function () {
            PostApi(this.api.save, {view: this.current}, function (a) {
                if (a.status === 'success'){
                    window.location.reload();
                }
            });
        },
        changeView:function (view) {
            this.current = view;
        }
    }
});