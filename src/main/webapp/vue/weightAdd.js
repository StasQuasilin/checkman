var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            findOrganisation:'',
            parseOrganisation:'',
            findDeal:'',
            findVehicle:'',
            parseVehicle:'',
            findDriver:'',
            parseDriver:'',
            save:''
        },
        products:[],
        units:[],
        deals:[],
        plan:{
            date:new Date().toISOString().substring(0, 10),
            deal:-1,
            organisation:-1,
            product:-1,
            plan:0,
            unit:-1,
            vehicle:-1,
            driver:-1
        },
        input:{
            organisation:'organisation',
            vehicle:'vehicle',
            driver:'driver'
        }
    },
    methods:{
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.plan.date = date;
            }, self.plan.date)
        }
    }
});