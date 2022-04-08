package WikiExp;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;


public class Download{
	
	public Download() {}	
	
	public void DownPagesFrom(String pid1,int limit,int expl) throws IOException{ //visita le pagine di wikipedia e le scarica/pulisce
			
	StreamText st=new StreamText();
    JsonParts jp=new JsonParts();
	HtmlParts hp=new HtmlParts();  
	Cleaner c= new Cleaner();
	DataBaseWork dbw=new DataBaseWork();
	FrequenzaParole fp=new FrequenzaParole();
    
    HashSet<String> da_visitare = new HashSet<String>();
    HashSet<String> visitate = new HashSet<String>();
    TreeMap<String, Integer> dictionary = new TreeMap<String, Integer>();
   
    
    int aggiuntiMax=0;
    boolean aggiunto=true;
    String id2="";
    int numParole;
    int numeroRigheAggiunte= dbw.NumRighe("pagine_da_aggiungere", "da_aggiungere");
    int numeroRighe= dbw.NumRighe("pagine_da_visitare", "da_visitare");
    if(numeroRighe==0) {
    	
    	System.out.println("la tabella era vuota");
    	dbw.InsertIntoPagDaVisit(pid1);
    	numeroRighe=1;
    }
    
    da_visitare= dbw.AddToHashDaVisit(da_visitare);
    visitate= dbw.AddToHashVisit(visitate);
    String[] arrayT= new String[2];
    String[] ids=new String[500];
    
    int agg=numeroRigheAggiunte*expl;
    
    while(aggiuntiMax < limit ) {
    	 boolean presente=false;
    	
    	 String[] parole;
    	 if(agg==0) {
    		 id2= dbw.PollFromTable("pagine_da_visitare","da_visitare");
    		 presente = true;
    	 }else {
    		 id2= dbw.PollFromTable("pagine_da_aggiungere","da_aggiungere");
			 agg=agg-1;
		 	 if(agg==0)
		 		dbw.DeleteTable("pagine_da_aggiungere");
		 	 if(visitate.contains(id2)) {
		 		System.out.println("pagine da aggiungere aggiunte");
		 		id2= dbw.PollFromTable("pagine_da_visitare","da_visitare");
		 		presente = true;
		 	 }
    	 }
	   	 
    	 if(!visitate.contains(id2)) {
	   	 URL url = new URL("https://it.wikipedia.org/w/api.php?action=query&pageids="+id2+"&prop=extracts&exlimit=1&format=json");            	 
	   	 URL url1 = new URL("https://it.wikipedia.org/w/api.php?action=query&pageids="+id2+"&prop=linkshere&lhlimit=250&format=json");
	   	 
	   	 //estrae dalla pagina titolo e testo, li salva su un array
	   	 
	     String txt= st.CreateString_Url(url); //sporco
	   	
	   	 arrayT=jp.getText(txt,id2); //jparsato + estratto titolo e testo
	   	 
	   	 String title= arrayT[0];
	   	 title=title.replace("\"","");
	   	 title=title.replace("?","");
	   	 title=title.replace("'","");
	   	 
	   	 if (!(title.contains(":") || title.contains("/") || (title.contains("Discussioni")) || (title.contains("Discussione")) || (title.contains("jpg")) || (title.contains("Utente")))) {
	   		 
	   		 System.out.println(title);
	   		 String txt1= hp.getText(arrayT[1]); //httpinterpretato
	   		 
	   		 //pulizia StopWords
		   	 txt1=c.RStop(txt1);	
		   	 
	   	     //Stemming
		   	 txt1=c.Stemma(txt1);
		   	 
		   	 parole = txt1.split(" ");
		   	 numParole = parole.length;
		   	 Entry<String, Integer> entry;
		   	 String k1;
		   	 Integer k2;
		   	 
		   	 dictionary = fp.frequenza(txt1);
		   	 
		   	 //System.out.println(numParole);
		   	 aggiunto = dbw.InsertIntoTable4("testo","id", "titolo", "corpo", "num_parole",id2, title, txt1, numParole);
		   	 
		   	 while(!dictionary.isEmpty()) {
		   		 
			   	 entry = dictionary.pollFirstEntry();
			   	 k1 = entry.getKey();
			   	 k2 = entry.getValue();
			   	 
			   	 dbw.InsertAndIncrease("parola", "nome", "numero_testi", k1);
			   	 dbw.InsertIntoTable3("composizione", "testo", "parola", "frequenza", id2, k1, k2);
		   	 }
		   	 
		   	 
		   	 
		   	 boolean apposto = dbw.InsertIntoPagVisit(id2);
		   	 
		   	 if(aggiunto && apposto) {
		   		 aggiuntiMax++;
		   		 System.out.println(aggiuntiMax);
		   		 visitate.add(id2);
		   	 }
		   	 
		   	 //Scarica un array di link da ogni pagina ove possibile
		   	 
		   	 String txt2 = st.CreateString_Url(url1);
		   	 
		   	 ids= jp.getIdLinks(txt2,id2);
		   	 int numLink=0;
		   	 
		   	 for(int k=0;k<500;k++) {
		   		 if(ids[k]!=null) {
		   			 numLink++;
		   			 if(!visitate.contains(ids[k])) {
		   				 da_visitare.add(ids[k]);
		   				 dbw.InsertIntoTable1("link", "id_link", ids[k]);
		   				 dbw.InsertIntoTable2("collegamento", "testo", "link", id2, ids[k]);
		   				 if(agg==0 || presente) {
		   					 if(!da_visitare.contains(ids[k]))
		   						 dbw.InsertIntoPagDaVisit(ids[k]);
		   				 }else {
		   					 dbw.InsertIntoPagDaAgg(ids[k]);
		   				 }
		   			 }
		   		 }
		   	}
		   	dbw.UpdateLink(id2, numLink);	
	     }
	  }
    }
   }
}

