package WikiExp;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonParts {

		public JsonParts() {}
		
		public String[] getText(String file,String i)  { //interpreta un testo di tipo json			
			try{
			JsonParser parser = new JsonParser();
			

			String json = file;
			
			String[] arrayT= new String[2];
			
			JsonElement jsonTree = parser.parse(json);
			
			JsonObject jsonObject = jsonTree.getAsJsonObject();
			JsonObject query =(JsonObject) jsonObject.get("query");

			JsonObject pages = (JsonObject) query.get("pages");
			        			        	
			JsonObject num =(JsonObject) pages.get(""+i+"");
			
			arrayT[0] =  num.get("title").getAsString();
			arrayT[1] =  num.get("extract").getAsString();
	
			return arrayT;
			}catch(NullPointerException e){
				String[] r=new String[2];
				return r;}


			}
			
			
			public String[] getIdLinks(String file,String i) {  //link di una stringa json
					
				try{
					
				JsonParser parser = new JsonParser();

				String json = file;
				String[] ids = new String[500];

				JsonElement jsonTree = parser.parse(json);
				
				JsonObject jsonObject = jsonTree.getAsJsonObject();
				
				JsonObject query = (JsonObject) jsonObject.get("query");
				
				JsonObject pages = (JsonObject) query.get("pages");
				
				JsonObject num =(JsonObject) pages.get(""+i+"");
				
				JsonArray linksArray=(JsonArray) num.getAsJsonArray("linkshere");
				
				int j=0;
				
				for (JsonElement id : linksArray) {
					
					ids[j]=((JsonObject) id).get("pageid").getAsString();
					j++;
				}
				
				return ids;}
				
				catch(NullPointerException e) {
					
					String[] w=new String[500]; 
					return w;}
						
			}
			
			public String getId(String title)  { //interpreta un testo di tipo json			
				try{
				
				StreamText st=new StreamText();
				JsonParser parser = new JsonParser();
				title=title.replace(" ", "%20");
				URL url=new URL("https://it.wikipedia.org/w/api.php?action=parse&page="+title+"&prop=revid&format=json");
				String file= st.CreateString_Url(url);
				
				String json = file;
				
				JsonElement jsonTree = parser.parse(json);
				
				JsonObject jsonObject = jsonTree.getAsJsonObject();
				
				JsonObject parse =(JsonObject) jsonObject.get("parse");
				
				return parse.get("pageid").getAsString();
				
				}catch(NullPointerException e){
					System.out.println("errore1");
					e.printStackTrace();
					return "errore";
				} catch (MalformedURLException e) {
					System.out.println("errore2");
					e.printStackTrace();
					return "errore";
					
				} catch (IOException e) {
					System.out.println("errore3");
					e.printStackTrace();
					return "errore";
				}


				}
}
