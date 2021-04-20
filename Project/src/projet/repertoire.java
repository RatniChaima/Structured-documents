package projet;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

public class repertoire {
	boolean b1=false,b2=false,b3=false,b4=false,b5=false,b6=false;
	public  void choix(File f) throws Exception{
		
		switch(f.getName()){
		     case "M674.xml":{
		    	 check(f.getAbsolutePath());
		    	 boolean err=false;
		    	 try{
		 		    
				    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				    	 factory.setValidating(false);
				    	 factory.setNamespaceAware(true);

				    	 DocumentBuilder builder = factory.newDocumentBuilder();
				    	 SimpleErrorHandler error=new SimpleErrorHandler();
				    	 builder.setErrorHandler(error);    
				    	 builder.parse(new InputSource(f.getAbsolutePath()));
				    	}catch(SAXParseException e){
				    		
				    		err=true;
				    	}
		    	 
		    	 if(!err && !b1){
		    		
		    		 transform_xml xml=new  transform_xml();
			    	 xml.transform(f,"dom.dtd", "sortie1.xml");
			    	 
			    	
		    		 System.out.println("Le fichier M674.xml a été transformé à sortie1.xml ");
		    		 b1=true;
		    	 }
		    	
		    	 break;
		     }
		     case "M457.xml":{
		    	 check(f.getAbsolutePath());
		    	 boolean err=false;
		  	 
		    	
		    	try{
		    
		    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	 factory.setValidating(false);
		    	 factory.setNamespaceAware(true);

		    	 DocumentBuilder builder = factory.newDocumentBuilder();
		    	 SimpleErrorHandler error=new SimpleErrorHandler();
		    	 builder.setErrorHandler(error);    
		    	 
		    	 builder.parse(new InputSource(f.getAbsolutePath()));
		    	}catch(SAXParseException e){
		    		
		    		err=true;
		    		
		    	}
		    	
		    	
		    	if(!err && !b2){
	    		 transform_xml xml=new transform_xml();
	    		 
			      xml.transform(f,"dom.dtd","sortie2.xml");
		    	  System.out.println("Le fichier M457.xml a été transformé à sortie2.xml  ");
		    	  b2=true;
		    	}
		    	
		    	
		    	 break;
		     }
		     case "poeme.txt":{
		    	if(!b3){
		    	 transform poem =new transform();
		    	 
		    	 poem.tranformer(f, "neruda.dtd", "neruda.xml");
		    	 System.out.println("Le fichier poeme.txt a été transformé à neruda.xml");
		    	 b3=true;
		    	}
		    	 break;
		     }
		     case "fiches.txt":{
		    	 if(!b4){
		    	 transform_txt_to_xml txt=new transform_txt_to_xml();
		    	 
		    	 txt.tranform2(f, "fiches.dtd", "fiche1.xml");
		    	 System.out.println("Le fichier fiches.txt a été transformé à fiche1.txt");
		    	 txt.tranform(f,"fiche2.xml");
		    	 System.out.println("Le fichier fiches.txt a été transformé à fiche2.txt");
		    	 b4=true;
		     }
		    	 break;
		     }
		 /* case "renault.html":{
		    	 
		    		if(!b5){
		    		  transform_html html=new transform_html();
		    		
		    	       html.tranform(f,"renault.xml");
		               System.out.println("Le fichier renault.html a été transfomé à renault.xml ");
		               b5=true;
		    		}
		    		break;
		    
		     }	*/
		     case "boitedialog.fxml":{
		    	 
		    		if(!b6){
		    		  transform_javafx fxml=new transform_javafx();
		    		 
		    	       fxml.transform(f,"javafx.xml");
		               System.out.println("Le fichier boitedialog.fxml a été transfomé à javafx.xml ");
		               b6=true;
		    		}
		    		break;
		    
		     }
		     default :{
		    	  
		    	  
		    	  break;
		     }
		     
		}
	}
	
	public  void find_examen(String path,String name) throws Exception{
		   File root = new File( path );
	       File[] list = root.listFiles();
	     
	       if (list == null) return;
	      
	          for ( File f : list ) {
	            if ( f.isDirectory()) {
	            	if(!f.getName().equals(name)){
	            		
	            		 this.find_examen( f.getAbsolutePath(),name );
	            		
	            	}else{
	            		//if(!b1 || !b2 || !b3 || !b4 || !b5 || !b6 ) {
	            		if(!b1 || !b2 || !b3 || !b4 || !b6){
	            		 System.out.println("Le chemin du répertoire qui est en train de traiter est : \""+f.getAbsolutePath()+"\"");
	            		
	            		 this.traiter_fichier(f.getAbsolutePath());
	            		// if(b1 && b2 && b3 && b4 && b5 && b6){
	            		 	if(b1 && b2 && b3 && b4 && b6){
	     	            	 System.out.println("Execution est fini");
	         			    System.exit(0);
	     	            }
	            		}
	            		
	            	}
	            	
	            	
	            }
	          
	           
	        }
	}
	
	public  void traiter_fichier(String path) throws Exception{
		 File root = new File( path );
	       File[] list = root.listFiles();
	       if (list == null) return;
	       for ( File f : list ) {
	    	   if ( f.isDirectory()) {
	    		   this.traiter_fichier(f.getAbsolutePath());
	    	   }else if(f.isFile()){
	    		   if(f.getName().equals("M457.xml") 
         				 || f.getName().equals("M674.xml") 
         				 || f.getName().equals("poeme.txt") 
         				 || f.getName().equals("fiches.txt")
         				 //|| f.getName().equals("renault.html")
         				 || f.getName().equals("boitedialog.fxml")
         				 
         				 )
	    		    this.choix(f);
	    	   }
	       }
	}
	
	 public static  void check(String string) throws IOException{
		 BufferedReader bf=new BufferedReader(new  InputStreamReader(new FileInputStream(string)));
		
		  PrintWriter ecrire = new PrintWriter(new File("tmp.xml")); 
		 String line;
		 
		 while((line=bf.readLine())!=null){
			 if(line.contains("<availability status=\"free\">")){
				 ecrire.write(line);
				 ecrire.write(System.getProperty("line.separator")); 
				 line=bf.readLine();
				 if(!line.endsWith("</p>"))
				    line+="</p>";
								 ecrire.write(line);
				 ecrire.write(System.getProperty("line.separator")); 
				 
			 }else{
				 if(line.contains("<lb/>&")){
					 line=line.substring(0,line.length()-1);
					 ecrire.write(line);
					 ecrire.write(System.getProperty("line.separator"));  
					 
					 
				 }else{
					 ecrire.write(line);
			     	 ecrire.write(System.getProperty("line.separator"));  
				 }
				
			 }
		 }
		 
		 ecrire.close();
		 File f=new File("tmp.xml");
		 f.renameTo(new File(string));
		 File tmp=new File("tmp.xml");
		 tmp.delete();
		 
	
   }
	 

}
