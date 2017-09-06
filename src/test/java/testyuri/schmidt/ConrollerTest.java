/**
 * 
 */
package testyuri.schmidt;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*; 


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import yuri.schmidt.controllers.HomeController;
import yuri.schmidt.controllers.HomeControllerImpl;
import yuri.schmidt.dao.RecordDao;
import yuri.schmidt.model.Person;
import yuri.schmidt.model.PersonPhone;
//@RunWith(SpringRunner.class)
import yuri.schmidt.model.Phone;

/**
 * @author yuri
 *
 */
public class ConrollerTest {
		private MockMvc mockMvc;
		private RecordDao mockRecordDao;
		private HomeController controller;
		@Before
	public void setUp() throws Exception {
		mockRecordDao = mock(RecordDao.class);
//		PersonPhone personPhone = new PersonPhone();
		//doNothing().when(mockRecordDao).saveRecord(personPhone);
		controller = new HomeControllerImpl(mockRecordDao);
	
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}
		

	@Test
	public void testGetRecordPage() throws Exception {
		this.mockMvc.perform(get("/getrecord/"))
		.andExpect(status().isOk())
		.andExpect(view().name("get_record"))
		.andExpect(model().size(2))
		.andExpect(model().attributeExists("person_phone", "phone"));

	}
	
	@Test
	public void testSetRecord() throws Exception {
		this.mockMvc.perform(get("/setrecord/"))
		.andExpect(status().isOk())
		.andExpect(view().name("set_record"));
	}
	
	@Test
	public void testSetPhone() throws Exception {
		this.mockMvc.perform(get("/setphone/"))
		.andExpect(status().isOk())
		.andExpect(view().name("set_phone"))
		.andExpect(handler().handlerType(HomeControllerImpl.class))
		.andExpect(handler().methodName("setPhone"));
	}
	//Testing save method
	@Test
	public void testSaveAll() throws Exception {
		
		 this.mockMvc.perform(post ("/saveall")
		.param("person.first_name", "yuri")
		.param("person.last_name", "schmidt")
		.param("person.middle_name", "eduardovich"))
		.andExpect(status().isOk())
		.andExpect(view().name("set_phone"))
		.andExpect(handler().handlerType(HomeControllerImpl.class))
		.andExpect(handler().methodName("save"))
		.andExpect(model().attributeExists("personData"));
		
	}
	//Testing savephone method which returns "set_phone"
	@Test
	public void testOfSavePhone() throws Exception{
//		Person person = new Person();//create and fill out object for session attr
//		person.setFirst_name("yuri");
//		person.setMiddle_name("eduardovich");
//		person.setLast_name("schmidt");
		Person person = mock(Person.class);//mock obj for session attr
		this.mockMvc.perform(post ("/savephone")
				
				.param("phone_number", "1111")//form input data
				.param("phone_type", "home")//form input data
				.sessionAttr("personData", person))//session attribute "personData"

				.andExpect(status().isOk())
				.andExpect(view().name("set_phone"));
	}
		
