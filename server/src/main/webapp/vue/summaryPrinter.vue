printer = new Vue({
    el: '#print',
    data: {
        api: {},
        date1: new Date().toISOString().substr(0, 10),
        date2: new Date().toISOString().substr(0, 10),
        counterparty:-1,
        products: [],
        items: []
    },
    computed:{
        counterpartyList:function () {
            let cl = {};
            for (let i in this.items){
                if(this.items.hasOwnProperty(i)){
                    let counterparty = this.items[i].item.counterparty;
                    if (!cl[counterparty.id]){
                        cl[counterparty.id] = counterparty;
                    }
                }
            }
            return Object.values(cl);
        }
    },
    mounted: function () {
        this.items = list.getItems();
    },
    methods: {
        productList: function () {
            let products = {};
            for (let i in this.items) {
                if (this.items.hasOwnProperty(i)) {
                    let product = this.items[i].item.product;
                    if (!products[product.id]) {
                        product.check = true;
                        products[product.id] = product;
                    }
                }
            }
            return this.products = Object.values(products);
        },
        pickDate1: function () {
            const self = this;
            datepicker.show(function (date) {
                self.date1 = date;
            }, this.date1)
        },
        pickDate2: function () {
            const self = this;
            datepicker.show(function (date) {
                self.date2 = date;
            }, this.date2)
        },
        getItems: function () {
            let items = {};

            let d1 = new Date(this.date1);
            let d2 = new Date(this.date2);

            for (let i in this.items) {
                if (this.items.hasOwnProperty(i)) {
                    let item = this.items[i].item;
                    let date = new Date(item.date);
                    let productMatch = false;
                    for (let p in this.products) {
                        if (this.products.hasOwnProperty(p)) {
                            let product = this.products[p];
                            if (product.check && product.id === item.product.id) {
                                productMatch = true;
                                break;
                            }
                        }
                    }
                    let counterpartyMatch = true;
                    if (this.counterparty !== -1 && item.counterparty){
                        counterpartyMatch = this.counterparty === item.counterparty.id;
                    }
                    if (date >= d1 && date <= d2 && productMatch && counterpartyMatch) {
                        if (!items[item.date]) {
                            items[item.date] = {};
                        }
                        if (!items[item.date][item.product.name]) {
                            items[item.date][item.product.name] = []
                        }
                        items[item.date][item.product.name].push(item);
                    }
                }
            }
            return items;
        },
        print: function () {
            let items = this.getItems();
            let w = window.open();
            w.document.write('<style>table{border-collapse: collapse; width: 100%; font-size: 10pt;}');
            w.document.write('td{ border: solid gray 1pt; padding: 2pt 4pt; }');
            w.document.write('</style>');
            w.document.write('<html lang="${lang}"><table border="1">');
            for (let i in items) {
                if (items.hasOwnProperty(i)) {
                    w.document.write('<tr><td colspan="7">');
                    w.document.write('<b>');
                    w.document.write(new Date(i).toLocaleDateString().substring(0, 10));
                    w.document.write('</b>');
                    w.document.write('</td>');
                    let arr = items[i];
                    for (let j in arr) {
                        if (arr.hasOwnProperty(j)) {
                            let item = arr[j];
                            w.document.write('<tr><td colspan="7" style="padding-left: 8pt; background-color: #767676; color: white;">');
                            w.document.write('<b>');
                            w.document.write(j);
                            w.document.write('</b>');
                            w.document.write('</td>');
                            for (let k in item) {
                                if (item.hasOwnProperty(k)) {
                                    let row = item[k];
                                    w.document.write('<tr><td style="width: 48%; padding-left: 16pt;">');
                                    w.document.write(row.counterparty.value);
                                    if (row.contractNumber) {
                                        w.document.write(contractNumber);
                                        w.document.write(row.contractNumber);
                                        w.document.write(by);
                                        w.document.write(new Date(row.dealDate).toLocaleDateString());
                                    }
                                    if (row.transporter) {
                                        w.document.write('<br><span style="font-size: 10pt">');
                                        w.document.write(transporter);
                                        w.document.write(row.transporter.value);
                                        w.document.write('</span>');
                                    }

                                    w.document.write('</td><td>');
                                    w.document.write(row.product.name);
                                    w.document.write('</td><td>');
                                    w.document.write(row.plan);
                                    w.document.write('</td><td>');
                                    w.document.write(row.shipper);
                                    w.document.write('</td><td>');
                                    w.document.write(row.price);
                                    w.document.write('</td><td>');
                                    row.notes.forEach(function (note) {
                                        w.document.write(note.note + ' ');
                                    });

                                    w.document.write('</td></tr>')
                                }
                            }

                        }
                    }
                    w.document.write('</td></tr>')
                }
            }
            w.document.write('</table></html>');
            w.stop();
            w.print();
        }
    }
});