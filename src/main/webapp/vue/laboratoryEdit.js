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
            var parameteres = {};
            parameteres.plan = this.plan;
            var analyses = [];
            for (var i in this.analyses){
                analyses.push(this.analyses[i]);
            }
            parameteres.analyses = analyses;
            console.log(parameteres)
            const selt = this;
            PostApi(this.api.save, parameteres, function(a){
                if (a.status == 'success'){
                    closeModal();
                }
            })
        }
    }
});