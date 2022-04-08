package esa;

import WikiExp.*;

public class MatrixWork {
	
		private DataBaseWork dbw;

		public MatrixWork() {
			dbw=new DataBaseWork();
		}
		
		public double[][] MatrixCreatorLvl1(String[] termini,String[] concetti){
			
			int numTermini=termini.length;
			int numConcetti=concetti.length;
			
			double[][] pesi = new double[numTermini][numConcetti];
			double numTotCon=dbw.NumRighe("testo", "id");
			int[] nP=new int[numConcetti];
			
			for(int k=0;k<numConcetti;k++) {
				System.out.println(k);
				nP[k]=dbw.SelectTesto("num_parole", concetti[k]);
			}
				
			for(int i=0;i<numTermini;i++){                                //DUE CICLI PIù DISPENDIOSI 
				double cf=dbw.SelectParola("numero_testi",termini[i]);   //numero testi che contengono la parola i
				if(cf!=0.0) {
					double dfi=Math.log10(numTotCon/cf);
					System.out.println(termini[i]);
					System.out.println("----------------------------------"+(numTermini-i)+"   matrixCreator");
					for(int j=0;j<numConcetti;j++) {
						if(numTermini<10)
							System.out.println(numConcetti-j+"   matrixCreator");
						int tf=dbw.SelectComposiz(termini[i],concetti[j]);   //numero di volte in cui compare la parola i nel testo j
						//int nL=dbw.SelectTesto("num_link",concetti[j]);
						double tfi=0;
						if(tf!=0.0) {
							tfi = 1+Math.log(tf);
						}
				
						double ris=tfi*dfi;
						
						pesi[i][j]=ris/Math.log(nP[j]);
					}
				}
			}
			boolean ok=false;
			/*double[][] pesi1=new double[numTermini][numConcetti];
			if(numTermini>1) {   //Primo livello di pulizia (con molti termini)
				ok=true;
				for(int i=0;i<numTermini;i++){
					for(int j=0;j<numConcetti;j++) {
						if(pesi[i][j]!=0.0) {
							double somma=0;
							for(int k=0;k<numTermini;k++)
								somma=somma + (pesi[k][j]*pesi[k][j]);
							
							double ris = pesi[i][j]/Math.sqrt(somma);	
							pesi1[i][j]=ris;
						}
					}
				}
			}*/
			
			double[][] pesi2=new double[numTermini][numConcetti];   //Secondo livello di pulizia (con un pò di concetti)
			
			if(numTermini>9) {
				ok=true;
				String[] concettid=new String[numConcetti];
				for(int p=0;p<concettid.length;p++) {
					concettid[p]= dbw.GetId("id", concetti[p]);
				}                             
				boolean app=false;
				for(int j=0;j<numConcetti;j++) {
					System.out.println(numConcetti-j);
					for(int k=0;k<numConcetti;k++) {
						
						int appartiene=dbw.ControlEntry("testo", "link", concettid[j], concettid[k]);
						if(app)
							System.out.println("preso! "+concettid[j]+"--->"+concettid[k]);
						for(int i=0;i<numTermini;i++) {
							if(appartiene==1) {
								pesi2[i][j]=pesi[i][j]+0.5*pesi[i][k];
								app=true;	
							}else {
								pesi2[i][j]=pesi[i][j];
								app=false;
							}					
						}
					}
				}
			}
			if(!ok)
				return pesi;
			else
			//return pesi1;
				return pesi2;
		}
		
		public double[][] MatrixPoi(double[][] m,String[] concetti_princ,String[] concetti_tot, String[] termini){
			
			int y=0;
			int cp=concetti_princ.length;
			int ct=concetti_tot.length;
			double[][] n = new double[termini.length][concetti_princ.length];
			for(int i=0;i<termini.length;i++) {
				System.out.println(termini.length-i+"       matrixPoi");
				for(int j=0;j<concetti_princ.length;j++) {
					double somma=0;
					for(int k=cp;k<ct;k++) {
						somma=somma+dbw.SelectPeso(concetti_princ[j], concetti_tot[k])*m[i][k];
						if(somma!=0)
							y++;
					}	
					n[i][j]=m[i][j]*(somma);
				}
			}
			System.out.println(y);
			/*for(int j=0;j<concetti_princ.length;j++)
				System.out.println(concetti_princ[j]);
			
			System.out.println("_____________________________________________________");
			
			for(int i=0;i<concetti_tot.length;i++)
				System.out.println(concetti_tot[i]);*/
			
			System.out.println("_____________________________________________________");	
			
			return n;
		}
}
