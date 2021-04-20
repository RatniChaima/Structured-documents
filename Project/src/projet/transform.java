package projet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class transform {
	
	DocumentBuilderFactory factory;
	DocumentBuilder parseur;
	DOMImplementation domimp;
	DocumentType dtd;
	
	   public void  tranformer(File f,String domname,String sortie) throws Exception{
		   @SuppressWarnings("resource")
		BufferedReader bf=new BufferedReader(new  InputStreamReader(new FileInputStream(f),"UTF-8"));
		   factory=DocumentBuilderFactory.newInstance();
		    parseur=factory.newDocumentBuilder();
			domimp=parseur.getDOMImplementation();
			dtd=domimp.createDocumentType("poema",null,domname);
			Document doc=domimp.createDocument(null,"poema", dtd);
			String line;
			
			doc.setXmlStandalone(true);
			
			Element rac=doc.getDocumentElement();
			Element titre = doc.createElement("titulo");
			titre.appendChild(doc.createTextNode(bf.readLine()));
			rac.appendChild(titre);
			
			while ((line = bf.readLine()) != null) {
				if(!line.isEmpty()){
					   Element estrofa=doc.createElement("estrofa");
						rac.appendChild(estrofa);	
						while(line!=null && !line.isEmpty()){
							Element verso=doc.createElement("verso");
								verso.appendChild(doc.createTextNode(line));
								estrofa.appendChild(verso);
								line=bf.readLine();
						}
				}
				
			}
			
			DOMSource ds=new DOMSource(doc);
			StreamResult res=new StreamResult(new File(sortie));
			TransformerFactory transform=TransformerFactory.newInstance();
			Transformer tr=transform.newTransformer();
			
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, domname);
			
			  tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			tr.transform(ds, res);
			
	   }

}
