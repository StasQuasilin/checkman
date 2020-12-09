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
        drivers:[],
        driver:{id:-1},
        vehicle:'',
        product:-1,
        products:[],
        type:0,
        err:{
            organisation:false,
            driver:false
        }
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
        print:function(){
            if (!this.already) {
                this.already = true;
                let params = {};
                if (this.from) {
                    params.from = this.from;
                }
                if (this.to) {
                    params.to = this.to;
                }
                if (this.organisation) {
                    params.organisation = this.organisation.id
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
                params.type = this.type;
                const self = this;

                PostReq(this.api.print, params, function (a) {
                    self.already = false;
                    let print = window.open();
                    print.document.write(a);
                    print.print();
                })
            }
        }
    }
});