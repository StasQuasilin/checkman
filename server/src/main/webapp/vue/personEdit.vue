var editor = new Vue({
    el: '#personEditor',
    components:{
        'vehicle-input': objectInput
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
        m:false,
        phoneEdit:false,
        editablePhone:{
            id:-1,
            number:''
        }

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
            this.transporter = organisation;
        },
        cancelOrganisation:function(){
            this.transporter = -1;
            this.input.transporter = '';
        },
        addPhone:function(){
            this.phoneEdit = !this.phoneEdit;
            if (!this.phoneEdit){
                this.editablePhone = {
                    id:-1,
                    number:''
                }
            }
        },
        savePhone:function(){
            const self = this;
            this.editablePhone.person = this.person.id;
            PostApi(this.api.editPhone, this.editablePhone, function(a){
                let data = a.result;
                let found = false;
                for (let i in self.person.phones){
                    if (self.person.phones.hasOwnProperty(i)){
                        let phone = self.person.phones[i];
                        if (phone.id == data.id){
                            found = true;
                            self.person.phones.splice(i, 1, data);

                            break;
                        }
                    }
                }
                if (!found){
                    self.person.phones.push(data);
                }
                self.addPhone();
            });
        },
        removePhone:function(id){
            let phone = this.person.phones[id];
            let phones = this.person.phones;
            PostApi(this.api.removePhone, phone, function(a){
                if (a.status === 'success'){
                    phones.splice(id, 1);
                }
            });

        },
        save:function(){
            var result = {};
            if (this.transportationId){
                result.transportation_id = this.transportationId
            }
            if (this.id > 0) {
                result.id = this.id;
            }
            result.forename = this.person.forename;
            result.surname = this.person.surname;
            result.patronymic = this.person.patronymic;
            result.license = this.license;

            if (this.transporter.id){
                result.transporter = this.transporter.id;
            }
            if (this.vehicle.id){
                result.vehicle = this.vehicle.id;
            }
            if (this.trailer.id){
                result.trailer = this.trailer.id;
            }

            PostApi(this.api.save, result, function(a){
                console.log(a);
                saveModal(a);
                closeModal();
            })
        },
        close:function(){
            closeModal();
        }

    }

});