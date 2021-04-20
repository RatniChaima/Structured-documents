package projet;

import java.io.File;
import java.lang.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.application.*;
   
public class main {
	boolean b1=false;
	boolean b2=false;
	boolean b3=false;
	boolean b4=false;
	boolean b5=false;
	public static void main(String[] args) throws Exception {
		String nom_repertoire = "examen";
		repertoire repertoire=new repertoire();
		File[] roots = File.listRoots();
	
		try{
			
			//nom_repertoire=args[0];
		}catch(java.lang.ArrayIndexOutOfBoundsException e){
			System.out.println("Attention vous avez oublié de spécifier le nom du répertoire à traiter ! ");
		}
		if( nom_repertoire!=null &&  !nom_repertoire.isEmpty()){
	
			if(nom_repertoire.equals("examen")){  
				 System.out.println("Recherche du repertoire examen..");
			for(int i=0;i<roots.length;i++){
				
				 repertoire.find_examen(roots[i].getAbsolutePath(), nom_repertoire);
				}
				
			}else System.out.println("Le nom de répertoire n'est pas \"examen\" ");
		}
		
	}
	
	

}
