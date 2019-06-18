var logistic = new Vue({
    el:'#logistic',
    data:{
        api:{
            findVehicleAPI:'',
            findDriverAPI:'',
            parseVehicleAPI:'',
            parseDriverAPI:'',
            vehicleInput:'',
            driverInput:'',
            update:'',
            save:'',
            saveTransportationVehicleApi:'',
            saveTransportationDriverApi:'',
            changeDate:''
        },
        vehicleFind:{},
        driverFind:{},
        items:[],
        types:{},
        timeout:-1,
        filter:filter_control,
        menu:{
            show:false,
            title:'',
            id:-1,
            filed:'',
            x:0,
            y:0
        }
    },
    mounted:function(){
        this.filter.items = this.items;
    },
    computed:{

    },
    methods:{
        changeDate:function(key, days){
            var item = this.items[key].item;
            var date = new Date(item.date);
            date.setDate(date.getDate() + days);
            item.date = date.toISOString().substring(0, 10);
            PostApi(this.api.changeDate, {id: item.id, date: date.toISOString().substring(0, 10)})
        },
        filteredItems:function(){
            if (typeof this.filter.filteredItems === 'function') {
                return this.filter.filteredItems();
            } else {
                return this.items;
            }
        },
        rowName:function(date){
            return 'container-item-' + new Date(date).getDay();
        },
        add:function(item){
            this.items.push({
                vehicleEdit:false,
                driverEdit:false,
                vehicleInput:'',
                driverInput:'',
                fnd:-1,
                item:item
            });
        },
        update:function(item){
            for (var i in this.items){
                if (this.items[i].item.id == item.id){
                    this.items[i].item=item;
                    break;
                }
            }
        },
        remove:function(id){
            for(var i in this.items){
                if (this.items[i].item.id === id){
                    this.items.splice(i, 1)
                    break;
                }
            }
        },
        loadItems: function () {
            const self = this;
            var parameters = {};
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var item = this.items[i];
                    parameters[item.item.id] = item.item.hash;
                }

            }
            PostApi(this.api.update, parameters, function(e){
                if (e.add.length || e.update.length || e.remove.length) {
                    console.log(e)
                    for (var a in e.add){
                        self.add(e.add[a])
                    }
                    for (var u in e.update){
                        self.update(e.update[u])
                    }
                    for (var d in e.remove){
                        self.remove(e.remove[d])
                    }
                    self.items.sort(function(a, b){
                        return new Date(b.item.date) - new Date(a.item.date);
                    })
                }
            }, null, false)
            self.timeout = setTimeout(function(){self.loadItems()}, 1000);
        },
        addType:function(key, value){
            Vue.set(this.types, key, value)
        },
        stop:function(){
            console.log('Stop logistic list')
            clearTimeout(this.timeout)
        },
        openVehicleInput:function(id){
            for(var i in this.items){
                var item = this.items[i];
                item.vehicleEdit = item.item.id == id;
                item.driverEdit = false;
            }
        },
        closeVehicleInput:function(key){
            this.items[key].vehicleEdit=false
        },
        openDriverInput:function(id){
            this.driverFind={};
            for(var i in this.items){
                var item = this.items[i];
                item.driverEdit = item.item.id == id;
                item.vehicleEdit = false;
            }
        },
        closeDriverInput:function(key){
            this.driverFind={};
            this.items[key].driverInput = '';
            this.items[key].driverEdit = false;
            clearTimeout(this.items[key].fnd);
        },
        findDriver:function(value){
            const self = this;

            clearTimeout(value.fnd);
            value.fnd = setTimeout(function(){
                var p = {};
                p.key = value.driverInput;
                PostApi(self.api.findDriverAPI, p, function(a){
                    self.driverFind = a;
                })
            }, 500)
        },
        findVehicle:function(value){
            const self = this;
            clearTimeout(value.fnd);
            if (value.vehicleInput) {
                value.fnd = setTimeout(function () {
                    var p = {};
                    p.key = value.vehicleInput;
                    PostApi(self.api.findVehicleAPI, p, function (a) {
                        self.vehicleFind = a;
                    })
                }, 500)
            }
        },
        setDriver:function(transportation, driver, key){
            this.driverFind = {};
            this.closeDriverInput(key);
            var p = {};
            p.transportation_id = transportation;
            p.driver_id = driver;
            PostApi(this.api.saveTransportationDriverApi, p, function(a){
                console.log(a)
            })
        },
        setVehicle:function(transportation, vehicle, key){
            this.vehicleFind = {};
            this.closeVehicleInput(key);
            var p = {};
            p.transportation_id = transportation;
            p.vehicle_id = vehicle;
            PostApi(this.api.saveTransportationVehicleApi, p, function(a){
                console.log(a)
            })
        },
        parseVehicle:function(value){
            var p = {};
            p.transportation_id = value.item.transportation.id;
            if (value.item.transportation.vehicle.id){
                p.vehicle_id = value.item.transportation.vehicle.id;
            }
            if (value.vehicleInput) {
                p.key = value.vehicleInput;
            }
            loadModal(this.api.vehicleInput, p);
        },
        deleteVehicle:function(transportation){
            PostApi(this.api.saveTransportationVehicleApi, {transportation_id : transportation}, function(a){
                console.log(a)
            })
        },
        deleteDriver:function(transportation){
            PostApi(this.api.saveTransportationDriverApi, {transportation_id : transportation}, function(a){
                console.log(a)
            })
        },
        parseDriver:function(value){
            var p = {};
            p.transportation_id = value.item.transportation.id;
            if (value.item.transportation.driver.id){
                p.driver_id = value.item.transportation.driver.id;
            }
            if (value.driverInput) {
                p.key = value.driverInput;
            }
            loadModal(this.api.driverInput, p);
        },
        contextMenu:function(title, id, field){
            this.menu.x = event.pageX;
            this.menu.y = event.pageY;
            this.menu.show = true;
            this.menu.title = title;
            this.menu.id = id;
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
        }

    }
});