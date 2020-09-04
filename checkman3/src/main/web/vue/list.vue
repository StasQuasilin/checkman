list = {
    data:{
        api:{},
        items:{}
    },
    methods:{
        handler:function (data) {
            console.log(data);
        },
        edit:function (id) {
            let params = {};
            if (id){
                params.id = id;
            }
            loadModal(this.api.edit, params);
        }
    }
};