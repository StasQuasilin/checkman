transportView = {
  props:{
      titles:Object,
      fields: Object,
      item:Object,
      f:Object,
      props:Object
  },
    components: {
      'transport-data':transportaData,
        'commentator':commentator
    },
    computed:{
      itemDate:function () {
          return new Date(this.item.date).toLocaleDateString();
      },
        analyses:function () {
            let analyses = [];
            for (let i = 0 ; i < this.item.products.length; i++){
                let product = this.item.products[i];
                if (product.sun || product.oil){
                    analyses.push(product);
                }
            }
            return analyses;
        },
        weightTitle:function () {
            let title = '';
            for(let i = 0; i < this.item.products.length; i++){
                let product = this.item.products[i];
                if (product.weight){
                    title += product.productName + ', ' + product.shipperName + '\n';
                    let w = product.weight;
                    let g = w.brutto;
                    title += '- Б:\t' + g.toLocaleString() + '\n';
                    let t = w.tara;
                    title += '- Т:\t' + t.toLocaleString() + '\n';
                    let n = 0;
                    if (g > 0 && t > 0){
                        n = g - t;
                    }
                    title += '- Н:\t' + n.toLocaleString() + '\n';
                }
            }
            return title;
        }
    },
    methods:{
        dynamicClass:function () {
            let classes = [];
            let timeIn = this.item.timeIn;
            let timeOut = this.item.timeOut;

            let g = this.item.gross;
            let t = this.item.tare;

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
                '<div class="upper-row" style="display: flex; flex-direction: row; width: 100%">' +
                    '<span style="display: inline-block; width: 50px">' +
                        '{{new Date(item.date).toLocaleDateString().substring(0, 5)}}' +
                    '</span>' +
                    '<div style="display: flex; flex-direction: column; width: 100%">' +
                        '<div v-for="product in item.products" v-if="product.counterparty">' +
                            '<span style="display: inline-block; min-width: 30%; max-width: 45%;">' +
                                '<b class="secure" >' +
                                    '{{product.counterparty.value}}' +
                                '</b>' +
                                '<span v-if="item.contractNumber">' +
                                    '<b>' +
                                        '{{product.contractNumber}}' +
                                    '</b>' +
                                '</span>' +
                            '</span>' +
                            '<img style="width: 12pt" :src="titles.icon[product.type]" :title="titles[product.type]" :alt="titles[product.type]"/>' +
                            '<b>' +
                                '{{product.productName}} ' +
                            '</b>' +
                            '<template v-if="!product.product.group">' +
                                '<span v-if="product.amount > 0">' +
                                    '{{product.amount}} {{product.unitName}} ' +
                                '</span>' +
                                '<span v-if="product.price > 0">' +
                                    '{{titles.price}}: ' +
                                    '{{product.price.toLocaleString()}} ' +
                                '</span>' +
                            '</template>' +
                            '{{titles.from}}: ' +
                            '<span>' +
                                '{{product.shipperName}}' +
                            '</span>' +
                            '<template v-if="product.weight">' +
                                '<b v-if="product.weight.net > 0">' +
                                    ' H: {{product.weight.net.toLocaleString()}}' +
                                '</b>' +
                                '<b v-else-if="product.weight.gross > 0">' +
                                    ' Б: {{product.weight.gross.toLocaleString()}}' +
                                '</b>' +
                                '<b v-else-if="product.weight.tare > 0">' +
                                    ' T: {{product.weight.tare.toLocaleString()}}' +
                                '</b>' +
                            '</template>' +
                            '<b v-if="product.weight && product.weight.correction > 0">' +
                                ' -{{product.weight.correction.toLocaleString()}}%' +
                                '<template v-if="product.weight.net > 0">' +
                                    '={{product.weight.corrected.toLocaleString()}}' +
                                '</template>' +
                            '</b>' +
                            '<div v-if="product.address">' +
                                '{{titles.address[product.type]}}: ' +
                                '<span class="secure">' +
                                    '<template v-if="product.address.region">' +
                                        '{{product.address.region}} {{titles.region}}, ' +
                                    '</template>' +
                                    '<template v-if="product.address.district">' +
                                        '{{product.address.district}} {{titles.district}}, ' +
                                    '</template>' +
                                    '<template v-if="product.address.city">' +
                                        '{{product.address.city}}' +
                                    '</template>' +
                                    '<template v-if="product.address.street">' +
                                        ',{{titles.street}} {{product.address.street}}' +
                                        '<template v-if="product.address.build">' +
                                            ', {{product.address.build}}' +
                                        '</template>\n' +
                                    '</template>' +
                                '</span> ' +
                            '</div>' +
                        '</div>' +
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
                    '<transport-data :item="item" :titles="titles" :props="props"></transport-data>' +
                '</div>' +
                '<div style="text-align: right; font-size: 8pt">' +
                    '<template>' +
                        '{{titles.creator}}: {{item.create.creator.person.value}} {{new Date(item.create.time).toLocaleString().substring(0, 17)}}' +
                    '</template>' +
                    '<template v-if="item.manager && item.create.creator.id !== item.manager.id">' +
                        ', {{titles.manager}}: {{item.manager.person.value}}' +
                    '</template>' +
                '</div>' +
                '<div class="lower-row">' +
                    '<commentator :item="item" :props="props.notesProps"></commentator>' +
                '</div>' +
            '</div>' +
            '<div class="right-field" v-if="item.gross > 0 || item.tare > 0">' +
                '<table :title="weightTitle" class="weight-table" style="border: none">' +
                    '<tr v-if="item.products.length > 1">' +
                        '<td colspan="2" style="text-align: center">' +
                            '{{titles.summary}}' +
                        '</td> ' +
                    '</tr>' +
                    '<tr>' +
                        '<td>' +
                            'Б:' +
                        '</td>' +
                        '<td style="text-align: right; width: 100%">' +
                            '{{item.gross.toLocaleString()}}' +
                        '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td>' +
                            'Т:' +
                        '</td>' +
                        '<td style="text-align: right; width: 100%">' +
                            '{{item.tare.toLocaleString()}}' +
                        '</td>' +
                    '</tr>' +
                    '<tr>' +
                        '<td>' +
                            'Н:' +
                        '</td>' +
                        '<td style="text-align: right; width: 100%">' +
                            '<template v-if="item.gross > 0 && item.tare > 0">' +
                                '{{(item.gross - item.tare).toLocaleString()}}' +
                            '</template>' +
                            '<template v-else>' +
                                '0' +
                            '</template>' +
                        '</td>' +
                    '</tr>' +
                '</table>' +
            '</div>' +
            '<div class="right-field" v-if="analyses.length > 0" style="width: 100pt; overflow-y: scroll">' +
                '<div v-for="a in analyses">' +
                    '<div style="font-size: 8pt; overflow: hidden; border-bottom: dashed gray 1px" v-if="a.counterparty">' +
                        '{{a.counterparty.value}}' +
                    '</div>' +
                    '<template v-if="a.sun">' +
                        '<div>' +
                            '{{titles.sun.humidity1}}:&nbsp;{{a.sun.humidity1}}' +
                        '</div>' +
                        '<div v-if="a.sun.humidity2 > 0">' +
                            '{{titles.sun.humidity1}}:&nbsp;{{a.sun.humidity2}}' +
                        '</div>' +
                        '<div>' +
                            '{{titles.sun.soreness}}:&nbsp;{{a.sun.soreness}}' +
                        '</div>' +
                        '<div>' +
                            '{{titles.sun.impurity}}:&nbsp;{{a.sun.oilImpurity}}' +
                        '</div>' +
                        '<div>' +
                            '{{titles.sun.oiliness}}:&nbsp;{{a.sun.oiliness}}' +
                        '</div>' +
                        '<div v-if="a.sun.contamination" style="color: orange">' +
                            '{{titles.contamination}}' +
                        '</div>' +
                    '</template>' +
                    '<template v-else-if="a.oil">' +
                        '<div class="label">' +
                            '{{titles.oil.acid}}:&#9;{{a.oil.acidValue}}' +
                        '</div>' +
                        '<div class="label">' +
                            '{{titles.oil.peroxide}}:&#9;{{a.oil.peroxideValue}}' +
                        '</div>' +
                        '<div class="label">' +
                            '{{titles.oil.phosphorus}}:&#9;{{a.oil.phosphorus}}' +
                        '</div>' +
                        '<div class="label">' +
                            'Вол:&#9;{{a.oil.humidity}}' +
                        '</div>' +
                    '</template>' +
                '</div>' +
            '</div>' +
        '</div>'
};