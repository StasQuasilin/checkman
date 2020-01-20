var editor = new Vue({
    el: '#editor',
    data:{
        api:{},
        id:'',
        products:[],
        already:false
    },
    methods:{
        save:function(){
            const self = this;
            if (!this.already) {
                this.already = true;
                var data = {};
                data.weight = [];
                for (var i in this.products){
                    if (this.products.hasOwnProperty(i)){
                        var p = this.products[i];
                        data.weight.push({
                            id:p.weight.id,
                            product:p.id,
                            brutto:p.weight.brutto,
                            tara:p.weight.tara
                        })
                    }
                }
                PostApi(this.api.save, data, function (a) {
                    if (a.status == 'success') {
                        closeModal();
                    }
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
        },
        netto:function(weight){
            return weight.brutto == 0 || weight.tara == 0 ? 0 : (this.checkTonnas(weight.brutto) - this.checkTonnas(weight.tara));
        },
        checkBrutto:function(){
            this.weight.brutto = this.check(this.weight.brutto)
        },
        checkTara:function(){
            this.weight.tara = this.check(this.weight.tara)
        },
        check:function(w){
            return w > 1000 ? w / 1000 : w;
        },
        total:function(){
            var t = 0;

            return t;
        },
        checkTonnas:function(value){
            return value < 1000 ? value : value / 1000;
        },
        print:function(){
            PostReq(this.api.print, {id: this.id }, function(a){
                var print = window.open()
                print.document.write('<html>');
                print.document.write(a)
                print.document.write('</html>');
                print.print();
                print.close();
            })
        }

    }
});