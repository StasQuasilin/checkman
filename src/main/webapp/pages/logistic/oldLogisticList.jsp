<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
  <script>
  </script>
  <script src="${context}/vue/templates/laboratoryDataView.vue"></script>
  <script src="${context}/vue/templates/vehicleInput.vue"></script>
  <script src="${context}/vue/templates/transportationDataEdit.vue"></script>
  <script src="${context}/vue/templates/priceView.vue"></script>
  <script src="${context}/vue/templates/commentator.vue"></script>
  <script src="${context}/vue/dataList.vue?v=${now}"></script>
  <script>
    let save = '${save}';
    list.fields = {
      trailer:'<fmt:message key="transportation.automobile.trailer"/>',
      license:'<fmt:message key="driver.license"/>',
      comment:'<fmt:message key="comment.plus"/>',
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
  <jsp:include page="../summary/summaryHeader.jsp"/>
  <jsp:include page="../transportListTemplate.jsp"/>
</html>