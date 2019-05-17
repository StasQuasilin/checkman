<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
<style>
  .content table{
    border-collapse: collapse;
  }
  .content table, .content th, .content td {
    border: 1px solid black;
  }
</style>
<div style="padding-left: 24pt">
  <table width="100%" style="font-size: 14pt">
    <c:forEach items="${turns}" var="turn">
      <tr>
        <td align="center">
          <b>
            <fmt:message key="turn"/>&nbsp;${turn.number},
            <fmt:formatDate value="${turn.date}" pattern="dd.MM.yyyy"/>
          </b>
        </td>
      </tr>
      <tr>
        <td class="content">
          <table width="100%" style="font-size: 12pt">
            <tr>
              <td rowspan="2" align="center">
                <fmt:message key="time"/>
              </td>
              <td colspan="3" align="center">
                <fmt:message key="extraction.crude"/>
              </td>
              <td colspan="3" align="center">
                <fmt:message key="extraction.meal"/>
              </td>
              <td colspan="4" align="center">
                <fmt:message key="storage"/>
              </td>
              <td rowspan="2" align="center">
                <fmt:message key="laboratory.creator"/>
              </td>
            </tr>
            <tr>
              <td align="center">
                <fmt:message key="extraction.crude.humidity.income.short"/>
              </td>
              <td align="center">
                <fmt:message key="extraction.crude.small.fraction.short"/>
              </td>
              <td align="center">
                <fmt:message key="extraction.crude.miscellas.short"/>
              </td>
              <td align="center">
                <fmt:message key="extraction.crude.humidity"/>
              </td>
              <td align="center">
                <fmt:message key="extraction.crude.dissolvent.short"/>
              </td>
              <td align="center">
                <fmt:message key="extraction.crude.grease"/>
              </td>
            </tr>
            <c:forEach items="${turn.crudes}" var="crude">
              <tr>
                <td align="center">
                  <fmt:formatDate value="${crude.time}" pattern="HH:mm"/>
                </td>
                <td align="center">
                    ${crude.humidityIncome}
                </td>
                <td align="center">
                    ${crude.fraction}
                </td>
                <td align="center">
                    ${crude.miscellas}
                </td>
                <td align="center">
                    ${crude.humidity}
                </td>
                <td align="center">
                    ${crude.dissolvent}
                </td>
                <td align="center">
                    ${crude.grease}
                </td>
                <c:set var="foundProtein" value="false"/>
                <c:forEach items="${turn.protein}" var="sp">
                  <c:if test="${sp.time eq crude.time}">
                    <c:set var="foundProtein" value="true"/>
                    <td align="center">
                      ${sp.protein}
                    </td>
                    <td align="center">
                        ${sp.humidity}
                    </td>
                  </c:if>
                </c:forEach>
                <c:if test="${not foundProtein}">
                  <td align="center">
                    --
                  </td>
                  <td align="center">
                    --
                  </td>
                </c:if>
                <c:set var="foundGrease" value="false"/>
                <c:forEach items="${turn.greases}" var="sp">
                  <c:if test="${sp.time eq crude.time}">
                    <c:set var="foundGrease" value="true"/>
                    <td align="center">
                        ${sp.grease}
                    </td>
                    <td align="center">
                        ${sp.humidity}
                    </td>
                  </c:if>
                </c:forEach>
                <c:if test="${not foundGrease}">
                  <td align="center">
                    --
                  </td>
                  <td align="center">
                    --
                  </td>
                </c:if>
                <td align="center">
                    ${crude.createTime.creator.person.value}
                </td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div style="display: inline-block">
              <b>
                <fmt:message key="menu.extraction.turn.protein"/>
              </b>
              <c:forEach items="${turn.turnProteins}" var="tp">
                <fmt:message key="cake.protein"/>:&nbsp;${tp.protein},
                <fmt:message key="sun.humidity"/>:&nbsp;${tp.humidity}
              </c:forEach>
            </div>
            <div style="display: inline-block">
              <b>
                <fmt:message key="menu.extraction.turn.raw.grease"/>
              </b>
              <c:forEach items="${turn.turnGreases}" var="tg">
                <fmt:message key="extraction.raw.grease"/>:&nbsp;${tg.grease},
                <fmt:message key="sun.humidity"/>:&nbsp;${tg.humidity}
              </c:forEach>
            </div>
          </div>
          <div>
            <b>
              <fmt:message key="extraction.oil"/>
            </b>
            <c:forEach items="${turn.oils}" var="oil">
              <fmt:message key="sun.humidity"/>:&nbsp;${oil.humidity},
              <fmt:message key="sun.acid.value"/>:&nbsp;${oil.acid},
              <fmt:message key="oil.peroxide"/>:&nbsp;${oil.peroxide},
              <fmt:message key="oil.phosphorus"/>:&nbsp;${oil.phosphorus},
              <fmt:message key="extraction.oil.explosion"/>:&nbsp;${oil.explosionT}
            </c:forEach>

          </div>
        </td>
      </tr>
    </c:forEach>
  </table>
</div>
</html>
