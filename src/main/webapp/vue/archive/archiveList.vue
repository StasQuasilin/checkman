let archive = new Vue({
    el: '#archive',
    data: {
        filter: filterControl,
        api: {
            update: '',
            show: ''
        },
        items: [],
        upd: -1
    },
    methods: {
        updReq: function () {
            const self = this;
            let parameters = {};
            let items = {};
            for (var i in this.items) {
                if (this.items.hasOwnProperty(i)) {
                    let item = this.items[i];
                    items[item.item.id] = item.item.hash;
                }
            }
            parameters.items = items;
            PostApi(self.api.update, parameters, function (e) {
                if (e.add.length || e.update.length || e.remove.length) {
                    console.log(e);
                    for (let a in e.add) {
                        if (e.add.hasOwnProperty(a)) {
                            self.add(e.add[a])
                        }
                    }
                    for (let u in e.update) {
                        if (e.update.hasOwnProperty(u)) {
                            self.update(e.update[u])
                        }
                    }
                    for (let r in e.remove) {
                        if (e.remove.hasOwnProperty(r)) {
                            self.remove(e.remove[r])
                        }
                    }
                    self.items.sort(function (a, b) {
                        return new Date(b.item.date) - new Date(b.item.date);
                    })
                }
                self.upd = setTimeout(function () {
                    self.updReq()
                }, 5000)
            })
        },
        add: function (item) {
            this.items.push({
                item: item
            });
        },
        update: function (item) {
            for (let i in this.items) {
                if (this.items.hasOwnProperty(i)) {
                    let tmp = this.items[i];
                    if (tmp.item.id === item.id) {
                        tmp.item = item;
                        break;
                    }
                }
            }
        },
        remove: function (id) {
            for (let i in this.items) {
                if (this.items.hasOwnProperty(i)) {
                    let tmp = this.items[i];
                    if (tmp.item.id === id) {
                        this.items.splice(i, 1);
                        break;
                    }
                }
            }
        }
    }
});