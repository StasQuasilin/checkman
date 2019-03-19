var logistic = new Vue({
    el:'#logistic',
    data:{
        update_link:'',
        save_link:'',
        items:{},
        types:{}
    },
    methods:{
        add:function(item){
            var data = {};
            data.className = 'container-item-' + new Date(item.date).getDay()
            data.item = item
            Vue.set(this.items, item.id, data)
        },
        update:function(item){
            this.items[item.id].className = 'container-item-' + new Date(item.date).getDay()
            this.items[item.id].item=item
        },
        remove:function(id){
            for(var i in this.items){
                if (this.items[i].item.id === id){
                    this.$delete(this.items, i);
                    break
                }
            }
        },
        setUrls:function(update, save){
            this.update_link = update
            this.save_link = save
            this.loadItems()
        },
        loadItems: function () {
            const self = this;
            var parameters = [];
            for (var i in this.items){
                parameters[i]= this.items[i].item.hash;
            }
            PostApi(this.update_link, parameters, function(e){
                if (e.add.length || e.update.length || e.remove.length) {
                    console.log(e)
                }
                for (var a in e.add){
                    self.add(e.add[a])
                }
                for (var u in e.update){
                    self.update(e.update[u])
                }
                for (var d in e.remove){
                    self.remove(e.remove[d])
                }
            }, null, false)
            setTimeout(function(){self.loadItems()}, 1000);
        },
        addType:function(key, value){
            Vue.set(this.types, key, value)
        }
    }
})