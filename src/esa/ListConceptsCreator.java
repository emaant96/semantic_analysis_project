package esa;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import WikiExp.Cleaner;
import WikiExp.DataBaseWork;

public class ListConceptsCreator {
	
	public ListConceptsCreator() {};
	
	public TreeMap<Double,String> SortPesiConcetti(String[] concetti,double[] pesi){ //Per stilare una classifica di concetti max per ogni termine
		
		TreeMap<Double, String> lista_pesi=new TreeMap<>();
		for(int i=0;i<concetti.length;i++) {
			lista_pesi.put(pesi[i], concetti[i]);
		}
		return lista_pesi;
		
	}
	
	public TreeMap<Double,String> ListWork(String[] termini, String[] concetti,double[][] m) {
		
		Entry<Double,String> a;
		TreeMap<String,Double> ltc= new TreeMap<>(); //Lista top concetti
		TreeMap<Double,String> ltc1=new TreeMap<>();
		for(int i=0;i<termini.length;i++) {
			//System.out.println("classifica termine:----------------------------\""+termini[i]+"\"-----------------");
			TreeMap<Double,String> lista_pesi= SortPesiConcetti(concetti, m[i]); //Array pesi termine-concetti
			while(!lista_pesi.isEmpty()) {
				a=lista_pesi.pollLastEntry();
				System.out.println(a.getValue()+"---->"+a.getKey());
				if(ltc.containsValue(a.getKey())) {
					double temp=ltc.get(a.getValue());
					ltc.replace(a.getValue(),a.getKey() , temp+a.getKey());  //Classifica generale di 100 concetti tra tutti i concetti di tutti i termini
				}else {
					ltc.put(a.getValue(), a.getKey());
				}
				
				//l++;
			}
		}
		
		Entry<String,Double> g;
		while(!ltc.isEmpty()) {   //Per ordinarla secondo i pesi
			g=ltc.pollLastEntry();
			ltc1.put(g.getValue(),g.getKey()); 
		}

	return ltc1;
	}
	
	public String[] ConcettiAffini1(String[] concetti){ //Estrae i concetti più collegati a dei concetti principali
		
		DataBaseWork dbw=new DataBaseWork();
		Cleaner c=new Cleaner();
		MatrixWork mw=new MatrixWork();
		
		ArrayList<String> al=dbw.SelectTesto1P();
		Object[] lc=new Object[al.size()]; //Lista concetti
		String[] termini=new String[lc.length];  //Parole stemmate
		
		lc= al.toArray(); 
		
		for(int i=0;i<lc.length;i++) {
			termini[i]=c.Stemma((String)lc[i]);
			termini[i]=termini[i].replace(" ", "").toLowerCase();
		}
		
		TreeMap<String,String> tm=new TreeMap<String,String>();
		for(int i=0;i<lc.length;i++) {
			tm.put(termini[i],(String) lc[i]);
		}
		
		
		double[][] m=mw.MatrixCreatorLvl1(termini, concetti);
		double[][] n=new double[concetti.length][termini.length];
		for(int i=0;i<termini.length;i++) {
			for(int j=0;j<concetti.length;j++) {
				n[j][i]=m[i][j];
			}
		}
		
		Entry<Double,String> a;
		TreeMap<String,Double> ltc= new TreeMap<>(); //Lista top concetti
		for(int i=0;i<concetti.length;i++) {
			int l=0;
			TreeMap<Double,String> lista_pesi= SortPesiConcetti(termini, n[i]); //Array pesi termine-concetti
			while(l<100 && !lista_pesi.isEmpty()) {
				a=lista_pesi.pollLastEntry();
				if(!concetti[i].equals(tm.get(a.getValue()))) {
					ltc.put(tm.get(a.getValue()), a.getKey());
					dbw.InsertIntoTable3_1("peso_collegamento_concetti", "concetto_principale", "concetto_secondario", "peso", concetti[i], tm.get(a.getValue()), a.getKey());
					l++;
				}
			}
		}
		
		//Ricavo la lista di concetti 
		Entry<String,Double> g;
		String[] q=new String[ltc.size()+concetti.length];
		for(int i=0;i<concetti.length;i++)
			q[i]=concetti[i];
		
		int i=concetti.length;
		while(!ltc.isEmpty()) {
			g=ltc.pollLastEntry();
			q[i]=g.getKey();
			i++;
		}
	return q;
	}
	
	public String[] ConcettiAffini2(String[] topic){ //Estrae i concetti più collegati a dei concetti principali
			
			DataBaseWork dbw=new DataBaseWork();
			Cleaner c=new Cleaner();
			MatrixWork mw=new MatrixWork();
			
			String[] concetti=dbw.SelectAllTesto("titolo");
			String[] termini1= topic; //Parole stemmate
			String[] termini2=new String[termini1.length];
			
			for(int i=0;i<termini2.length;i++) {
				termini2[i]=c.Stemma(termini1[i]);
				termini2[i]=termini2[i].replace(" ", "").toLowerCase();
			}
			
			TreeMap<String,String> tm=new TreeMap<String,String>();
			for(int i=0;i<termini1.length;i++) {
				tm.put(termini2[i], termini1[i]);
			}
			
			
			double[][] m=mw.MatrixCreatorLvl1(termini2, concetti);
			/*double[][] n=new double[concetti.length][termini.length];
			for(int i=0;i<termini.length;i++) {
				for(int j=0;j<concetti.length;j++) {
					n[j][i]=m[i][j];
				}
			}*/
			
			Entry<Double,String> a;
			TreeMap<String,Double> ltc= new TreeMap<>(); //Lista top concetti
			for(int i=0;i<termini2.length;i++) {
				int l=0;
				TreeMap<Double,String> lista_pesi= SortPesiConcetti(concetti, m[i]); //Array pesi termine-concetti
				while(l<100 && !lista_pesi.isEmpty()) {
					a=lista_pesi.pollLastEntry();
					System.out.println(tm.get(termini2[i]));
					if(!a.getValue().equals(tm.get(termini2[i]))) {
						ltc.put(a.getValue(), a.getKey());
						dbw.InsertIntoTable3_1("peso_collegamento_concetti", "concetto_principale", "concetto_secondario", "peso", tm.get(termini2[i]), a.getValue(), a.getKey());
						l++;
					}
				}
			}
			
			//Ricavo la lista di concetti 
			Entry<String,Double> g;
			String[] q=new String[ltc.size()+termini1.length];
			for(int i=0;i<termini1.length;i++)
				q[i]=termini1[i];
			
			int i=termini1.length;
			while(!ltc.isEmpty()) {
				g=ltc.pollLastEntry();
				q[i]=g.getKey();
				i++;
			}
		return q;
		}
}			
		
		
	

