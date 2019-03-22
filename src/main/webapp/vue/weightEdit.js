var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            saveWeightAPI:''
        },
        id:'',
        weights:[],
        length:function(){
            return this.weights.length;
        }
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
        removeWeight:function(key){
            this.weights.splice(key, 1)
        },
        save:function(){
            var result = {};
            result.id = this.id;
            var weights = [];
            for (var i in this.weights){
                var w =this.weights[i];
                if (this.netto(w.brutto, w.tara) > 0) {
                    weights.push({id: w.id, brutto: w.brutto, tara: w.tara})
                }
            }
            result.weights = weights;
            console.log(result);
            PostApi(this.api.saveWeightAPI, result, function(a){
                console.log(a)
            })
        },
        netto:function(brutto, tara){
            return brutto == 0 || tara == 0 ? 0 : (brutto - tara);
        },
        total:function(){
            var t = 0;
            for (var i in this.weights){
                var w = this.weights[i];
                t += this.netto(w.brutto, w.tara);
            }
            return t;
        }

    }
});