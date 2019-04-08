var arch = new Vue({
    el: '#archive',
    data:{
        api:{
            update:'',
            show:''
        },
        items:[],
        filter:filter,
        parameters:{}
    },
    methods:{
        doRequest:function(){
            var parameters = {};
            for (var k in this.parameters) {
                if (this.parameters.hasOwnProperty(k)){
                    parameters[k] = this.parameters[k];
                }
            }
            const self = this;
            PostApi(this.api.update, parameters, function(a){
                self.items = a;
            })
        },
        rowName:function(date){
            return 'container-item-' + new Date(date).getDay()
        }
    }
});