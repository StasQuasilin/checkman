let reports = new Vue({
    el:'#reports',
    components:{
        cell:cell,
        'user-cell':userCell
    },
    data:{
        api:{},
        users:[],
        reports:{},
        scroll:0,
        opacity:0
    },
    methods:{
        handle(data){
            for (let a in data.add){
                if (data.add.hasOwnProperty(a)){
                    let add = data.add[a];
                    this.update(add);
                }
            }
            console.log(data);
        },
        update:function(item){
            let owner = item.owner;
            if (!this.reports[owner.id]){
                Vue.set(this.reports, owner.id, {})
            }
            let reports = this.reports[owner.id];
            let done = item.done ? new Date(item.done) : new Date();
            let doneDate = done.toLocaleDateString();
            if (!reports[doneDate]){
                Vue.set(reports, doneDate, {});
            }
            let report = reports[doneDate];
            let leave = new Date(item.leave);
            report.length = Math.floor((done - leave) / 1000 / 60 / 60 / 24) + 1;
            report.data = {
                id:item.id,
                route : item.route,
                product : item.product
            }
        },
        nowDate:function(){
            return new Date();
        },
        dateOffset:function(date, offset){
            return new Date(date.setDate(date.getDate() - offset));
        },
        getReportLength(report, index){
            let length = this.getSomeFromReport(report, index, 'length');
            if (length != null){
                return length;
            }
            return 0;
        },
        getReportData:function(report, index){
            return this.getSomeFromReport(report, index, 'data');
        },
        getSomeFromReport:function(report, index, field){
            let date = this.dateOffset(this.nowDate(), index - 1).toLocaleDateString();
            if (this.reports.hasOwnProperty(report)){
                let reports = this.reports[report];
                if (reports.hasOwnProperty(date)){
                    return reports[date][field];
                }
            }
            return null;
        },
        getReportCells:function (report) {
            let length = 12;
            if (this.reports.hasOwnProperty(report)){
                let reports = this.reports[report];
                for (let i in reports){
                    if (reports.hasOwnProperty(i)){
                        let index = reports[i];
                        length -= (index.length - 1);
                    }
                }
            }
            return length;
        },
        scrollListener:function(){
            let scroll = this.$refs.reportContainer.scrollLeft;
            this.scroll = scroll;
            this.opacity = scroll / 100;
        },
        backgroundColor:function(){
            return 'rgba(248, 248, 248,' + this.opacity + ')';
        },
        open:function(id){
            loadModal(this.api.show, {id:id});
        }
    }
});