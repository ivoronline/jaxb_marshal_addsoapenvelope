import org.w3c.dom.Document;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

public class MarshalEnvelope {

  //=======================================================================================
  // MAIN
  //=======================================================================================
  public static void main(String[] args) throws Exception {

    //CREATE PERSON
    Person  person = new Person();
            person.id   = 1;
            person.name = "John";
            person.age  = 20;

    //MARSHAL PERSON
    Document    document   = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    Marshaller  marshaller = JAXBContext.newInstance(Person.class).createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(person, document);
    System.out.println(documentToString(document));  //Display Marshaled XML

    //ADD SOAP ENVELOPE
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                soapMessage.getSOAPBody().addDocument(document);

    //CONVERT SOAP MESSAGE TO OUTPUT STREAM
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                          soapMessage.writeTo(outputStream);

    //STORE OUTPUT STREAM INTO STRING
    String output = new String(outputStream.toByteArray());
    System.out.println(output);

    //STORE OUTPUT STREAM INTO FILE
    File              file = new File("PersonSOAP.xml");
    FileOutputStream  fos = new FileOutputStream(file);
                      fos.write(outputStream.toByteArray());
                      fos.flush();



  }

  //=======================================================================================
  // DOCUMENT TO STRING (only used to display Marshaled XML)
  //=======================================================================================
  private static String documentToString(final Document responseDoc) throws Exception {

    DOMSource          domSource = new DOMSource(responseDoc);
    StringWriter       stringWriter = new StringWriter();
    StreamResult       streamResult = new StreamResult(stringWriter);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer        transformer;
                       transformer = transformerFactory.newTransformer();
                       transformer.transform(domSource, streamResult);

    return stringWriter.toString();

  }

}