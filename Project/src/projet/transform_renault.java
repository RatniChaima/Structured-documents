package projet;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class transform_renault {
	DocumentBuilderFactory factory;
	DocumentBuilder parseur;
	DOMImplementation domimp;
	DocumentType dtd;
	   public void  tranform (File f,String sortie) throws Exception{
		   try{
			factory=DocumentBuilderFactory.newInstance();

	        factory.setFeature("http://xml.org/sax/features/namespaces", false);
	        factory.setFeature("http://xml.org/sax/features/validation", false);
	        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);   
			factory.setValidating(false);
			
		    parseur=factory.newDocumentBuilder();
		    parseur.setEntityResolver(new EntityResolver() {
		    	  @Override
		    	  public InputSource resolveEntity(String arg0, String arg1)
		    	        throws SAXException, IOException {
		    	    if(arg0.contains("xhtml1-transitional")) {
		    	        return new InputSource(new StringReader(""));
		    	    } else {
		    	        // TODO Auto-generated method stub
		    	        return null;
		    	    }
		    	  }
		    	});
			domimp=parseur.getDOMImplementation();
			dtd=domimp.createDocumentType("Concessionnaires",null,null);
			Document doc=domimp.createDocument(null,"Concessionnaires", null);
			
			
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

			Element rac=doc.getDocumentElement();
			Document document_src =parseur.parse(f.getAbsolutePath());
			Element racine=document_src.getDocumentElement();
			creat_res(racine,doc,rac);
			
			DOMSource ds=new DOMSource(doc);
			
			StreamResult res=new StreamResult(new File(sortie));
			TransformerFactory transform=TransformerFactory.newInstance();
			Transformer tr=transform.newTransformer();
			
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
		    tr.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    
			tr.transform(ds, res);
		   }catch(java.net.UnknownHostException e){
			  System.out.println("error dans le téléchargement le fichier dtd a partir du web");
		   }catch(java.net.ConnectException e){
			   System.out.println("le delai d'attendre a été depassé ");
		   }
			
	   }
	   
	   public static void creat_res(Node element,Document doc,Element rac){
		   
		  if(element.getNodeName().equals("p") && element.getParentNode().getNodeName().equals("div")) {
			  Node temp=element;
				  NamedNodeMap atts = element.getParentNode().getAttributes(); 
				   Attr attr = (Attr) atts.item(0);
				   
				   
				  for (Node child2=element.getFirstChild();child2!=null;child2=child2.getNextSibling()){
					   if(child2.getNodeName().equals("strong")){
						   
						     NodeList name=child2.getChildNodes();
						     for(int i=0;i<name.getLength();i++){
						    	  if(name.item(i).getNodeValue()!="#text" && name.item(i).getNodeValue()!=null && (!name.item(i).getNodeValue().trim().isEmpty()) ){
						    		 
						    		  Element nom=doc.createElement("Nom");
						    		  rac.appendChild(nom);
						    		  nom.appendChild(doc.createTextNode(child2.getChildNodes().item(i).getNodeValue().trim()));
						    	  }
						     }
						  break;
					   }
					    
				  }
				   
				   if(attr.getName().equals("class") && attr.getValue().equals("post-single")){
					   
					   NodeList l=temp.getChildNodes();
					   String text="";
					   Element adresse=doc.createElement("Adresse");
					   rac.appendChild(adresse);
					   Element n_tel=doc.createElement("Num_téléphone");
					   rac.appendChild(n_tel);
					   for(int i=0;i<l.getLength();i++){
						   if(l.item(i).getNodeValue()!="#text" && l.item(i).getNodeValue()!=null && (!l.item(i).getNodeValue().trim().isEmpty()) ){ 
							   if(temp.getChildNodes().item(i).getNodeValue().startsWith(":"))
	    			 			text+=temp.getChildNodes().item(i).getNodeValue().trim().replaceAll("\n", " ").substring(1);
							   else text+=temp.getChildNodes().item(i).getNodeValue().replaceAll("\n", " ");
	    			 			text+=" ";
							 
	    			        }
					   }
					 
					  
					 
					  String tem[]=text.split("021");
					  adresse.appendChild(doc.createTextNode(tem[0]));
					  n_tel.appendChild(doc.createTextNode("021"+tem[1]));
					
				   }
				  
				
			 			
    	    		  
    	    			
			    
			}
		   
			for (Node child=element.getFirstChild();child!=null;child=child.getNextSibling()){
				 if (child.getNodeType() == Node.ELEMENT_NODE)
					 
					 creat_res( child,doc,rac);
			}
		   
	   }
}
