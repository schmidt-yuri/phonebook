package yuri.schmidt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import yuri.schmidt.model.Person;
import yuri.schmidt.model.PersonPhone;
import yuri.schmidt.model.Phone;

/**
 * This is a Data Access class
 * @author Yuri Schmidt
 * @version 1.0
 */
public class RecordDaoImpl implements RecordDao {
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * This method saves both Person and Phone data to the database
	 * @param phone A PersonPhone type received from a form via controller
	 * @see yuri.schmidt.dao.RecordDao#saveRecord(yuri.schmidt.model.PersonPhone)
	 */
	@Override
	public void saveRecord(PersonPhone phone) {
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Person person = phone.getPerson();
			session.save(person);
			phone.getPhone().setPerson(person);
			session.save(phone.getPhone());
			tx.commit();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			session.close();

		}

	}

	/**
	 * Method that saves phone data having Person type already filled out
	 * @param phone Phone data received from a form via the controller
	 * @param person Person data already present in the modelAttribute
	 * @see yuri.schmidt.dao.RecordDao#savePhone(yuri.schmidt.model.Phone, yuri.schmidt.model.Person)
	 */
	@Override
	
	public void savePhone(Phone phone, Person person) {
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			phone.setPerson(person);
			session.save(phone);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	/**
	 * Method that obtains a list of persons by the template 'inVal'
	 * @param ivVal Parameter by which a person is searched by the last name
	 * @return Returns a list of found names
	 * @see yuri.schmidt.dao.RecordDao#getlist(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getlist(String inVal) {
		List<Person> persons = null;
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			String temp = inVal + "%";
			persons = session.createQuery("select p from Person p " + "where p.last_name like :name")
					.setParameter("name", temp).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return persons;
	}

	/**
	 * Method that obtains a list of Person type
	 * @param ivVal Parameter by which a person is searched by the first name
	 * @return Returns a list of found names
	 * @see yuri.schmidt.dao.RecordDao#getlistByFirstName(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getlistByFirstName(String inVal) {
		List<Person> persons = null;
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			String temp = inVal + "%";
			persons = session.createQuery("select p from Person p " + "where p.first_name like :name")
					.setParameter("name", temp).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return persons;
	}
	
	/**
	 * Method that obtains a list of Person type
	 * @param ivVal Parameter by which a person is searched by the middle name
	 * @return Returns a list of found names
	 * @see yuri.schmidt.dao.RecordDao#getlistByMiddleName(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Person> getlistByMiddleName(String inVal) {
		List<Person> persons = null;
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			String temp = inVal + "%";
			persons = session.createQuery("select p from Person p " + "where p.middle_name like :name")
					.setParameter("name", temp).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return persons;
	}

	/**
	 * The method searches phones by person id
	 * @param personId The person id
	 * @return returns a list of type Phone
	 * @see yuri.schmidt.dao.RecordDao#getPhoneByPersonId(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Phone> getPhoneByPersonId(String personId) {
		List<Phone> phones = null;
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Long pId = Long.valueOf(personId);
			Person person = (Person) session.get(Person.class, pId);
			phones = session.createQuery("select ph from Phone ph where ph.person like :pers")
					.setParameter("pers", person).list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return phones;
	}

	/**
	 * This method seaches a single person by id
	 * @param personId person's id
	 * @return returns Person
	 * @see yuri.schmidt.dao.RecordDao#getPersonById(java.lang.String)
	 */
	@Override
	public Person getPersonById(String personId) {
		Person person = null;
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Long persId = Long.valueOf(personId);
			person = session.get(Person.class, persId);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return person;
	}

	/**
	 * This method deletes phone number by its id
	 * @param phoneId id of the phone record
	 * @see yuri.schmidt.dao.RecordDao#delSinglePhoneById(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void delSinglePhoneById(Long phoneId) {
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			System.out.println("RecordDao deletes by phoneId = " + phoneId);
			Query q = session.createQuery("delete from Phone ph where ph.id = :id").setParameter("id", phoneId);
			q.executeUpdate();

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 	This method is deleting phone numbers and a person bound to them
	 * @param personId The person's id
	 * @see yuri.schmidt.dao.RecordDao#deletePersonWithAllPhones(java.lang.Long)
	 */
	@Override
	public void deletePersonWithAllPhones(Long personId) {
		Session session = sessionFactory.openSession();
		try {
			org.hibernate.Transaction tx = session.beginTransaction();
			Person person = (Person) session.get(Person.class, personId);
			session.createQuery("delete from Phone ph where ph.person like :pers").setParameter("pers", person)
					.executeUpdate();
			session.createQuery("delete from Person p where p.id = :pId").setParameter("pId", personId).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}
