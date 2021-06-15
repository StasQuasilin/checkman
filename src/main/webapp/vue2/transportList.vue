transportList = new Vue({
    el:'#transportList',
    mixins:[bl, contextMenuView, transportationSaver],
    components:{
        'transport-view': transportView
    },
    data:{
        labels:{},
        props:{
            customers:[],
            save:function(item){
                transportList.saveTransportation(item);
            },
            driverProps:{
                put:function(driver, item){
                    item.driver = driver;
                    transportList.saveTransportation(item);
                },
                edit:'',
                show:['value']
            },
            vehicleProps:{
                put:function(vehicle, item){
                    item.vehicle = vehicle;
                    transportList.saveTransportation(item);
                },
                show:['model', 'number']
            },
            trailerProps:{
                put:function(trailer, item){
                    item.trailer = trailer;
                    transportList.saveTransportation(item);
                },
                show:['number']
            },
            transporterProps:{
                put:function(transporter, item){
                    item.transporter = transporter;
                    transportList.saveTransportation(item);
                },
                show:['value']
            }
        },
        f:{
            p:false,
            a:false
        }
    },
    mounted:function () {
        this.filter = transportFilter;
    },
    methods:{
        getF:function(){
            return Object.assign({}, this.f);
        },
        sort:function (a, b) {
            if (a.date === b.date) {
                let aState = this.calculateState(a.timeIn, a.timeOut);
                let bState = this.calculateState(b.timeIn, b.timeOut);
                let aG = a.gross;
                let aT = a.tare;
                let bG = b.gross;
                let bT = b.tare;

                let aN = (aG > 0 && aT === 0) || (aG === 0 && aT > 0);
                if (aState > 1 && aN){
                    aState = 0;
                }
                let bN = (bG > 0 && bT === 0) || (bG === 0 && bT > 0);
                if(bState > 1 && bN){
                    bState = 0;
                }

                let sort =  aState - bState;
                if (sort === 0){
                    let aDriver = a.driver;
                    let bDriver = b.driver;
                    if (aDriver && bDriver){
                        sort = aDriver.person.value.localeCompare(bDriver.person.value);
                    } else if (aDriver){
                        sort = -1;
                    } else {
                        sort = 1;
                    }
                }
                return sort;
            }
            return new Date(a.date) - new Date(b.date);
        },
        calculateState:function (timeIn, timeOut) {
            if (timeIn && !timeOut){
                return 0;
            } else if (!timeIn && timeOut){
                return 1;
            } else if (!timeIn && !timeOut){
                return 2;
            } else {
                return 3;
            }
        },
        show:function (itemId) {
            if (this.api.show) {
                loadModal(this.api.show, {id: itemId});
            }
        }
    }
});