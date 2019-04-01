const short = new Vue({
    el: '#cuts',
    data:{
        api:{
            update:'',
            show:''
        },
        types:{
            buy:'Выгрузка',
            sell:'Погрузка'
        },
        onTerritory:[],
        onCruise:[],
        upd:-1

    }, methods:{
        updReq:function(){
            const self = this;
            var parameters = {};
            var territory = {};
            for (var t in this.onTerritory){
                if (this.onTerritory.hasOwnProperty(t)){
                    var terr = this.onTerritory[t];
                    territory[terr.id] = terr.hash;
                }
            }
            parameters.territory = territory;
            parameters.cruise = {};
            PostApi(this.api.update, parameters, function(e){
                if (e.territory.add || e.territory.update || e.territory.remove){
                    console.log(e.territory);
                    for (var a in e.territory.add){
                        if (e.territory.add.hasOwnProperty(a)){
                            self.add(self.onTerritory, e.territory.add[a]);
                        }
                    }
                }
                self.upd = setTimeout(function(){
                    self.updReq();
                }, 1000)
            })
        },
        add:function(list, item){
            list.push(item);
        },
        update:function(list, item){
            for (var u in list){
                if (list.hasOwnProperty(u)){
                    if (list[u].id == item.id){
                        list.splice(u, 1, item)
                        break;
                    }
                }
            }
        },
        remove:function(list, id){
            for (var r in list){
                if (list.hasOwnProperty(r)){
                    if (list[r].id == id){
                        list.splice(i, 1);
                        break;
                    }
                }
            }
        }
    }
});