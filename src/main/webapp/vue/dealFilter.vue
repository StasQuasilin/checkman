dealFilter = new Vue({
    el:'#filterView',
    data:{
        items:null,
        defaultId:-1,
        filter:{
            date:-1,
            organisation:-1,
            product:-1,
            creator:-1
        }
    },
    methods:{
        organisations:function(){
            let organisations = {};

            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let organisation = this.items[i].counterparty;
                    if (!organisations[organisation.id]) {
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
                    let item = this.items[i];
                    for (let j = 0; j < item.products.length; j++){
                        let product = item.products[j].productId;
                        if (!products[product]) {
                            products[product] = {
                                id:product,
                                name:item.products[j].productName
                            };
                        }
                    }
                }
            }
            return products;
        },
        dates:function(){
            let dates = {};
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let date = this.items[i].date;
                    if (!dates[date]) {
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
                    let creator = this.items[i].create.creator;
                    if (!creators[creator.id]) {
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
        clearFilterField:function(name){
            this.filter[name] = -1;
        },
        filteredItems:function(){
            return Object.values(this.items)[0];
        },
        doFilter:function (item) {
            let byProduct = this.filter.product === -1;
            if (!byProduct){
                for(let i = 0; i < item.products.length; i++){
                    let product = item.products[i];
                    if (this.filter.product === product.productId){
                        byProduct = true;
                        break;
                    }
                }
            }
            return (this.filter.date === -1 || this.filter.date === item.date) &&
                (this.filter.organisation === -1 || this.filter.organisation === item.organisation.id) &&
                byProduct &&
                (this.filter.creator === -1 || this.filter.creator === item.create.creator.id)
        }
    }
});
