package projet;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.w3c.dom.ProcessingInstruction;

public class transform_txt_to_xml {
	
	DocumentBuilderFactory factory;
	DocumentBuilder parseur;
	DOMImplementation domimp;
	DocumentType dtd;
	
	  public void  tranform (File f,String sortie) throws Exception{
		 
		  String line;
		  String reg="(.+)([A-Z][A-Z]:)";
		   
		  Pattern.compile(reg);    
		  String regex="(.+\\.)(\\s*VE\\s*:)?(\\s*DF\\s*:)?(\\s*PH\\s*:)?(\\s*NT\\s*:)?(\\s*RF\\s*:)?";
		  Pattern p = Pattern.compile(regex);  
		 BufferedReader bf=new BufferedReader(new  InputStreamReader(new FileInputStream(f),"UTF-8"));
		  
		 
		  factory=DocumentBuilderFactory.newInstance();
		 
		    parseur=factory.newDocumentBuilder();
			domimp=parseur.getDOMImplementation();
			dtd=domimp.createDocumentType("FICHES",null,null);
			Document doc=domimp.createDocument(null,"FICHES", dtd);
		
			int i=0;
		      String etiq,text = null,bu;
		     Element rootElement=doc.getDocumentElement();
		 
		    ProcessingInstruction pi = (ProcessingInstruction)doc.createProcessingInstruction
			         ("xml-stylesheet", "type=\"text/xsl\" href=\"fiche.xsl\"");
		   
		    
			doc.insertBefore(pi, rootElement);
			
			
				   
		     line=bf.readLine();
		     Element FICHE = null;
		      loop:while (line!=null ){ 
		    	  
		    	  
		    	 	if(!line.isEmpty()){
		    	 		  if(line.contains("PNR")){
		    	 			 FICHE=doc.createElement("FICHE");
		    	 			i++;	
		    	 			FICHE.setAttribute("id",Integer.toString(i));
	  						rootElement.appendChild(FICHE);	
		    	 		  }
		    	 			
		    	 		   
	  						
							while(line!=null && !line.isEmpty() && FICHE !=null && (line.endsWith("DO") || line.endsWith("DO") ||line.endsWith("SD") ||line.endsWith("TY") ||line.endsWith("BE") || line.endsWith("AU"))){
	  						       etiq = line.substring(line.length()-2,line.length());
									Element one=doc.createElement(etiq);
									text = etiq+" : "+line.substring(0, line.length()-2); 
									one.appendChild(doc.createTextNode(text));
									FICHE.appendChild(one);
									line=bf.readLine();
	  						}
		                    
	  					 // if(i==2) System.out.println(i+" "+line);
	  						if(line.contains("AR")){ 
	  							//if(i==2)System.out.println("hello");
	  							Element Lang=doc.createElement("Langue");
	  								Lang.setAttribute("id","AR");
	  								FICHE.appendChild(Lang);
	  								line=bf.readLine();
	  								
	  						 
	  						while(line!=null && !line.isEmpty() && !line.equals("FR") && !line.contains("RF")){
	  							bu=line.substring(line.length()-4,line.length()-1);
	  							Element one=doc.createElement(bu.trim());
	  							Lang.appendChild(one);
	  							text = bu+" : "+line.substring(0, line.length()-4); 
	  							one.appendChild(doc.createTextNode(text));
	  							line=bf.readLine(); 
	  							
	  						}
	  					  while(line!=null && !line.isEmpty() && !line.equals("FR") ){
	  							Matcher m = p.matcher(line); 
	  							if(m.matches()){
	  							
	  							 	String ref=m.group(0).substring(m.group(0).length()-4,m.group(0).length()-1);
	  							 
	  						    	Element one=doc.createElement(ref.trim());
	  								Lang.appendChild(one);
	  							   String ref1 = null;
	  							    for(int j=m.groupCount()-1;j>1;j--){
	  									if(m.group(j)!=null && !m.group(j).contains("RF")){
	  										if(ref1==null){
	  											ref1=m.group(j);
	  										}else ref1+=m.group(j);
	  									
	  									}
	  								
	  							}
	  								text=ref+" | "+ref1+m.group(1);
	  							   
	  								if(text!=null)
	  							    	one.appendChild(doc.createTextNode(text));
	  							
	  								
	  								
	  							}else{
	  								
	  								Element one=doc.createElement("RF");
	  								Lang.appendChild(one);
	  							   String ref=line.substring(line.length()-5,line.length());
	  								text="RF"+"  | "+" "+ref+line.substring(0,line.length()-5);
	  								one.appendChild(doc.createTextNode(text));
	  							}
	  						  line=bf.readLine();	
	  						  
	  						}
	  					  
	  					} 
	  						
	  						 if(line.contains("FR") && !line.isEmpty() ){
		  						 Element Lang=doc.createElement("Langue");
									Lang.setAttribute("id","FR");
									FICHE.appendChild(Lang);
									line=bf.readLine();
									while(line!=null && !line.isEmpty() &&!line.contains("RF")){
			  							bu=line.substring(line.length()-4,line.length()-1);
			  							Element one=doc.createElement(bu.trim());
			  							Lang.appendChild(one);
			  							text = bu+" : "+line.substring(0, line.length()-4); 
			  							one.appendChild(doc.createTextNode(text));
			  							line=bf.readLine(); 
			  						 
			  							
			  						} 
									while(line!=null && !line.isEmpty() && !line.equals("FR") ){
										
										Matcher m = p.matcher(line); 
			  							if(m.matches()){
			  							
			  							 	String ref=m.group(0).substring(m.group(0).length()-4,m.group(0).length()-1);
			  							 
			  						    	Element one=doc.createElement(ref.trim());
			  								Lang.appendChild(one);
			  							   String ref1 = null;
			  							    for(int j=m.groupCount()-1;j>1;j--){
			  									if(m.group(j)!=null && !m.group(j).contains("RF")){
			  										if(ref1==null){
			  											ref1=m.group(j);
			  										}else ref1+=m.group(j);
			  									
			  									}
			  								
			  							}
			  								text=ref+" | "+" "+ref1.trim()+m.group(1);
			  							   
			  								if(text!=null)
			  							    	one.appendChild(doc.createTextNode(text));
			  							
			  								
			  								
			  							}else{
			  								
			  								Element one=doc.createElement("RF");
			  								Lang.appendChild(one);
			  							   String ref=line.substring(line.length()-5,line.length());
			  								text="RF"+"  | "+" "+ref+line.substring(0,line.length()-5);
			  								one.appendChild(doc.createTextNode(text));
			  							}
			  						  line=bf.readLine();	
			  							  
			  					   }
		  						
		  				       }
		  							
		    	 	}
		    	 	
		    	    line=bf.readLine();
		    	
		    	  
		      }
		      doc.setXmlStandalone(true);
		  
		      PrintWriter ecrire = new PrintWriter(sortie,"UTF-8"); 
				DOMSource ds=new DOMSource(doc);
				File fres=new File(sortie);
				StreamResult res=new StreamResult(fres);
			
				TransformerFactory transform=TransformerFactory.newInstance();
				Transformer tr=transform.newTransformer();
				
				tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
		      	tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		       tr.setOutputProperty("http://www.oracle.com/xml/is-standalone", "yes");
		      	tr.setOutputProperty(OutputKeys.METHOD, "xml"); 
		      	 tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
		      
		    	tr.transform(ds, res);
		    
		    	 String l;
				
		    	 BufferedReader bl=new BufferedReader(new  InputStreamReader(new FileInputStream(fres),"UTF-8"));
		    	
		    	 while((l=bl.readLine())!=null){
					  if(!l.isEmpty()){
						 
						  
							 ecrire.write(l);
						    ecrire.write(System.getProperty("line.separator")); 
						  
						   
						
					  }
						 
				  }
		    	 
				 
		    	 ecrire.close();
	  }
	  
	  
	  public void  tranform2 (File f,String domname,String sortie) throws Exception{
		  
		  String line;
		  String reg="(.+)([A-Z][A-Z]:)";
		   
		  Pattern.compile(reg);    
		  String regex="(.+\\.)(\\s*VE\\s*:)?(\\s*DF\\s*:)?(\\s*PH\\s*:)?(\\s*NT\\s*:)?(\\s*RF\\s*:)?";
		  Pattern p = Pattern.compile(regex);  
		 BufferedReader bf=new BufferedReader(new  InputStreamReader(new FileInputStream(f),"UTF-8"));
			
			
		 factory=DocumentBuilderFactory.newInstance();
		    parseur=factory.newDocumentBuilder();
			domimp=parseur.getDOMImplementation();
			dtd=domimp.createDocumentType("FICHES",null,domname);
			Document doc=domimp.createDocument(null,"FICHES", dtd);
			doc.setXmlStandalone(true);
			TransformerFactory transform=TransformerFactory.newInstance();
			Transformer tr=transform.newTransformer();
			tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, domname);
			int i=0;
		      String etiq,text = null,bu;
		     Element rootElement=doc.getDocumentElement();
		   
