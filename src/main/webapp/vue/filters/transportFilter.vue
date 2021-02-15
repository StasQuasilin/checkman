transportFilter = new Vue({
    el: '#filter_view',
    data:{
        filters:{
            types:[]
        },
        items:{},
        fItems:[],
        type:-1,
        organisation:-1,
        product:-1,
        onTerritory:false,
        onVoyage:false,
        onWait:false,
        onDone:false,
        date:-1,
        driver:[]
    },
    computed:{
        productList:function(){
            return this.products();
        },
        driverList:function(){
            return this.drivers();
        },
        anyTime:function () {
            return this.onTerritory || this.onVoyage || this.onWait || this.onDone;
        }
    },
    mounted:function(){
        let product = localStorage.getItem('product');
        if (product) {
            this.putProduct(parseInt(product));
        }
        this.date = new Date().toISOString().substring(0, 10);
    },
    methods:{
        checkFilter:function(){
            if (this.items.length > 0 && this.filteredItems().length === 0){
                this.product = -1;
            }
            if (this.items.length > 0 && this.filteredItems().length === 0){
                this.date = -1;
            }
        },
        putProduct:function(id){
            this.product = id;
            // if (typeof this.product === "string"){
            //     this.product = parseInt(this.product);
            // }
            localStorage.setItem('product', id);
        },
        organisations:function(){
            let organisations = {};
            let items = this.filtered(this.product, null, this.date, this.driver);
            let found = false;
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let counterparty = items[i].counterparty;
                    let counterpartyId = counterparty.id;
                    if (counterpartyId) {
                        if (!counterparty[counterpartyId]) {
                            organisations[counterpartyId] = counterparty;
                        }
                    }
                    if (this.organisation === counterpartyId){
                        found = true;
                    }
                }
            }
            if (!found){
                this.organisation = -1;
            }

            return Object.values(organisations).sort(function(a, b){
                return a.value.localeCompare(b.value);
            });
        },
        products:function() {
            let products = {};
            let items = this.filtered(null, this.organisation, this.date, this.driver);
            let found = false;
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let product = items[i].product;
                    let productId = product.id;
                    if (!products[productId]) {
                        product.amount = 1;
                        products[productId] = product;
                    }else {
                        products[productId].amount++;
                    }
                    if (this.product === productId){
                        found = true;
                    }
                }
            }
            if (!found){
                this.product = -1;
            }
            return Object.values(products).sort(function (a, b) {
                return a.name.localeCompare(b.name);
            });
        },
        dates:function(){
            let dates = {};
            let items = this.items;
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let date = items[i].date;
                    date.amount = 1;
                    if (!dates[date]) {
                        dates[date] = {
                            date:date,
                            amount:1
                        };
                    } else {
                        dates[date].amount++;
                    }
                }
            }
            return dates;
        },
        drivers:function(){
            let drivers = {};
            let items = this.filtered(this.product, this.organisation, this.date, null);
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let driver = items[i].driver;
                    if (driver && driver.id){
                        if (!drivers[driver.id]){
                            driver.amount = 1;
                            drivers[driver.id] = driver;
                        } else {
                            drivers[driver.id].amount++;
                        }
                    }
                }
            }
            let result = Object.values(drivers);
            result.sort(function(a, b){
                if (a.person.value && b.person.value) {
                    return a.person.value.localeCompare(b.person.value);
                } else if (a.person.value){
                    return -1
                } else {
                    return 1;
                }
            });
            return result;
        },
        filtered:function(product, counterparty, date, driver){
            const self = this;
            let items = Object.values(this.items);
            return items.filter(function (item) {
                return self.filter(item, product, counterparty, date, driver)
            });
        },
        filter:function(item, product, counterparty, date, driver){
            let byProduct = true;
            if (item.product && product && product !== -1){
                byProduct = item.product.id === product;
            }
            let byCounterparty = true;
            if (counterparty && counterparty !== -1){
                byCounterparty = item.counterparty.id === counterparty;
            }
            let byDate = true;
            if (date && date !== -1){
                byDate = item.date === date;
            }
            let byDriver = true;
            if (driver && driver.length > 0) {
                if (item.driver && item.driver.id) {
                    let anyDriver = false;
                    for (let i in driver) {
                        if (driver.hasOwnProperty(i)) {
                            let d = driver[i];
                            if (item.driver.id === d) {
                                anyDriver = true;
                                break;
                            }
                        }
                    }
                    byDriver = anyDriver;
                } else {
                    byDriver = false;
                }
            }
            let timeIn = item.timeIn;
            let timeOut = item.timeOut;

            let g = 0;
            let t = 0;
            let w = item.weight;
            if (w){
                g = w.brutto;
                t = w.tara;
            }
            let any = true;
            if (this.anyTime){
                any =
                    this.onTerritory && ((timeIn && !timeOut) || (g > 0 && t === 0 || g === 0 && t > 0)) ||
                    this.onVoyage && !timeIn && timeOut ||
                    this.onDone && ((timeIn && timeOut) && ((g > 0 && t > 0) || (g === 0 && t === 0))) ||
                    this.onWait && !timeIn && !timeOut;
            }
            return byProduct && byCounterparty && byDate && byDriver && any;
        },
        doFilter:function(item){
            const product = this.product;
            const counterparty = this.organisation;
            const date = this.date;
            const driver = this.driver;
            return this.filter(item, product, counterparty, date, driver);
        },
        clear:function(){
            this.type = -1;
            this.product = -1;
            this.organisation = -1;
            this.date = -1;
            this.driver = [];
            this.clearTimes();
        },
        clearTimes:function () {
            this.onTerritory = false;
            this.onVoyage = false;
            this.onWait = false;
            this.onDone = false;
        }
    }
});