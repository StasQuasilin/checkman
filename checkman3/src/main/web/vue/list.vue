list = {
    data:{
        api:{},
        items:{}
    },
    methods:{
        handler:function (data) {
            if (data.add){
                let add = data.add;
                for (let i in add){
                    if (add.hasOwnProperty(i)){
                        let a = add[i];
                        Vue.set(this.items, a.id, a);
                    }
                }
            }
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