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
        logs:[],
        upd:-1
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
            var weights = [];
            for (var w in this.weights){
                if (this.weights.hasOwnProperty(w)){
                    weights.push(this.weights[w].id);
                }
            }
            var logs = [];
            for (var l in this.logs) {
                if (this.logs.hasOwnProperty(l)){
                    logs.push(this.logs[l].id);
                }
            }
            PostApi(this.api.update, {id:this.id, logs: logs}, function(a){
                if(a.weights.length || a.logs.length) {
                    self.weights = a.weights;
                    //self.analyses = a.analyses;
                    //self.seals = a.seals;
                    for (var i in a.logs){
                        if (a.logs.hasOwnProperty(i)){
                            self.logs.push(a.logs[i]);
                        }
                    }
                    self.logs.sort(function(a, b){
                        return new Date(a.date) - new Date(b.date)
                    })
                }
            });
            this.upd = setTimeout(function(){
                self.update()
            }, 5000)
        },
        stop:function(){
            console.log('stop summary update');
            clearTimeout(this.upd);
        }
    }
});