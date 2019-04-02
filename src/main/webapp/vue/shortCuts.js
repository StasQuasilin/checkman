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
        upd:-1,
        window:{
            width:0
        },
        dynamic:{
            show:false
        },
        dynamicWidth:function(){
            return this.dynamic.show ? '350px' : '0'
        }
    },
    created:function(){
        window.addEventListener('resize', this.handleResize);
        this.handleResize();
    },
    methods:{
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
                if (e.territory.add.length || e.territory.update.length || e.territory.remove.length){
                    console.log(e.territory);
                    for (var a in e.territory.add){
                        if (e.territory.add.hasOwnProperty(a)){
                            self.add(self.onTerritory, e.territory.add[a]);
                        }
                    }
                    for (var u in e.territory.update){
                        if (e.territory.update.hasOwnProperty(u)){
                            self.update(self.onTerritory, e.territory.update[u]);
                        }
                    }
                    for (var r in e.territory.remove){
                        if (e.territory.remove.hasOwnProperty(r)){
                            self.remove(self.onTerritory, e.territory.remove[r]);
                        }
                    }
                }

            })
            self.upd = setTimeout(function(){
                self.updReq();
            }, 1000)
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
                        list.splice(r, 1);
                        break;
                    }
                }
            }
        },
        handleResize:function(){
            this.window.width = window.innerWidth;
        },
        isDynamic:function(){
            return this.window.width <= 1280;
        },
        show:function(){
            this.dynamic.show = !this.dynamic.show;
        },
        prettyDate:function(time){
            var now = new Date();
            var date = new Date(time);
            return (now.toLocaleDateString() != date.toLocaleDateString() ? date.toLocaleDateString() + ' ' : '') + date.toLocaleTimeString();
        },
        timeDifferent:function(time){
            var now = new Date();
            var date = new Date(time);
            return (now - date)
        }
    }
});