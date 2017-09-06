<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!--<fmt:requestEncoding value="utf-8" />-->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Внести запись</title>
<link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>" type="text/css" />

</head>
<body>
<h1>Внести запись в тел. книгу</h1>

<spring:url value="/saveall" var="saveFrm"></spring:url>
<form:form action="${saveFrm}" method="POST" modelAttribute="person_phone">
	<label for="last-name">Фамилия</label>
	<form:input path="person.last_name" id="last-name"  />
	<br><br>
	<label for="first-name">Имя</label>
	<form:input path="person.first_name" id="first-name"  />
	<br><br>
	<label for="middle-name">Отчество</label>
	<form:input path="person.middle_name" id="middle-name"  />
	<br><br>
 	
	<label for="phone-number">Номер телефона</label>
	<form:input path="phone.phone_number" id="phone-number"  />
	<br><br>
	<label for="phone-type">Тип номера</label>
	<form:input path="phone.phone_type" id="phone-type"  />
	<br><br>
	
	
	<input type="submit" value="Внести запись"/>
	<br><br>
</form:form>
<a href="<spring:url value="/getrecord"/>">Посмотреть запись из тел. книги</a>
<br/><br/>


</body>
</html>