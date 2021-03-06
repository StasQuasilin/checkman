<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.11.2019
  Time: 17:07
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/drop-menu.css">
<div id="container-header" style="display: inline">
    <script>
        function editTransportation(dealProduct) {
            loadModal('${edit}', {dealProduct:dealProduct});
        }
    </script>
  <c:if test="${not empty add}">
    <button onclick="loadModal('${add}', null, function(a) {
        if (a.code === 0){
            editTransportation(a.product.id);
        } else {
            loadModal('${dealEdit}', {organisation:a.organisation.id,b:'a'}, function(b) {
                if (b.status === 'success'){
                    let deal = b.deal;
                    let products = deal.products;
                    if(products.length > 0){
                        let product = products[0];
                        editTransportation(product.id);
                    }
                }
            })
        }

            })"><fmt:message key="button.add"/> </button>
  </c:if>
    <c:if test="${not empty edit}">
        <button onclick="loadModal('${edit}')">
            <fmt:message key="old.add"/>
        </button>
    </c:if>
    <div class="drop-menu">
      <a class="drop-btn"><fmt:message key="document.print"/></a>
      <div class="drop-menu-content">
<%--        <div class="drop-menu drop-menu-item" onclick="loadModal('${print}')">--%>
<%--          <fmt:message key="document.print"/>--%>
<%--        </div>--%>
        <div class="drop-menu drop-menu-item" onclick="loadModal('${transportCarriages}')">
          <fmt:message key="transport.carriage.head"/>
        </div>
        <c:if test="${role eq 'manager' or role eq 'admin'}">
          <div class="drop-menu drop-menu-item" onclick="loadModal('${transportCost}')">
            <fmt:message key="title.reports.transport.cost"/>
          </div>
        </c:if>
      </div>
    </div>
</div>
</html>
