var summary = new Vue({
    el: '#summary',
    data:{
        api:{
            update:''
        },
        id:-1,
        hash:-1,
        weight:{},
        analyses:{},
        seals:[],
        logs:[],
        upd:-1
    },
    methods:{
        update:function(){
            const self = this;
            var logs = [];
            for (var l in this.logs) {
                if (this.logs.hasOwnProperty(l)){
                    logs.push(this.logs[l].id);
                }
            }
            PostApi(this.api.update, {id:this.id, logs: logs}, function(a){
                if(a.weight || a.logs.length) {
                    self.weight = a.weight;

                    self.analyses = a.analyses;
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
            }, 1000)
        },
        stop:function(){
            clearTimeout(this.upd);
        }
    }
});