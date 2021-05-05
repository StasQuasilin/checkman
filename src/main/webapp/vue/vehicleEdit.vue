var editor = new Vue({
    el: '#vehicleEditor',
    data:{
        api:{
            findOrganisation:'',
            saveVehicleAPI:''
        },
        transportationId:'',
        vehicleId:'',
        vehicleModel:'',
        vehicleNumber:'',
        vehicleTrailer:'',
        transporterId:'',
        transporterInput:'',
        findTransporters:{}
    }, methods:{
        findOrganisation:function(input){
            const self = this;
            var p = [];
            p.key = input;
            PostApi(this.api.findOrganisation, p, function(a){
                self.findTransporters = a;
            })
        },
        setOrganisation:function(id, value){
            this.transporterId = id;
            this.transporterInput = value;
        },
        save:function(){
            let result = {};
            if (this.transportationId){
                result.transportation_id = this.transportationId
            }
            if (this.vehicleId){
                result.vehicle_id = this.vehicleId;
            }
            result.model = this.vehicleModel;
            result.number = this.vehicleNumber;
            result.trailer = this.vehicleTrailer;

            if (this.transporterId){
                result.transporter_id = this.transporterId;
            }

            PostApi(this.api.saveVehicleAPI, result, function(a){
                console.log(a);
                saveModal(a);
            })

        },
        close:function(){
            closeModal();
        }
    }
});