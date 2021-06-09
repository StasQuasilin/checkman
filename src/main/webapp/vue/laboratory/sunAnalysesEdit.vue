sunEditor = new Vue({
    mixins:[laboratoryEdit],
    data:{

    },
    methods:{
        checkAnalyses:function(item){
            if (typeof item.sun === "undefined"){
                item.sun = {
                    oiliness:0,
                    humidity1:0,
                    humidity2:0,
                    soreness:0,
                    oilImpurity:0,
                    acidValue:0,
                    contamination:false
                }
            }
        },
        recount:function(item){
            const basis = {
                humidity:7,
                soreness:3
            };
            let humidity = (item.humidity1 > 0 || item.humidity2 > 0 ? (
                (item.humidity1 + item.humidity2) / ((item.humidity1 > 0 ? 1 : 0) + (item.humidity2 > 0 ? 1 : 0))
            ) : 0);
            let soreness = item.soreness;

            let recount = 0;
            if (humidity > basis.humidity && soreness > basis.soreness){
                recount = 100-((100-humidity)*(100-soreness)*100)/((100-basis.humidity)*(100-basis.soreness));
            } else if (humidity > basis.humidity) {
                recount = (humidity - basis.humidity)*100 / (100 - basis.humidity)
            } else if (soreness > basis.soreness){
                recount = (soreness - basis.soreness)*100 / (100 - basis.soreness);
            }
            let r = (Math.round(recount * 1000) / 1000);
            item.recount = r;
            return r;
        }
    }
});