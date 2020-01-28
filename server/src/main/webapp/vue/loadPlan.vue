var plan = new Vue({
    el: '#load_plan',
    components:{
        'object-input':objectInput
    },
    data: {
        api: {
            save: '',
            remove:'',
            findVehicle: '',
            parseVehicle:'',
            findDriver: '',
            parseDriver:'',
            editVehicle: '',
            editDriver: ''
        },
        deal:0,
        dateFrom:'',
        dateTo:'',
        quantity:0,
        unit:'',
        customers:[],
        plans:[],
        foundVehicles:[],
        foundDrivers:[],
        fnd:-1,
        upd:-1,
        picker:false,
        worker:{},
        filterDate:-1
    },
    methods:{
        handler:function(a){

        },
        messages:function(){
            var msgs = [];
            var total = this.totalPlan();
            if (total > this.quantity) {
                msgs.push('Объем по сделке будет увеличен на ' + (total - this.quantity).toLocaleString() + '&nbsp;' + this.unit + '!');
            }
            return msgs;
        },
        newCarriage:function(){


        },
        newVehicle:function(){
            var date = this.filterDate == -1 ? new Date() : new Date(this.filterDate);

            this.add({
                id:-randomNumber(),
                date: date.toISOString().substring(0, 10),
                plan:0,
                customer:this.customers[0].id,
                transportation:{
                    vehicle:{
                        id:-1
                    },
                    driver:{
                        id:-1
                    },
                    notes:[],
                    weight:{}
                }
            })
        },
        itemsByDate:function(date){
            var result = {
                count:0,
                weight:0
            };
            var items = this.plans.filter(function(item){
                return item.item.date == date;
            });
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    result.count++;
                    result.weight+=parseInt(items[i].item.plan);
                }
            }
            return result;
        },
        dates:function(){
            var dates = [];
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var date = this.plans[i].item.date;
                    if(!dates.includes(date)){
                        dates.push(date);
                    }
                }
            }
            dates.sort(function(a, b){
                return new Date(b) - new Date(a);
            });
            return dates;
        },
        getPlans:function(){
            if (this.filterDate == -1){
                return this.plans;
            } else {
                const self = this;
                return this.plans.filter(function(item){
                    return item.item.date === self.filterDate;
                })
            }
        },
        selectDate:function(date){
            if (this.filterDate === date){
                this.filterDate = -1;
            } else{
                this.filterDate = date;
            }
        },
        copy:function(idx){
            let p = Object.assign({}, this.plans[idx].item);
            p.id=-1;
            let plan = this.add(p);

            this.initSaveTimer(plan.key);
        },
        add:function(plan){
            let p = {
                key : randomUUID(),
                editVehicle:false,
                editDriver:false,
                editNote:false,
                vehicleInput:'',
                driverInput : '',
                noteInput:'',
                item : plan,
                removed:false,
                saveTimer:-1
            };
            this.plans.push(p);
            this.sort();
            return p;
        },
        initSaveTimer:function(item){
            const self = this;
            clearTimeout(item.saveTimer);
            item.saveTimer = setTimeout(function(){
                self.save(item.item);
            }, 1500);
        },
        update:function(plan){
            var found = false;
            for (var p in this.plans){
                if (this.plans.hasOwnProperty(p)){
                    if (!this.plans[p].item.id){
                        this.plans.splice(p, 1);
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                this.add(plan);
            }
        },
        remove:function(id){
            const _id = id;
            var plan = this.plans[id].item;
            if (plan.id > 0){
                console.log('remove ' + plan.id);
                const self = this;
                PostApi(this.api.remove, {id:plan.id},function(a){
                    if (a.status === 'success'){
                        self.plans.splice(_id, 1);
                    }
                })
            } else {
                this.plans.splice(id, 1);
            }
        },
        save:function(item){
            var plan = Object.assign({}, item);
            delete plan.transportation;
            plan.driver = item.transportation.driver.id;
            plan.vehicle = item.transportation.vehicle.id;
            var transporter = -1;
            if(item.transportation.transporter){
                transporter = item.transportation.transporter.id;
            }
            plan.transporter = transporter;
            plan.notes = Object.assign([], item.transportation.notes);

            PostApi(this.api.save, {deal : this.deal, plan : plan},function(a){
                if (a.status === 'success'){
                    if(a.id) {
                        plan.id = a.id;
                    }
                }
            });
        },
        focusInput:function(){
            setTimeout(function(){
                var inp = document.getElementById('input');
                if (inp){
                    inp.focus();
                }else {
                    console.log('input not found')
                }
            }, 500)
        },
        setDriver:function(driver, item){
            item.item.transportation.driver = driver;
            if(driver.vehicle && item.item.transportation.vehicle.id == -1){
                this.setVehicle(driver.vehicle, item);
            }else{
                this.initSaveTimer(item);
            }
            if (driver.organisation && !item.item.transportation.transporter || item.item.transportation.transporter.id == -1){
                this.setTransporter(driver.organisation);
            }
        },
        setVehicle:function(vehicle, item){
            item.item.transportation.vehicle = vehicle;
            if (vehicle.trailer && item.item.transportation.trailer.id == -1){
                this.setTrailer(vehicle.trailer, item);
            } else {
                this.initSaveTimer(item);
            }
        },
        setTrailer:function(trailer, item){
            item.item.transportation.trailer = trailer;
            this.initSaveTimer(item);
        },
        setTransporter:function(transporter, item){
            item.item.transportation.transporter = transporter;
            this.initSaveTimer(item);
        },
        weight:function(item){
            if (item.weight == 'undefined') {
                console.log('calculate weight');
                var w = {
                    brutto:0,
                    tara:0,
                    netto:function(){
                        if(brutto > 0 && tara > 0) {
                            return brutto - tara;
                        } else {
                            return 0;
                        }
                    }
                };
                for (var i in item.transportation.weights) {
                    if (item.transportation.weights.hasOwnProperty(i)){
                        var weight = item.transportation.weights[i];
                        w.brutto += weight.brutto;
                        w.tara += weight.tara;
                    }
                }
                item.weight = w;
            }

            return item.weight;
        },
        totalPlan:function(){
            var total = 0;
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    var p = parseFloat(this.plans[i].item.plan);
                    if (!isNaN(p)){
                        total += p;
                    }
                }
            }
            return total;
        },
        totalFact:function(){
            var total = 0;
            for (var p in this.plans){
                if (this.plans.hasOwnProperty(p)){
                    var plan = this.plans[p];
                    for(var w in plan.item.transportation.weights){
                        if (plan.item.transportation.weights.hasOwnProperty(w)){
                            var weight = plan.item.transportation.weights[w];
                            total += weight.brutto == 0 || weight.tara == 0 ? 0 : weight.brutto - weight.tara;
                        }
                    }
                }
            }
            return total;
        },
        stop:function(){
            console.log('Stop load plan');
            clearTimeout(this.upd);
        },
        dateTimePicker:function(item){
            const self = this;
            datepicker.show(function (date) {
                item.item.date = date;
                self.sort();
                self.initSaveTimer(item);
            }, item.item.date)
        },
        sort:function(){
            this.plans.sort(function(a, b){
                return new Date(b.item.date) - new Date(a.item.date)
            })
        },
        different:function(w, plan){
            var netto = w.netto;
            if (netto > 0  && netto !== plan){
                var d = netto - plan;
                if (d > 0) {
                    d = '+' + (d).toLocaleString();
                } else {
                    d = (d).toLocaleString();
                }
                return '(' + d + ')';
            }
        },
        addNote:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    this.plans[i].editNote = this.plans[i].key === key;
                }
            }
            this.focusInput();
        },
        editNote:function(key, id){
            console.log('editNote');
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    let plan = this.plans[i];

                    if (plan.key === key){
                        console.log(plan);
                        plan.editNote = true;
                        plan.noteInput = plan.item.transportation.notes[id].note
                        this.focusInput();
                        this.removeNote(i, id);
                    } else {
                        plan.editNote = false;
                    }

                }
            }
        },
        saveNote:function(item){
            if (item.noteInput) {
                item.item.transportation.notes.push({
                    id: -randomNumber(),
                    creator: this.worker,
                    note: item.noteInput
                });
                item.noteInput = '';
                item.editNote = false;
                this.initSaveTimer(item);
            }
        },
        closeNote:function(key){
            for (var i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    this.plans[i].editNote = false;
                }
            }
        },
        removeNote:function(item, id){
            item.item.transportation.notes.splice(id, 1);
            this.initSaveTimer(item);
        }

    }
});