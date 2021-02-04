transportFilter = new Vue({
    el: '#filter_view',
    data:{
        api:{},
        filter:{
            date:'',
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
        tooFewParam:false

    },
    computed:{
        filterDate:function(){
            return this.filter.date ? new Date(this.filter.date).toLocaleDateString() : '';
        }
    },
    mounted:function(){
        transportList.getItems = function () {
            return this.result;
        }
    },
    methods:{
        clear:function(){
            this.filter.date = '';
            this.filter.product = -1;
            this.closeOrganisation();
            this.filter.driver = -1;
        },
        pickDate:function(){
            const self = this;
            datepicker.show(function(date){
                self.filter.date = self.input.date = date;
            }, self.input.date)
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
            if (f.date) {
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
                    a.sort(function (a, b) {
                        return new Date(b.date) - new Date(a.date);
                    });
                    self.result = a;
                    transportList.items = {};
                    for (let i in a){
                        if (a.hasOwnProperty(i)){
                            let item = a[i];
                            transportList.items[item.id] = item;
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