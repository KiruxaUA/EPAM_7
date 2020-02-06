<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<% String linkToImg = "img/"; %>

<fmt:setLocale value= "${param.lang}" />
<fmt:setBundle basename="messages" />

<html lang="${param.lang}">
<head>
    <title>CRUD Application</title>
</head>
<body>
<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=de"><fmt:message key="label.lang.de" /></a></li>
</ul>
<h1>
    CRUD Application
</h1>
<h2>
    <fmt:message key="entities" />
</h2>
<ul>
    <li><fmt:message key="entities.developer" /></li>
    <li><fmt:message key="entities.skill" /></li>
    <li><fmt:message key="entities.account" /></li>
</ul>
<h2>
    <fmt:message key="model" />
</h2>
<ul>
    <li><fmt:message key="model.developer" /></li>
    <li><fmt:message key="model.skill" /></li>
    <li><fmt:message key="model.account" /></li>
</ul>
<h2>
    <fmt:message key="storage" />
</h2>
<ul>
    <li><fmt:message key="storage.accounts" /></li>
    <li><fmt:message key="storage.skills" /></li>
    <li><fmt:message key="storage.developers" /></li>
    <li><fmt:message key="storage.developerskill" /></li>
</ul>
<h2>
    <fmt:message key="actions" />
</h2>
<ul>
    <li><fmt:message key="actions.create" /></li>
    <li><fmt:message key="actions.read" /></li>
    <li><fmt:message key="actions.update" /></li>
    <li><fmt:message key="actions.delete" /></li>
</ul>
<h2>
    <fmt:message key="layers" />
</h2>
<ul>
    <li><fmt:message key="layers.model" /></li>
    <li><fmt:message key="layers.view" /></li>
    <li><fmt:message key="layers.service" /></li>
    <li><fmt:message key="layers.controller" /></li>
</ul>
<h2>
    <fmt:message key="interfaces" />
</h2>
<ul>
    <li><fmt:message key="interfaces.accountrepository" /></li>
    <li><fmt:message key="interfaces.developerrepository" /></li>
    <li><fmt:message key="interfaces.skillrepository" /></li>
    <li><fmt:message key="interfaces.genericrepository" /></li>
    <li><fmt:message key="interfaces.mapper" /></li>
</ul>
<h2>
    <fmt:message key="implementations" />
</h2>
<ul>
    <li><fmt:message key="implementations.javaiodeveloperrepositoryimpl" /></li>
    <li><fmt:message key="implementations.javaioskillrepositoryimpl" /></li>
    <li><fmt:message key="implementations.javaioaccountrepositoryimpl" /></li>
    <li><fmt:message key="implementations.javajdbcaccountrepositoryimpl" /></li>
    <li><fmt:message key="implementations.javajdbcdeveloperrepositoryimpl" /></li>
    <li><fmt:message key="implementations.javajdbcskillrepositoryimpl" /></li>
</ul>
<h2>
    <fmt:message key="requirements" />
</h2>
<ul>
    <li><fmt:message key="requirements.javaversion" /></li>
</ul>
<table style="border-spacing: 50px; width: 100%">
    <tr>
        <td style="text-align: center" colspan="3">
            <a href="<%=linkToImg%>ProjectUml.png"><img src="<%=linkToImg%>ProjectUml.png" alt="UML" height="50%" width="50%"></a><br>
            <h3><fmt:message key="diagrams.uml" /></h3>
        </td>
    </tr>
    <tr>
        <td style="text-align: center" >
            <img src="<%=linkToImg%>SkillSequence.png" alt="SkillSeq" width="100%" height="100%"><br>
            <h3><fmt:message key="diagrams.accountsequence" /></h3>
        </td>
        <td style="text-align: center" >
            <img src="<%=linkToImg%>AccountSequence.png" alt="AccountSeq" width="100%" height="100%"><br>
            <h3><fmt:message key="diagrams.developersequence" /></h3>
        </td>
        <td style="text-align: center" >
            <img src="<%=linkToImg%>DeveloperSequence.png" alt="DeveloperSeq" width="100%" height="100%"><br>
            <h3><fmt:message key="diagrams.skillsequence" /></h3>
        </td>
    </tr>
</table>
</body>
</html>