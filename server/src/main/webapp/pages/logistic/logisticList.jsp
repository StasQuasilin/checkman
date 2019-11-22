<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <script>
  if (typeof laboratoryView === 'undefined'){
    laboratoryView = {
      template:'<div><!--Laboratory view can be here--></div>'
    }
  }
  </script>
  <script src="${context}/vue/templates/vehicleInput.vue"></script>
  <script src="${context}/vue/templates/transportationDataEdit.vue"></script>
  <script src="${context}/vue/dataList.vue"></script>
  <script>
    list.fields = {
      driverProps:{
        find:'--',
        add:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="transportation.driver.insert.info"/>',
        put:function(driver){
          console.log(driver);
        },
        show:['value']
      },
      vehicleProps:{
        find:'',
        add:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="transportation.automobile.insert.info"/>',
        put:function(vehicle){
          console.log(vehicle);
        },
        show:['model', 'number']
      },
      trailerProps:{
        find:'',
        add:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.trailer"/>',
        put:function(trailer){
          console.log(trailer);
        },
        show:['number']
      },
      customerProps:{
        find:'',
        add:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.customer"/>',
        put:function(trailer){
          console.log(trailer);
        },
        show:['number']
      }
    };
  </script>
  <jsp:include page="../transportListTemplate.jsp"/>
</html>