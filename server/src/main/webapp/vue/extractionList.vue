var extractionList = new Vue({
    el:'#container',
    mixins:[laboratoryList],
    mounted:function(){
        console.log('Extraction list mounted')
        let date = Date.now();
    },
    methods:{
        crudeEdit:function(id, date){
            let data = {
                id:id
            }
            if (date){
                data.date = date.toISOString();
            }
            console.log(data)
            this.edit(this.api.crudeEdit, data);
        },
        edit:function(api, data){
            loadModal(api, data);
        },
        middle:function(item){
            if (!item.middle){
                let middle = {
                    humidityIncome:0,
                    oilinessIncome:0,
                    fraction:0,
                    miscellas:0,
                    humidity:0,
                    explosionTemperature:0,
                    dissolvent:0,
                    grease:0,
                    oilHumidity:0
                }
                let count = 0;
                for (let j in item.crude){
                    if (item.crude.hasOwnProperty(j)){
                        let crude = item.crude[j];
                        if (!crude.empty) {
                            count++;
                            middle.humidityIncome += crude.humidityIncome;
                            middle.oilinessIncome += crude.oilinessIncome;
                            middle.fraction += crude.fraction;
                            middle.miscellas += crude.miscellas;
                            middle.humidity += crude.humidity;
                            middle.explosionTemperature += crude.explosionTemperature;
                            middle.dissolvent += crude.dissolvent;
                            middle.grease += crude.grease;
                            middle.oilHumidity += crude.oilHumidity;
                        }
                    }
                }
                if (count > 0) {
                    middle.humidityIncome /= count;
                    middle.oilinessIncome /= count;
                    middle.fraction /= count;
                    middle.miscellas /= count;
                    middle.humidity /= count;
                    middle.explosionTemperature /= count;
                    middle.explosionTemperature = Math.round(middle.explosionTemperature);
                    middle.dissolvent /= count;
                    middle.dissolvent = Math.round(middle.dissolvent * 10000) / 10000;
                    middle.grease /= count;
                    middle.oilHumidity /= count;
                }
                item.middle = middle;

            }
            return item.middle;
        },
        checkTurn:function(number, date){
            let found = false;
            for (let i in this.items){
                if (this.items.hasOwnProperty(i)){
                    let turn = this.items[i];
                    if (turn.number === number && turn.date === date){
                        found = true;
                        break;
                    }
                }
            }
            if (!found){
                this.initTurn(number, date);
            }
        },
        getCrude:function(item, offset, min){
            let res = [];
            let time = this.buildTime(item.date, offset, min);
            for (let i in item.crude){
                if (item.crude.hasOwnProperty(i)){
                    let crude = item.crude[i];
                    let crudeDate = new Date(crude.time);
                    if (crudeDate.toISOString() === time.toISOString()){
                        res.push(crude);
                        for (let j in item.storageProtein){
                            if (item.storageProtein.hasOwnProperty(j)){
                                let protein = item.storageProtein[j];
                                let proteinDate = new Date(protein.time);
                                if (proteinDate.toISOString() === time.toISOString()){
                                    crude.protein = protein;
                                }
                            }
                        }
                        for (let k in item.storageGrease){
                            if (item.storageGrease.hasOwnProperty(k)){
                                let grease = item.storageGrease[k];
                                let greaseDate = new Date(grease.time);
                                if (greaseDate.toISOString() === time.toISOString()){
                                    crude.storageGrease = grease;
                                }
                            }
                        }
                    }
                }
            }
            if (res.length === 0 && this.api.crudeEdit){
                res.push({
                    id:-1,
                    time:time,
                    empty:true
                })
            }
            return res;
        },
        proteinStorageEdit:function(id){
            if (this.api.proteinStorageEdit) {
                this.edit(this.api.proteinStorageEdit, {id:id});
            }
        },
        greaseStorageEdit:function(id){
            if (this.api.greaseStorageEdit) {
                this.edit(this.api.greaseStorageEdit, {id:id});
            }
        },
        proteinTurnEdit:function(id){
            if (this.api.proteinTurnEdit) {
                this.edit(this.api.proteinTurnEdit, id);
            }
        },
        greaseTurnEdit:function(id){
            if (this.api.greaseTurnEdit) {
                this.edit(this.api.greaseTurnEdit, id);
            }
        },
        oilEdit:function(id){
            if (this.api.oilEdit) {
                this.edit(this.api.oilEdit, id);
            }
        },
        editCellulose:function(id){
            if (this.api.celluloseEdit){
                this.edit(this.api.celluloseEdit, id)
            }
        }
    }
})