transportView = {
  props:{
      titles:Object,
      fields: Object,
      item:Object
  },
    computed:{
      itemDate:function () {
          return new Date(this.item.date).toLocaleDateString();
      }
    },
    methods:{
        dynamicClass:function () {
            let classes = [];
            let timeIn = this.item.timeIn;
            let timeOut = this.item.timeOut;
            if (timeIn && timeOut){
                classes.push('done');
            } else if (timeIn && !timeOut){
                classes.push('loading');
            } else if (!timeIn && timeOut){
                classes.push('voyage')
            } else {
                classes.push('container-item-' + new Date(this.item.date).getDay())
            }
            return classes;
        },
        buildTime:function (value) {
            let time = new Date(value);

            let result = '';
            if (this.itemDate !== time.toLocaleDateString()){
                result += time.toLocaleDateString().substring(0, 5) + ' '
            }
            result += time.toLocaleTimeString().substring(0, 5);
            return result;
        }
    },
    template:'<div class="container-item" :class="dynamicClass()">' +
            '<div style="width: 100%">' +
                '<div class="upper-row">' +
                    '<span style="display: inline-block; width: 80px">' +
                        '{{itemDate}}' +
                    '</span>' +
                    '<span style="display: inline-block; width: 45%;">' +
                        '<b>' +
                            '{{item.counterparty.value}}' +
                        '</b>' +
                        '<span v-if="item.contractNumber">' +
                            '<span class="label">' +
                            '</span>' +
                            '<b>' +
                                '{{item.contractNumber}}' +
                            '</b>' +
                        '</span>' +
                    '</span>' +
                    '<span>' +
                        '<img style="width: 12pt" :src="titles.icon[item.type]" :title="titles[item.type]" :alt="titles[item.type]"/>' +
                        '<b>' +
                            '{{item.product.name}}' +
                        '</b>' +
                    '</span>' +
                    '<span style="float: right">' +
                        '<span v-if="item.price > 0">' +
                            '<span class="label">' +
                                '{{titles.price}}: ' +
                            '</span>' +
                            '{{item.price.toLocaleString()}} ' +
                        '</span>' +
                        '<span class="label">' +
                            '{{titles.from}}: ' +
                        '</span>' +
                        '<span>' +
                            '{{item.shipper}}' +
                        '</span>' +
                    '</span>' +
                    '<div v-if="item.address">' +
                        '{{item.address}}' +
                    '</div>' +
                '</div>' +
                '<div class="middle-row">' +
                    '<div style="display: inline-block; width: 130px">' +
                        '<div>' +
                            '{{titles.timeIn}}: ' +
                            '<span v-if="item.timeIn">' +
                                '{{buildTime(item.timeIn.time)}}' +
                            '</span>' +
                            '<span v-else>' +
                                '--:--' +
                            '</span>' +
                        '</div>' +
                        '<div>' +
                            '{{titles.timeOut}}: ' +
                            '<span v-if="item.timeOut">' +
                                '{{buildTime(item.timeOut.time)}}' +
                            '</span>' +
                            '<span v-else>' +
                                '--:--' +
                            '</span>' +
                        '</div>' +
                    '</div>' +
                    '<div class="data-block">' +
                        '<div>' +
                            // '{{titles.driver}}: ' +
                            '<span v-if="item.driver">' +
                                '<b>' +
                                    '{{item.driver.person.value}}' +
                                '</b>' +
                                '<span v-if="item.driver.license" style="display: inline-block; width: 90px; float: right; text-align: right">' +
                                    '{{item.driver.license}}' +
                                '</span>' +
                            '</span>' +
                            '<b v-else>' +
                                '{{titles.noSpecified}}' +
                            '</b>' +
                        '</div>' +
                        '<div>' +
                            // '{{titles.truck}}: ' +
                            '<span v-if="item.vehicle">' +
                                '{{item.vehicle.model}}' +
                                '<span class="vehicle-number">' +
                                    '{{item.vehicle.number}}' +
                                '</span>' +
                                '<span v-if="item.vehicle.trailer" class="vehicle-number">' +
                                    '{{item.vehicle.trailer.number}}' +
                                '</span>' +
                            '</span>' +
                            '<span v-else>' +
                                '{{titles.noSpecified}}' +
                            '</span>' +
                        '</div>' +
                        '<div v-if="item.driver && item.driver.person.phones.length > 0">' +
                            '<img style="width: 10pt" :src="titles.phoneIcon" alt=""/>' +
                            '<span style="padding-left: 2px" v-for="phone in item.driver.person.phones">' +
                                '{{phone.number}}' +
                            '</span>' +
                        '</div>' +
                    '</div>' +
                    '<div class="data-block">' +
                        '<div>' +
                            '{{titles.customer}}: {{titles[item.customer]}}' +
                        '</div>' +
                        '<div>' +
                            '{{titles.transporter}}: ' +
                            '<b v-if="item.transporter">' +
                                '{{item.transporter.value}}' +
                            '</b>' +
                            '<b v-else>' +
                                '{{titles.noSpecified}}' +
                            '</b>' +
                        '</div>' +
                    '</div>' +
                '</div>' +
                '<div class="lower-row" v-if="item.notes.length > 0">' +
                    '<span v-for="note in item.notes">' +
                        '<template v-if="note.creator">' +
                            '{{note.creator}}: ' +
                        '</template>' +
                        '<b>' +
                            '{{note.note}}' +
                        '</b>' +
                    '</span>' +
                '</div>' +
            '</div>' +
            '<div class="right-field">' +
                '<div class="label" style="width: 100%; text-align: center">' +
                    '{{titles.weight}}' +
                '</div>' +
                '<div>' +
                    'Б:\t' +
                    '<b v-if="item.weight">' +
                        '{{item.weight.brutto.toLocaleString()}}' +
                    '</b>' +
                    '<template v-else>' +
                        '0' +
                    '</template>' +
                '</div>' +
                '<div>' +
                    'Т:\t' +
                    '<b v-if="item.weight">' +
                        '{{item.weight.tara.toLocaleString()}}' +
                    '</b>' +
                    '<template v-else>' +
                        '0' +
                    '</template>' +
                '</div>' +
                '<div>' +
                    'Н:\t' +
                    '<b v-if="item.weight">' +
                        '{{item.weight.netto.toLocaleString()}}' +
                    '</b>' +
                    '<template v-else>' +
                        '0' +
                    '</template>' +
                '</div>' +
            '</div>' +
            '<div class="right-field">' +
                '<div class="label" style="width: 100%; text-align: center">' +
                    '{{titles.analyses}}' +
                '</div>' +
                '<template v-if="item.analyses.sun">' +
                    '<div class="label">' +
                        '{{titles.sun.humidity1}}: {{item.analyses.sun.humidity1}}' +
                    '</div>' +
                    '<div class="label" v-if="item.analyses.sun.humidity2 > 0">' +
                        '{{titles.sun.humidity1}}: {{item.analyses.sun.humidity2}}' +
                    '</div>' +
                    '<div class="label">' +
                        '{{titles.sun.soreness}}: {{item.analyses.sun.soreness}}' +
                    '</div>' +
                    '<div class="label">' +
                        '{{titles.sun.impurity}}: {{item.analyses.sun.oilImpurity}}' +
                    '</div>' +
                    '<div class="label">' +
                        '{{titles.sun.oiliness}}: {{item.analyses.sun.oiliness}}' +
                    '</div>' +
                    '<div v-if="item.analyses.sun.contamination" style="color: orange">' +
                        '{{titles.contamination}}' +
                    '</div>' +
                '</template>' +
                '<template v-else-if="item.analyses.oil">' +
                    '<div class="label">' +
                        '{{titles.acid}}: {{item.analyses.oil.acid}}' +
                    '</div>' +
                    '<div class="label">' +
                        '{{titles.peroxide}}: {{item.analyses.oil.peroxide}}' +
                    '</div>' +
                    '<div class="label">' +
                        '{{titles.phosphorus}}: {{item.analyses.oil.phosphorus}}' +
                    '</div>' +
                '</template>' +
                '<div v-else>' +
                    '{{titles.missing}}' +
                '</div>' +
            '</div>' +
        '</div>'
};