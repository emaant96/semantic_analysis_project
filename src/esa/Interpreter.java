package esa;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.io.IOException;
import java.net.URL;

import WikiExp.*;

public class Interpreter {
	
	String[] l;
	
	public Interpreter(String[] concetti) {
		ListConceptsCreator lcc=new ListConceptsCreator();
		this.l=lcc.ConcettiAffini2(concetti);	
	}
	
	public void PoiInterpreter(String titolo,String[] concetti) throws IOException  {
			
			MatrixWork mw=new MatrixWork();
			DataBaseWork dbw=new DataBaseWork();
			JsonParts jp=new JsonParts();
			StreamText st=new StreamText();
			Cleaner c=new Cleaner();
			HtmlParts hp=new HtmlParts();
			Download d=new Download();
			
			
			String id=jp.getId(titolo);
			
			URL url = new URL("https://it.wikipedia.org/w/api.php?action=query&pageids="+id+"&prop=extracts&exlimit=1&format=json");
			String txt=st.CreateString_Url(url);
			
			String[] txt1= jp.getText(txt, id);
			String t=txt1[1];
			t=hp.getText(t);
			//String t= " Colosseo, originariamente conosciuto come Amphitheatrum Flavium semplicemente come Amphitheatrum, è il più grande anfiteatro del mondo, situato nel centro della città di Roma. è il più importante anfiteatro romano, nonché il più imponente monumento dell'antica Roma che sia giunto fino a noi, conosciuto in tutto il mondo come simbolo della città di Roma e uno dei simboli d'Italia.";
			//String t= " Terra è il luogo primigenio degli esseri umani, che ospita la vita come da noi concepita e conosciuta. Sulla sua superficie si trova acqua in tutti e tre gli stati (solido, liquido e gassoso) e un'atmosfera composta in prevalenza da azoto e ossigeno che, insieme al campo magnetico che avvolge il pianeta, protegge la Terra dai raggi cosmici e dalle radiazioni solari.";
			t=c.RStop(t);
			t=c.Stemma(t);
			String[] termini=t.split(" ");
			/*String[] termini = new String[300];
			for(int i=0;i<300 && i<terminitot.length;i++) 
				termini[i]=terminitot[i];*/
			
			
			
			//String[] termini= { "stor", "mort", "musulm","antic","discors","Network","radiolog"};
			//String [] concetti= dbw.SelectAllTesto("titolo");
			//String[] concetti= {"Religione","Arte","Storia","Biologia","Natura","Scienza","Archeologia","Architettura"};
			
			for(int i=0;i<concetti.length;i++) {
				int presente=dbw.Control("testo", "titolo", concetti[i]);
				if(presente==0) {
					System.out.println("Non conosco la parola "+concetti[i]+", prima di continuare vado ad impararla");
					String id3 =jp.getId(concetti[i]);
					dbw.InsertIntoPagDaAgg(id3);
					d.DownPagesFrom(id3,1000,500);
					
				}
			}
			
			//mw.MatrixConceptsCreator(concetti);
			
			
			
			
			
			//TreeMap<Double,String> ltc1=lcc.ListWork(termini, concetti, m);
							//Concetti più collegati ai concetti principali (creazione matrice di concetti su db)
			
			long inizio= System.currentTimeMillis();
			
			double[][] m1 = mw.MatrixCreatorLvl1(termini, this.l); 			//Matrice pesi termini/ tutti i concetti
			
			double[][] m2 =mw.MatrixPoi(m1, concetti, this.l, termini);  //solo i concetti principali
			
			/*TreeMap<Double, String> ltc1= lcc.ListWork(termini, l, m1); //Prende tutti i concetti e ne stila una classifica generale di 100 concetti
			TreeMap<Double, String> ltc1=null;
			Entry<Double, String> a;
			
			while(!ltc1.isEmpty()) {
				a=ltc1.pollLastEntry();
				if(h.contains(a.getValue()))
					System.out.println(a.getValue()+"---->"+a.getKey());
			}*/
			double[] somme_concetti=new double[concetti.length];
			for(int j=0;j<concetti.length;j++) {
				double somma=0;
				for(int i=0;i<termini.length;i++) {
					somma=somma+m2[i][j];
				}
				somme_concetti[j]=somma;
			}
			
			TreeMap<Double,String> pesi= new TreeMap<Double,String>();
			for(int i=0;i<somme_concetti.length;i++) 
				pesi.put(somme_concetti[i], concetti[i]);
			
			Arrays.sort(somme_concetti);
			/*double max=somme_concetti[somme_concetti.length-1];
			
			Entry<Double,String> p;
			for(int i=0;i<somme_concetti.length;i++) {
				p=pesi.pollLastEntry();
				double peso=(100*(p.getKey()))/(max);
				peso = Math.floor(peso*100);
				System.out.println(p.getValue()+"--->"+peso/100+"%");
			}*/
			
			Entry<Double,String> p;
			for(int i=0;i<somme_concetti.length;i++) {
				p=pesi.pollLastEntry();
				double peso = Math.floor((p.getKey()*100));
				System.out.println(p.getValue()+"--->"+(peso/100)/Math.log(termini.length));
				dbw.InsertIntoTable3_1("topic_poi","topic", "poi", "peso", p.getValue(), titolo, (peso/100)/Math.log(termini.length));
			}
			

			
			/*for(int i=0;i<somme_concetti.length;i++) {
				System.out.println(concetti[i]+"--->"+somme_concetti[i]);
			}*/
			
			long fine= System.currentTimeMillis();	
   		 	if((fine-inizio)/60000>0) {
   		 		System.out.println("finito in "+((fine-inizio)/60000)+" minuti");
   		 		System.out.println("velocita di interpretazione pari a: "+termini.length/((fine-inizio)/60000)+" termini al minuto");
   		 	}else {
   		 		if((fine-inizio)/1000>0) {
   		 			System.out.println("finito in "+((fine-inizio)/1000)+" secondi");
   		 		}else {
   				System.out.println("finito in meno di un secondo");
   		 		}
   		 	}
   		 	
   		 	
   		 	
			
   		 	
			/*System.out.print("     ");
			
			for(int k=0;k<concetti.length;k++)
				
				System.out.print(" "+concetti[k]+" ");
			
			System.out.print("\n");
			
			for(int i=0;i<termini.length;i++) {
				
				System.out.print(termini[i]+" ");
				System.out.print("|");
				
				for(int j=0;j<concetti.length;j++) {
					System.out.print(" "+m2[i][j]+"  ");
				}
				System.out.println("|");
			}*/
		}
	}
