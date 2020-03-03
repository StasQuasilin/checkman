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
        visibles:[],
        customers:{},
        plan:{
            id:-1,
            type:-1,
            date:new Date().toISOString().substring(0, 10),
            number:'',
            deal:-1,
            quantity:0,
            organisation:{
                id:-1
            },
            address:-1,
            product:-1,
            plan:20,
            from:-1,
            price:0,
            unit:-1,
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
            var types = this.types[this.plan.product];
            var fount = false;
            for (var i in types){
                if (types.hasOwnProperty(i)){
                    var t = types[i];
                    if (this.plan.type == t){
                        fount = true;
                        break;
                    }
                }
            }
            if (!fount && types){
                this.plan.type = types[0];
            }
            return types;
        },
        editAddress:function(id){
            var data = {
                type:'load',
                counterparty:this.plan.organisation.id,
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
                    self.plan.address = self.addressList[0].id
                }
            })
        },
        editNote:function(note, key){
            console.log(note);
            console.log(key);
            this.note.edit = true;

            if (key != undefined) {
                this.plan.notes.splice(key, 1);
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
                this.plan.notes.push({
                    id:this.note.id,
                    creator: this.worker,
                    note: this.note.input
                });
                this.clearNote();
            }
        },
        removeNote:function(key){
            this.plan.notes.splice(key, 1);
        },
        setQuantity:function(){
            for (let i in this.deals){
                if (this.deals.hasOwnProperty(i)){
                    if (this.deals[i].id == this.plan.deal){
                        this.plan.quantity = this.deals[i].quantity;
                        this.plan.price = this.deals[i].price;
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
            this.plan.type = -1;
            this.plan.deal = -1;
            this.plan.product = -1;
            this.deals = [];
            this.addressList = [];
        },
        productList:function(){
            if (this.plan.deal == -1){
                return this.products;
            } else {
                var products = [];
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.plan.deal){
                            products.push(deal.product);
                            this.plan.product = deal.product.id;
                            break;
                        }
                    }
                }
                return products;
            }
        },
        visibleList:function(){
            if (this.plan.deal == -1){
                return this.visibles;
            } else {
                var froms = [];
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.plan.deal){
                            froms.push(deal.visibility);
                            this.plan.from = deal.visibility;
                            break;
                        }
                    }
                }
                return froms;
            }
        },
        typeList:function(){
            if (this.plan.deal == -1){
                return this.types;
            } else {
                var types = {};
                for (var d in this.deals){
                    if (this.deals.hasOwnProperty(d)){
                        var deal = this.deals[d];
                        if (deal.id === this.plan.deal){
                            types[deal.type] = this.types[deal.type];
                            this.plan.type = deal.type;
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
                self.plan.date = date;
            }, self.plan.date)
        },
        putOrganisation:function(organisation){
            this.plan.organisation = organisation;
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
                   self.plan.address = a[0].id ;
                }

            });
        },
        findDeals:function(id){
            const self = this;
            PostApi(this.api.findDeals, {organisation:id}, function(a){
                self.deals = a;
                if(a.length > 0) {
                    self.plan.deal = a[0].id;
                    self.plan.type = a[0].type;
                    self.plan.product = a[0].product.id;
                    var now = new Date(self.plan.date);
                    for (var i in a) {
                        if (a.hasOwnProperty(i)) {
                            var deal = a[i];
                            var from = new Date(deal.date);
                            var to = new Date(deal.date_to);
                            if (now >= from && now <= to) {
                                self.plan.deal = deal.id;
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
                    if (deal.id === this.plan.deal){
                        return this.plan.price = deal.price;
                    }
                }
            }
        },
        putVehicle:function(vehicle){
            this.plan.vehicle = vehicle;

            if (vehicle.trailer && vehicle.trailer.id > 0){
                this.putTrailer(vehicle.trailer);
            }
        },
        putTrailer:function(trailer){
            this.plan.trailer = trailer;
        },
        putTransporter:function(transporter){
            this.plan.transporter = transporter;
        },
        putDriver:function(driver){
            this.plan.driver = driver;
            if (driver.vehicle && this.plan.vehicle.id == -1){
                this.putVehicle(driver.vehicle);
            }
            if (driver.organisation && this.plan.transporter.id == -1){
                this.putTransporter(driver.organisation);
            }
        },
        save:function(){
            if (!this.already) {

                if (this.note.edit) {
                    this.saveNote();
                }
                var e = this.errors;
                e.type = this.plan.type == -1;
                e.organisation = this.plan.organisation.id == -1;
                e.product = this.plan.product == -1;
                e.manager = this.plan.manager == -1;
                var plan = Object.assign({}, this.plan);
                if (!plan.quantity){
                    plan.quantity = 0;
                }
                if (!plan.price){
                    plan.price = 0;
                }
                plan.organisation = this.plan.organisation.id;
                plan.vehicle = plan.vehicle.id;
                plan.trailer = plan.trailer.id;
                plan.driver = plan.driver.id;
                plan.transporter = plan.transporter.id;

                for(var i in plan.notes){
                    if (plan.notes.hasOwnProperty(i)){
                        var note = plan.notes[i];
                        delete note.creator;
                    }
                }

                if (!e.type && !e.organisation && !e.product) {
                    PostApi(this.api.save, plan, function (a) {
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