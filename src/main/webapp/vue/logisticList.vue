var list = new Vue({
    el:'#logistic',
    components:{
        'vehicle-input':objectInput
    },
    data:{
        api:{
            findVehicle:'',
            findDriver:'',
            parseVehicleAPI:'',
            parseDriverAPI:'',
            saveNote:'',
            removeNote:'',
            update:'',
            save:'',
            changeDate:''
        },
        foundVehicles:[],
        foundDrivers:[],
        input:'',
        items:[],
        types:{},
        timeout:-1,
        menu:{
            show:false,
            title:'',
            id:-1,
            filed:'',
            any:false,
            x:0,
            y:0
        },
        worker:{}
    },
    mounted:function(){
        if (typeof filterControl !== 'undefined'){
            filterControl.items = this.items;
            if (typeof filterControl.filteredItems === 'function') {
                this.getItems = function () {
                    return filterControl.filteredItems();
                }
            }
        }
    },
    methods:{
        sort:function(){
            this.items.sort(function(a, b){
                let ai = a.item.timeIn.time && !a.item.timeOut.time;
                let bi = b.item.timeIn.time && !b.item.timeOut.time;
                if (ai && !bi){
                    return -1;
                }

                if (!ai && bi){
                    return 1;
                }

                return new Date(a.item.date) - new Date(b.item.date);
            })
        },
        handler:function(e){
            const self = this;
            if (e.add){
                for(var a in e.add){
                    if (e.add.hasOwnProperty(a)) {
                        self.add(e.add[a])
                    }
                }
            }
            if (e.update) {
                for (var u in e.update) {
                    if (e.update.hasOwnProperty(u)) {
                        self.update(e.update[u]);
                    }
                }
            }
            if (e.remove){
                for(var r in e.remove){
                    if (e.remove.hasOwnProperty(r)) {
                        self.drop(e.remove[r])
                    }
                }
            }
            filterControl.checkFilter();
        },
        changeDate:function(key, days){
            var item = this.getItems()[key].item;
            var date = new Date(item.date);
            date.setDate(date.getDate() + days);
            item.date = date.toISOString().substring(0, 10);
            PostApi(this.api.changeDate, {id: item.id, date: date.toISOString().substring(0, 10)})
        },
        getItems:function(){
            return this.items;
        },
        add:function(item){
            this.items.push({
                editVehicle:false,
                editDriver:false,
                editNote:false,
                fnd:-1,
                item:item
            });
            this.sort();
        },
        update:function(item){
            var found = false;
            for (var i in this.items){
                if (this.items[i].item.id === item.id){
                    found = true;
                    this.items[i].item=item;
                    break;
                }
            }
            if (!found){
                this.add(item);
            } else {
                this.sort();
            }

        },
        remove:function(id){
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    if (this.items[i].item.id === id) {
                        this.items.splice(i, 1)
                        this.sort();
                        break;
                    }
                }
            }

        },
        openVehicleInput:function(id){
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    var item = this.items[i];
                    item.editVehicle = item.item.id == id;
                    item.editDriver = false;
                    item.editNote = false;
                }
            }
            this.input = '';
        },
        closeVehicleInput:function(key){
            var item = this.getItems()[key];
            item.editVehicle=false
            this.input = '';
            item.vehicleInput = '';
            this.foundVehicles = [];
            clearTimeout(item.fnd);
        },
        openDriverInput:function(id){
            this.foundDrivers=[];
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)) {
                    var item = this.items[i];
                    item.editDriver = item.item.id == id;
                    item.editVehicle = false;
                    item.editNote = false;
                }
            }
            this.input = '';
        },
        closeDriverInput:function(id){
            var item = this.getItems()[id];
            item.editDriver = false;
            this.foundDrivers = [];
            this.input = [];
            clearTimeout(item.fnd);
        },
        findDriver:function(value){
            const self = this;
            if (value.driverInput) {
                clearTimeout(value.fnd);
                value.fnd = setTimeout(function () {
                    PostApi(self.api.findDriver, {key : value.driverInput}, function (a) {
                        self.foundDrivers = a;
                    })
                }, 500)
            } else {
                this.foundDrivers = [];
            }
        },
        findVehicle:function(value){
            const self = this;
            if (value.vehicleInput) {
                clearTimeout(value.fnd);
                value.fnd = setTimeout(function () {
                    PostApi(self.api.findVehicle, {key : value.vehicleInput}, function (a) {
                        self.foundVehicles = a;
                    })
                }, 300)
            } else {
                this.foundVehicles = [];
            }
        },
        setDriver:function(id, driver, item){
            console.log(id+', ' + driver);
            item.driverInput = '';
            item.editDriver = false;
            this.foundDrivers = [];
            PostApi(this.api.saveDriver, {transportation : id, driver_id : driver}, function(a){
                console.log(a)

            })
        },
        setVehicle:function(transportation, vehicle, item){
            console.log(transportation+', ' + vehicle);
            item.vehicleInput = '';
            item.editVehicle = false;
            this.foundVehicles = [];
            PostApi(this.api.saveVehicle, {transportation : transportation, vehicle : vehicle}, function(a){
                console.log(a)
            })
        },
        parseDriver:function(value){
            if (value.driverInput){
                var p = {};
                p.transportation = value.item.id;
                p.key = value.driverInput;
                PostApi(this.api.parseDriver, p);
            }
        },
        parseVehicle:function(value) {
            if (value.vehicleInput) {
                console.log('parse ' + value.vehicleInput);
                var p = {};
                p.transportation = value.item.id;
                p.key = value.vehicleInput;
                PostApi(this.api.parseVehicle, p);
            }
        },
        editVehicle:function(value){
            if (value.item.vehicle.id){
                loadModal(this.api.editVehicle, {id: value.item.vehicle.id})
            }
        },
        editDriver:function(value){
            if (value.item.driver.id){
                loadModal(this.api.editDriver, {id: value.item.driver.id})
            }
        },
        deleteVehicle:function(transportation){
            PostApi(this.api.saveVehicle, {transportation : transportation}, function(a){
                console.log(a)
            })
        },
        deleteDriver:function(transportation){
            PostApi(this.api.saveDriver, {transportation : transportation}, function(a){
                console.log(a)
            })
        },
        contextMenu:function(item, field){
            this.menu.x = event.pageX;
            this.menu.y = event.pageY;
            this.menu.show = true;
            this.menu.id = item.id;
            this.menu.any = item.any;
            this.menu.field = field;
            event.preventDefault();
        },
        closeMenu:function(){
            this.menu.show = false;
        },
        newClick:function(id, field){
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var transpotation = this.items[i].transportation;
                    this.setDriver(id, null, null)
                }
            }
        },
        openNote:function(id, open){
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var item = this.items[i];
                    if (item.item.id == id){
                        item.editNote = open;
                        item.noteInput = '';
                    } else {
                        item.editNote = false;
                    }
                }
            }
        },
        saveNote:function(id){
            for (var i in this.items) {
                if (this.items.hasOwnProperty(i)) {
                    var item = this.items[i];
                    if (item.item.id == id){
                        PostApi(this.api.saveNote, {plan:item.item.id, note:item.noteInput}, function(){
                            item.editNote = false;
                            item.noteInput = '';
                        })

                        break;
                    }
                }
            }
        },
        removeNote:function(id){
            PostApi(this.api.removeNote, {id:id})
        },
    }
});