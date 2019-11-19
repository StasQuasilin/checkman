var editor = new Vue({
    el: '#personEditor',
    components:{
        'vehicle-input': vehicleInput
    },
    data:{
        api:{
            save:''
        },
        person:{
            id:-1,
            forename:'',
            surname:'',
            patronymic:'',
            phones:[]
        },
        license:'',
        transportationId:'',
        transporter:-1,
        vehicle:{},
        trailer:{},
        input:{
            transporter:'',
            vehicle:''
        },
        arr:{
            organisations:[]
        },
        errors:{
            vehicle:false
        },
        fnd:-1,
        vehicleInput:false,
        foundVehicles:[],
        trailerInput:false,
        m:false
    },
    mounted:function(){
        this.m=true;
    },
    methods:{
        closeVehicle:function(){
            this.vehicle = {}
        },
        closeTrailer:function(){
            this.trailer = {};
        },
        openVehicleInput:function(){
            this.vehicleInput = true;
            this.trailerInput = false;
        },
        openTrailerInput:function(){
            this.vehicleInput = false;
            this.trailerInput = true;
        },
        putVehicle:function(vehicle){
            this.vehicle = vehicle;
        },
        putTrailer:function(trailer){
            this.trailer = trailer;
        },
        findTrailer:function(){

        },
        findOrganisation:function(){
            if (this.api.find){
                clearTimeout(this.fnd);
                this.arr.organisations=[];
                if (this.input.transporter) {
                    const self = this;
                    this.fnd = setTimeout(function () {
                        PostApi(self.api.find, {key: self.input.transporter}, function (a) {
                            self.arr.organisations = a;
                        })
                    }, 300)
                }
            }
        },
        parseOrganisation:function(){
            if (this.api.parse){
                const self = this;
                PostApi(this.api.parse, {name : this.input.transporter}, function(a){
                   self.setOrganisation(a);
                })
            }
        },
        setOrganisation:function(organisation){
            this.transporter = organisation.id;
            this.input.transporter = organisation.value;
            this.arr.organisations =[];
        },
        cancelOrganisation:function(){
            this.transporter = -1;
            this.input.transporter = '';
        },
        addPhone:function(phone){
            this.person.phones.push(phone)
        },
        removePhone:function(id){
            this.person.phones.splice(id, 1);
        },
        save:function(){
            var result = {};
            if (this.transportationId){
                result.transportation_id = this.transportationId
            }
            if (this.person.id > 0) {
                result.id = this.person.id;
            }
            result.forename = this.person.forename;
            result.surname = this.person.surname;
            result.patronymic = this.person.patronymic;
            result.license = this.license;

            if (this.transporter != -1){
                result.transporter = this.transporter;
            }
            if (this.vehicle.id){
                result.vehicle = this.vehicle.id;
            }
            if (this.trailer.id){
                result.trailer = this.trailer.id;
            }

            PostApi(this.api.save, result, function(a){
                saveModal(a);
                closeModal();
            })
        },
        close:function(){
            closeModal();
        }
    }

});