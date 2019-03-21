var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            saveWeightAPI:''
        },
        id:'',
        weights:[]
    },
    methods:{
        newWeight:function(){
            this.addWeight(0, 0, 0)
        },
        addWeight:function(id, brutto, tara){
            Vue.set(this.weights, this.weights.length, {
                id:id,
                brutto:brutto,
                tara:tara
            })
        },
        save:function(){
            var result = [];
            result.id = this.weight.id;
            result.brutto = this.weight.brutto;
            result.tara = this.weight.tara;
            PostApi(this.api.saveDriverAPI, result, function(a){
                console.log(a)
            })
        },
        netto:function(brutto, tara){
            return brutto == 0 || tara == 0 ? 0 : brutto - tara;
        }
    }
});