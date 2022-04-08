package WikiExp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParts {
	
		public HtmlParts() {}
		
		public String getText(String txt) {  //estrae il corpo da un testo html
				Document doc = Jsoup.parse(txt);
				String txt1 = doc.body().text();
					return txt1;
		}		
}
