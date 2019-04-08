var archive = new Vue({
   el: '#archive',
    data:{
        filter:fltr,
        api:{
            update:'',
            show:''
        },
        items:[],
        upd:-1
    },
    methods:{
        updReq:function(){
            const self = this;
            var parameters={};
            var items = {};
            for(var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var item = this.items[i];
                    items[item.item.id]=item.item.hash;
                }
            }
            parameters.items=items;
            PostApi(self.api.update, parameters, function(e){
                if (e.add.length || e.update.length || e.remove.length) {
                    console.log(e);
                    for (var a in e.add){
                        if (e.add.hasOwnProperty(a)){
                            self.add(e.add[a])
                        }
                    }
                    for (var u in e.update){
                        if (e.update.hasOwnProperty(u)){
                            self.update(e.update[u])
                        }
                    }
                    for (var r in e.remove){
                        if (e.remove.hasOwnProperty(r)){
                            self.remove(e.remove[r])
                        }
                    }
                    self.items.sort(function(a, b){
                        return a.item.date - b.item.date;
                    })
                }
                self.upd = setTimeout(function(){
                    self.updReq()
                }, 5000)
            })
        },
        add:function(item) {
            var data = {};
            data.item = item;
            this.items.push(data);
        },
        update:function(item){
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var tmp = this.items[i];
                    if (tmp.item.id == item.id){
                        tmp.item = item;
                        break;
                    }
                }
            }
        },
        remove:function(id){
            for (var i in this.items){
                if (this.items.hasOwnProperty(i)){
                    var tmp = this.items[i];
                    if (tmp.item.id == id){
                        this.items.splice(i, 1);
                        break;
                    }
                }
            }
        },
        stopContent:function(){
            console.log('Stop archive list');
            clearTimeout(this.upd);
        }
    }
});