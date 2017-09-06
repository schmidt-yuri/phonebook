package yuri.schmidt.controllers;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import yuri.schmidt.model.Person;
import yuri.schmidt.model.PersonPhone;
import yuri.schmidt.model.Phone;

public interface HomeController {

	PersonPhone person();

	Phone phone();

	String getRecord();

	String setRecord();

	String setPhone();

	String save(Model model, PersonPhone pphn);

	String savephone(Phone phone, BindingResult result, Person person);

	String setOneMorePhone(Model model, String personId);

	List<Person> getlist(String inVal, String radio);

	List<Phone> getPhoneByPersonId(String personId);

	String deletePhoneById(Long phoneId);

	void deletePerson(Long personId);

}