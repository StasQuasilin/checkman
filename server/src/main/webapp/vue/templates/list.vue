var list = {
    data:function(){
        return{
            api:{},
            items: {},
            limit:-1
        }
    },
    methods:{
        handler:function(data){
            console.log(data);
            for (let a in data.add){
                if (data.add.hasOwnProperty(a)){
                    let add = data.add[a];
                    this.update(add);
                }
            }
            for (let u in data.update){
                if (data.update.hasOwnProperty(u)){
                    let update = data.update[u];
                    this.update(update);
                }
            }
        },
        update:function(upd){
            Vue.set(this.items, upd.id, {item:upd});
        },
        getItems:function(){
            let values = Object.values(this.items);
            values.sort(this.sort)
            return values;
        },
        sort:function (a, b) {
            return new Date(b.item.date) - new Date(a.item.date);
        }
    }
}