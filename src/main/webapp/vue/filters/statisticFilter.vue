filterControl = new Vue({
    components:{
      'object-input':objectInput
    },
    el:'#filter_view',
    data:{
        api:{},
        from:new Date().toISOString().substring(0, 10),
        to:new Date().toISOString().substring(0, 10),
        product:-1,
        organisation:{
            id:-1
        },
        products:[],
        organisationProps:{},
        result:[]
    },
    methods:{
        selectDateFrom:function(){
            const self = this;
            datepicker.show(function(d){
                self.from = d;
            }, this.from);
        },
        selectDateTo:function(){
            const self = this;
            datepicker.show(function(d){
                self.to = d;
            }, this.to);
        },
        getItems:function(){
            return this.result;
        },
        find:function(){
            let data = {
                from: this.from,
                to: this.to
            };
            if (this.product !== -1){
                data.product = this.product;
            }
            if (this.organisation.id !== -1){
                data.organisation = this.organisation.id;
            }
            const self = this;

            PostApi(this.api.find, data, function(a){
                self.result = [];
                self.result.push(a);
            })
        }
    }
});