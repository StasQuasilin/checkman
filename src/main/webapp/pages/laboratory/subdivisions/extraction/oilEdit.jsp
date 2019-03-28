<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/editor.css">
<table class="editor">
    <tr>
        <td>
            <fmt:message key="date"/>
        </td>
        <td>
            :
        </td>
        <td>

        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="time"/>
        </td>
        <td>
            :
        </td>
        <td>

        </td>
    </tr>
    <tr>
        <td>
            <label for="humidity">
                <fmt:message key="extraction.crude.humidity"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="humidity" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="acid">
                <fmt:message key="sun.acid.value"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="acid" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="peroxide">
                <fmt:message key="oil.peroxide"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="peroxide" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="phosphorus">
                <fmt:message key="oil.phosphorus"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="phosphorus" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="explosion">
                <fmt:message key="extraction.oil.explosion"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="explosion" type="number" step="0.01" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button>
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
</html>