package yuri.schmidt.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import yuri.schmidt.dao.RecordDao;
import yuri.schmidt.model.Person;
import yuri.schmidt.model.PersonPhone;
import yuri.schmidt.model.Phone;


@Controller
@SessionAttributes("personData")
public class HomeControllerImpl implements HomeController {
	
	//HomeController(){}

	@Autowired
	private RecordDao recordDao;

	public HomeControllerImpl(RecordDao recordDao) {
		this.recordDao = recordDao;
	}
	@Override
	@ModelAttribute("person_phone")
	public PersonPhone person(){
		return new PersonPhone();
	}
	@Override
	@ModelAttribute("phone")
	public Phone phone(){
		return new Phone();
	}
	
	@Override
	@GetMapping("/getrecord")
		public String getRecord(){
		return "get_record";
	}
	
	@Override
	@GetMapping("/setrecord")
		public String setRecord(){
		return "set_record";
	}
	
	@Override
	@GetMapping("/setphone")
		public String setPhone(){
		return "set_phone";
	}
	//Saving all the record - personal data and phone data
	@Override
	@PostMapping("/saveall")
		public String save(Model model, @ModelAttribute("person_phone") PersonPhone pphn){
		model.addAttribute("personData", pphn.getPerson());
		recordDao.saveRecord(pphn);
		return "set_phone";
	}
	//Saving the phone data
	@Override
	@PostMapping("/savephone")
		public String savephone(@Valid @ModelAttribute("phone") Phone phone, BindingResult result,
				@ModelAttribute("personData") Person person){
		if (result.hasErrors()){
			return "set_phone";
		}
		recordDao.savePhone(phone, person);
		return "set_phone";
	}
	//Saving additional phone to the already existing ones
	@Override
	@GetMapping("/set-one-more-phone")//called by ajax button(add-phone) click.
		public String setOneMorePhone(Model model, @RequestParam("personId") String personId){
		Person person = recordDao.getPersonById(personId);
		model.addAttribute("personData", person);
		return "redirect:/setphone";
	}
	//getting  names by the parameter template
	@Override
	@GetMapping("/getlist")
	@ResponseBody
		public List<Person> getlist(@RequestParam("inVal") String inVal,
									@RequestParam("searchby") String radio){
		System.out.println("inVal = " + inVal);
		System.out.println("searchby = " + radio);
		
		List<Person> pl = null;
		if (radio.equals("lastname")) {
		 pl = recordDao.getlist(inVal);//by last_name
		 System.out.println("after IF with lastname pl = " + pl);
		}
		else if(radio.equals("firstname")) {
			 pl = recordDao.getlistByFirstName(inVal);//by firstname	
		} else if(radio.equals("middlename")){
			 pl = recordDao.getlistByMiddleName(inVal);	//by  middle name
		}
		return pl;
		
	}
	//TESTING CODE
//	@GetMapping("/gettestlist")
//	@ResponseBody
//		public List<Person> gettestlist(){
//		System.out.println("Printing out the gettestlist method");
//		List<Person> pl = new ArrayList<Person>();
//		Person p = new Person();
//		p.setFirst_name("Anna");
//		p.setMiddle_name("Fedorovna");
//		p.setLast_name("Schmidt");
//		pl.add(p);
//		return pl ;
//	}
	//getting phone by person's id
	@Override
	@GetMapping("/getphones")
	@ResponseBody
		public List<Phone> getPhoneByPersonId(@RequestParam("personId") String personId){
		List<Phone> ph = recordDao.getPhoneByPersonId(personId);
		return ph;
	}
	//deleting phone by id
	@Override
	@GetMapping("/deletephone")
	@ResponseBody
	public String deletePhoneById(@RequestParam("phoneId")Long phoneId){
		recordDao.delSinglePhoneById(phoneId);
		return("Deleted");
	}
	//deleting person by his id
	@Override
	@GetMapping("/deleteperson")
	//@ResponseBody
	public void deletePerson(@RequestParam("personId") Long personId){
		recordDao.deletePersonWithAllPhones(personId);
	}
	
	
}
