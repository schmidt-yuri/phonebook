package testyuri.schmidt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import yuri.schmidt.dao.RecordDao;
import yuri.schmidt.model.Person;
import yuri.schmidt.model.Phone;

//This class is testing DAO layer (package yuri.schmidt.dao.RecordDaoImpl)

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationContext.class})
public class TestDao {
	@Autowired
	private RecordDao dao;
	@Autowired
	private TestApplicationContext ctx;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	public void verifyBeans(){
		assertNotNull(dao);
		assertNotNull(ctx);
		assertNotNull(jdbcTemplate);
	}
	//testing RecordDaoImpl#getlist
	@Test
	public void testGetList(){
		final String inVal = "S";
		List<Person> personList = dao.getlist(inVal);
		System.out.println("LIST of PERSONS " + personList.size() + personList);
		assertNotNull(personList);
	}
	//testing RecordDaoImpl#getlistByFirstName
	@Test
	public void testOfGetListByFirstName(){ 
		final String inVal = "Y";
		List<Person> personList = dao.getlistByFirstName(inVal);
		System.out.println("BY FIRST NAME " + personList.size() + personList);
		assertNotNull(personList);
	}
	//testing RecordDaoImpl#getlistByMiddleName
	@Test
	public void testOfGetListByMiddleName(){ 
		final String inVal = "E";
		List<Person> personList = dao.getlistByMiddleName(inVal);
		System.out.println("BY MIDDLE NAME " + personList.size() + personList);
		assertNotNull(personList);
	}
	//testing RecordDaoImpl#getPhoneByPersonId
	@Test
	public void testOfGetPhoneByPersonId(){ 
		final String personId = "1";
		List<Phone> phoneList = dao.getPhoneByPersonId(personId);
		System.out.println("GET PHONE BY PERSON ID " + phoneList.size() + phoneList);
		assertNotNull(phoneList);
	}
	//testing RecordDaoImpl#getPersonById
	@Test
	public void testOfGetPersonById(){ 
		final String personId = "1";
		Person person = dao.getPersonById(personId);
		System.out.println("GET PERSON BY ID " + person);
		assertNotNull(person);
	}
	//testing deletion of a single phone from the phone table
	@Test
	public void testOfDelSinglePhoneById() throws InterruptedException{
		//row count before deleting a row
		 RowCountCallbackHandler countCallback = new RowCountCallbackHandler();  // not reusable
		 jdbcTemplate.query("select * from phone", countCallback);
		 int rowCount = countCallback.getRowCount();
			System.out.println("DEL SINGLE PHONE BY ID first " + rowCount);
		//deleting a row
		 dao.delSinglePhoneById(1L);
		//row count after deleting a row 
		 RowCountCallbackHandler countCallback2 = new RowCountCallbackHandler();  // not reusable
		 jdbcTemplate.query("select * from phone", countCallback2);
		 int rowCount2 = countCallback2.getRowCount();
		 
		 //assertion of difference of row counts
		System.out.println("DEL SINGLE PHONE BY ID second " + rowCount2);
		assertEquals(1L, rowCount-rowCount2);
	}
	//testing deletion of person with all phones
	@Test
	public void testOfDeletingPersonWithAllPhones(){
		//row count before deleting a person row
		 
		RowCountCallbackHandler countCallbackPerson = new RowCountCallbackHandler();  // not reusable
		 jdbcTemplate.query("select * from person", countCallbackPerson);
		 int rowCountPersonBefore = countCallbackPerson.getRowCount();
		//row count before deleting phones
		
		 RowCountCallbackHandler countCallbackPhone = new RowCountCallbackHandler();  // not reusable
		 jdbcTemplate.query("select * from phone", countCallbackPhone);
		 int rowCountPhoneBefore = countCallbackPhone.getRowCount();
		 
		 System.out.println("BEFORE " + rowCountPersonBefore + " " + rowCountPhoneBefore);
		 
		 //deleting a person with all phones by dao
		 dao.deletePersonWithAllPhones(2L);
		 
		 RowCountCallbackHandler countCallbackPerson2 = new RowCountCallbackHandler();  // not reusable
		 jdbcTemplate.query("select * from person", countCallbackPerson2);
		 int rowCountPersonAfter = countCallbackPerson2.getRowCount();

		 RowCountCallbackHandler countCallbackPhone2 = new RowCountCallbackHandler();  // not reusable
		 jdbcTemplate.query("select * from phone", countCallbackPhone2);
		 int rowCountPhoneAfter = countCallbackPhone2.getRowCount();

		 System.out.println("AFTER " + rowCountPersonAfter + " " + rowCountPhoneAfter);
	 
		 //testing the difference
		 assertEquals(1L, rowCountPersonBefore - rowCountPersonAfter );
		 assertEquals(1L, rowCountPhoneBefore - rowCountPhoneAfter);

	}



}