		//Testing setOneMorePhone method which returns "redirect:/setphone"
	@Test
	public void testOfSetOneMorePhone() throws Exception{
		Person person = mock(Person.class);
		when(mockRecordDao.getPersonById("1")).thenReturn(person);
		
		this.mockMvc.perform(get ("/set-one-more-phone")
		.param("personId", "1"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/setphone"))
		.andExpect(handler().handlerType(HomeControllerImpl.class));
		
	}
	
	//Testing getlist method with params "inVal" - "schmidt", and "searchby" - "lastname"
	//testing if branch where radio.equals("lastname")
	@Test
	public void testGetList() throws Exception {
		Person person = new Person();
		person.setId(1L);
		person.setFirst_name("yuri");
		person.setMiddle_name("ed");
		person.setLast_name("schmidt");
		List<Person> pl = new ArrayList<>();
		pl.add(person);
		when(mockRecordDao.getlist("schmidt")).thenReturn(pl);
		
		this.mockMvc.perform(get("/getlist")
				.param("inVal", "schmidt")
				.param("searchby", "lastname"))
		.andExpect(status().isOk())

		.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].first_name", is("yuri")))
				.andExpect(jsonPath("$[0].last_name", is("schmidt")))
				.andExpect(jsonPath("$[0].middle_name", is("ed")));
	
		
	}
	//Testing getlist method with params "inVal" - "yuri", and "searchby" - "firstname"
	//testing 'if' branch where radio.equals("firstname")
	@Test
	public void testGetListByFirstName() throws Exception {
		Person person = new Person();
		person.setId(1L);
		person.setFirst_name("yuri");
		person.setMiddle_name("ed");
		person.setLast_name("schmidt");
		List<Person> pl = new ArrayList<>();
		pl.add(person);
	
		when(mockRecordDao.getlistByFirstName("yuri")).thenReturn(pl);
		
		this.mockMvc.perform(get("/getlist")
				.param("inVal", "yuri")
				.param("searchby", "firstname"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].first_name", is("yuri")))
		.andExpect(jsonPath("$[0].last_name", is("schmidt")))
		.andExpect(jsonPath("$[0].middle_name", is("ed")));
		
	}

	//Testing getlist method with params "inVal" - "ed", and "searchby" - "middlename"
	//testing 'if' branch where radio.equals("middlename")
	@Test
	public void testGetListByMiddleName() throws Exception {
		Person person = new Person();
		person.setId(1L);
		person.setFirst_name("yuri");
		person.setMiddle_name("ed");
		person.setLast_name("schmidt");
		List<Person> pl = new ArrayList<>();
		pl.add(person);
	
		when(mockRecordDao.getlistByMiddleName("ed")).thenReturn(pl);
		
		this.mockMvc.perform(get("/getlist")
				.param("inVal", "ed")
				.param("searchby", "middlename"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].first_name", is("yuri")))
		.andExpect(jsonPath("$[0].last_name", is("schmidt")))
		.andExpect(jsonPath("$[0].middle_name", is("ed")));
		
	}
	//Testing getPhoneByPersonId
	@Test
	public void testGetPhoneByPersonId() throws Exception{
		//create instance of Person
		Person person = new Person();
		person.setId(1L);
		person.setFirst_name("yuri");
		person.setMiddle_name("ed");
		person.setLast_name("schmidt");
		//create instance of Phone
		Phone phone = new Phone();
		phone.setId(1L);
		phone.setPhone_number("222");
		phone.setPhone_type("home");
		phone.setPerson(person);
		//add phone to the ArrayList
		List<Phone> ph = new ArrayList();
		ph.add(phone);
		
		//making a stub
		when(mockRecordDao.getPhoneByPersonId("1")).thenReturn(ph);
		//perfoming get request with params
		this.mockMvc.perform(get("/getphones")
				.param("personId", "1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].phone_number", is("222")))
		.andExpect(jsonPath("$[0].phone_type", is("home")))
		.andExpect(jsonPath("$[0].person.id", is(1)))
		.andExpect(jsonPath("$[0].person.first_name", is("yuri")))
		.andExpect(jsonPath("$[0].person.middle_name", is("ed")))
		.andExpect(jsonPath("$[0].person.last_name", is("schmidt")));

	}
	
	//Testing the deletePhoneById
	@Test
	public void testDeletePhoneById() throws Exception{
		String s = "Deleted";
		this.mockMvc.perform(get("/deletephone").param("phoneId", "1"))
		.andExpect(status().isOk())
		.andExpect(content().string(s))
		.andExpect(handler().methodName(is("deletePhoneById")));
		
	}
	//Testing the deletePerson
	@Test
	public void testDeletePerson() throws Exception{
		this.mockMvc.perform(get("/deleteperson").param("personId", "1"))
		.andExpect(status().isOk())
		.andExpect(handler().methodName(is("deletePerson")));
		
	}

}
	
	
	


