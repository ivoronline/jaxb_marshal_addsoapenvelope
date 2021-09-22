import org.w3c.dom.Document;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class UtilSOAP {

  //=======================================================================================
  // CREATE SOAP
  //=======================================================================================
  public static SOAPMessage createSOAP(Document document) throws Exception {

    //ADD SOAP ENVELOPE
    SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                soapMessage.getSOAPBody().addDocument(document);

    //RETURN SOAP MESSAGE
    return soapMessage;

  }

  //=======================================================================================
  // SOAP TO FILE
  //=======================================================================================
  public static void SOAPToFile(String fileName, SOAPMessage soapMessage) throws Exception {

    //CONVERT SOAP MESSAGE TO OUTPUT STREAM
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                          soapMessage.writeTo(outputStream);

    //STORE RESULT INTO FILE
    File             file = new File(fileName);
    FileOutputStream fos  = new FileOutputStream(file);
                     fos.write(outputStream.toByteArray());
                     fos.flush();

  }

}
