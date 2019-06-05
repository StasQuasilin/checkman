var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        empty:'',
        plan:-1,
        organisation:'',
        vehicle:{
            model:'',
            number:'',
            trailer:''
        },
        driver:'',
        analyses:[],
        laborants:[]
    },
    methods:{
        newAnalyses:function(){
            this.addAnalyses(this.empty);
        },
        addAnalyses:function(analyses){
            console.log('Add analyses');
            console.log(analyses);
            this.analyses.push(analyses);
        },
        save:function(){
            var parameters = {};
            parameters.plan = this.plan;
            var analyses = [];
            for (var i in this.analyses){
                if (this.analyses.hasOwnProperty(i)){
                    analyses.push(this.analyses[i]);
                }
            }
            parameters.analyses = analyses;
            console.log(parameters);
            PostApi(this.api.save, parameters, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        },
        recount:function(){}
    }
});