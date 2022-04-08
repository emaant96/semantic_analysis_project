package WikiExp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OutputText {

	public OutputText() {}
	
	public String ReadText(String id) throws IOException {  //estrae un testo da un file locale
    BufferedReader br=new BufferedReader(new FileReader(id));
    String txt="";
    String s;
    while((s=br.readLine())!=null){
   	 txt=txt+s;
    	}	
    br.close();
    return txt;
    }
}
