var edit = new Vue({
    components:{
      'object-input':objectInput
    },
    el:'#editor',
    data:{
        api:{},
        products:[],
        actions:{},
        managers:[],
        deals:[],
        transportation:{
            id:-1,
            date:new Date().toISOString().substring(0, 10),
            counterparty:{id:-1},
            deal:-1,
            product:-1,
            action:'',
            driver:{id:-1},
            vehicle:{id:-1},
            trailer:{id:-1},
            transporter:{id:-1},
            manager:-1
        },
        organisationProps:{},
        driverProps:{},
        vehicleProps:{},
        trailerProps:{},
        transporterProps:{}
    },
    methods:{
        selectDate:function(){
            const self = this;
            datepicker.show(function (a) {
                self.transportation.date = a;
            }, this.transportation.date)
        },
        putDriver:function(driver){
            this.transportation.driver = driver;
            if (driver.vehicle && (typeof this.transportation.vehicle == 'undefined' || this.transportation.vehicle.id === -1)){
                this.putVehicle(driver.vehicle);
            }
            if (driver.organisation && (typeof this.transportation.transporter == 'undefined' || this.transportation.transporter.id === -1)){
                this.putTransporter(driver.organisation);
            }
        },
        putVehicle:function(vehicle){
            this.transportation.vehicle = vehicle;
            if (vehicle.trailer && (typeof this.transportation.trailer == 'undefined' || this.transportation.trailer.id === -1)){
                this.putTrailer(vehicle.trailer);
            }
        },
        putTrailer:function(trailer){
            this.transportation.trailer = trailer;
        },
        putTransporter:function(transporter){
            this.transportation.transporter = transporter;
        },
        putOrganisation:function(counterparty){
            this.transportation.counterparty = counterparty;
            this.findDeals(counterparty.id);
        },
        findDeals:function(id){
            const self = this;
            let param = {
                organisation:id
            };
            PostApi(this.api.findDeals, param, function (a){
                self.deals = a;
                self.setDeal();
            })
        },
        setDeal:function(){
            let date;
            let d;
            for (let i in this.deals){
                if (this.deals.hasOwnProperty(i)){
                    let deal = this.deals[i];
                    let dealDate = new Date(deal.date);
                    if (!date || dealDate > date){
                        date = dealDate;
                        d = deal;
                    }
                }
            }
            console.log('->> ' + date.toLocaleDateString() + ' ' + d.product.name);
            this.transportation.deal = d.id;
            this.transportation.product = d.product.id;
        },
        dealByProduct:function(){
            let dealId = -1;
            let date;
            for(let i in this.deals){
                if (this.deals.hasOwnProperty(i)){
                    let deal = this.deals[i];
                    if (deal.product.id === this.transportation.product){
                        let dealDate = new Date(deal.date);
                        if (!date || dealDate > date){
                            date = dealDate;
                            dealId = deal.id;
                        }
                    }
                }
            }
            this.transportation.deal = dealId;
        },
        addAction:function(action, name){
            let product = action.product;
            if (!this.actions[product.id]){
                this.actions[product.id] = [];
            }
            this.actions[product.id].push({
                type:action.type,
                name:name
            });
        },
        getActions:function(){
            let actions = this.actions[this.transportation.product];
            let founded = false;
            for(let i in actions){
                if (actions.hasOwnProperty(i)){
                    let action = actions[i];
                    if (this.transportation.action === action.type){
                        founded = true;
                    }
                }
            }
            if (!founded){
                this.transportation.action = actions[0].type;
            }
            return actions;
        },
        save:function(){
            let data = {
                id:this.transportation.id,
                date:this.transportation.date,
                deal:{
                    id:this.transportation.deal,
                    date:this.transportation.date,
                    counterparty:this.transportation.counterparty.id,
                    product:this.transportation.product
                },
                driver:this.transportation.driver.id,
                vehicle:this.transportation.vehicle.id,
                trailer:this.transportation.trailer.id,
                transporter:this.transportation.transporter.id,
                manager:this.transportation.manager
            };
            PostApi(this.api.save, data, function (a) {
                console.log(a);
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});