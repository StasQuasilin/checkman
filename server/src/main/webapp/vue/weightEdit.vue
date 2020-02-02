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
        },
        already:false
    },
    methods:{
        save:function(){
            if (!this.already) {
                this.already = true;
                PostApi(this.api.saveWeightAPI, {id: this.id, weight: this.weight}, function (a) {
                    console.log(a)
                    if (a.status == 'success') {
                        closeModal();
                    }
                    this.already = false;
                }, function(e){
                    this.already = false;
                })
            }
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
            loadModal(this.api.print, {id: this.id })
        }

    }
});