transportListBase = {
    mixins:[bl, contextMenuView],
    components:{
        'transport-view': transportView
    },
    data:{
        labels:{},
        f:{
            p:false,
            a:false
        }
    },
    mounted:function () {
        this.filter = transportFilter;
    },
    methods:{
        getF:function(){
            return Object.assign({}, this.f);
        },
        sort:function (a, b) {
            if (a.date === b.date) {
                let aState = this.calculateState(a.timeIn, a.timeOut);
                let bState = this.calculateState(b.timeIn, b.timeOut);
                let aWeight = a.weight;
                let bWeight = b.weight;
                let aG = 0;
                let aT = 0;
                let bG = 0;
                let bT = 0;

                if (aWeight){
                    aG = aWeight.brutto;
                    aT = aWeight.tara;
                }

                let aN = (aG > 0 && aT === 0) || (aG === 0 && aT > 0);
                if (aState > 1 && aN){
                    aState = 0;
                }
                if (bWeight){
                    bG = bWeight.brutto;
                    bT = bWeight.tara;
                }
                let bN = (bG > 0 && bT === 0) || (bG === 0 && bT > 0);
                if(bState > 1 && bN){
                    bState = 0;
                }

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
        show:function (itemId) {
            if (this.api.show) {
                loadModal(this.api.show, {id: itemId});
            }
        }
    }
};