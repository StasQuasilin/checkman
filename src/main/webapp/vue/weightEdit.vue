editor = new Vue({
    el: '#editor',
    data:{
        api:{
            save:'',
            print:''
        },
        id:'',
        weight:{},
        weights:[],
        analyses:{
            sun:[],
            oil:[],
            cake:[]
        },
        already:false,
        typeNames:{}
    },
    mounted:function(){
        for (let i in this.weights){
            if (this.weights.hasOwnProperty(i)){
                let weight = this.weights[i];
                if(typeof weight.weight === "undefined"){

                }
            }
        }
    },
    methods:{
        addWeight:function(item){
            if (typeof item.weight === "undefined"){
                item.weight = {
                    gross:0,
                    tare:0
                }
            }
            this.weights.push(item);
        },
        save:function(){
            if (!this.already) {
                this.already = true;
                let data = {
                    weights:[]
                };
                for (let i in this.weights){
                    if(this.weights.hasOwnProperty(i)){
                        let w = this.weights[i];
                        data.weights.push({
                            id:w.id,
                            gross:w.weight.gross,
                            tare:w.weight.tare
                        })
                    }
                }
                const self = this;
                PostApi(this.api.save, data, function (a) {
                    console.log(a);
                    if (a.status === 'success') {
                        closeModal();
                    }
                    self.already = false;
                }, function(e){
                    self.already = false;
                })
            }
        },
        netto:function(brutto, tara){
            return brutto === 0 || tara === 0 ? 0 : (this.checkTonnas(brutto) - this.checkTonnas(tara));
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
            let t = 0;
            for (let i in this.weights){
                if(this.weights.hasOwnProperty(i)) {
                    let w = this.weights[i];
                    t += this.netto(w.brutto, w.tara);
                }
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