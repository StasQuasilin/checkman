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
            <label for="protein">
                <fmt:message key="cake.protein"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="protein" type="number" step="0.1" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="cellulose">
                <fmt:message key="cake.cellulose"/>
            </label>
        </td>
        <td>
            :
        </td>
        <td>
            <input id="cellulose" type="number" step="0.1" autocomplete="off">
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