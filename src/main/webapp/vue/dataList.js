var deamon = new Vue({
    el: '#container',
    data:{
        url:'',
        showLink:'',
        itemClass: function() {
            return 'custom-item-'
        },
        items:{},
        timeout:-1
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
            loadModal(this.showLink + '?id=' + id)
        },
        doRequest:function(){
            const self = this;
            var parameters = {};
            for (var k in this.items){
                if (this.items.hasOwnProperty(k)){
                    var item = this.items[k];
                    parameters[k]=item.item.hash;
                }
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
                self.timeout = setTimeout(function(){
                    self.doRequest();
                }, 1000)
            })
        },
        stop:function(){
            console.log('Stop deamon list')
            clearTimeout(this.timeout)
        }
    }
})