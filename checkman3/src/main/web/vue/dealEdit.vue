dealEdit = new Vue({
    el:'#dealEdit',
    mixins:[editor],
    components:{
        search:search
    },
    data:function () {
        return{
            types:[],
            typeNames:{},
            products:[],
            units:[],
            organisationProps:{
                put:function (organisation) {
                    dealEdit.object.counterparty = organisation;
                }
            }
        }
    },
    methods:{
        newProduct:function(){
            const self = this;
            loadModal(this.api.productEdit, {}, function (a) {
                self.products.push(a);
            })
        },
        save:function(){
            PostApi(this.api.save, this.object, function (a) {
                console.log(a);
            })
        }
    }
});