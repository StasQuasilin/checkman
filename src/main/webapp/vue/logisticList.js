var logistic = new Vue({
    el:'#logistic',
    data:{
        findVehicleAPI:'',
        findDriverAPI:'',
        parseVehicleAPI:'',
        parseDriverAPI:'',
        vehicleFind:{},
        driverFind:{},
        vehicleInput:'',
        driverInput:'',
        vehicleDriverInput:'',
        update_link:'',
        save_link:'',
        saveTransportationVehicleApi:'',
        saveTransportationDriverApi:'',
        items:[],
        types:{},
        timeout:-1
    },
    methods:{
        add:function(item){
            var data = {};
            data.className = 'container-item-' + new Date(item.date).getDay();
            data.vehicleEdit = false;
            data.driverEdit = false;
            data.vehicleInput = '';
            data.driverInput='';
            data.fnd=-1;
            data.item = item;
            this.items.push(data);
        },
        update:function(item){
            for (var i in this.items){
                if (this.items[i].item.id == item.id){
                    this.items[i].className = 'container-item-' + new Date(item.date).getDay()
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
        setUrls:function(update, save){
            this.update_link = update
            this.save_link = save
            this.loadItems()
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
            PostApi(this.update_link, parameters, function(e){
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
                        return new Date(a.item.date) - new Date(b.item.date);
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
                PostApi(self.findDriverAPI, p, function(a){
                    self.driverFind = a;
                })
            }, 500)
        },
        findVehicle:function(value){
            const self = this;
            clearTimeout(value.fnd);
            value.fnd = setTimeout(function(){
                var p = {};
                p.key = value.vehicleInput;
                PostApi(self.findVehicleAPI, p, function(a){
                    self.vehicleFind = a;
                })
            }, 500)
        },
        setDriver:function(transportation, driver, key){
            this.driverFind = {};
            this.closeDriverInput(key);
            var p = {};
            p.transportation_id = transportation;
            p.driver_id = driver;
            PostApi(this.saveTransportationDriverApi, p, function(a){
                console.log(a)
            })
        },
        setVehicle:function(transportation, vehicle, key){
            this.vehicleFind = {};
            this.closeVehicleInput(key);
            var p = {};
            p.transportation_id = transportation;
            p.vehicle_id = vehicle;
            PostApi(this.saveTransportationVehicleApi, p, function(a){
                console.log(a)
            })
        },
        parseVehicle:function(value){
            var p = {};
            p.transportation_id = value.item.transportation.id;
            p.key = value.vehicleInput;
            loadModal(this.vehicleInput, p);
        },
        parseDriver:function(value){
            var p = {};
            p.transportation_id = value.item.transportation.id;
            p.key = value.driverInput;
            loadModal(this.driverInput, p);
        }
    }
});