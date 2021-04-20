package projet;

import java.io.File;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class transform_xml {
	DocumentBuilderFactory factory;
	DocumentBuilder parseur;
	DOMImplementation domimp;
	DocumentType dtd;

	
	
    public void  transform (File f,String domname,String sortie) throws Exception{
    	
    	factory=DocumentBuilderFactory.newInstance();
	    parseur=factory.newDocumentBuilder();
		domimp=parseur.getDOMImplementation();
		dtd=domimp.createDocumentType("TEI_S",null,domname);
		Document doc=domimp.createDocument(null,"TEI_S", dtd);
		doc.setXmlStandalone(true);
	
		Element rac=doc.getDocumentElement();
		Element prem_ele=doc.createElement(f.getName());
		rac.appendChild(prem_ele);
		
		Document document_src =parseur.parse(f.getAbsolutePath());
		Element racine=document_src.getDocumentElement();
		creat_balise_text(racine,doc,prem_ele);
	
		DOMSource ds=new DOMSource(doc);
		
		StreamResult res=new StreamResult(new File(sortie));
		TransformerFactory transform=TransformerFactory.newInstance();
		Transformer tr=transform.newTransformer();
		
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, domname);
		
		//  tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		tr.transform(ds, res);
		
		
    	
    }
    
    
    public static void creat_balise_text(Node element,Document doc,Element first){
    	
 
    			if(element.getNodeName()=="p" ){
    				
    			NodeList l=element.getChildNodes();
    			  
    			        
    			    for(int i=0;i<l.getLength();i++){
    			        		
    			        if(l.item(i).getNodeValue()!="#text" && l.item(i).getNodeValue()!=null && (!l.item(i).getNodeValue().trim().isEmpty()) ){  
    			 			Element rac=doc.getDocumentElement();
        	    			Element sec=doc.createElement("texte");
        	    			first.appendChild(sec);
        	    		    sec.appendChild(doc.createTextNode(element.getChildNodes().item(i).getNodeValue().trim()));
    			        }
    			      }
    			 	
    			}
    			
    		
    	for (Node child=element.getFirstChild();child!=null;child=child.getNextSibling()){
			 if (child.getNodeType() == Node.ELEMENT_NODE)
				 creat_balise_text(child,doc,first);
		}
		
    }
    
  
	
    }


