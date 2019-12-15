var editor = new Vue({
    el:'#editor',
    components:{
        'object-input':objectInput
    },
    data:{
        id:-1,
        type:'',
        api:{},
        date:new Date().toISOString().substring(0, 10),
        customer:-1,
        driver:{
            id:-1
        },
        vehicle:{
            id:-1
        },
        trailer:{
            id:-1
        },
        transporter:{
            id:-1
        },
        shipper:-1,
        shippers:[],
        customers:[],
        units:[],
        deals:[],
        counterpartyDeals:{},
        driverProps:{},
        vehicleProps:{},
        trailerProps:{},
        counterpartyProps:{},
        transporterProps:{},
        productProps:{}
    },
    methods:{
        putDriver:function(driver){
            this.driver = driver;
            if (driver.vehicle && this.vehicle.id == -1){
                this.putVehicle(driver.vehicle);
            }
            if (driver.organisation && this.transporter.id == -1){
                this.putTransporter(driver.organisation);
            }
        },
        putTransporter:function(transporter){
            this.transporter = transporter
        },
        putVehicle:function(vehicle){
            this.vehicle = vehicle;

            if (vehicle.trailer && this.trailer.id == -1){
                this.putTrailer(vehicle.trailer);
            }
        },
        putTrailer:function(trailer){
            this.trailer = trailer;
        },
        getCounterpartyDeals:function(counterparty){
            if(this.counterpartyDeals[counterparty.id]){
                return this.counterpartyDeals[counterparty.id];
            }
        },
        addDeal:function(){
            let deal = {
                id:-1,
                key:randomUUID(),
                counterparty:{
                    id:-1
                },
                addressList:[],
                products:[],
                address:{
                    id:-1
                }
            };
            this.deals.push(deal);
            this.addField(deal);
        },
        getContracts:function(counterparty, deal){
            if (counterparty.name){
                console.log("Get contracts for " + counterparty.name);
                loadModal(this.api.findContracts, {counterparty:counterparty.id}, function(a){
                    console.log(a);
                    if(a) {
                        Object.assign(deal, a);
                    }
                });
            }
        },
        getAddress:function(counterparty, deal){
            if (counterparty.name) {
                console.log('Get Address for ' + counterparty.value + ', id=' + counterparty.id);
                PostApi(this.api.findAddress, {counterparty: counterparty.id}, function (a) {
                    deal.addressList = a;
                    if (a.length == 1){
                        deal.address = a[0];
                    }
                })
            } else {
                console.log('Clear address list');
                deal.addressList = [];
            }
        },
        palletsAmount:function(){
            let result = 0;
            this.deals.forEach(function(d){
                d.products.forEach(function(p){
                    result += p.pallet;
                })
            });
            return result;
        },
        addCounterparty:function(counterpart, deal){
            console.log(deal);
            console.log(counterpart);
        },
        addField:function(deal){
            deal.products.push({
                product:{
                    id:-1,
                    pallet:0
                },
                unit:{
                    id:-1
                },
                amount:0,
                pallet:0,
                price:0,
                shipper:{
                    id:this.shipper
                }
            })
        },
        computeShipper:function(){
          let shippers = {};
            this.deals.forEach(function(d){
                d.products.forEach(function(p){
                    let shipper = p.shipper.id;
                    if (!shippers[shipper]){
                        shippers[shipper] = 0;
                    }
                    shippers[shipper] ++;
                })
            });
            console.log(shippers);
            let min = 0;
            let shipper;
            let keys = Object.keys(shippers);
            for (let i in keys){
                if (keys.hasOwnProperty(i)){
                    let k = keys[i];
                    let v = shippers[k];
                    if (v >= min){
                        min = v;
                        shipper = k;
                    }
                }
            }
            return shipper;
        },
        removeDeal:function(idx){
            console.log('Remove deal ' + (idx + 1));
            this.deals.splice(idx, 1);
        },
        removeProduct:function(deal, id){
            if (deal.products.length > 1) {
                deal.products.splice(id, 1);
            }
        },
        total:function(products){
            let total = 0;
            if (products) {
                products.forEach(function (a) {
                    total += a.amount * a.price;
                });
            }
            return total;
        },
        checkAddress:function(deal){
            if (deal.address.id == -2){
                deal.address.id = -1;
                this.addAddress(deal);
            }
        },
        addAddress:function(deal){
            loadModal(this.api.editAddress, {counterparty:deal.counterparty.id}, function(a){
                deal.addressList.push(a.address);
                deal.address = a.address;
            });
        },
        getPrice:function(deal, product){
            const self = this;
            this.deals.forEach(function(d){
                d.products.forEach(function(p){
                    if (p == deal){
                        if (d.counterparty.id != -1) {
                            PostApi(self.api.price, {counterparty:d.counterparty.id, product:product.id}, function(a){
                                console.log(a);
                                if (deal.price == 0) {
                                    if (a.status === 'success') {
                                        deal.price = a.price;
                                    }
                                }
                            })
                        }
                    }
                })
            });

        },
        waybillPrint:function(){
            const self = this;
            this.save(function(a){
                if (a.status === 'success'){
                    PostReq(self.api.waybill, {id:self.id}, function(a){
                        let print = window.open();
                        print.document.write(a);
                    });
                }
            });
        },
        moveItem: function (idx, val) {
            this.deals.splice(idx + val, 0, this.deals.splice(idx, 1)[0]);
        },
        print:function(deal){
            const self = this;
            this.save(function(a){
                if (a.status === 'success'){
                    PostReq(self.api.print, {contract:deal.id}, function(p){
                        let print = window.open();
                        print.document.write(p);
                    })
                }
            })
        },
        checkPallets:function(product){
            let amount = product.pallet * product.product.pallet;
            if (product.amount != amount){
                product.amount = amount;
            }
        },
        checkAmount:function(product){
            let pallets = product.amount / product.product.pallet;
            if (product.pallet != pallets){
                product.pallet = pallets;
            }
        },
        saveAndClose:function(){
            this.save(function(a){
                if(a.status === 'success'){
                    closeModal();
                }
            })
        },
        selectDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.date = date;
            }, this.date);
        },
        checkErrors:function(){
            let saveIt = true;
            this.deals.forEach(function(deal){
                Vue.set(deal, 'error', deal.counterparty.id == -1);
                if (deal.error){
                    saveIt = false;
                }
                deal.products.forEach(function(product){
                    if (product.product.id == -1){
                        Vue.set(product, 'productError', true);
                        saveIt = false;
                    }
                    if (product.unit.id == -1){
                        Vue.set(product, 'unitError', true);
                        saveIt = false;
                    }
                });
            });
            return saveIt;
        },
        save:function(onSave){
            if (this.checkErrors()) {
                let data = {
                    id: this.id,
                    date: this.date,
                    driver: this.driver.id,
                    vehicle: this.vehicle.id,
                    trailer: this.trailer.id,
                    transporter: this.transporter.id,
                    customer: this.customer,
                    deals: []
                };
                for (let i in this.deals) {
                    if (this.deals.hasOwnProperty(i)) {
                        let d = this.deals[i];
                        let deal = {
                            id: d.id,
                            from: this.date,
                            to: this.date,
                            counterparty: d.counterparty.id,
                            products: [],
                            index: i
                        };
                        if (d.address && d.address.id) {
                            deal.address = d.address.id;
                        }
                        for (let j in d.products) {
                            if (d.products.hasOwnProperty(j)) {
                                let p = d.products[j];
                                deal.products.push({
                                    id: p.id,
                                    amount: p.amount,
                                    unit: p.unit.id,
                                    type: this.type,
                                    price: p.price,
                                    product: p.product.id,
                                    shipper: p.shipper.id
                                });
                            }
                        }
                        data.deals.push(deal)
                    }
                }
                PostApi(this.api.save, data, function (a) {
                    onSave(a);
                });
            }
        }
    }
});