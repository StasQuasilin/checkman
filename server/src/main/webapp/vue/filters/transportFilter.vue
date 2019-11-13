var filter_control = new Vue({
    el: '#filter_view',
    data:{
        filters:{
            types:[]
        },
        items:[],
        fItems:[],
        type:-1,
        organisation:-1,
        product:-1,
        on:false,
        date:-1,
        driver:-1
    },
    mounted:function(){
        let product = localStorage.getItem('product');
        if (product) {
            this.product = product;
        }
        this.date = new Date().toISOString().substring(0, 10);
    },
    methods:{
        putProduct:function(){
            localStorage.setItem('product', this.product);
        },
        organisations:function(){
            let organisations = {};
            let items = this.filtered(this.product, null, this.date, this.driver);
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let counterparty = items[i].item.counterparty;
                    if (!counterparty[counterparty.id]) {
                        organisations[counterparty.id] = counterparty;
                    }
                }
            }
            let res = Object.values(organisations);
            let found = false;
            for (let i in res){
                if (res.hasOwnProperty(i)){
                    if (res[i].id === this.organisation){
                        found = true;
                        break;
                    }
                }
            }
            if (!found){
                this.organisation = -1;
            }
            res.sort(function(a, b){
                return a.value.localeCompare(b.value);
            });
            return res;
        },
        products:function() {
            var products = {};
            var items = this.items;
            //for (var i in items){
            //    if (items.hasOwnProperty(i)){
            //        var product = items[i].item.product;
            //        if (products[product.id] == undefined) {
            //            products[product.id] = product;
            //        }
            //    }
            //}
            return products;
        },
        dates:function(){
            var dates = {};
            var items = this.items;
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    var date = items[i].item.date;
                    if (dates[date] == undefined) {
                        dates[date] = date;
                    }
                }
            }
            return dates;
        },
        drivers:function(){
            let drivers = {};
            let items = this.filteredItems();
            //for (let i in items){
            //    if (items.hasOwnProperty(i)){
            //        var driver = items[i].item.driver;
            //        if (driver.id && !drivers[driver.id]){
            //            drivers[driver.id] = driver;
            //        }
            //    }
            //}
            let result = Object.values(drivers);
            result.sort(function(a, b){
                return a.person.value.localeCompare(b.person.value);
            });
            return result;
        },
        filtered:function(product, counterparty, date, driver){
            const self = this;
            return this.items.filter(function (item){
                let byProduct = true;
                if (product && product != -1){
                    byProduct = item.item.product.id === product;
                }
                let byCounterparty = true;
                if (counterparty && counterparty != -1){
                    byCounterparty = item.item.counterparty.id === counterparty;
                }
                let byDate = true;
                if (date && date != -1){
                    byDate = item.item.date === date;
                }
                return byProduct & byCounterparty & byDate;
            });
        },
        filteredItems:function(){
            return this.filtered(this.product, this.organisation);
            //const self = this;
            //return this.items.filter(function(item){
            //    var byDriver = self.driver == -1 ||
            //        (self.driver == 0 && item.item.driver.id == undefined) ||
            //        (item.item.driver.id == self.driver);
            //    var on = true;
            //    if (self.on){
            //        let timeIn = item.item.timeIn.time;
            //        let timeOut = item.item.timeOut.time;
            //        on = timeIn && !timeOut;
            //    }
            //    return (self.type == -1 || item.item.type == self.type) & on &
            //        (self.organisation == -1 || item.item.counterparty.id == self.organisation) &
            //        (self.product == -1 || item.item.product.id == self.product) &
            //        (self.date == -1 || item.item.date === self.date) & byDriver;
            //})
        },
        clear:function(){
            this.type = -1;
            this.product = -1;
            this.organisation = -1;
            this.on = false;
            this.date = -1;
            this.driver = -1;
        }
    }
});