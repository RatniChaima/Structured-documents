package projet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class transform_javafx {


	static void transform(File f, String sortie) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		
		DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		DOMImplementation domimp = parseur.getDOMImplementation();
		Document doc = domimp.createDocument(null,"Racine", null);
		doc.setXmlStandalone(true);
		
		Element rac = doc.getDocumentElement();
		Node noeux = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f).getDocumentElement();
		Attr attrR = (Attr) noeux.getAttributes().getNamedItem("xmlns:fx");
		String strVal = attrR.getValue();
		rac.setAttribute(attrR.getName(), strVal.substring(0, strVal.length()-2));
		
		Element elt = null;
		for(int j=0;j<noeux.getAttributes().getLength();j++){
			Attr attr = (Attr) noeux.getAttributes().item(j);
			elt = doc.createElement("texte");
			elt.setAttribute(attr.getName(), "x");
			elt.setTextContent(attr.getValue());
			rac.appendChild(elt);
		}
		att(noeux,elt,rac, doc);
	//	System.out.println("</Racine>");
		DOMSource ds = new DOMSource(doc);
		StreamResult res = new StreamResult(new File(sortie)); 
		TransformerFactory transform = TransformerFactory.newInstance();
		Transformer tr = transform.newTransformer();
		tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes"); 
		tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		tr.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","2");
		tr.transform(ds, res);

		Path file = Paths.get(sortie);
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(file), charset);
		content = content.replaceAll("  ", "\t");
		Files.write(file, content.getBytes(charset));
	}

static void att (Node n,Element elemt,Element rac,Document doc){

	for (int o=0;o<n.getChildNodes().getLength();o++)
		if((!n.getChildNodes().item(o).getNodeName().matches("#text|#comment"))){

			Node e = n.getChildNodes().item(o);		

			for(int x=0;x<e.getAttributes().getLength();x++){
				Attr attr = (Attr) e.getAttributes().item(x);
				elemt = doc.createElement("texte");
				elemt.setAttribute(attr.getName(), "x");
				elemt.setTextContent(attr.getValue());
				rac.appendChild(elemt);
			}		
			att(e, elemt, rac, doc);
		}
	}	

}
