var filter_controll = new Vue({
    el: '#filter_view',
    data:{
        filters:{
            types:[]
        },
        items:[],
        type:-1,
        organisation:-1,
        product:-1
    },
    beforeUpdate:function(){
        this.items.filter(function(item){
            return item.item.organisation.id == this.organisation;
        })
    },
    methods:{
        organisations:function(){
            var organisations = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var organisation = this.items[i].item.organisation;
                    if (typeof organisation[organisation.id] !== undefined){
                        organisations[organisation.id] = organisation;
                    }
                }
            }
            return organisations;
        },
        products:function() {
            var products = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var product = this.items[i].item.product;
                    console.log(products[product.id] != undefined);
                    if (typeof products[product.id] !== undefined){
                        products[product.id] = product;
                    }
                }
            }
            console.log(products);
            return products;
        },
        filterFunction:function(item){
            return item.organisation.id == this.organisation.id
        }
    }
});