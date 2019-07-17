var filter_control = new Vue({
    el: '#filter_view',
    data:{
        filters:{
            types:[]
        },
        items:[],
        type:-1,
        organisation:-1,
        product:-1,
        date:-1,
        vehicle:-1,
        driver:-1
    },
    methods:{
        organisations:function(){
            var organisations = {};
            var items = this.items;
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    organisations[organisation.id] = items[i].item.organisation;
                }
            }
            return organisations;
        },
        products:function() {
            var products = {};
            var items = this.items;
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    var product = items[i].item.product;
                    if (products[product.id] == undefined) {
                        products[product.id] = product;
                    }
                }
            }
            return products;
        },
        dates:function(){
            var dates = {};
            var items = this.items;
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    var date = items[i].item.date;
                    if (dates[date] == undefined) {
                        dates[date] = date;
                    }
                }
            }
            return dates;
        },
        vehicles:function(){
            var vehicles = {};
            var items = this.items;
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    var vehicle = items[i].item.transportation.vehicle;
                    if (vehicle.id != undefined && vehicles[vehicle.id] == undefined){
                        vehicles[vehicle.id] = vehicle;
                    }
                }
            }
            return vehicles;
        },
        drivers:function(){
            var drivers = {};
            var items = this.items;
            for (var i in items){
                if (items.hasOwnProperty(i)){
                    var driver = items[i].item.transportation.driver;
                    if (driver.id != undefined && drivers[driver.id] == undefined){
                        drivers[driver.id] = driver;
                    }
                }
            }
            return drivers;
        },
        filteredItems:function(){
            const self = this;
            return this.items.filter(function(item){
                var byVehicle = self.vehicle == -1 ||
                    (self.vehicle == 0 && item.item.transportation.vehicle.id == undefined) ||
                    (item.item.transportation.vehicle.id == self.vehicle);
                var byDriver = self.driver == -1 ||
                    (self.driver == 0 && item.item.transportation.driver.id == undefined) ||
                    (item.item.transportation.driver.id == self.driver);
                return (self.type == -1 || item.item.type == self.type) &
                    (self.organisation == -1 || item.item.organisation.id == self.organisation) &
                    (self.product == -1 || item.item.product.id == self.product) &
                    (self.date == -1 || item.item.date === self.date) & byVehicle & byDriver;
            })
        },
        clear:function(){
            this.type = -1;
            this.product = -1;
            this.organisation = -1;
            this.date = -1;
            this.vehicle = -1;
            this.driver = -1;
        }
    }
});