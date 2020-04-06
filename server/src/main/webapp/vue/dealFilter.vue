var filter_control = new Vue({
    el:'#filter_view',
    data:{
        items:[],
        filter:{
            date:-1,
            organisation:-1,
            product:-1,
            creator:-1
        }
    },
    mounted:function(){
        console.log('Deal filter mounted')
    },
    methods:{
        organisations:function(){
            let organisations = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let organisation = this.items[i].item.counterparty;
                    if (organisations[organisation.id] === undefined) {
                        organisations[organisation.id] = organisation;
                    }
                }
            }
            return Object.values(organisations).sort(function(a, b){
                return a.value.localeCompare(b.value);
            })
        },
        products:function(){
            let products = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let product = this.items[i].item.product;
                    if (products[product.id] === undefined) {
                        products[product.id] = product;
                    }
                }
            }
            return products;
        },
        dates:function(){
            let dates = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let date = this.items[i].item.date;
                    if (dates[date] === undefined) {
                        dates[date] = date;
                    }
                }
            }
            return Object.values(dates).sort(function(a, b){
                return new Date(b) - new Date(a);
            });
        },
        creators:function(){
            let creators = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let creator = this.items[i].item.create.creator;
                    if (creators[creator.id] === undefined) {
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
            let self = this;
            return this.items.filter(function(item){
                return (self.filter.date === -1 || self.filter.date === item.item.date) &
                    (self.filter.organisation === -1 || self.filter.organisation === item.item.organisation.id) &
                    (self.filter.product === -1 || self.filter.product === item.item.product.id) &
                    (self.filter.creator === -1 || self.filter.creator === item.item.create.creator.id)
            })
        }
    }
});
