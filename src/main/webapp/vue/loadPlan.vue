plan = new Vue({
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
        deal:null,
        selectedProduct:-1,
        dealId:0,
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
        filterDate:-1,
        tab:'vehicles',
        tabs:[
            'vehicles'
        ],
        tabNames:{}
    },
    methods:{
        handler:function(a){

        },
        messages:function(){
            const msgs = [];
            let total = this.totalPlan();
            if (total > this.quantity) {
                msgs.push('Объем по сделке будет увеличен на ' + (total - this.quantity).toLocaleString() + '&nbsp;' + this.unit + '!');
            }
            return msgs;
        },
        newCarriage:function(){

        },
        newVehicle:function(){
            let date = this.filterDate === -1 ? new Date() : new Date(this.filterDate);
            this.add({
                id:-randomNumber(),
                date: date.toISOString().substring(0, 10),
                plan:0,
                customer:this.customers[0].id
            })
        },
        itemsByDate:function(date){
            let result = {
                count:0,
                weight:0
            };
            let items = this.plans.filter(function(item){
                return item.item.date === date;
            });
            for (let i in items){
                if (items.hasOwnProperty(i)){
                    result.count++;
                    result.weight+=parseInt(items[i].item.plan);
                }
            }
            return result;
        },
        dates:function(){
            let dates = [];
            for (let i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    let date = this.plans[i].item.date;
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
            if (this.filterDate === -1){
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
            console.log(p);
            p.id=-1;
            p.timeIn = null;
            p.timeOut = null;
            p.weight = null;
            p.sunAnalyses = null;
            p.oilAnalyses = null;
            p.mealAnalyses = null;
            p.archive = false;
            p.done = false;
            this.initSaveTimer(this.add(p));
        },
        add:function(plan){
            if (!plan.vehicle){
                plan.vehicle = {id:-1}
            }
            if (!plan.trailer){
                plan.trailer = {id:-1}
            }
            if (!plan.transporter){
                plan.transporter = {id:-1}
            }
            let p = {
                key : randomUUID(),
                editNote:false,
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
            console.log(item);
            const self = this;
            if (item.saveTimer !== -1){
                clearTimeout(item.saveTimer);
            }

            item.saveTimer = setTimeout(function(){
                item.saveTimer = -1;
                self.save(item.item);
            }, 1500);
        },
        update:function(plan){
            let found = false;
            for (let p in this.plans){
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
            let plan = this.plans[id].item;
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
            console.log(item);
            if (item) {
                let plan = Object.assign({}, item);
                if (item.driver) {
                    plan.driver = item.driver.id;
                }
                if (item.vehicle) {
                    plan.vehicle = item.vehicle.id;
                }
                if (item.trailer) {
                    plan.trailer = item.trailer.id;
                }
                let transporter = -1;
                if (item.transporter) {
                    transporter = item.transporter.id;
                }
                plan.transporter = transporter;
                plan.notes = Object.assign([], item.notes);
                for (let i in plan.notes) {
                    if (plan.notes.hasOwnProperty(i)) {
                        delete plan.notes[i].creator;
                    }
                }

                PostApi(this.api.save, {deal: this.dealId, product:this.selectedProduct, plan: plan}, function (a) {
                    if (a.status === 'success') {
                        if (a.id) {
                            item.id = a.id;
                        }
                    }
                });
            }
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
            item.item.driver = driver;
            if(driver.vehicle && item.item.vehicle.id === -1){
                this.setVehicle(driver.vehicle, item);
            }else{
                this.initSaveTimer(item);
            }
            if (driver.organisation && !item.item.transporter || item.item.transporter.id === -1){
                this.setTransporter(driver.organisation, item);
            }
        },
        setVehicle:function(vehicle, item){
            item.item.vehicle = vehicle;
            if (vehicle.trailer && item.item.trailer.id === -1){
                this.setTrailer(vehicle.trailer, item);
            } else {
                this.initSaveTimer(item);
            }
        },
        setTrailer:function(trailer, item){
            item.item.trailer = trailer;
            this.initSaveTimer(item);
        },
        setTransporter:function(transporter, item){
            item.item.transporter = transporter;
            this.initSaveTimer(item);
        },
        weight:function(item){
            if (item.weight === 'undefined') {
                console.log('calculate weight');
                let w = {
                    brutto: 0,
                    tara: 0,
                    netto: function () {
                        if (brutto > 0 && tara > 0) {
                            return brutto - tara;
                        } else {
                            return 0;
                        }
                    }
                };
                let weight = item.weight[i];
                w.brutto += weight.brutto;
                w.tara += weight.tara;
                item.weight = w;
            }

            return item.weight;
        },
        totalPlan:function(){
            let total = 0;
            for (let i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    let p = parseFloat(this.plans[i].item.plan);
                    if (!isNaN(p)){
                        total += p;
                    }
                }
            }
            return total;
        },
        totalFact:function(){
            let total = 0;
            for (let p in this.plans){
                if (this.plans.hasOwnProperty(p)){
                    let plan = this.plans[p];
                    let weight = plan.item.weight;
                    if (weight) {
                        total += weight.brutto === 0 || weight.tara === 0 ? 0 : weight.brutto - weight.tara;
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
            let net = w.netto;
            if (net > 0  && net !== plan){
                let d = net - plan;
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
        editNote:function(item, id){
            item.editNote = true;
            if (id !== undefined) {
                item.noteId = item.item.notes[id].id;
                item.noteInput = item.item.notes[id].note;
                item.item.notes.splice(id, 1);
            }
            this.focusInput();
        },
        saveNote:function(item){
            if (item.noteInput) {
                item.item.notes.push({
                    id: item.noteId,
                    creator: this.worker,
                    note: item.noteInput
                });
                item.noteInput = '';
                item.editNote = false;
                this.initSaveTimer(item);
            }
        },
        closeNote:function(key){
            for (let i in this.plans){
                if (this.plans.hasOwnProperty(i)){
                    this.plans[i].editNote = false;
                }
            }
        },
        removeNote:function(item, id){
            item.item.notes.splice(id, 1);
            this.initSaveTimer(item);
        }

    }
});