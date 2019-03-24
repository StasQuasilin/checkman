/**
 * Created by quasilin on 13.03.2019.
 */
var context;

function PostReq(url, parametrs, onSuccess, onError, debug){
    if (debug) {
        console.log('[ Application Core ] Request to ' + url);
    }
    var body = [];
    if (parametrs != null){
        for (var k in parametrs){
            body[body.length] = k +'='+parametrs[k];
        }
    }
    var xhr = new XMLHttpRequest();
    xhr.onload = function(e){
        if (xhr.readyState == 4){
            if (xhr.status == 200) {
                if (onSuccess) {
                    onSuccess(xhr.responseText);
                }
            } else if (onError){
                onError(xhr.status + ':' + xhr.responseText);
            }
        }
    };

    if (url.substring(0, context.length) != context){
        url = context + url;
    }
    xhr.open('POST', url);
    xhr.send(JSON.stringify(parametrs));
}
function PostApi(url, parameters, onSuccess, onError, debug){
    PostReq(url, parameters, function(answer){
        if (onSuccess){
            if (answer != '') {
                try {
                    var json = JSON.parse(answer)
                    try {
                        onSuccess(json);
                    } catch (on) {
                        console.error('[ Application Core ] ' + on);
                    }

                } catch (e) {
                    console.error('[ Application Core ] Can\'t parse \'' + answer + '\'')
                    if (onError) {
                        onError(answer);
                    }
                }
            } else{
                console.log('[ Application Core ] Empty answer body');
            }
        }
    }, function(err){
        console.error('[ Application Core ] ' + err)
        if (onError){
            onError(err);
        }
    }, debug)
}
function GetChildElemById(parent, childId){
    var elems = parent.getElementsByTagName("*");
    for (var i = 0; i < elems.length; i++){
        if (elems[i].id == childId){
            return elems[i];
        }
    }
}
function valid(input, min){
    if (min && input.value.length < min || input.value == 0){
        input.setAttribute('invalid');
        input.onclick=function(){
            input.removeAttribute('invalid');
        };
        return false;
    }
    return true;
}
function find(url, input, list, onClick){
    var timer;
    var value;
    return function() {
        if (this.value != value && this.value.length > 2) {
            value = this.value

            if (timer) {
                clearTimeout(timer);
            }
            timer = setTimeout(function () {
                $(list).html('');

                var parameters = [];
                parameters.key = input.value;
                PostApi(url, parameters, function (e) {
                    if (e.length > 0) {
                        input.lock=true;
                        list.style.display = 'block';
                        for (var i in e) {
                            console.log(e[i]);
                            var item = document.createElement('div');
                            item.setAttribute('class', 'custom-data-list-item');
                            item.innerText = e[i].value;
                            item.data = e[i];
                            item.onclick = function () {
                                onClick(this.data);
                                list.style.display = 'none';
                            };
                            list.appendChild(item);
                        }
                    } else {
                        input.lock=false;
                        list.style.display = 'none';
                    }
                })
            }, 500)
        }
    }
}
function randomUUID(){
    const s4 = function(){
        return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    }
    return s4() + '-' + s4() + '-' + s4() + s4() + '-' + s4() + '-' + s4();
}
