var reporter = new Vue({
    el:'#reporter',
    data:{
        api:{},
        report:{
            id:-1,
            date:new Date().toISOString().substring(0, 10),
            time:new Date().toLocaleTimeString(),
            reports:[]
        },
        subdivisions:[]
    },
    methods:{
        addSubdivision:function(subdivision){
            this.report.reports.push({
                id:-1,
                subdivision:subdivision,
                good:true,
                note:''
            })
        },
        save:function(){
            PostApi(this.api.save, this.report, function (ans) {
                if (ans.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});