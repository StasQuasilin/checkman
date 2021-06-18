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
            let items = this.filtered(this.product, -1, this.date, this.driver);
            let found = false;
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i];
                    for (let j = 0; j < item.products.length; j++){
                        let product = item.products[j];
                        if (product.counterparty) {
                            let counterparty = product.counterparty;
                            let counterpartyId = counterparty.id;
                            if (counterpartyId) {
                                if (!counterparty[counterpartyId]) {
                                    organisations[counterpartyId] = counterparty;
                                }
                            }
                            if (this.organisation === counterpartyId) {
                                found = true;
                            }
                        }
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
            let items = this.filtered(-1, this.organisation, this.date, this.driver);
            let found = false;
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let item = items[i];
                    for (let j = 0; j < item.products.length; j++){
                        let p = item.products[j];
                        let productId = p.productId;
                        if (!products[productId]) {
                            products[productId] = {
                                id:productId,
                                name:p.productName,
                                amount:1
                            };
                        }else {
                            products[productId].amount++;
                        }
                        if (this.product === productId){
                            found = true;
                        }
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
            let items = this.filtered(this.product, this.organisation, -1, this.driver);
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    let date = items[i].date;
                    if (!dates[date]) {
                        dates[date] = 1;
                    } else {
                        dates[date]++;
                    }
                }
            }

            return Object.keys(dates).sort(function (a, b) {
                return new Date(a) - new Date(b);
            }).reduce(
                (obj, key) => {
                    obj[key] = dates[key];
                    return obj;
                },{}
            );
        },
        drivers:function(){
            let drivers = {};
            let items = this.filtered(this.product, this.organisation, this.date, -1);
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

            let byDate = true;
            let byDriver = true;

            if (date !== -1 && date !== '-1'){
                byDate = item.date === date;
            }

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

            let g = item.gross;
            let t = item.tare;

            let any = true;
            if (this.anyTime){
                any =
                    this.onTerritory && ((timeIn && !timeOut) || (g > 0 && t === 0 || g === 0 && t > 0)) ||
                    this.onVoyage && !timeIn && timeOut ||
                    this.onDone && ((timeIn && timeOut) && ((g > 0 && t > 0) || (g === 0 && t === 0))) ||
                    this.onWait && !timeIn && !timeOut;
            }
            let byProduct = product === -1;
            let byCounterparty = counterparty === -1;

            for (let i = 0; i < item.products.length; i++){
                let p = item.products[i];

                if (p.productId && product !== -1){
                    if (p.productId === product){
                        byProduct = true;
                    }
                }

                if (p.counterparty && counterparty && counterparty !== -1){
                    if (p.counterparty.id === counterparty){
                        byCounterparty = true;
                    }
                }
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
            this.date = '-1';
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