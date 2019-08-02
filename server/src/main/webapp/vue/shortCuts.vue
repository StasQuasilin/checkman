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
            if (this.isDynamic()) {
                return this.dynamic.show ? '350px' : '0';
            } else {
                return '100%';
            }
        }
    },
    created:function(){
        window.addEventListener('resize', this.handleResize);
        this.handleResize();
    },
    methods:{
        updReq:function(){
            const self = this;
            var onTerritory = {};
            for (var t in this.onTerritory){
                if (this.onTerritory.hasOwnProperty(t)){
                    var territory = this.onTerritory[t];
                    onTerritory[territory.id] = territory.hash;
                }
            }
            var onCruise = {};
            for (var c in this.onCruise){
                if (this.onCruise.hasOwnProperty(c)){
                    var cruise = this.onCruise[c];
                    onCruise[cruise.id] = cruise.hash;
                }
            }

            PostApi(this.api.update, { territory:onTerritory, cruise:onCruise }, function(e){
                if (e.territory.add.length || e.territory.update.length || e.territory.remove.length){
                    console.log(e.territory);
                    for (var at in e.territory.add){
                        if (e.territory.add.hasOwnProperty(at)){
                            self.add(self.onTerritory, e.territory.add[at]);
                        }
                    }
                    for (var ut in e.territory.update){
                        if (e.territory.update.hasOwnProperty(ut)){
                            self.update(self.onTerritory, e.territory.update[ut]);
                        }
                    }
                    for (var rt in e.territory.remove){
                        if (e.territory.remove.hasOwnProperty(rt)){
                            self.remove(self.onTerritory, e.territory.remove[rt]);
                        }
                    }
                }
                if (e.cruise.add.length || e.cruise.update.length || e.cruise.remove.length){
                    console.log(e.cruise);
                    for (var ac in e.cruise.add){
                        if (e.cruise.add.hasOwnProperty(ac)){
                            self.add(self.onCruise, e.cruise.add[ac]);
                        }
                    }
                    for (var uc in e.cruise.update){
                        if (e.cruise.update.hasOwnProperty(uc)){
                            self.update(self.onCruise, e.cruise.update[uc]);
                        }
                    }
                }
            });
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