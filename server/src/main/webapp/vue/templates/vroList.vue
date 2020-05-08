var vroList = new Vue({
    el:'#container',
    mixins:[list],
    data:function(){
        return{
            forpress:[]
        }
    },
    methods:{
        middle:function(item){
            if (!item.middle){
                let middle = {
                    humidityBefore:0,
                    sorenessBefore:0,
                    humidityAfter:0,
                    sorenessAfter:0,
                    kernelOffset:0,
                    huskiness:0
                };
                let count = 0;
                for (let j in item.crudes){
                    if (item.crudes.hasOwnProperty(j)){
                        count++;
                        let crude = item.crudes[j];
                        middle.humidityBefore += crude.humidityBefore;
                        middle.sorenessBefore += crude.sorenessBefore;
                        middle.humidityAfter += crude.humidityAfter;
                        middle.sorenessAfter += crude.sorenessAfter;
                        middle.kernelOffset += crude.kernelOffset;
                        middle.huskiness += crude.huskiness;
                    }
                }

                if (count > 0) {
                    middle.humidityBefore /= count;
                    middle.sorenessBefore /= count;
                    middle.humidityAfter /= count;
                    middle.sorenessAfter /= count;
                    middle.kernelOffset /= count;
                    middle.huskiness /= count;
                }
                item.middle = middle;

            }
            return item.middle;
        },
        editGranules:function(id){
            loadModal(this.api.editGranules, {id: id});
        },
        sortedCrudes:function(crudes){
            let arr = Object.assign([], crudes);
            arr.sort(function(a, b){
                return new Date(a.time) - new Date(b.time);
            })
            return arr;
        },
        editDaily:function(id){
            loadModal(this.api.editDaily, {id: id});
        }
    }
})