var filter_control = new Vue({
    el:'#filter',
    data:{
        items:[],
        filter:{
            date:-1,
            organisation:-1,
            product:-1,
            creator:-1
        }

    },
    methods:{
        organisations:function(){
            var organisations = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var organisation = this.items[i].item.organisation;
                    if (organisations[organisation.id] == undefined) {
                        organisations[organisation.id] = organisation;
                    }
                }
            }
            return organisations;
        },
        products:function(){
            var products = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var product = this.items[i].item.product;
                    if (products[product.id] == undefined) {
                        products[product.id] = product;
                    }
                }
            }
            return products;
        },
        dates:function(){
            var dates = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var date = this.items[i].item.date;
                    if (dates[date] == undefined) {
                        dates[date] = date;
                    }
                }
            }
            return dates;
        },
        creators:function(){
            var creators = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var creator = this.items[i].item.creator;
                    if (creators[creator.id] == undefined) {
                        creators[creator.id] = creator;
                    }
                }
            }
            return creators;
        },
        clear:function(){
            this.filter.date=-1;
            this.filter.organisation=-1;
            this.filter.product=-1;
            this.filter.creator=-1;
        },
        filteredItems:function(){
            var self = this;
            return this.items.filter(function(item){
                return (self.filter.date === -1 || self.filter.date === item.item.date) &
                    (self.filter.organisation === -1 || self.filter.organisation === item.item.organisation.id) &
                    (self.filter.product === -1 || self.filter.product === item.item.product.id) &
                    (self.filter.creator === -1 || self.filter.creator === item.item.creator.id)
            })
        }
    }
});
