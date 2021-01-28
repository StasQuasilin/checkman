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
                serviceability:true,
                adherence:true,
                note:'',
                editNote:false
            })
        },
        save:function(){
            let data = Object.assign({}, this.report);
            for (let i in data.reports){
                if (data.reports.hasOwnProperty(i)){
                    let report = data.reports[i];
                    report.subdivision = report.subdivision.id;
                }
            }
            PostApi(this.api.save, data, function (ans) {
                if (ans.status === 'success'){
                    closeModal();
                }
            })
        }
    }
});