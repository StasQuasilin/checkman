transportView = {
  props:{
      titles:Object,
      fields: Object,
      item:Object,
      f:Object
  },
    components: {
      'transport-data':transportaData
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

            let g = 0;
            let t = 0;
            let weight = this.item.weight;
            if (weight){
                g = weight.brutto;
                t = weight.tara;
            }

            if (timeIn && timeOut && ((g > 0 && t > 0) || g === 0 && t === 0)){
                classes.push('done');
            } else if ((timeIn && !timeOut) || ((g > 0 && t === 0) || (g === 0 && t > 0))){
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
                        '{{new Date(item.date).toLocaleDateString()}}' +
                    '</span>' +
                    '<span style="display: inline-block; width: 45%;">' +
                        '<b class="secure">' +
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
                            '{{item.product.name}} ' +
                        '</b>' +
                        '<span v-if="item.plan > 0">' +
                            '{{item.plan}} {{item.unit}}' +
                        '</span>' +
                    '</span>' +
                    '<span style="float: right" class="secure">' +
                        '<span v-if="item.price > 0 && f.p">' +
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
                        '{{titles.address}}: ' +
                        '<span class="secure">' +
                            '<template v-if="item.address.region">' +
                                '{{item.address.region}} {{titles.region}}, ' +
                            '</template>' +
                            '<template v-if="item.address.district">' +
                                '{{item.address.district}} {{titles.district}}, ' +
                            '</template>' +
                            '<template v-if="item.address.city">' +
                                '{{item.address.city}}' +
                            '</template>' +
                        '</span> ' +
                    '</div>' +
                '</div>' +
                '<div class="middle-row">' +
                    '<div style="display: inline-block; width: 142px">' +
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
                    '<transport-data :item="item" :titles="titles"></transport-data>' +
                '</div>' +
                '<div class="lower-row" v-if="item.notes.length > 0">' +
                    '<span v-for="note in item.notes" style="padding-left: 2pt">' +
                        '<template v-if="note.creator">' +
                            '{{note.creator}}: ' +
                        '</template>' +
                        '<b class="secure">' +
                            '{{note.note}}' +
                        '</b>' +
                    '</span>' +
                '</div>' +
            '</div>' +
            '<div class="right-field" v-if="item.weight">' +
                '<table class="weight-table" style="border: none">' +
                    '<tr>' +
                        '<td>' +
                            'Б:' +
                        '</td>' +
                        '<td style="text-align: right; width: 100%">' +
                            '<template v-if="item.weight">' +
                                '{{item.weight.brutto.toLocaleString()}}' +
                            '</template>' +
                            '<template v-else>' +
                                '0' +
                            '</template>' +
                        '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td>' +
                            'Т:' +
                        '</td>' +
                        '<td style="text-align: right; width: 100%">' +
                            '<template v-if="item.weight">' +
                                '{{item.weight.tara.toLocaleString()}}' +
                            '</template>' +
                            '<template v-else>' +
                                '0' +
                            '</template>' +
                        '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td>' +
                            'Н:' +
                        '</td>' +
                        '<td style="text-align: right; width: 100%">' +
                            '<template v-if="item.weight">' +
                                '{{item.weight.netto.toLocaleString()}}' +
                            '</template>' +
                            '<template v-else>' +
                                '0' +
                            '</template>' +
                        '</td>' +
                    '</tr>' +
                    '<tr v-if="item.weight.correction > 0">' +
                        '<td colspan="2">' +
                            '<template v-if="item.weight.netto > 0">' +
                                '({{(item.weight.netto * (1 - item.weight.correction / 100)).toLocaleString()}}) ' +
                            '</template>' +
                            '-{{item.weight.correction.toLocaleString()}} %' +
                        '</td>' +
                    '</tr>' +
                '</table>' +
            '</div>' +
            '<div class="right-field" v-if="f.a && (item.analyses.sun || item.analyses.oil)" style="width: 100pt">' +
                '<template v-if="item.analyses.sun">' +
                    '<div>' +
                        '{{titles.sun.humidity1}}:&nbsp;{{item.analyses.sun.humidity1}}' +
                    '</div>' +
                    '<div v-if="item.analyses.sun.humidity2 > 0">' +
                        '{{titles.sun.humidity1}}:&nbsp;{{item.analyses.sun.humidity2}}' +
                    '</div>' +
                    '<div>' +
                        '{{titles.sun.soreness}}:&nbsp;{{item.analyses.sun.soreness}}' +
                    '</div>' +
                    '<div>' +
                        '{{titles.sun.impurity}}:&nbsp;{{item.analyses.sun.oilImpurity}}' +
                    '</div>' +
                    '<div>' +
                        '{{titles.sun.oiliness}}:&nbsp;{{item.analyses.sun.oiliness}}' +
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
            '</div>' +
        '</div>'
};