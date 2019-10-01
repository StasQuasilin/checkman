var filter_control = new Vue({
    el: '#filter',
    data:{
        api:{},
        filter:{
            date:'',
            product:-1,
            organisation:-1,
            driver:-1,
            vehicle:-1,
            any:function(){
                return date || product > 0;
            }
        },
        input:{
            date:new Date().toISOString().substring(0, 10),
            organisation:'',
            driver:'',
            vehicle:''
        },
        productList:[],
        items:[],
        result:null
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
            this.result = null;
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.filter.date = self.input.date = date
                self.find();
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
        },
        find:function(){
            console.log(this.filter);
            const self = this;
            PostApi(this.api.find, this.filter, function(a){
                console.log(a);
                self.result = [];
                for (var i in a){
                    if (a.hasOwnProperty(i)){
                        self.result.push({item:a[i]})
                    }
                }
            });
        },
        filteredItems:function(){
            if (this.result){
                return this.result;
            } else{
                return this.items;
            }
        }
    }
});