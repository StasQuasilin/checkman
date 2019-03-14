var deamon = new Vue({
    el: '#container',
    data:{
        url:'',
        itemClass: function() {
            return 'custom-item-'
        },
        items:{}
    },
    methods:{
        setUpdateUrl:function(url){
            this.url = url
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
        }
    },
    mounted:function(){
        for (var i = 1; i < 21; i++){
            this.add(
                {
                    id:i,
                    date:new Date(),
                    organisation:{
                        id:1,
                        name: 'TOB Porosjatko'
                    },
                    product:{
                        id:0,
                        name: 'Maclo raf',
                    },
                    visiblity:{
                        id:1,
                        name: 'ПП Млин'
                    },
                    quantity: 500,
                    unit: 't.',
                    price:25600,
                    done: 258,
                    manager:{
                        id:1,
                        name: 'Ololoshko A.I.'
                    }
                }
            )
        }
    }
})