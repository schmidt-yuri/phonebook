<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Посмотреть запись</title>
<link rel="stylesheet" href="<spring:url value="/resources/css/style.css"/>" type="text/css" />
<script src="<spring:url value="/resources/js/jquery-3.2.1.min.js"/>"></script>
<script src="<spring:url value="/resources/js/main.js"/>"></script>

</head>
<body>
<h1>Посмотреть запись из тел. книги</h1>

<h4>Искать по:</h4>

<input type="radio" name="searchby" value="lastname" checked>Фамилии
<input type="radio" name="searchby" value="firstname" >Имени
<input type="radio" name="searchby" value="middlename">Отчеству

<br/><br/>
<label for="testinput">Введите фамилию, имя или отчество</label>
<input type="text" id="testinput"/>
<br/><br/>
<div class="one"></div>
<div class="two"></div>
<div class="clear"></div>

<a href="<spring:url value="/setrecord"/>">Внести новую запись в тел. книгу</a>
<form action="/phoneBook/set-one-more-phone" method="get">
<input type="text" name="personId" /><br/>
<button id= "add_tel" class="add_phone">Добавить телефон</button>
</form>
<button id="del_tel" class="delete_phone">Удалить телефон</button>
<button id="del_rec" class="delete_record">Удалить запись</button>
<button id = "test">TEST BUTTON</button>
</body>
</html>