		      Node pi = doc.createProcessingInstruction
				         ("xml-stylesheet", "type=\"text/xsl\" href=\"fiche.xsl\"");
				      doc.insertBefore(pi, rootElement);
		     line=bf.readLine();
		     Element FICHE = null;
		     String tmp[]=new String[2]; 
		     
		     loop:while (line!=null ){ 
		    	  
		    	  
		    	 	if(!line.isEmpty()){
		    	 		  if(line.contains("PNR")){
		    	 			 FICHE=doc.createElement("FICHE");
		    	 			i++;	
		    	 			FICHE.setAttribute("id",Integer.toString(i));
	  						rootElement.appendChild(FICHE);	
		    	 		  }
		    	 			
		    	 		   
	  						
							while(line!=null && !line.isEmpty() && FICHE !=null && (line.endsWith("DO") || line.endsWith("DO") ||line.endsWith("SD") ||line.endsWith("TY") ||line.endsWith("BE") || line.endsWith("AU"))){
	  						     if(!line.endsWith("DO") && !line.endsWith("SD")){
	  						    	 
								  etiq = line.substring(line.length()-2,line.length());
									Element one=doc.createElement(etiq);
									text = etiq+" : "+line.substring(0, line.length()-2); 
									one.appendChild(doc.createTextNode(text));
									FICHE.appendChild(one);
									line=bf.readLine();
								}else{
									if(line.endsWith("DO")){
										tmp[0]=line;
										line=bf.readLine();
									}
									if(line.endsWith("SD")){
										tmp[1]=line;
										line=bf.readLine();
									}
								}
	  						}
		                    
	  				
	  						if(line.contains("AR")){ 
	  							
	  							Element Lang=doc.createElement("Langue");
	  								Lang.setAttribute("id","AR");
	  								FICHE.appendChild(Lang);
	  								
	  								
	  								Element dos=doc.createElement("DO");
	  								Element sd=doc.createElement("SD");
	  								Lang.appendChild(dos);
	  								Lang.appendChild(sd);
	  								text="DO :"+tmp[0].substring(0,tmp[0].length()-4);
	  								bu="SD :"+tmp[1].substring(0,tmp[1].length()-4);
	  								dos.appendChild(doc.createTextNode(text));
	  								sd.appendChild(doc.createTextNode(bu));
	  								line=bf.readLine();
	  						 
	  						while(line!=null && !line.isEmpty() && !line.equals("FR") && !line.contains("RF")){
	  							bu=line.substring(line.length()-4,line.length()-1);
	  							Element one=doc.createElement(bu.trim());
	  							Lang.appendChild(one);
	  							text = bu+" : "+line.substring(0, line.length()-4); 
	  							one.appendChild(doc.createTextNode(text));
	  							line=bf.readLine(); 
	  							
	  						}
	  					  while(line!=null && !line.isEmpty() && !line.equals("FR") ){
	  							Matcher m = p.matcher(line); 
	  							if(m.matches()){
	  							
	  							 	String ref=m.group(0).substring(m.group(0).length()-4,m.group(0).length()-1);
	  							 
	  						    	Element one=doc.createElement(ref.trim());
	  								Lang.appendChild(one);
	  							   String ref1 = null;
	  							    for(int j=m.groupCount()-1;j>1;j--){
	  									if(m.group(j)!=null && !m.group(j).contains("RF")){
	  										if(ref1==null){
	  											ref1=m.group(j);
	  										}else ref1+=m.group(j);
	  									
	  									}
	  								
	  							}
	  								text=ref+" | "+ref1+m.group(1);
	  							   
	  								if(text!=null)
	  							    	one.appendChild(doc.createTextNode(text));
	  							
	  								
	  								
	  							}else{
	  								
	  								Element one=doc.createElement("RF");
	  								Lang.appendChild(one);
	  							   String ref=line.substring(line.length()-5,line.length());
	  								text="RF"+"  | "+" "+ref+line.substring(0,line.length()-5);
	  								one.appendChild(doc.createTextNode(text));
	  							}
	  						  line=bf.readLine();	
	  						  
	  						}
	  					  
	  					} 
	  						
	  						 if(line.contains("FR") && !line.isEmpty() ){
		  						 Element Lang=doc.createElement("Langue");
									Lang.setAttribute("id","FR");
									FICHE.appendChild(Lang);
									Element dos=doc.createElement("DO");
	  								Element sd=doc.createElement("SD");
	  								Lang.appendChild(dos);
	  								Lang.appendChild(sd);
	  								text="DO :"+tmp[0].substring(0,tmp[0].length()-4);
	  								bu="SD :"+tmp[1].substring(0,tmp[1].length()-4);
	  								dos.appendChild(doc.createTextNode(text));
	  								sd.appendChild(doc.createTextNode(bu));
									line=bf.readLine();
									while(line!=null && !line.isEmpty() &&!line.contains("RF")){
			  							bu=line.substring(line.length()-4,line.length()-1);
			  							Element one=doc.createElement(bu.trim());
			  							Lang.appendChild(one);
			  							text = bu+" : "+line.substring(0, line.length()-4); 
			  							one.appendChild(doc.createTextNode(text));
			  							line=bf.readLine(); 
			  						 
			  							
			  						} 
									while(line!=null && !line.isEmpty() && !line.equals("FR") ){
										
										Matcher m = p.matcher(line); 
			  							if(m.matches()){
			  							
			  							 	String ref=m.group(0).substring(m.group(0).length()-4,m.group(0).length()-1);
			  							 
			  						    	Element one=doc.createElement(ref.trim());
			  								Lang.appendChild(one);
			  							   String ref1 = null;
			  							    for(int j=m.groupCount()-1;j>1;j--){
			  									if(m.group(j)!=null && !m.group(j).contains("RF")){
			  										if(ref1==null){
			  											ref1=m.group(j);
			  										}else ref1+=m.group(j);
			  									
			  									}
			  								
			  							}
			  								text=ref+" | "+" "+ref1.trim()+m.group(1);
			  							   
			  								if(text!=null)
			  							    	one.appendChild(doc.createTextNode(text));
			  							
			  								
			  								
			  							}else{
			  								
			  								Element one=doc.createElement("RF");
			  								Lang.appendChild(one);
			  							   String ref=line.substring(line.length()-5,line.length());
			  								text="RF"+"  | "+" "+ref+line.substring(0,line.length()-5);
			  								one.appendChild(doc.createTextNode(text));
			  							}
			  						  line=bf.readLine();	
			  							  
			  					   }
		  						
		  				       }
		  							
		    	 	}
		    	 	
		    	    line=bf.readLine();
		    	
		    	  
		      }
		      
		   
			  
