transportEdit = new Vue({
    el:'#transportEdit',
    mixins:[editor],
    components:{
        search:search
    },
    data:function () {
        return {
            fixedProducts:true,
            products:[],
            shippers:[],
            deals:{}
        }
    },
    computed:{
        productMap:function () {
            let map = {};
            for (let i = 0 ; i < this.products.length; i++){
                let product = this.products[i];
                map[product.id] = product;
            }
            return map;
        }
    },
    methods:{
        addDocument:function () {
            let document = {
                counterparty:{
                    id:-1,
                    name:'',
                    type:''
                },
                products:[]
            };
            this.addTransportationProduct(document);
            this.object.documents.push(document);
        },
        addTransportationProduct:function (document) {
            let product = {
                id:-1,
                amount:25,
                product:{
                    id:-1,
                    name:''
                },
                price:0,
                shipper:this.shippers[0]
            };
            document.products.push(product);
        },
        putProduct:function (dp, product) {

            Vue.set(dp, 'product', product)
        },
        removeProduct:function(document, productIndex){
            document.products.splice(productIndex, 1);
        }
    }
});