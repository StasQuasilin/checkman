<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<script>
    function initFields(holder) {
        holder.counterparty = '<fmt:message key="deal.organisation"/>';
        holder.address = {};
        holder.address['buy'] = '<fmt:message key="download.address"/>';
        holder.address['sell'] = '<fmt:message key="upload.address"/>';
        holder.street = '<fmt:message key="address.street.short"/>';
        holder.region = '<fmt:message key="address.region.short"/>';
        holder.district = '<fmt:message key="address.district.short"/>';
        holder.buy = '<fmt:message key="_buy"/>';
        holder.sell = '<fmt:message key="_sell"/>';
        holder.price = '<fmt:message key="deal.price"/>';
        holder.from = '<fmt:message key="deal.from"/>';
        holder.timeIn = '<fmt:message key="transportation.time.in"/>';
        holder.timeOut = '<fmt:message key="transportation.time.out"/>';
        holder.driver = '<fmt:message key="transportation.driver"/>';
        holder.truck = '<fmt:message key="transportation.automobile"/>';
        holder.phoneIcon = '${context}/images/phone.svg';
        holder.noSpecified = '<fmt:message key="not.specified"/>';
        holder.customer = '<fmt:message key="transport.customer"/>';
        holder.transporter = '<fmt:message key="transportation.transporter"/>';
        holder.szpt='<fmt:message key="szpt"/>';
        holder.cont='<fmt:message key="deal.organisation"/>';
        holder.summary = '<fmt:message key="summary"/>';
        holder.weight = '<fmt:message key="weight"/>';
        holder.net = '<fmt:message key="weight.net"/>';
        holder.analyses = '<fmt:message key="analyses"/>';
        holder.icon = {
            buy:'${context}/images/upload.svg',
            sell:'${context}/images/download.svg'
        };
        holder.sun = {
            humidity1:'<fmt:message key="sun.humidity.1.short"/>',
            humidity2:'<fmt:message key="sun.humidity.2.short"/>',
            soreness:'<fmt:message key="sun.soreness"/>',
            impurity:'<fmt:message key="sun.oil.impurity.short"/>',
            oiliness:'<fmt:message key="sun.oiliness.s"/>',
            contamination:'<fmt:message key="sun.contaminate"/>'
        };
        holder.oil = {
            acid:'<fmt:message key="sun.acid.value"/>',
            peroxide:'<fmt:message key="oil.peroxide"/>',
            phosphorus:'<fmt:message key="oil.phosphorus.mass.fraction"/>'
        };
        holder.meal = {
            humidity:'<fmt:message key="sun.humidity"/>',
            protein:'<fmt:message key="cake.protein"/>',
            cellulose:'<fmt:message key="cake.cellulose"/>',
            oiliness:'<fmt:message key="sun.oiliness"/>'
        };
        holder.missing = '<fmt:message key="missing"/>';
    }
</script>