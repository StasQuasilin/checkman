/**
 * Created by szpt_user045 on 12.07.2019.
 */
var Settings = {
    protocol:'ws',
    address:'',
    context:'',
    api:'/api/subscribes',
    worker:'',
    getAddress:function(){
        return this.protocol + '://' + this.address + this.context + this.api;
    },
    switchWidth:1390
};