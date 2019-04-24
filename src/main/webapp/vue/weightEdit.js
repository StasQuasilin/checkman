var editor = new Vue({
    el: '#editor',
    data:{
        api:{
            saveWeightAPI:'',
            print:''
        },
        id:'',
        weights:[],
        length:function(){
            return this.weights.length;
        },
        analyses:{
            sun:[],
            oil:[],
            cake:[]
        }
    },
    methods:{
        newWeight:function(){
            this.addWeight(0, 0, 0)
        },
        addWeight:function(id, brutto, tara){
            Vue.set(this.weights, this.weights.length, {
                id:id,
                brutto:brutto,
                tara:tara,
                checkBrutto:function(){
                    var b = parseFloat(this.brutto);
                    if(!isNaN(b)) {
                        this.brutto = b < 1000 ? b : b / 1000;
                    }
                },
                checkTara:function(){
                    var t = parseFloat(this.tara);
                    if (!isNaN(t)){
                        this.tara = t < 1000 ? t : t / 1000;
                    }
                }

            })
        },
        removeWeight:function(key){
            this.weights.splice(key, 1)
        },
        save:function(){
            var result = {};
            result.id = this.id;
            var weights = [];
            for (var i in this.weights){
                var w =this.weights[i];
                if (w.brutto > 0 || w.tara > 0) {
                    weights.push({id: w.id, brutto: w.brutto, tara: w.tara})
                }
            }
            result.weights = weights;
            console.log(result);
            PostApi(this.api.saveWeightAPI, result, function(a){
                console.log(a)
                if (a.status == 'success'){
                    closeModal();
                }
            })
        },
        netto:function(brutto, tara){

            return brutto == 0 || tara == 0 ? 0 : (this.checkTonnas(brutto) - this.checkTonnas(tara));
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