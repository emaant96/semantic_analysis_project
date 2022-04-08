package WikiExp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.italianStemmer;

public class Cleaner{
	
	public Cleaner() {};
	
	public String RStop(String txt) throws IOException {  //pulisce un testo dalle stopwords e dai simboli
		
		txt = txt.replaceAll("\\p{Punct}", " ");
		    
		BufferedReader br=new BufferedReader(new FileReader("files/italian.txt"));
		HashSet<String> list=new HashSet<String>();
		StringTokenizer st=new StringTokenizer(txt);
	    String s;
	    String txt1="";
	    while((s=br.readLine())!=null){
	    	list.add(s);
	    }
	    
	    while(st.hasMoreElements()) {
	    	String token=st.nextToken();
	    	if(!list.contains(token)){
	    		txt1=txt1+" "+token;
	    	}
	    }
	    	
	    
	    /*txt=txt.replace(","," ");
	    txt=txt.replace(";"," ");
	    txt=txt.replace(":"," ");
	    txt=txt.replace("."," ");
	    txt=txt.replace("("," ");
	    txt=txt.replace(")"," ");
	    txt=txt.replace("\""," ");
	    txt=txt.replace("?"," ");
	    txt=txt.replace("["," ");
	    txt=txt.replace("]"," ");
	    txt=txt.replace("="," ");
	    txt=txt.replace("-"," ");
	    txt=txt.replace("*"," ");
	    txt=txt.replace("'"," ");
	    txt=txt.replace("«"," ");
	    txt=txt.replace("»"," ");
	    txt=txt.replace("//"," ");*/

	    /*while((s=br.readLine())!=null){
	   	 	txt=txt.replace(" "+s+" ", " ");
	    	}*/
	    
	    /*txt=txt.replace("quest'"," ");
	    //txt=txt.replace("l "," ");
	    //txt=txt.replace("L'"," ");
	    //txt=txt.replace("L "," ");
	    //txt=txt.replace("il "," ");
	    txt=txt.replace("Storia","storia");
	    //txt=txt.replace("La "," ");
	    //txt=txt.replace(" i "," ");
	    //txt=txt.replace(" A "," ");
	    //txt=txt.replace("in "," ");*/
	    
	    br.close();
	    return txt1;
	}
	
	public String Stemma(String txt) {
		
		SnowballStemmer stemmer = (SnowballStemmer) new italianStemmer();
		
		String[] tokens = txt.split(" ");
	   	String text="";
	   	
	   	for (String string : tokens) {
	        stemmer.setCurrent(string);
	        stemmer.stem();
	        String stemmed = stemmer.getCurrent();
	        text=text+" "+stemmed;
	   	 }
	   	return text; 
	}
}