package application.util;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class Person {
	@NotNull
	private int code_person;
	@NotNull
	private String lastName;
	@NotNull
	private String firstName;
	private String address;
	private String city;
	private int zip_code;
	@Digits(integer=10,fraction=0)
	private int phone;
	
	public Person(int code_person, String lastName, String firstName, String address, String city, int zip_code, int phone){
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.city = city;
		this.zip_code = zip_code;
		this.phone = phone;
		this.code_person = code_person;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZip_code() {
		return zip_code;
	}
	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}

	public int getCode_person() {
		return code_person;
	}

	public void setCode_person(int code_person) {
		this.code_person = code_person;
	}
}
