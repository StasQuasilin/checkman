var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            saveWeightAPI:'',
            print:''
        },
        id:'',
        weight:{},
        analyses:{
            sun:[],
            oil:[],
            cake:[]
        }
    },
    methods:{
        save:function(){
            PostApi(this.api.saveWeightAPI, {id: this.id, weight: this.weight}, function(a){
                console.log(a)
                if (a.status == 'success'){
                    closeModal();
                }
            })
        },
        netto:function(brutto, tara){
            return brutto == 0 || tara == 0 ? 0 : (this.checkTonnas(brutto) - this.checkTonnas(tara));
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
            for (var i in this.weights){
                var w = this.weights[i];
                t += this.netto(w.brutto, w.tara);
            }
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