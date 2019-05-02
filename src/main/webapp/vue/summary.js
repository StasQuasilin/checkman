var summary = new Vue({
    el: '#summary',
    data:{
        api:{
            update:''
        },
        id:-1,
        hash:-1,
        weights:[],
        analyses:{},
        seals:[],
        logs:[]
    },
    computed:{
        weight:function(){
            var w = {
                brutto:0,
                tara:0,
                netto:0
            };
            for (var i in this.weights){
                if (this.weights.hasOwnProperty(i)){
                    var weight = this.weights[i];
                    w.brutto += weight.brutto;
                    w.tara += weight.tara;
                }
            }
            if (w.brutto > 0 && w.tara > 0){
                w.netto = w.brutto - w.tara;
            }
            return w;
        }
    },
    methods:{
        update:function(){
            const self = this;
            PostApi(this.api.update, {id:this.id, hash: this.hash}, function(a){
                if(a.weights.length || a.analyses.length || a.logs.length) {

                    //self.weights = a.weights;
                    //self.analyses = a.analyses;
                    //self.seals = a.seals;
                    self.logs = a.logs;
                }
            });
            setTimeout(function(){
                self.update()
            }, 5000)
        }
    }
});