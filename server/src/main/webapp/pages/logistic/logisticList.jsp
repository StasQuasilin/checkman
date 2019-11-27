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
  <script src="${context}/vue/templates/priceView.vue"></script>
  <script src="${context}/vue/dataList.vue"></script>
  <script>
    var save = '${save}';
    list.fields = {
      trailer:'<fmt:message key="transportation.automobile.trailer"/>',
      driverProps:{
        find:'${findDriver}',
        add:'${parseDriver}',
        edit:'${editDriver}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="transportation.driver.insert.info"/>',
        put:function(driver, item){
          if (item) {
            PostApi(save, {id: item.id, driver: driver.id});
          }
        },
        show:['person/surname','person/forename','person/patronymic']
      },
      vehicleProps:{
        find:'${findVehicle}',
        add:'${parseVehicle}',
        edit:'${editVehicle}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.vehicle"/>',
        put:function(vehicle, item){
          if (item) {
            PostApi(save, {id: item.id, vehicle: vehicle.id});
          }
        },
        show:['model', 'number']
      },
      trailerProps:{
        find:'${findTrailer}',
        add:'${parseTrailer}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.trailer"/>',
        put:function(trailer, item){
          if (item) {
            PostApi(save, {id: item.id, trailer: trailer.id});
          }
        },
        show:['number']
      },
      transporterProps:{
        find:'${findOrganisation}',
        add:'${parseOrganisation}',
        edit:'${editOrganisation}',
        addHeader:'<fmt:message key="button.add"/>',
        header:'<fmt:message key="button.add.transporter"/>',
        put:function(transporter, item){
          if(item) {
            PostApi(save, {id: item.id, transporter: transporter.id});
          }
        },
        show:['value']
      }
    };
  </script>
  <jsp:include page="../transportListTemplate.jsp"/>
</html>