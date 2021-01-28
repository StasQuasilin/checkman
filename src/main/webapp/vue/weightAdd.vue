editor = new Vue({
    el: '#editor',
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
        duplicateDeal:-1
    },
    methods:{
        dealEdit:function(){
            this.loadDealEdit(this.transportation.deal);
        },
        loadDealEdit:function(deal){
            const self = this;
            let attr = {};
            if (deal.id !== -1){
                attr.id=deal.id;
            } else {
                attr.deal=deal;
            }
            loadModal(this.api.dealEdit, attr, function (a) {
                console.log('!!!!');
                console.log(a);

                editor.transportation.deal = a;
                self.findDeals(a.organisation.id);
                editor.deal = a;
                self.$forceUpdate();

            })
        },
        dealCopy:function(){
            let deal = Object.assign({}, this.transportation.deal);
            deal.id = -1;
            deal.organisation = deal.counterparty;
            this.loadDealEdit(deal);
        },
        selectCounterparty:function(){
            const self = this;
            loadModal(this.api.selectCounterparty, {have:this.transportation.deal.id !== -1}, function (a) {
                if (a.code === 0){
                    self.transportation.deal = a.deal;
                    self.deal = a.deal;
                    self.deals = a.deals;
                    self.$forceUpdate();
                } else {
                    self.transportation.deal.id = -1;
                    self.transportation.deal.organisation = a.organisation;
                    self.dealEdit();
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
        editAddress:function(id){
            let data = {
                type: 'load',
                counterparty: this.transportation.deal.counterparty.id,
                id: id
            };
            const self = this;
            loadModal(this.api.editAddress, data, function(a){
                let found = false;
                for (let i in self.addressList){
                    if (self.addressList.hasOwnProperty(i)){
                        if(self.addressList[i].id === a.id){
                            self.addressList.splice(i, 1, a);
                            found = true;
                        }
                    }
                }
                if (!found){
                    self.addressList.push(a);
                }
                if (self.addressList.length === 1){
                    self.transportation.address = self.addressList[0].id
                }
            })
        },
        editNote:function(note, key){
            console.log(note);
            console.log(key);
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
        findAddress:function(id){
            const self = this;
            PostApi(this.api.findAddress, {counterparty:id}, function(a){
                self.addressList = [];
                a.forEach(function(item){
                    self.addressList.push(item);
                });
            });
        },
        findDeals:function(id){
            const self = this;
            PostApi(this.api.findDeals, {organisation:id}, function(a){
                self.deals = a;
                if(a.length > 0) {
                    if (self.deal.id === -1){
                        self.deal = a[0];
                    }
                    let now = new Date(self.transportation.date);
                    for (let i in a) {
                        if (a.hasOwnProperty(i)) {
                            let deal = a[i];

                            if(deal.id === self.deal.id){
                                self.deal = deal;
                            }
                            let from = new Date(deal.date);
                            let to = new Date(deal.date_to);
                            if (now >= from && now <= to) {
                                self.transportation.deal = deal.id;
                            }
                        }
                    }
                    self.setQuantity();
                } else {
                    console.log(self.transportation);
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
            Vue.set(this.transportation, 'vehicle', vehicle);
            if (vehicle.trailer && vehicle.trailer.id > 0){
                this.putTrailer(vehicle.trailer);
            }
        },
        putTrailer:function(trailer){
            this.transportation.trailer = trailer;
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
        save:function(){
            if (!this.already) {
                if (this.note.edit) {
                    this.saveNote();
                }
                let e = this.errors;
                e.organisation = !(this.transportation.deal.counterparty && this.transportation.deal.counterparty.id !== -1);
                e.product = this.transportation.deal.product.id === -1;

                if (!e.type && !e.organisation && !e.product) {
                    let transportation = {
                        id:this.transportation.id,
                        date:this.transportation.date,
                        customer:this.transportation.customer,
                        deal:{
                            id:this.transportation.deal.id,
                            type:this.transportation.deal.type,
                            date:this.transportation.date,
                            counterparty : this.transportation.deal.counterparty.id,
                            product : this.transportation.deal.product.id,
                            unit : this.transportation.deal.unit.id,
                            shipper : this.transportation.deal.shipper.id,
                        },

                        manager : this.transportation.manager.id,
                        notes:[]
                    };
                    if (this.transportation.address > 0){
                        transportation.address = this.transportation.address;
                    }
                    if (this.transportation.vehicle){
                        transportation.vehicle = this.transportation.vehicle.id;
                    }
                    if (this.transportation.trailer){
                        transportation.trailer = this.transportation.trailer.id;
                    }
                    if (this.transportation.driver){
                        transportation.driver = this.transportation.driver.id;
                    }
                    if (this.transportation.transporter){
                        transportation.transporter = this.transportation.transporter.id;
                    }
                    if (!this.transportation.deal.quantity){
                        transportation.deal.quantity = 0;
                    } else {
                        transportation.deal.quantity = this.transportation.deal.quantity;
                    }
                    if (!this.transportation.deal.price){
                        transportation.deal.price = 0;
                    } else {
                        transportation.deal.price = this.transportation.deal.price;
                    }
                    if (!this.transportation.plan){
                        transportation.plan = 0;
                    } else {
                        transportation.plan = this.transportation.plan;
                    }
                    transportation.deal.products = [
                        {
                            product : transportation.deal.product,
                            quantity: transportation.deal.quantity,
                            unit: transportation.deal.unit,
                            shipper:transportation.deal.shipper,
                            price:transportation.deal.price
                        }
                    ];

                    for(let i in this.transportation.notes){
                        if (this.transportation.notes.hasOwnProperty(i)){
                            let note = this.transportation.notes[i];
                            transportation.notes.push({
                                id:note.id,
                                note:note.note
                            });
                        }
                    }
                    PostApi(this.api.save, transportation, function (a) {
                        if (a.status === 'success') {
                            closeModal();
                        }
                    }, function(e){
                        console.log(e);
                        self.already = false
                    })
                }
            }
        }
    }
});