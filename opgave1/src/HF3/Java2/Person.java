package HF3.Java2;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

	private String firstName;
	private String setMiddleName;
	private String lastName;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String setMiddleName) {
		this.setMiddleName = setMiddleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSetMiddleName() {
		return setMiddleName;
	}

	public String getLastName() {
		return lastName;
	}
}