		      PrintWriter ecrire = new PrintWriter(sortie,"UTF-8"); 
			DOMSource ds=new DOMSource(doc);
			
			StreamResult res=new StreamResult(new File(sortie));
			File fres=new File(sortie);
		
		
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
		
			
			  tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			tr.transform(ds, res);
		    
		    	 String l;
				
		    	 BufferedReader bl=new BufferedReader(new  InputStreamReader(new FileInputStream(fres),"UTF-8"));
		    	
		    	 while((l=bl.readLine())!=null){
					  if(!l.isEmpty()){
						  
						  if(l.equals("<?xml-stylesheet type=\"text/xsl\" href=\"fiche.xsl\"?><!DOCTYPE FICHES SYSTEM \"fiches.dtd\">")){
							 
							  String tempp[]=new String[2];
							  tempp=l.split(">");
							 
							  ecrire.write(tempp[1]+">");
							  ecrire.write(System.getProperty("line.separator")); 
							  ecrire.write(tempp[0]+">");
							  ecrire.write(System.getProperty("line.separator")); 
						  }else{
							  ecrire.write(l);
						    ecrire.write(System.getProperty("line.separator")); 
						  
							  
						  }
						
							
						   
						
					  }
						 
				  }
		    	 
				 
		    	 ecrire.close();
			 
		  
	  }
	  
	
	  
}
