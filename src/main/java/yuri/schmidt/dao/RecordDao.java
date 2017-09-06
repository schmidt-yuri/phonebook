package yuri.schmidt.dao;

import java.util.List;

import yuri.schmidt.model.Person;
import yuri.schmidt.model.PersonPhone;
import yuri.schmidt.model.Phone;

public interface RecordDao {

	void saveRecord(PersonPhone phone);

	void savePhone(Phone phone, Person person);

	List<Person> getlist(String inVal);

	List<Person> getlistByFirstName(String inVal);

	List<Person> getlistByMiddleName(String inVal);

	List<Phone> getPhoneByPersonId(String personId);

	Person getPersonById(String personid);

	//deleting phone number by its id
	void delSinglePhoneById(Long phoneId);

	//deleting phone numbers and a person bound to them
	void deletePersonWithAllPhones(Long personId);

}