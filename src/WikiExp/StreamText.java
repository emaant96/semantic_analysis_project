package WikiExp;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class StreamText {


	public StreamText() {}

	public void CreateTxt_Url(URL url,String id) throws IOException{ //crea file di testo da un url

    URLConnection connection=url.openConnection();
    InputStream in=connection.getInputStream();
    BufferedReader br= new BufferedReader(new InputStreamReader(in));
    
    String txt="";
    String s;
    while((s=br.readLine())!=null){
        txt+=s;
    }
    br.close();
    in.close();
    //_______________________________________________________________________
    PrintWriter pw=new PrintWriter(new FileWriter(id));
    
    pw.println(txt);
    pw.close();
    //_______________________________________________________________________
    }
	
	public void CreateTxt_String(String txt,String id) throws IOException{ //crea file di testo da una stringa

		if(txt.length() > 10000   ) {
			//____________________________________________________
			PrintWriter pw=new PrintWriter(new FileWriter(id));
	    
			pw.println(txt);
			pw.close();
			//____________________________________________________
	    }
	}
	
	public String CreateString_Url(URL url) throws IOException{ //crea una stringa da un url

	    URLConnection connection=url.openConnection();
	    InputStream in=connection.getInputStream();
	    BufferedReader br= new BufferedReader(new InputStreamReader(in));
	    
	    String txt="";
	    String s;
	    while((s=br.readLine())!=null){
	        txt+=s;
	    }

	    br.close();
	    in.close();
	    return txt;
	}
}
