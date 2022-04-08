package WikiExp;
import java.io.IOException;
import java.util.Scanner;

public class WikiExplorer {

     public static void main(String[] args) throws IOException{
    	 
    	 Download dw=new Download();
    	 try {
    		 long inizio= System.currentTimeMillis();
    		 	int limit=0;
    			System.out.println("Quante pagine vuoi scaricare? ");
    			Scanner input = new Scanner(System.in);
    			limit = input.nextInt(); 
    		    input.close();
    			dw.DownPagesFrom("2173419",limit,150); //Storia 
    		 long fine= System.currentTimeMillis();	
    		 if((fine-inizio)/60000>0) {
    			System.out.println("finito in "+((fine-inizio)/60000)+" minuti");
    		 }else {
    			 if((fine-inizio)/1000>0) {
    				 System.out.println("finito in "+((fine-inizio)/1000)+" secondi");
    			 }else {
    				 System.out.println("finito in meno di un secondo");
    			 }
    		 }
    		 System.out.println(limit/((fine-inizio)/60000)+" pagine scaricate al minuto");
		 }catch (Exception e) {
			e.printStackTrace();
		 }
     } 	 
}			 
             
             
                   
             
             

             	
             	
            
     	
     


