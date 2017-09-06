package yuri.schmidt.model;

public class PersonPhone {
	private Person person;
	private Phone phone;
	
	public PersonPhone(){
		person = new Person();
		phone = new Phone();
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	
	
}
