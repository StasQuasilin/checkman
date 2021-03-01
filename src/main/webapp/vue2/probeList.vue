probeList = new Vue({
    el:'#probeList',
    mixins:[bl],
    data:{
        fields:{}
    },
    components:{
        'probe-view':probeView
    },
    mounted:function(){
        this.filter = probeFilter;
    },
    methods:{
        filteredItems:function(){
            if (this.filter !== null){
                if (this.filter.result.length > 0){
                    return this.filter.result;
                }
            }
            return this.getItems();
        },
        sort:function (a, b) {
            return new Date(b.date) - new Date(a.date);
        }
    }
});