var editor = new Vue({
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
        transportation:{
            id:-1,
            date:new Date().toISOString().substring(0, 10),
            deal:{},
            quantity:0,
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
            manager:-1,
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
        organisationProps:{},
        driverProps:{},
        vehicleProps:{},
        trailerProps:{},
        transporterProps:{}
    },
    methods:{
        addType:function(action){
            if (!this.types[action.product.id]){
                this.types[action.product.id] = [];
            }
            this.types[action.product.id].push(action.type);
        },
        typesByProduct:function(){
            var types = this.types[this.transportation.deal.product.id];
            var fount = false;
            for (var i in types){
                if (types.hasOwnProperty(i)){
                    var t = types[i];
                    if (this.transportation.type == t){
                        fount = true;
                        break;
                    }
                }
            }
            if (!fount && types){
                this.transportation.type = types[0];
            }
            return types;
        },
        editAddress:function(id){
            var data = {
                type:'load',
                counterparty:this.transportation.organisation.id,
                id:id
            };

            const self = this;
            loadModal(this.api.editAddress, data, function(a){
                var found = false;
                for (var i in self.addressList){
                    if (self.addressList.hasOwnProperty(i)){
                        if(self.addressList[i].id == a.id){
                            self.addressList.splice(i, 1, a);
                            found = true;
                        }
                    }
                }
                if (!found){
                    self.addressList.push(a);
                }
                if (self.addressList.length == 1){
                    self.transportation.address = self.addressList[0].id
                }
            })
        },
        editNote:function(note, key){
            console.log(note);
            console.log(key);
            this.note.edit = true;

            if (key != undefined) {
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
            for (let i in this.deals){
                if (this.deals.hasOwnProperty(i)){
                    if (this.deals[i].id == this.transportation.deal){
                        this.transportation.quantity = this.deals[i].quantity;
                        this.transportation.price = this.deals[i].price;
                        break;
                    }
                }
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
            this.transportation.type = -1;
            this.transportation.deal = -1;
            this.transportation.product = -1;
            this.deals = [];
            this.addressList = [];
        },
        productList:function(){
            if (this.transportation.deal.id == -1){
                return this.products;
            } else {
                var products = [];
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.transportation.deal){
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
            if (this.transportation.deal.id == -1){
                return this.shippers;
            } else {
                var froms = [];
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.transportation.deal.id){
                            froms.push(deal.visibility);
                            this.transportation.from = deal.visibility;
                            break;
                        }
                    }
                }
                return froms;
            }
        },
        typeList:function(){
            if (this.transportation.deal == -1){
                return this.types;
            } else {
                var types = {};
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
            this.transportation.organisation = organisation;
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
                if (a.length == 1){
                   self.transportation.address = a[0].id ;
                }

            });
        },
        findDeals:function(id){
            const self = this;
            PostApi(this.api.findDeals, {organisation:id}, function(a){
                self.deals = a;
                if(a.length > 0) {
                    self.transportation.deal = a[0];
                    var now = new Date(self.transportation.date);
                    for (var i in a) {
                        if (a.hasOwnProperty(i)) {
                            var deal = a[i];
                            var from = new Date(deal.date);
                            var to = new Date(deal.date_to);
                            if (now >= from && now <= to) {
                                self.transportation.deal = deal.id;
                            }
                        }
                    }
                    self.setQuantity();
                }
            })
        },
        getPrice: function(){
            for (var d in this.deals){
                if (this.deals.hasOwnProperty(d)){
                    var deal = this.deals[d];
                    if (deal.id === this.transportation.deal){
                        return this.transportation.price = deal.price;
                    }
                }
            }
        },
        putVehicle:function(vehicle){
            this.transportation.vehicle = vehicle;

            if (vehicle.trailer && vehicle.trailer.id > 0){
                this.putTrailer(vehicle.trailer);
            }
        },
        putTrailer:function(trailer){
            this.transportation.trailer = trailer;
        },
        putTransporter:function(transporter){
            this.transportation.transporter = transporter;
        },
        putDriver:function(driver){
            this.transportation.driver = driver;
            if (driver.vehicle && this.transportation.vehicle.id == -1){
                this.putVehicle(driver.vehicle);
            }
            if (driver.organisation && this.transportation.transporter.id == -1){
                this.putTransporter(driver.organisation);
            }
        },
        save:function(){
            if (!this.already) {

                if (this.note.edit) {
                    this.saveNote();
                }
                var e = this.errors;
                e.organisation = this.transportation.deal.organisation.id == -1;
                e.product = this.transportation.deal.product == -1;
                e.manager = this.transportation.manager == -1;
                var transportation = Object.assign({}, this.transportation);
                if (!transportation.quantity){
                    transportation.quantity = 0;
                }
                if (!transportation.price){
                    transportation.price = 0;
                }
                transportation.deal.counterparty = this.transportation.deal.counterparty.id;
                transportation.deal.product = this.transportation.deal.product.id;

                //transportation.vehicle = transportation.vehicle.id;
                //transportation.trailer = transportation.trailer.id;
                //transportation.driver = transportation.driver.id;
                //transportation.transporter = transportation.transporter.id;

                for(var i in transportation.notes){
                    if (transportation.notes.hasOwnProperty(i)){
                        var note = transportation.notes[i];
                        delete note.creator;
                    }
                }

                if (!e.type && !e.organisation && !e.product) {
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