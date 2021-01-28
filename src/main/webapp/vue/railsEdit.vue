var edit = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        rails:[],
        truck:{},
    },
    methods:{
        save:function(){
            PostApi(this.api.save, this.truck, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});