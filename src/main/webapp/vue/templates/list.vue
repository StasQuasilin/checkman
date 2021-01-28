var list = {
    data:function(){
        return{
            api:{},
            items: {},
            limit:-1
        }
    },
    mounted:function(){
        console.log('Vro list mounted');
        if (typeof filter_control !== 'undefined'){
            filter_control.items = this.items;
            if (typeof filter_control.filteredItems === 'function') {
                this.getItems = function () {
                    let items = Object.values(filter_control.filteredItems());
                    items.sort(this.sort);
                    return items;
                }
            }
        } else {
            console.log('Filter not found')
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
            values.sort(this.sort);
            return values;
        },
        sort:function (a, b) {
            return new Date(b.item.date) - new Date(a.item.date);
        }
    }
};