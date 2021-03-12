transportFilter = new Vue({
    el: '#filter_view',
    data:{
        api:{},
        filter:{
            date:'',
            dateTo:'',
            product:-1,
            organisation:-1,
            driver:-1,
            any:function(){
                return date || product > 0;
            }
        },
        input:{
            date:new Date().toISOString().substring(0, 10),
            organisation:'',
            driver:''
        },
        productList:[],
        items:[],
        foundOrganisations:[],
        foundDrivers:[],
        result:[],
        fnd:-1,
        tooFewParam:false,
        dateLimit:false,
        period:false
    },
    computed:{
        filterDate:function(){
            return this.buildDate(this.filter.date);
        },
        filterDateTo:function () {
            return this.buildDate(this.filter.dateTo);
        }
    },
    mounted:function(){
        transportList.getItems = function () {
            return this.result;
        }
    },
    methods:{
        buildDate:function(val){
            return val ? new Date(val).toLocaleDateString() : '';
        },
        clear:function(){
            let filter = this.filter;
            filter.date = '';
            filter.dateTo = '';
            filter.product = -1;
            filter.driver = -1;
            this.closeOrganisation();
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.filter.date = self.input.date = date;
            }, self.input.date)
        },
        pickDateTo:function(){
            const self = this;
            datepicker.show(function(date){
                self.filter.dateTo = date;
            }, self.filter.dateTo)
        },
        findOrganisation:function(){
            clearTimeout(this.fnd);
            const self = this;
            if (this.input.organisation) {
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findOrganisations, {key: self.input.organisation}, function (a) {
                        self.foundOrganisations = a;
                    })
                }, 300);
            } else{
                this.foundOrganisations = [];
            }

        },
        putOrganisation:function(organisation){
            this.filter.organisation = organisation.id;
            this.input.organisation = organisation.value;
            this.foundOrganisations = [];
        },
        closeOrganisation:function(){
            this.filter.organisation = -1;
            this.input.organisation = '';
        },
        findDriver:function(){
            clearTimeout(this.fnd);
            const self = this;
            if (this.input.driver) {
                this.fnd = setTimeout(function () {
                    PostApi(self.api.findDrivers, {key: self.input.driver}, function (a) {
                        self.foundDrivers = a;
                    })
                }, 300);
            } else{
                this.foundDrivers = [];
            }
        },
        putDriver:function(driver){
            this.filter.driver = driver.id;
            this.input.driver = driver.person.value;
            this.foundDrivers = [];
        },
        checkFilter:function(){
            console.log('all right');
        },
        find:function(){
            let f = Object.assign({}, this.filter);
            delete f.any;
            console.log(f);
            let fields = 0;
            if (f.date || f.dateTo) {
                fields++;
            }
            if (f.product !== -1) {
                fields++;
            }
            if (f.organisation !== -1) {
                fields++;
            }
            if (f.driver !== -1) {
                fields++;
            }
            this.tooFewParam = fields < 2;
            if (!this.tooFewParam) {
                const self = this;
                PostApi(this.api.find, f, function (a) {
                    transportList.items = {};
                    if(a.status === 'success'){
                        let result = a.result;
                        result.sort(function (a, b) {
                            return new Date(b.date) - new Date(a.date);
                        });
                        self.result = result;
                        self.dateLimit = a.limit;

                        for (let i in result){
                            if (result.hasOwnProperty(i)){
                                let item = result[i];
                                transportList.items[item.id] = item;
                            }
                        }
                    }
                    transportList.$forceUpdate();
                });
            }
        },
        filteredItems:function(){
            return this.result;
        }
    }
});