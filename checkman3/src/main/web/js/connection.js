function PostReq(url, parametrs, onSuccess, onError, debug){
    if (url) {
        if (context && url.substring(0, context.length) !== context) {
            url = context + url;
        }

        if (debug) {
            console.log('[ Application Core ] Request to \'' + url + '\'...');
            console.log('<-------Parameters------->');
            console.log(parametrs);
        }
        let xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.onreadystatechange = function (e) {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    if (debug) {
                        console.log('[ Application Core ] Request successfully');
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
        console.error('Empty url!');
        if (onError){
            onError('Empty url!');
        }
    }
}
function PostApi(url, parameters, onSuccess, onError, debug){
    PostReq(url, parameters, function(answer){
        if (onSuccess){
            if (answer !== '') {
                try {
                    let json = JSON.parse(answer);
                    try {
                        onSuccess(json);
                    } catch (on) {
                        console.error('[ Application Core ] ' + on);
                    }

                } catch (e) {
                    console.error('[ Application Core ] Can\'t parse \'' + answer + '\'');
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