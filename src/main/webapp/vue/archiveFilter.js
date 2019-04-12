var filter_control = new Vue({
    el: '#filter',
    data:{
        filter:{
            date:'',
            product:-1,
            organisation:-1,
            driver:-1,
            vehicle:-1
        },
        input:{
            date:new Date().toISOString().substring(0, 10),
            organisation:'',
            driver:'',
            vehicle:'',
        },
        items:{
            products:[],
            organisations:[],
            drivers:[],
            vehicles:[]
        }
    },
    computed:{
        filterDate:function(){
            return this.filter.date ? new Date(this.filter.date).toLocaleDateString() : '';
        }
    },
    methods:{
        clear:function(){
            this.filter.date = '';
            this.filter.product = -1;
            this.closeOrganisation();
            this.filter.driver = -1;
            this.filter.vehicle = -1;
        },
        clearItems:function(){
            this.items.organisations = [];
            this.items.drivers = [];
            this.items.vehicles = [];
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.filter.date = self.input.date = date
            }, self.input.date)
        },
        findOrganisation:function(){

        },
        putOrganisation:function(organisation){
            this.filter.organisation = organisation.id;
            this.input.organisation = organisation.value;
        },
        closeOrganisation:function(){
            this.filter.organisation = -1;
            this.input.organisation = '';
        },
        findDriver:function(){

        },
        putDriver:function(driver){
            this.filter.driver = driver.id;
            this.input.driver = driver.person.value;
        },
        findVehicle:function(){

        },
        putVehicle:function(vehicle){
            this.filter.vehicle = vehicle.id;
            this.input.vehicle = vehicle.model + ' \'' + vehicle.number + '\''
        }
    }
});