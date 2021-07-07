editor = new Vue({
    el: '#weightEditor',
    mixins:[transportationSaver],
    components:{
        'object-input':objectInput
    },
    data:{
        api:{},
        types:{},
        typeNames:{},
        products:[],
        units:[],
        deals:[],
        shippers:[],
        customers:{},
        deal:{
            id:-1
        },
        transportation:{
            id:-1,
            date:new Date().toISOString().substring(0, 10),
            products:[],
            deal:{
                id:-1,
                type:null,
                organisation:{
                    id:-1
                },
                product:{
                    id:-1
                },
                quantity:0,
                price:0
            },
            address:-1,
            plan:20,
            from:-1,
            customer:'szpt',
            vehicle:{
                id:-1
            },
            trailer:{
                id:-1
            },
            driver:{
                id:-1
            },
            transporter:{
                id:-1
            },
            manager:{
                id:-1
            },
            notes:[]
        },
        addressList:[],
        errors:{
            type:false,
            organisation:false,
            product:false,
            manager:false,
            vehicle:false,
            driver:false,
            any:function(){
                return type || organisation || product || manager;
            }
        },
        managers:[],
        role:'',
        note:{
            key:-1,
            edit:false,
            id:-1,
            input:''
        },
        already:false,
        dealProps:{},
        organisationProps:{},
        driverProps:{},
        vehicleProps:{},
        trailerProps:{},
        transporterProps:{},
        duplicateDeal:-1,
        count:1
    },
    methods:{
        save:function(){
            if (!this.already) {
                if (this.note.edit) {
                    this.saveNote();
                }
                this.already = true;
                this.saveTransportation(this.transportation, function (a) {
                    if (a.status === 'success') {
                        closeModal();
                    }
                }, function () {
                    this.already = false;
                })
            }
        },
        dealEdit:function(product, idx){
            if(typeof product === "number"){
                let p = this.transportation.products[product];
                this.loadDealEdit(this.transportation.products[product].deal, product);
            } else {
                this.loadDealEdit(product, idx)
            }
        },
        loadDealEdit:function(deal, productIdx){

            let attr = {};
            if (typeof deal === "number"){
                attr.id = deal;
            } else if (typeof deal === "object"){
                attr.deal = deal;
            } else {
                console.log(deal);
            }
            const self = this;
            loadModal(this.api.dealEdit, attr, function (a) {
                // console.log('!!!! Set deal for product ' + productIdx);
                // console.log(a);
                self.insertProduct(a, productIdx);
            })
        },
        insertProduct:function(data, productIdx){
            let deal = data.deal;
            let dealProduct;
            if(data.product){
                dealProduct = data.product;
            } else {
                dealProduct = deal.products[0];
            }
            let product;
            if (productIdx !== -1){
                product = this.transportation.products[productIdx];
            } else {
                product = {
                    id:-1,
                    amount:0
                }
            }

            product.counterparty=deal.organisation;
            product.deal=deal.id;
            product.dealProduct=dealProduct.id;
            product.quantity=dealProduct.quantity;
            product.price=dealProduct.price;
            product.shipperId=dealProduct.shipperId;
            product.shipperName=dealProduct.shipperName;
            product.deals=data.deals;

            if(productIdx !== -1){
                this.transportation.products.splice(productIdx, 1, product);
            } else {
                this.transportation.products.push(product);
            }
            // self.findDeals(a.organisation.id);
            // self.deal = a;
            this.$forceUpdate();
        },
        dealCopy:function(idx){
            let deal = Object.assign({}, this.transportation.products[idx].deal);
            deal.id = -1;
            deal.organisation = deal.counterparty;
            this.loadDealEdit(deal);
        },
        selectCounterparty:function(productIdx){
            const self = this;
            let dealId= -1;
            if (productIdx !== -1){
                dealId = this.transportation.products[productIdx].deal;
            }
            loadModal(this.api.selectCounterparty, {have:dealId}, function (a) {
                console.log(a);
                if (a.code === 0){
                    self.insertProduct(a, productIdx);
                } else {
                    self.dealEdit({
                        id:-1,
                        organisation:a.organisation,
                        products:[]
                    }, productIdx);
                }
            })
        },
        addType:function(action){
            if (!this.types[action.product.id]){
                this.types[action.product.id] = [];
            }
            this.types[action.product.id].push(action.type);
        },
        typesByProduct:function(){
            let types = this.types[this.transportation.deal.product.id];
            let fount = false;
            for (let i in types){
                if (types.hasOwnProperty(i)){
                    let t = types[i];
                    if (this.transportation.deal.type === t){
                        fount = true;
                        break;
                    }
                }
            }
            if (!fount && types){
                Vue.set(this.transportation.deal, 'type', types[0]);
            }
            return types;
        },
        checkDeal:function(){
            this.duplicateDeal = -1;
            if (this.deal === '-1'){
                let target = this.transportation.deal;
                for (let i in this.deals){
                    if (this.deals.hasOwnProperty(i)){
                        let deal = this.deals[i];
                        if (target.price === deal.price
                            && target.shipper.id === deal.shipper.id
                            && target.quantity === deal.quantity
                            && target.product.id === deal.product.id
                            && target.type === deal.type){
                            this.duplicateDeal = deal.id;
                        }
                    }
                }
            }
        },
        editAddress:function(product, id){
            let data = {
                type: 'load',
                counterparty: product.counterparty.id,
                id: id
            };
            const self = this;
            loadModal(this.api.editAddress, data, function(a){
                if(typeof product.addressList === "undefined"){
                    product.addressList = [];
                }
                let found = false;
                let addressList = product.addressList;

                for (let i in addressList){
                    if (addressList.hasOwnProperty(i)){
                        if(addressList[i].id === a.id){
                            addressList.splice(i, 1, a);
                            self.$forceUpdate();
                            found = true;
                        }
                    }
                }
                if (!found){
                    addressList.push(a);
                    self.$forceUpdate();
                }
                if (addressList.length === 1){
                    product.addressId = addressList[0].id
                }
            })
        },
        editNote:function(note, key){
            this.note.edit = true;

            if (key !== undefined) {
                this.transportation.notes.splice(key, 1);
                this.note.key = key;
            }
            if (note) {
                this.note.id = note.id;
                this.note.input = note.note;
            }
            const self = this;
            setTimeout(function(){
                self.$refs.comment.select();
            }, 10)
        },
        saveNote:function(){
            if (this.note.input) {
                this.transportation.notes.push({
                    id:this.note.id,
                    creator: this.worker,
                    note: this.note.input
                });
                this.clearNote();
            }
        },
        removeNote:function(key){
            this.transportation.notes.splice(key, 1);
        },
        removeProduct:function(idx){
            this.transportation.products.splice(idx, 1);
        },
        setQuantity:function(){
            this.checkDeal();
            if (this.deal !== "-1") {
                this.transportation.deal = Object.assign({}, this.deal);
            } else{
                this.transportation.deal.id = -1;
            }
        },
        clearNote:function(){
            this.note = {
                key:-1,
                edit:false,
                id:-1,
                input:''
            }
        },
        cancelOrganisation:function(){
            this.deal = '-1';
            this.transportation.type = -1;
            this.transportation.address = -1;
            this.transportation.product = -1;
            this.deals = [];
            this.addressList = [];
            this.duplicateDeal = -1;
        },
        productList:function(){
            if (this.deal === '-1'){
                return this.products;
            } else {
                let products = [];
                for (let d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        let deal = this.deals[d];
                        if (deal.id === this.deal){
                            products.push(deal.product);
                            this.transportation.product = deal.product.id;
                            break;
                        }
                    }
                }
                return products;
            }
        },
        shipperList:function(){
            if (this.deal === '-1'){
                return this.shippers;
            } else {
                let shippers = [];
                for (let d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        let deal = this.deals[d];
                        if (deal.id === this.deal.id){
                            shippers.push(deal.shipper);
                            this.transportation.from = deal.shipper;
                            break;
                        }
                    }
                }
                return shippers;
            }
        },
        typeList:function(){
            if (this.transportation.deal === -1){
                return this.types;
            } else {
                let types = {};
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.transportation.deal){
                            types[deal.type] = this.types[deal.type];
                            this.transportation.type = deal.type;
                            break;
                        }
                    }
                }
                return types;
            }
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.transportation.date = date;
            }, self.transportation.date)
        },
        putOrganisation:function(organisation){
            this.transportation.deal.counterparty = organisation;
            if (organisation.id > 0) {
                this.findDeals(organisation.id);
                this.findAddress(organisation.id);
            } else {
                this.cancelOrganisation();
            }
        },
        findAddress:function(id, product){
            const self = this;
            PostApi(this.api.findAddress, {counterparty:id}, function(a){
                product.addressList = a;
                if (typeof product.addressId === "undefined"){
                    product.addressId = -1;
                }
                self.$forceUpdate();
            });
        },
        initDealsLists:function(){
            let products = this.transportation.products;
            for (let i = 0; i < products.length; i++){
                let product = products[i];
                let counterpartyId = product.counterparty.id;
                this.findDeals(counterpartyId, product);
                this.findAddress(counterpartyId, product)
            }
            this.checkManager();
        },
        checkManager:function(){
            if (this.transportation.manager) {
                for (let i in this.managers) {
                    if (this.managers.hasOwnProperty(i)) {
                        let manager = this.managers[i];
                        if (this.transportation.manager.id === manager.id) {
                            return;
                        }
                    }
                }
            } else {
                this.transportation.manager = {};
            }
            this.transportation.manager.id = -1;
        },
        findDeals:function(id, product){
            let p = product;
            const self = this;
            PostApi(this.api.findDeals, {organisation:id, product:product.id}, function(a){
                if(a.status === 'success'){
                    p.deals = a.result;

                    let f = false;
                    for (let i in p.deals){
                        if (p.deals.hasOwnProperty(i)){
                            if (p.deals[i].id === product.dealProduct){
                                f = true;
                                break;
                            }
                        }
                    }
                    if (!f){
                        p.deals.push({
                            id:product.deal,
                            counterparty:product.counterparty,
                            type:product.type,
                            products:[
                                {
                                    id:product.dealProduct,
                                    productName:product.productName,
                                    price:product.price,
                                    shipperName:product.shipperName
                                }
                            ]
                        });
                    }
                }
            })
        },
        getPrice: function(){
            for (let d in this.deals){
                if (this.deals.hasOwnProperty(d)){
                    let deal = this.deals[d];
                    if (deal.id === this.transportation.deal){
                        return this.transportation.price = deal.price;
                    }
                }
            }
        },
        putVehicle:function(vehicle){
            console.log(vehicle);
            Vue.set(this.transportation, 'vehicle', vehicle);
            if (vehicle.trailer && vehicle.trailer.id > 0){
                this.putTrailer(vehicle.trailer);
            }
        },
        putTrailer:function(trailer){
            this.transportation.trailer = trailer;
            this.$forceUpdate();
        },
        putTransporter:function(transporter){
            Vue.set(this.transportation, 'transporter', transporter);
        },
        putDriver:function(driver){
            Vue.set(this.transportation, 'driver', driver);
            console.log(typeof this.transportation.vehicle);
            if (driver.vehicle && (typeof this.transportation.vehicle === 'undefined' || this.transportation.vehicle.id === -1)){
                this.putVehicle(driver.vehicle);
            }
            if (driver.organisation && (typeof this.transportation.transporter === 'undefined' || this.transportation.transporter.id === -1)){
                this.putTransporter(driver.organisation);
            }
        },

    }
});