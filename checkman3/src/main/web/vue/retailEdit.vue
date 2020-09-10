retailEdit = new Vue({
    el:'#retailEdit',
    mixins:[editor],
    components:{
        search:search
    },
    computed:{
        productMap:function(){
            let map = {};
            for (let i = 0; i < this.products.length; i++){
                let product = this.products[i];
                map[product.id] = product;
            }
            return map;
        }
    },
    data:function () {
        return{
            products:[],
            units:[],
            shippers:[],
            driverProps:{

            },
            vehicleProps:{

            },
            trailerProps:{

            },
            counterpartyProps:{

            },
            productProps:{

            }
        }
    },
    methods:{
        newDocument:function () {
            let document = {
                id:-1,
                counterparty:{
                    id:-1,
                    name:''
                },
                products:[],
                key:this.object.documents.length + 1
            };
            this.newProduct(document);
            this.object.documents.push(document);
        },
        newProduct:function (document) {
            let product = {
                id:-1,
                product:{
                    id:-1,
                    name:''
                },
                amount:0,
                unit:-1,
                price:0,
                shipper:this.object.shipper
            };
            document.products.push(product);
        },
        moveItem: function (idx, val) {
            let docs = this.object.documents;
            docs.splice(idx + val, 0, docs.splice(idx, 1)[0]);
        },
        save:function () {

        }
    }
});