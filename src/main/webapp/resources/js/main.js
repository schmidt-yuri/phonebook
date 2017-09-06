
$(document).ready(function(){
	var persId = null; //id of person in PERSON table
	var phnId = null; //id of phone in PHONE table
	var ptext = null;//a helper variable
	//all buttons are disabled
	$("button.add_phone").attr("disabled","disabled");
	$("button.delete_record").attr("disabled","disabled");
	$("button.delete_phone").attr("disabled","disabled");
//inserting letters into the input text for searching names
	$("#testinput").keyup(function(){
		$("div.one" ).empty();//should be empty in the beginning
		$("div.two").empty();//the same
		$("button.add_phone").attr("disabled","disabled");//button is disabled
		$("button.delete_record").attr("disabled","disabled");//button is disabled
		$("button.delete_phone").attr("disabled","disabled");//button is disabled
		var inVal = null; //the variable for values from input text field
		var searchby = null;//variable for choosing by which radio: last_name, first_name or middle_name is search provided 
		var inputText = {inVal:$("#testinput").val(), searchby:$("input:radio[name=searchby]:checked").val()};//the object for $.getJSON
//getting a list of names according to the letters and radios by ajax request
		$.getJSON("http://localhost:8080/phoneBook/getlist", inputText, function(data){//ajax request
			for(var i = 0; i < data.length; i++){	//putting the names from Person object in database row by row into 'div.two' with [id] attribute
				$("div.one").append("<p " + "id=" + data[i].id + ">" + data[i].last_name + " " +
					data[i].first_name + " " +
					data[i].middle_name + "</p>");
			}//end of for cycle
		});//end of get.JSON ".../getlist" function
	});//end of "#testinput" keyup() function
	
	
	//$( "div.one p" ).click(function() {
	$('body').on('click', 'div.one p', function(){
		persId = $(this).attr("id");//get person's id from div.one p [id] attribute
		ptext = $(this).text();//get the content of paragraph
		findPhones();
	
	});//end of "div.one p" click function

//CHANGING APPEARANCE OF THE ROW and remembering id attribute
//clicking on newly appearred <p> with attribute [id] in the div.two and changing their appearance
	$('body').on('click', 'div.two p[id]', function(){
		phnId =$(this).attr("id");//get phone's id from clicked element to the variable
		$("div.two p[id]").css({"background-color": "#FFF987", "color":"black"});//first it sets the normal style corresponding to style.css for div.two p
		$(this).css({"background-color": "blue", "color":"white"});//then changing appearance of the element when it's clicked
		$("button.delete_phone").removeAttr("disabled");//enabling delete_phone button

	});//end of $('body').on('click', 'div.two p[id]', function()


//DELETING PHONE NUMBER
	$("button.delete_phone").click(function(){
		if(confirm("Вы действительно хотите удалить номер из телефонной книги?")){
		
		$("button.delete_phone").attr("disabled","disabled");

		//AJAX to "/deletephone"
		$.get("http://localhost:8080/phoneBook/deletephone", {phoneId:phnId}, function(data){alert(data)});//end of get .../deletephone	
		findPhones();
	}//end of confirm statement
	}); //end of $("button.delete_phone").click
	
//	$("#test").click(function(){alert("this is test button")
//	alert(phnId);	
//	});

//DELETING RECORD (Person with all it's phone numbers)
	$("button.delete_record").click(function(){	
		if (confirm("Вы действительно хотите удалить запись из телефонной книги?")){
		$.get("http://localhost:8080/phoneBook/deleteperson", {personId:persId}, function(data){alert(data);});
		
		$("div.one" ).empty();//should be empty in the beginning
		//alert("after div.one empty");
		$("div.two").empty();//the same
		//alert("after div two empty");
		$("button.add_phone").attr("disabled","disabled");//button is disabled
		$("button.delete_record").attr("disabled","disabled");//button is disabled
		$("button.delete_phone").attr("disabled","disabled");//button is disabled
		var inVal = null; //the variable for values from input text field
		var searchby = null;//variable for choosing by which radio: last_name, first_name or middle_name is search provided 
		var inputText = {inVal:"", searchby:$("input:radio[name=searchby]:checked").val()};//the object for $.getJSON
//getting a list of names according to the letters and radios by ajax request
		alert("Запись " + ptext + " со всеми телефонами стерта из базы данных");
		$.getJSON("http://localhost:8080/phoneBook/getlist", inputText, function(data){//ajax request
			for(var i = 0; i < data.length; i++){	//putting the names from Person object in database row by row into 'div.two' with [id] attribute
				$("div.one").append("<p " + "id=" + data[i].id + ">" + data[i].last_name + " " +
					data[i].first_name + " " +
					data[i].middle_name + "</p>");
			}//end of for cycle
		});//end of get.JSON ".../getlist" function

	}//end of if(confirm()){} statement
	});//end of$("button.delete_record").click 
	

//finding phones
function findPhones (){		//clicking div.one paragraphs
	$("button.add_phone").removeAttr("disabled"); //button .add_phone gets enabled
	$("button.delete_record").removeAttr("disabled");//button .delete_record gets enabled
	$("button.delete_phone").attr("disabled","disabled");//button .delete_phone gets disabled
//	persId = $(this).attr("id");//get person's id from div.one p [id] attribute
//	var ptext = $(this).text();//get the content of paragraph
	$("div.two").empty();//clear content of div.one
	$("div.two").append("<p>" + ptext + "</p>"); //add name to the first paragraph

	//GETTING PHONES FOR THE PERSON BY PERSON'S  persId with AJAX request
//	alert("persId = " + persId);
	$.getJSON("http://localhost:8080/phoneBook/getphones", {personId:persId}, function(data){
		if(data.length > 0){			//if there are any data returned
			$("button.add_phone").removeAttr("disabled"); //enable add_phone button
			$("button.delete_record").removeAttr("disabled");//enable delete_record button
						
		//and put data in the div.two by means of for cycle
		for(var i = 0; i < data.length; i++){
		$("div.two").append("<p " + "id=" + data[i].id +">" + data[i].phone_number + " " + data[i].phone_type + "</p>");
		}//end of for statement
	}//end if statement
	});//end of getJSON ".../getphones" function

	$("input[name='personId']").val(persId); //putting the value of variable persId in the invisible input text field of the form
	
}//end of function findPhones()

//$("#test").click(function(){
//	if(confirm("Вы действительно хотите удалить из телефонной книги имя?")){
//		alert("Этот алерт появляется в случае нажатия на кнопку да");}
//
//	
//});
});