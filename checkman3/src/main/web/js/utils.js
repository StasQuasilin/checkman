function GetChildElemById(parent, childId){
    let elements = parent.getElementsByTagName("*");
    for (let i = 0; i < elements.length; i++){
        if (elements[i].id === childId){
            return elements[i];
        }
    }
}