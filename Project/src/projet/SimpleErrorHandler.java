package projet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {
	@Override
	public void error(SAXParseException e) throws SAXException {
		
		 show("Error", e);
		    throw (e);
		
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
	
		 show("Fatal Error", e);
		    throw (e);

	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
	
		 show("Warning", e);
		    throw (e);


		
	}
	
	 private void show(String type, SAXParseException e) {
		    System.out.println(type + ": " + e.getMessage());
		    System.out.println("Line " + e.getLineNumber() + " Colonne "
		        + e.getColumnNumber());
		    System.out.println("le chemain du fichier: " + e.getSystemId().replaceAll("file:///", ""));
		  }
	 
}
