import org.w3c.dom.Document;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;

public class MarshalEnvelope {

  static String fileSOAP = "src/main/resources/PersonSOAP.xml";

  //=======================================================================================
  // MAIN
  //=======================================================================================
  public static void main(String[] args) throws Exception {

    //CREATE PERSON OBJECT
    Person      person      = new Person();
                person.id   = 1;
                person.name = "John";
                person.age  = 20;

    //CREATE PERSON DOCUMENT (MARSHAL)
    Document    personDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    Marshaller  marshaller     = JAXBContext.newInstance(Person.class).createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(person, personDocument);

    //ADD SOAP ENVELOPE
    SOAPMessage personSOAP = UtilSOAP.createSOAP(personDocument);

    //SAVE SOAP TO FILE
    UtilSOAP.SOAPToFile(fileSOAP, personSOAP);

  }

}
