print = new Vue({
    el:'#print',
    components:{
        'object-input':objectInput
    },
    data:{
        api:{},
        already:false,
        from:new Date().toISOString().substring(0, 10),
        to:new Date().toISOString().substring(0, 10),
        organisation:{},
        organisations:[],
        drivers:[],
        driver:{id:-1},
        vehicle:'',
        product:-1,
        products:[],
        type:0,
        err:{
            organisation:false,
            driver:false
        },
        organisationProps:{},
        driverProps:{},
        file:''
    },
    methods:{
        selectFrom:function(){
            const self = this;
            datepicker.show(function(date){
                self.from = date;
            }, this.from)
        },
        selectTo:function(){
            const self = this;
            datepicker.show(function(date){
                self.to = date;
            }, this.to)
        },
        removeDriver:function(idx){
            this.drivers.splice(idx, 1);
        },
        removeOrganisation:function(idx){
            this.organisations.splice(idx, 1);
        },
        download:function(){
            this.print(true);
        },
        print:function(download){
            if (!this.already) {
                this.already = true;
                let params = {};
                if (this.from) {
                    params.from = this.from;
                }
                if (this.to) {
                    params.to = this.to;
                }
                params.organisations = [];
                for (let o in this.organisations){
                    if (this.organisations.hasOwnProperty(o)){
                        params.organisations.push(this.organisations[o].id)
                    }
                }

                params.drivers = [];
                for (let i in this.drivers){
                    if (this.drivers.hasOwnProperty(i)){
                        params.drivers.push(this.drivers[i].id)
                    }
                }

                if (this.vehicle) {
                    params.vehicleContain = this.vehicle;
                }
                if (this.product !== -1) {
                    params.product = this.product;
                }
                if(download){
                    params.download = download;
                }
                params.type = this.type;
                const self = this;

                PostReq(this.api.print, params, function (a) {
                    self.already = false;
                    if(!download) {
                        let print = window.open();
                        print.document.write(a);
                        print.print();
                    } else {
                        console.log(a);
                        let json = JSON.parse(a);
                        if (json.status === 'success'){
                            window.open(context + self.api.print + '?file=' + json.file);
                        }
                    }
                })
            }
        }
    }
});