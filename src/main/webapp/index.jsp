<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri = "http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>PHONEBOOK</title>
<link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>" type="text/css" />
<script src="<spring:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
<script src="<spring:url value="/resources/js/main.js"/>"></script>
</head>
<body>
<h1>Телефонная книга</h1>
<a href="<spring:url value="/getrecord"/>">Посмотреть запись из тел. книги</a>
<br/><br/>
<a href="<spring:url value="/setrecord"/>">Внести запись в тел. книгу</a>

 </body>
</html>