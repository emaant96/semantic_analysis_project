package WikiExp;
import java.util.StringTokenizer;
import java.util.TreeMap;

class FrequenzaParole{
	
	private TreeMap<String, Integer> dictionary = new TreeMap<String, Integer>(); //Inizializzo il TreeMap

	public FrequenzaParole(){}
	
	public TreeMap<String, Integer> frequenza(String stringa) //Il metodo per il calcolo della frequenza
	{
		StringTokenizer st = new StringTokenizer(stringa); //Tokenizzo la stringa

		while(st.hasMoreTokens()) //Continua finchè ci sono token. Se non specifico il token, prende automaticamente lo spazio.
		{
			String token = st.nextToken(); //Mi salvo il token
			if(dictionary.containsKey(token)) //Se lo ho già aggiunto
			{
				dictionary.put(token, dictionary.get(token)+1); //Aggiungo +1
			}
			else
			{
				dictionary.put(token , 1); //Altrimenti non l'ho mai incontrato quindi lo salvo e inizializzo ad 1.
			}
		}

		return dictionary; //Mi stampo il dizionario usando il metodo toString
	}
}
