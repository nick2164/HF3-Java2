package HF3.Java2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String City;
	private String AddressLine1;
	private String PostalCode;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public void setAddressLine1(String AddressLine1) {
		this.AddressLine1 = AddressLine1;
	}

	public void setPostalCode(String PostalCode) {
		this.PostalCode = PostalCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddressLine1() {
		return AddressLine1;
	}

	public String getCity() {
		return City;
	}

	public String getPostalCode() {
		return PostalCode;
	}


}
