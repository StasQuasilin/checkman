<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    closeEvents = [];
    saveEvents = [];
    function addOnCloseEvent(event){
        closeEvents.push(event);
    }
    function closeModal(){
        for (let e in closeEvents){
            if (closeEvents.hasOwnProperty(e)){
                closeEvents[e]();
            }
        }
    }
    function addOnSaveEvent(event){
        saveEvents.push(event);
    }
    function saveModal(result){
        for (let a in saveEvents){
            if (saveEvents.hasOwnProperty(a)){
                let save = saveEvents[a];
                if (typeof save === 'function') {
                    save(result)
                }
            }
        }
        saveEvents = []
    }
</script>
<link rel="stylesheet" href="${context}/css/ModalView.css">
    <div class="modal-wrapper">
        <table style="width: 100%; height: 100%">
            <tr>
                <td>
                    <div class="modal-body">
                        <div class="modal-header-wrapper modal-header">
                            <fmt:message key="${title}"/>
                            <span class="mini-close modal-close" onclick="closeModal()">
                                &times;
                            </span>
                        </div>
                        <div class="modal-content" id="modal-content">
                            <jsp:include page="${modalContent}"/>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</html>