package HF3.Java2;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Persons")
@XmlAccessorType (XmlAccessType.FIELD)
public class Persons {

	@XmlElement(name = "Person")
	private List<Person> Persons = null;

	public List<Person> getPersons() {
		return Persons;
	}

	public void setPersons(List<Person> Persons) {
		this.Persons = Persons;
	}

}
