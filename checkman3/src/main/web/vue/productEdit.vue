productEdit = new Vue({
    el:'#productEdit',
    mixins:[editor],
    data:function(){
        return{
            actions:[],
            actionNames:{},
        }
    },
    methods:{
        save:function(){

            PostApi(this.api.save, this.object, function (a) {
                if (a.status === 'success'){
                    closeModal();
                    if (a.result){
                        saveModal(a.result);
                    }
                }
            })
        }
    }
});