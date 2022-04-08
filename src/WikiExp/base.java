package WikiExp;
/*import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashSet;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.italianStemmer;


public class Download{
	
	public Download() {}	
	
	public String DownPagesFrom(String pid1) throws IOException{ //visita le pagine di wikipedia e le scarica/pulisce
			
	StreamText st=new StreamText();
    //OutputText ot=new OutputText();
    JsonParts jp=new JsonParts();
	HtmlParts hp=new HtmlParts();  
	SnowballStemmer stemmer = (SnowballStemmer) new italianStemmer();
	Cleaner c= new Cleaner();
	DataBaseWork dbw=new DataBaseWork();
    
    HashSet<String> visited = new HashSet<String>();
    ArrayDeque<String> q = new ArrayDeque<String>(1000000); 

    String[] arrayT= new String[2];
    String[] ids=new String[500];
    q.add(pid1);
    visited.add(pid1);
    int aggiunti=0;
    boolean aggiunto=true;
    
    while(!q.isEmpty() && aggiunti<10000) {
    	
    	 
	   	 String id2=q.poll();
	   	 //String c1="C:\\Users\\emaan\\Desktop\\cartella\\output.txt";
	   	 
	   	 URL url = new URL("https://it.wikipedia.org/w/api.php?action=query&pageids="+id2+"&prop=extracts&exlimit=1&format=json");            	 
	   	 URL url1 = new URL("https://it.wikipedia.org/w/api.php?action=query&pageids="+id2+"&prop=linkshere&lhlimit=200&format=json");
	   	 
	   	 //estrae dalla pagina titolo e testo, li salva su un array
	   	 //st.CreateTxt_Url(url, c1);
	   	 //String txt= ot.ReadText(c1); 
	   	 
	     String txt= st.CreateString_Url(url);
	   	
	   	 arrayT=jp.getText(txt,id2);
	   	 
	   	 String title= arrayT[0];
	   	 title=title.replace("\"","");
	   	 title=title.replace("?","");
	   	 
	   	 String txt1= hp.getText(arrayT[1]);
	   	 
	   	 
	   	 //String c2="";
	   	 if (!(title.contains("/") || (title.contains("Discussioni")) || (title.contains("Discussione")) || (title.contains("jpg")) || (title.contains("utente")))) {
	   		 
	   		 //pulizia StopWords
		   	 txt1=c.RStop(txt1);	
		   	 
	   	     //Stemming
		   	 String[] tokens = txt1.split(" ");
		   	 String text4="";
		   	 for (String string : tokens) {
		        stemmer.setCurrent(string);
		        stemmer.stem();
		        String stemmed = stemmer.getCurrent();
		        text4=text4+" "+stemmed;
		   	 }
		   	 	   	 	
		   	 //1String c3="C:\\Users\\emaan\\Desktop\\cartellapulita\\"+title+".txt";
		   	 //c2="C:\\Users\\emaan\\Desktop\\cartella_links\\"+title+".txt";
		   	 //1st.CreateTxt_String(text4,c3); 
		   	 aggiunto = dbw.InsertIntoTable("testo", "titolo", "corpo", title, text4);
		   	 if(aggiunto) {
		   		 aggiunti++;
		   		 System.out.println(aggiunti);
		   	 }
		   	 //Scarica un array di link da ogni pagina ove possibile
		   	 //----------------------------------------//
		   	 //st.CreateTxt_Url(url1, c2);
	   	 	 //String txt2= ot.ReadText(c2);
	   	 
		   	 String txt2 = st.CreateString_Url(url1);
	   	 
		   	 ids= jp.getIdLinks(txt2,id2);
	   	 
		   	 for(int k=0;k<500;k++) {
		   		 if(ids[k]!=null) {
		   			 if(!visited.contains(ids[k])) {
		   				 visited.add(ids[k]);
		   				 q.add(ids[k]);
	   				 
		   			 }
		   		 }
		   	 }
	     }
	  }
   }
}*/