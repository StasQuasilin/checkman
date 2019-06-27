/**
 * Created by quasilin on 13.03.2019.
 */

function PostReq(url, parametrs, onSuccess, onError, debug){
    if (url) {
        if (context && url.substring(0, context.length) != context) {
            url = context + url;
        }

        if (debug) {
            console.log('[ Application Core ] Request to \'' + url + '\'...');
        }
        var xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.onreadystatechange = function (e) {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    if (debug) {
                        console.log('[ Application Core ] Request successfuly');
                    }
                    if (onSuccess) {
                        onSuccess(xhr.responseText);
                    }
                }else if (xhr.status === 401) {
                    location.reload();
                } else if (onError) {
                    onError(xhr.status + ':' + xhr.statusText);
                } else {
                    console.error(xhr.status + ':' + xhr.statusText)
                }
            }
        };


        xhr.send(JSON.stringify(parametrs));
    } else {
        console.error('Empty url!!!');
    }
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
    var Timer;
    var value;
    return function() {
        if (this.value != value && this.value.length > 2) {
            value = this.value

            if (Timer) {
                clearTimeout(Timer);
            }
            Timer = setTimeout(function () {
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
Vue.rowName = function(date){
    var d = new Date(date);
    return 'container-item-' + d.getDay()
}
