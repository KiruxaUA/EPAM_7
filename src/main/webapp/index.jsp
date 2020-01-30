<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
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
    <fmt:message key="label.entities" />
</h2>

<ul>
    <li><fmt:message key="label.entities.developer" /></li>
    <li><fmt:message key="label.entities.skill" /></li>
    <li><fmt:message key="label.entities.account" /></li>
</ul>

<h2>
    <fmt:message key="label.model" />
</h2>

<ul>
    <li><fmt:message key="label.model.developer" /></li>
    <li><fmt:message key="label.model.skill" /></li>
    <li><fmt:message key="label.model.account" /></li>
</ul>

<h2>
    <fmt:message key="label.storage" />
</h2>

<ul>
    <li><fmt:message key="label.storage.accounts" /></li>
    <li><fmt:message key="label.storage.skills" /></li>
    <li><fmt:message key="label.storage.developers" /></li>
    <li><fmt:message key="label.storage.developerskill" /></li>
</ul>

<h2>
    <fmt:message key="label.actions" />
</h2>

<ul>
    <li><fmt:message key="label.actions.create" /></li>
    <li><fmt:message key="label.actions.read" /></li>
    <li><fmt:message key="label.actions.update" /></li>
    <li><fmt:message key="label.actions.delete" /></li>
</ul>

<h2>
    <fmt:message key="label.layers" />
</h2>

<ul>
    <li><fmt:message key="label.layers.model" /></li>
    <li><fmt:message key="label.layers.view" /></li>
    <li><fmt:message key="label.layers.service" /></li>
    <li><fmt:message key="label.layers.controller" /></li>
</ul>

<h2>
    <fmt:message key="label.interfaces" />
</h2>

<ul>
    <li><fmt:message key="label.interfaces.accountrepository" /></li>
    <li><fmt:message key="label.interfaces.developerrepository" /></li>
    <li><fmt:message key="label.interfaces.skillrepository" /></li>
    <li><fmt:message key="label.interfaces.genericrepository" /></li>
    <li><fmt:message key="label.interfaces.mapper" /></li>
</ul>

<h2>
    <fmt:message key="label.implementations" />
</h2>

<ul>
    <li><fmt:message key="label.implementations.javaiodeveloperrepositoryimpl" /></li>
    <li><fmt:message key="label.implementations.javaioskillrepositoryimpl" /></li>
    <li><fmt:message key="label.implementations.javaioaccountrepositoryimpl" /></li>
    <li><fmt:message key="label.implementations.javajdbcaccountrepositoryimpl" /></li>
    <li><fmt:message key="label.implementations.javajdbcdeveloperrepositoryimpl" /></li>
    <li><fmt:message key="label.implementations.javajdbcskillrepositoryimpl" /></li>
</ul>

<h2>
    <fmt:message key="label.requirements" />
</h2>

<ul>
    <li><fmt:message key="label.requirements.javaversion" /></li>
</ul>

</body>
</html>