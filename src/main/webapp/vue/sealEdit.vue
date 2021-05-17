editor = new Vue({
    el: '#editor',
    data:{
        api:{
            save:''
        },
        prefix:'А',
        number:'',
        suffix:'',
        quantity:100,
        err:'',
        already:false
    },
    methods:{
        preview:function(){
            return this.doPreview(this.number) + (this.quantity > 0 ? ' ... ' + this.doPreview(this.number + this.quantity - 1) : '')
        },
        doPreview:function(number){
            return (this.prefix ? (this.prefix).toUpperCase() + '-' : '') + number + (this.suffix ? '-' + (this.suffix).toUpperCase() : '')
        },
        save:function(){
            if (!this.already) {
                this.already = true;

                let parameters = {
                    prefix: this.prefix,
                    number: this.number,
                    suffix: this.suffix,
                    quantity: this.quantity,
                };

                const self = this;
                PostApi(this.api.save, parameters, function (a) {
                    if (a.status === 'success') {
                        closeModal();
                    } else {
                        self.err = a.msg;
                    }
                })
            }
        }
    }
});