plan = new Vue({
        el: '#dealShow',
        components: {
            'object-input': objectInput
        },
        data: {
            api: {},
            deal: null,
            selectedProduct: -1,
            dealId: 0,
            dateFrom: '',
            dateTo: '',
            quantity: 0,
            unit: '',
            customers: [],
            plans: {},
            items: {},
            foundVehicles: [],
            foundDrivers: [],
            fnd: -1,
            upd: -1,
            picker: false,
            worker: {},
            filterDate: -1,
            tab: 'vehicles',
            tabs: [
                'vehicles'
            ],
            tabNames: {}
        },
        methods: {
            totalAmount: function () {
                console.log('calculate total amount');
                let total = 0;
                for (let i in this.items) {
                    if (this.items.hasOwnProperty(i)) {
                        let plan = this.items[i];

                        for (let j in plan.products) {
                            if (plan.products.hasOwnProperty(j)) {
                                let product = plan.products[j];
                                let a = parseFloat(product.amount);

                                if (!isNaN(a)) {
                                    total += a;
                                }
                            }
                        }
                    }
                }
                return total;
            },
            totalAmountString: function () {
                let total = this.totalAmount();
                let quantity = this.deal.products[this.selectedProduct].quantity;
                return total.toLocaleString() + ' / ' + quantity + ' ( ' + (total / quantity * 100).toLocaleString() + '% )';
            },
            totalFactString: function () {
                let total = this.totalFact();
                let quantity = this.deal.products[this.selectedProduct].quantity;
                return total.toLocaleString() + ' / ' + quantity + ' ( ' + (total / quantity * 100).toLocaleString() + '% )';
            },
            totalFact: function () {
                let total = 0;
                for (let i in this.items) {
                    if (this.items.hasOwnProperty(i)) {
                        let item = this.items[i];
                        for (let j in item.products) {
                            if (item.products.hasOwnProperty(j)) {
                                let product = item.products[j];
                                if (product.weight) {
                                    let g = product.weight.gross;
                                    let t = product.weight.tare;
                                    if (g > 0 && t > 0) {
                                        total += (g - t);
                                    }
                                }
                            }
                        }
                    }
                }
                return total;
            },
            selectProduct: function (idx) {
                this.selectedProduct = idx;

                const self = this;
                this.items = {};
                self.plans = [];

                subscribe('TRANSPORT_' + this.deal.type.toUpperCase(), function (data) {
                    if (data.update) {
                        let p = self.deal.products[idx].id;
                        for (let u = 0; u < data.update.length; u++) {
                            let update = data.update[u];
                            let products = update.products;
                            for (let i = 0; i < products.length; i++) {
                                let product = products[i];
                                if (product.dealProduct === p) {
                                    self.addItem(update);
                                } else {
                                    self.deleteItem(update);
                                }
                            }
                        }
                    }
                    if (data.delete) {
                        for (let d = 0; d < data.delete.length; d++) {
                            let remove = data.delete[d];
                            if (self.items[remove.id]) {
                                self.deleteItem(remove);
                            }
                        }

                    }
                });
            },
            deleteItem: function (item) {
                if (this.items[item.id]) {
                    delete this.items[item.id];
                    this.$forceUpdate();
                }
            },
            messages: function () {
                return [];
            },
            newCarriage: function () {

            },
            newVehicle: function () {
                let date = this.filterDate === -1 ? new Date() : new Date(this.filterDate);
                let id = -randomNumber();
                while (this.items[id]) {
                    id = -randomNumber();
                }
                this.add({
                    id: id,
                    date: date.toISOString().substring(0, 10),
                    products: [
                        {
                            id: -1,
                            amount: 0,
                            dealProduct: this.deal.products[this.selectedProduct].id
                        }
                    ],
                    customer: this.customers[0].id,

                })
            },
            itemsByDate: function (date) {
                let result = {
                    count: 0,
                    weight: 0
                };
                let items = Object.values(this.plans).filter(function (item) {
                    return item.date === date;
                });
                for (let i in items) {
                    if (items.hasOwnProperty(i)) {
                        result.count++;
                        result.weight += parseInt(items[i].plan);
                    }
                }
                return result;
            },
            dates: function () {
                let dates = [];
                for (let i in this.plans) {
                    if (this.plans.hasOwnProperty(i)) {
                        let date = this.plans[i].date;
                        if (!dates.includes(date)) {
                            dates.push(date);
                        }
                    }
                }
                dates.sort(function (a, b) {
                    return new Date(b) - new Date(a);
                });
                return dates;
            },
            getPlans: function () {
                if (this.filterDate === -1) {
                    return Object.values(this.items);
                } else {
                    const self = this;
                    return Object.values(this.items).filter(function (item) {
                        return item.date === self.filterDate;
                    })
                }
            },
            selectDate: function (date) {
                if (this.filterDate === date) {
                    this.filterDate = -1;
                } else {
                    this.filterDate = date;
                }
            },
            copy: function (idx) {
                let p = Object.assign({}, this.plans[idx].item);
                console.log(p);
                p.id = -1;
                p.timeIn = null;
                p.timeOut = null;
                p.weight = null;
                p.sunAnalyses = null;
                p.oilAnalyses = null;
                p.mealAnalyses = null;
                p.archive = false;
                p.done = false;
                this.initSaveTimer(this.add(p));
            },
            add: function (plan) {
                if (!plan.vehicle) {
                    plan.vehicle = {id: -1}
                }
                if (!plan.trailer) {
                    plan.trailer = {id: -1}
                }
                if (!plan.transporter) {
                    plan.transporter = {id: -1}
                }

                this.addItem(plan);
                // this.plans.push(p);
                // this.sort();
            },
            addItem: function (item) {
                this.items[item.id] = (item);
                this.$forceUpdate();
            },
            initSaveTimer: function (item) {
                console.log(item);
                const self = this;
                if (item.saveTimer !== -1) {
                    clearTimeout(item.saveTimer);
                }

                item.saveTimer = setTimeout(function () {
                    item.saveTimer = -1;
                    self.save(item);
                }, 1500);
            },
            update: function (plan) {
                let found = false;
                for (let p in this.plans) {
                    if (this.plans.hasOwnProperty(p)) {
                        if (!this.plans[p].item.id) {
                            this.plans.splice(p, 1);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    this.add(plan);
                }
            },
            remove: function (id) {
                const _id = id;
                let plan = this.plans[id].item;
                if (plan.id > 0) {
                    console.log('remove ' + plan.id);
                    const self = this;
                    PostApi(this.api.remove, {id: plan.id}, function (a) {
                        if (a.status === 'success') {
                            self.plans.splice(_id, 1);
                        }
                    })
                } else {
                    this.plans.splice(id, 1);
                }
            },
            save: function (item) {
                if (item) {
                    let plan = Object.assign({}, item);
                    if (item.driver) {
                        plan.driver = item.driver.id;
                    }
                    if (item.vehicle) {
                        plan.vehicle = item.vehicle.id;
                    }
                    if (item.trailer) {
                        plan.trailer = item.trailer.id;
                    }
                    let transporter = -1;
                    if (item.transporter) {
                        transporter = item.transporter.id;
                    }
                    plan.transporter = transporter;
                    plan.notes = Object.assign([], item.notes);
                    for (let i in plan.notes) {
                        if (plan.notes.hasOwnProperty(i)) {
                            delete plan.notes[i].creator;
                        }
                    }

                    PostApi(this.api.save, {deal: this.dealId, product: this.selectedProduct, plan: plan}, function (a) {
                        console.log(a);
                        if (a.status === 'success') {
                            if (a.id) {
                                item.id = a.id;
                            }
                        }
                    });
                }
            },
            focusInput: function () {
                setTimeout(function () {
                    var inp = document.getElementById('input');
                    if (inp) {
                        inp.focus();
                    } else {
                        console.log('input not found')
                    }
                }, 500)
            },
            setDriver: function (driver, item) {
                console.log(item);
                item.driver = driver;
                if (driver.vehicle && (typeof item.vehicle === "undefined" || item.vehicle.id === -1)) {
                    this.setVehicle(driver.vehicle, item);
                } else {
                    this.initSaveTimer(item);
                }
                if (driver.organisation && (typeof item.transporter === "undefined" || item.transporter.id === -1)) {
                    this.setTransporter(driver.organisation, item);
                }
            },
            setVehicle: function (vehicle, item) {
                item.vehicle = vehicle;
                if (vehicle.trailer && (typeof item.trailer === "undefined" || item.trailer.id === -1)) {
                    this.setTrailer(vehicle.trailer, item);
                } else {
                    this.initSaveTimer(item);
                }
            },
            setTrailer: function (trailer, item) {
                item.trailer = trailer;
                this.initSaveTimer(item);
            },
            setTransporter: function (transporter, item) {
                item.transporter = transporter;
                this.initSaveTimer(item);
            },
            weight: function (item) {
                if (typeof item.weight === 'undefined') {
                    console.log('calculate weight');
                    let w = {
                        brutto: 0,
                        tara: 0,
                        netto: function () {
                            if (brutto > 0 && tara > 0) {
                                return brutto - tara;
                            } else {
                                return 0;
                            }
                        }
                    };
                    let weight = item.weight[i];
                    w.brutto += weight.brutto;
                    w.tara += weight.tara;
                    item.weight = w;
                }

                return item.weight;
            },

            goBack: function () {
                loadContent(this.api.back, this.api.attributes);
            },
            stop: function () {
                clearTimeout(this.upd);
            },
            dateTimePicker: function (item) {
                const self = this;
                datepicker.show(function (date) {
                    item.date = date;
                    self.sort();
                    self.initSaveTimer(item);
                }, item.date)
            },
            sort: function () {
                this.plans.sort(function (a, b) {
                    return new Date(b.item.date) - new Date(a.item.date)
                })
            },
            different: function (w, plan) {
                let net = w.netto;
                if (net > 0 && net !== plan) {
                    let d = net - plan;
                    if (d > 0) {
                        d = '+' + (d).toLocaleString();
                    } else {
                        d = (d).toLocaleString();
                    }
                    return '(' + d + ')';
                }
            },
            addNote: function (key) {
                for (let i in this.plans) {
                    if (this.plans.hasOwnProperty(i)) {
                        this.plans[i].editNote = this.plans[i].key === key;
                    }
                }
                this.focusInput();
            },
            editNote: function (item, id) {
                item.editNote = true;
                if (id !== undefined) {
                    item.noteId = item.notes[id].id;
                    item.noteInput = item.notes[id].note;
                    item.notes.splice(id, 1);
                }
                this.focusInput();
            },
            saveNote: function (item) {
                if (item.noteInput) {
                    item.notes.push({
                        id: item.noteId,
                        creator: this.worker,
                        note: item.noteInput
                    });
                    item.noteInput = '';
                    item.editNote = false;
                    this.initSaveTimer(item);
                }
            },
            closeNote: function (key) {
                for (let i in this.plans) {
                    if (this.plans.hasOwnProperty(i)) {
                        this.plans[i].editNote = false;
                    }
                }
            },
            removeNote: function (item, id) {
                item.notes.splice(id, 1);
                this.initSaveTimer(item);
            }

        }
    }
);