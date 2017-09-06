<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Добавить номер телефона</title>
<link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>" type="text/css" />
<script src="<spring:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
<script src="<spring:url value="/resources/js/set_phones.js"/>"></script>

</head>
<body>
<h1>Добавить номер телефона</h1>
<h2><c:out value="${personData.last_name}"></c:out></h2>
<h2><c:out value="${personData.first_name}"></c:out></h2>
<h2><c:out value="${personData.middle_name}"></c:out></h2>

<spring:url value="/savephone" var="savePhn"></spring:url>
<form:form action="${savePhn}" method="POST" modelAttribute="phone">
 	
	<label for="phone-number">Номер телефона</label>
	<form:input path="phone_number" id="phone-number"  />
	<form:errors path="phone_number"/>
	<br><br>
	<label for="phone-type">Тип номера</label>
	<form:input path="phone_type" id="phone-type"  />
	<br><br>
	
	
	<input type="submit" value="Внести запись"/>
	<br><br>
</form:form>
<a href="<spring:url value="/getrecord"/>">Посмотреть запись из тел. книги</a>
<br/><br/>
<a href="<spring:url value="/setrecord"/>">Внести новую запись в тел. книгу</a>



</body>
</html>