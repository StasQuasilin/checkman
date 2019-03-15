var deamon = new Vue({
    el: '#container',
    data:{
        url:'',
        showLink:'',
        itemClass: function() {
            return 'custom-item-'
        },
        items:{}
    },
    methods:{
        setUrls:function(url, show){
            this.url = url
            this.showLink = show
            this.doRequest()
        },
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
        drop:function(id){
            for(var i in this.items){
                if (this.items[i].item.id === id){
                    this.$delete(this.items, i);
                    break
                }
            }
        },
        show:function(id){
            console.log(this.showLink + '?id=' + id);
        },
        doRequest:function(){
            const self = this;
            var parameters = [];
            for (var i in this.items){
                parameters[i]= this.items[i].item.hash;
            }
            PostApi(this.url, parameters, function(e){
                if (e.add.length || e.update.length || e.delete.length) {
                    console.log(e);
                }
                for(var a in e.add){
                    self.add(e.add[a])
                }
                for(var u in e.update){
                    self.update(e.update[u])
                }
                for(var d in e.delete){
                    self.drop(e.delete[d])
                }
                setTimeout(function(){
                    self.doRequest();
                }, 1000)
            })
        }
    }
})