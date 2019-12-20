var report = new Vue({
    el:'#report',
    data:{
        api:{
            print:''
        },
        parameters:{
            date:new Date().toISOString().substring(0, 10)
        },
        dateType:'date'
    },
    methods:{
        pickDate:function(){
            var self = this;
            datepicker.show(function(date){
                self.parameters.date = new Date(date).toISOString().substring(0, 10);
            }, self.parameters.date, this.dateType)
        },
        print:function(){
            PostReq(this.api.print, {parameters: this.parameters }, function(a){
                var print = window.open();
                print.document.write('<html>');
                print.document.write(a);
                print.document.write('</html>');
                print.print();
                //print.close();
            })
        }
    }
});