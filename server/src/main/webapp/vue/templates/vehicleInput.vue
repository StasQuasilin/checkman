var vehicleInput = {
    props:{props:Object, vehicle:Object, trailer:Object},
    data:function(){
        return{
            input:'',
            error:false,
            foundVehicles:[],
            timer:-1,
            open:false
        }
    },
    mounted:function(){
        let props = this.props;
        if (!props.add){
            console.warn('Title \'add\' required');
        }
        if (!props.header){
            console.warn('Title \'header\' required');
        }
        if(!props.find){
            console.warn('String \'find\' required');
        }
        if (!props.put){
            console.warn('Method \'put\' required');
        }
    },
    methods:{
        findVehicle:function(){
            if (this.props.find) {
                clearTimeout(this.timer);
                if (this.input) {
                    const self = this;
                    this.timer = setTimeout(function () {
                        PostApi(self.props.find, {key: self.input}, function (a) {
                            self.foundVehicles = a;
                        })
                    }, 300);
                } else {
                    this.foundVehicles = [];
                }
            }
        },
        putVehicle:function(vehicle){
            this.props.put(vehicle);
            this.foundVehicles = [];
            if (vehicle.trailer && this.props.trailerProps){
                this.props.trailerProps.put(vehicle.trailer);
            }
        },
        openVehicleInput:function(){
            this.open = true;
        },
        closeVehicle:function(){
            this.props.put({});
        },
        edit:function(){
            if (this.props.edit){
                const self = this;
                loadModal(this.props.edit, {id:this.vehicle.id}, function(a){
                    self.props.put(a);
                })
            }

        }
    },
    template:
        '<span v-if="vehicle.id" class="vehicle-block">' +
            '<a v-on:click="edit">{{vehicle.model}} {{vehicle.number}}</a>' +
            '<span class="mini-close" v-on:click="closeVehicle()">' +
                '&times;' +
            '</span>' +
        '</span>' +
        '<div v-else style="display: inline-block">'+
            '<div v-if="open" style="display: inline-block">' +
                '<input v-model="input" autocomplete="off" ' +
                    'v-on:keyup="findVehicle()" ' +
                    ':class="{error : error}" v-on:click="error = false"' +
                    'style=" width: 100%; border: none">' +
                '<div class="custom-data-list" v-if="foundVehicles.length > 0 || input">' +
                    '<div v-for="vehicle in foundVehicles"' +
                        'class="custom-data-list-item" ' +
                            'v-on:click="putVehicle(vehicle)">' +
                                '<span>' +
                                    '{{vehicle.model}} ' +
                                    '\'{{vehicle.number}}\' ' +
                                '</span>' +
                                '<span v-if="vehicle.trailer">' +
                                    '\'{{vehicle.trailer.number}}\'' +
                                '</span>' +
                    '</div>' +
                    '<div class="custom-data-list-item">' +
                        '<b>' +
                            '+ {{props.add}}' +
                        '</b>' +
                    '</div>' +
                '</div>' +
            '</div>' +
            '<a v-else style="font-size: 10pt" v-on:click="openVehicleInput()">' +
                '{{props.header}}' +
            '</a>' +
        '</div>'

};