/**
 * Created by quasilin on 13.03.2019.
 */
var context;

function PostReq(url, parametrs, onSuccess, onError){
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
                onError(xhr.status);
            }
        }
    };

    if (url.substring(0, context.length) != context){
        url = context + url;
    }
    xhr.open('POST', url);
    xhr.send(body.join('&'));
}
function PostApi(url, parameters, onSuccess, onError){
    PostReq(url, parameters, function(answer){
        if (onSuccess){
            try {
                onSuccess(JSON.parse(answer));
            } catch (e){
                console.error('[ Application Core ] Can\'t parse ' + answer)
                if (onError){
                    onError(answer);
                }
            }
        }
    }, function(err){
        if (onError){
            onError(err);
        }
    })
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

