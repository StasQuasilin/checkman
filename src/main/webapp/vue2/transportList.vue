transportList = new Vue({
    el:'#transportList',
    mixins:[bl, contextMenuView],
    components:{
        'transport-view': transportView
    },
    data:{
        labels:{}
    },
    mounted:function () {
        this.filter = transportFilter
    },
    methods:{
        sort:function (a, b) {
            if (a.date === b.date) {
                let aState = this.calculateState(a.timeIn, a.timeOut);
                let bState = this.calculateState(b.timeIn, b.timeOut);
                let sort =  aState - bState;
                if (sort === 0){
                    let aDriver = a.driver;
                    let bDriver = b.driver;
                    if (aDriver && bDriver){
                        sort = aDriver.person.value.localeCompare(bDriver.person.value);
                    } else if (aDriver){
                        sort = -1;
                    } else {
                        sort = 1;
                    }
                }
                return sort;
            }
            return new Date(a.date) - new Date(b.date);
        },
        calculateState:function (timeIn, timeOut) {
            if (timeIn && !timeOut){
                return 0;
            } else if (!timeIn && timeOut){
                return 1;
            } else if (!timeIn && !timeOut){
                return 2;
            } else {
                return 3;
            }
        },
        edit:function (itemId) {
            console.log(itemId);
        }
    }

});