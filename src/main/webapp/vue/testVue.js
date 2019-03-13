var vue = new Vue({
    el: '#app',
    data: {
        message: 'hello',
        items: {
            1:{
                id:1,
                name:'Item 1',
                date: new Date().toLocaleDateString()
            },
            2:{
                id:2,
                name:'Item 2',
                date: new Date().toLocaleDateString()
            },
            3:{
                id:3,
                name:'Item 3',
                date: new Date().toLocaleDateString()
            }
        }
    },
    methods:{
        add:function(item){
            item.date = new Date().toLocaleTimeString()
            Vue.set(this.items, item.id, item)
        },
        update:function(item){
            item.date = new Date().toLocaleTimeString()
            Vue.set(this.items, item.id, item)
        },
        remove:function(id){
            for(var i in this.items){
                if (this.items[i].id === id){
                    this.$delete(this.items, i);
                    break
                }
            }
        }
    }
})


