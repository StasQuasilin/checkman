userPage = new Vue({
    el:'#page',
    data:{
        api:{},
        worker:null,
        password:'',
        roles:[]
    },
    methods:{
        save:function () {
            PostApi(this.api.save, this.worker, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        },
        deleteUser:function () {
            PostApi(this.api.delete, {id:this.worker.id}, function (a) {
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});