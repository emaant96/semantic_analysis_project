package WikiExp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import org.postgresql.util.PSQLException;

public class DataBaseWork {
	
	private Connection connection;
	private Statement statement;

	public DataBaseWork() {
		try {
			this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "pausa");
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			
			System.out.println("Connection failed");
			e.printStackTrace();
		}
	}
	
	public boolean InsertIntoPagVisit(String id){
	        try {
	        	statement.execute(
	            		"INSERT INTO public.pagine_visitate(visitate) VALUES('"+id+"');");
	            return true;
	 
	        } catch (PSQLException e ) {
	            System.out.println("Impossibile inserirlo su pagine visitate");
	            return false;
	        } catch (SQLException e) {
	        	System.out.println("Connection failed");
	        	return false;
			}
	}
	
	public boolean InsertIntoPagDaVisit(String id){
        try {statement.execute("INSERT INTO public.pagine_da_visitare(da_visitare) VALUES('"+id+"')");		
            return true;
 
        } catch (PSQLException e ) {
        	System.out.println("Impossibile inserirlo su pagine da visitare");
        	//e.printStackTrace();
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
    
	public boolean InsertIntoTable4(String nameTable, String colonna1,String colonna2, String colonna3,String colonna4, String val1, String val2, String val3,int val4){
        try {
        	statement.execute(
            		"INSERT INTO public."+nameTable+"("+colonna1+","+colonna2+","+colonna3+","+colonna4+") VALUES('"+val1+"','"+val2+"','"+val3+"','"+val4+"');");
            return true;
 
        } catch (PSQLException e ) {
            System.out.println("Problemi di inserimento4");
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
	
	public boolean InsertIntoTable3(String nameTable, String colonna1,String colonna2, String colonna3, String val1, String val2, int val3){
        try {
        	statement.execute(
            		"INSERT INTO public."+nameTable+"("+colonna1+","+colonna2+","+colonna3+") VALUES('"+val1+"','"+val2+"','"+val3+"');");
            return true;
 
        } catch (PSQLException e ) {
            System.out.println("Problemi di inserimento3");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
	
	public boolean InsertIntoTable3_1(String nameTable, String colonna1,String colonna2, String colonna3, String val1, String val2, double val3){
        try {
        	statement.execute(
            		"INSERT INTO public."+nameTable+"("+colonna1+","+colonna2+","+colonna3+") VALUES('"+val1+"','"+val2+"','"+val3+"');");
            return true;
 
        } catch (PSQLException e ) {
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
	
	public boolean InsertIntoTable1(String nameTable, String colonna1, String val1){
        try {
        	statement.execute(
            		"INSERT INTO public."+nameTable+"("+colonna1+") "
            				+ "SELECT '"+val1+"' "
            				+"WHERE NOT EXISTS( SELECT "+colonna1+" FROM public."+nameTable+" WHERE "+colonna1+" = '"+val1+"');");
            return true;
 
        } catch (PSQLException e ) {
            System.out.println("Problemi di inserimento1");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
	
	public boolean InsertIntoTable2(String nameTable, String colonna1,String colonna2, String val1, String val2){
        try {
        	statement.execute(
            		"INSERT INTO public."+nameTable+"("+colonna1+","+colonna2+") VALUES('"+val1+"','"+val2+"');");
            return true;
 
        } catch (PSQLException e ) {
            System.out.println("Problemi di inserimento2");
            //e.printStackTrace();
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
    
   public boolean InsertAndIncrease(String nameTable, String colonna1,String colonna2, String val1){
        try {
 
            PreparedStatement preparedStmt = connection.prepareStatement(
            		"INSERT INTO public."+nameTable+"("+colonna1+","+colonna2+")  "
            				+ "SELECT '"+val1+"', '1' "
            				+"WHERE NOT EXISTS( SELECT "+colonna1+" FROM public."+nameTable+" WHERE "+colonna1+" = '"+val1+"');"
            		+"UPDATE public."+nameTable+" SET "+colonna2+"="+colonna2+"+1 WHERE "+colonna1+"='"+val1+"';");
            preparedStmt.execute();	
            return true;
 
        } catch (PSQLException e ) {
            System.out.println("Already Exist.");
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
    
    /*public void InsertAndControlPagVisit(String nameTable, String id){
    	
    	 try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "pausa")) {
	 
	            PreparedStatement preparedStmt = connection.prepareStatement(
	            		   "INSERT INTO public.pagine_visitate(visitate) "
	            		   + "SELECT '"+id+"' "
	            		   		+ "WHERE NOT EXISTS( SELECT * FROM public.pagine_visitate WHERE visitate = '"+id+"')");
	            preparedStmt.execute();
	            
	            System.out.println("done");
	 
	        } catch (PSQLException a ) {
	            System.out.println("Already Exist.");
	            a.printStackTrace();
	            
	        } catch (SQLException e) {
	        	System.out.println("Connection failed");
	        	
	        }
    }*/
    
	public ArrayDeque<String> AddToQueue(ArrayDeque<String> q){
		
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pagine_da_visitare");
            while (resultSet.next()) {
            	q.add(resultSet.getString("da_visitare"));
            }
        PreparedStatement preparedStmt = connection.prepareStatement(
         		   "DELETE FROM public.pagine_da_visitare");
            preparedStmt.execute();
        return q;
            
        } catch (PSQLException a ) {
        return q;
        
        } catch (SQLException e) {
    	System.out.println("Connection failed");    
        return q;
        }
	}

	public String PollFromTable(String tabella,String colonna){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public."+tabella+" LIMIT 1");
            resultSet.next();
            String id = resultSet.getString(colonna);
            statement.execute(
          		   "DELETE FROM public."+tabella+" WHERE "+colonna+"='"+id+"'");
            return id;
 
        } catch (PSQLException e ) {
            System.out.println("Impossibile estrarre il primo");
            e.printStackTrace();
            return "";
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return "";
		}
   }

	/*public void DeleteFromDaVisit(String id2) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "pausa")) {
			 PreparedStatement preparedStmt = connection.prepareStatement(
          		   "DELETE FROM public.pagine_da_visitare WHERE da_visitare='"+id2+"'");
             boolean cancellato=preparedStmt.execute();
             if(cancellato) {
            	 System.out.println("Deleted "+id2);}
             
        } catch (PSQLException e ) {
            System.out.println("Not Deleted.");
            e.printStackTrace();
        } catch (SQLException e) {
        	System.out.println("Connection failed");
		}
		
		
	}*/
	
	
	public int NumRighe(String tabella, String colonna) {
		try {
			 ResultSet resultSet = statement.executeQuery("SELECT COUNT("+colonna+") FROM public."+tabella);
			 resultSet.next();
				 
			 return resultSet.getInt(1); 
			 
	        } catch (PSQLException e ) {
	            System.out.println("errore sql su conteggio elementi .");
	            return 0;
	            
	        } catch (SQLException e) {
	        	System.out.println("Connection failed");
	        	return 0;
			}
	}

	public HashSet<String> AddToHashDaVisit(HashSet<String> visited){
		
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pagine_da_visitare");
            while (resultSet.next()) {
            	visited.add(resultSet.getString("da_visitare"));
            }
        return visited;
            
        } catch (PSQLException a ) {
        return visited;
        
        } catch (SQLException e) {
    	System.out.println("Connection failed");    
        return visited;
        }
	}
	
public HashSet<String> AddToHashVisit(HashSet<String> visited){
		
        try {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM testo");
            while (resultSet.next()) {
            	visited.add(resultSet.getString("id"));
            }
        return visited;
            
        } catch (PSQLException a ) {
        return visited;
        
        } catch (SQLException e) {
    	System.out.println("Connection failed");    
        return visited;
        }
	}
	
	public boolean UpdateLink(String id, int numLink){
        try {
        	statement.execute(
            		"UPDATE testo SET num_link='"+numLink+"' WHERE id='"+id+"'");
            return true;
 
        } catch (PSQLException e ) {
            System.out.println("Impossibile inserire numero link");
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
	}
	
	/*public void InsertAndControlPagVisit(String nameTable, String id){
    	
   	 try { 
   		 	statement.execute(
	            		   "INSERT INTO public.pagine_visitate(visitate) "
	            		   + "SELECT '"+id+"' "
	            		   		+ "WHERE NOT EXISTS( SELECT * FROM public.pagine_visitate WHERE visitate = '"+id+"')");
	            
	            
	            System.out.println("done");
	 
	        } catch (PSQLException a ) {
	            System.out.println("Already Exist.");
	            a.printStackTrace();
	            
	        } catch (SQLException e) {
	        	System.out.println("Connection failed");
	        	
	        }
   }*/
	
	public int SelectComposiz(String termine, String concetto){
        try {
        	
            ResultSet resultSet = statement.executeQuery("SELECT frequenza "
            											+ "FROM public.composizione "
            											+ "WHERE parola='"+termine+"' AND testo IN (SELECT id "
								            													 + "FROM public.testo "
								            													 + "WHERE titolo='"+concetto+"')");
            resultSet.next();
            int id = resultSet.getInt("frequenza");
            //System.out.println("ok");
            return id;
 
        } catch (PSQLException e ) {
            //System.out.println("impossibile trovare la corrispondenza");
            //e.printStackTrace();
            return 0;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return 0;
		}
   }
	
	public int SelectParola(String colonna, String termine){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * "
            											+ "FROM public.parola "
            											+ "WHERE nome='"+termine+"'");
            resultSet.next();
            int id = resultSet.getInt(colonna);
            //System.out.println("ok");
            return id;
 
        } catch (PSQLException e ) {
           System.out.println("non conosco questa parola");
            return 0;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return 0;
		}
   }
	
	public int SelectTesto(String colonna,String termine){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * "
            											+ "FROM public.testo "
            											+ "WHERE titolo='"+termine+"'");
            resultSet.next();
            int numLink = resultSet.getInt(colonna);
            //System.out.println("ok");
            return numLink;
 
        } catch (PSQLException e ) {
            System.out.println("boh");
            e.printStackTrace();
            return 0;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return 0;
		}
   }
	
	public double SelectPeso(String concetto1, String concetto2){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT peso "
            											+ "FROM public.peso_collegamento_concetti "
            											+ "WHERE concetto_principale='"+concetto1+"' AND concetto_secondario ='"+concetto2+"'");
            resultSet.next();
            double peso = resultSet.getDouble("peso");
            //System.out.println("ok");
            return peso;
 
        } catch (PSQLException e ) {
        	//e.printStackTrace();
            //System.out.println("non conosco questa parola");
            return 0;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return 0;
		}
   }
	
	public String[] SelectAllTesto(String colonna){
        try {
        	Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * "
            											+ "FROM public.testo ");
            int i=0;
            int j=NumRighe("testo", colonna);
            String[] titoli = new String[j];
            
            while(resultSet.next()) {
            	
            	titoli[i] = resultSet.getString(colonna);
            	i++;
            	
            }
            
            System.out.println("ok"+" "+j);
            return titoli;
 
        } catch (PSQLException e ) {
        	String[] a = null;
            System.out.println("boh");
            e.printStackTrace();
            return a;
        } catch (SQLException e) {
        	String[] b = null;
        	System.out.println("Connection failed");
        	return b;
		}
   }
	
	public int ControlEntry(String colonna1,String colonna2, String concetto1, String concetto2){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.collegamento WHERE "+colonna1+"='"+concetto1+"' AND "+colonna2+"='"+concetto2+"'");
            resultSet.next();
            resultSet.getString(colonna1);
            //System.out.println("ok");
            return 1;
 
        } catch (PSQLException e ) {
            //System.out.println("null");
            //e.printStackTrace();
            return 0;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return 0;
		}
   }
	
	public String GetId(String colonna,String termine){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * "
            											+ "FROM public.testo "
            											+ "WHERE titolo='"+termine+"'");
            resultSet.next();
            String id = resultSet.getString(colonna);
            //System.out.println("ok");
            return id;
 
        } catch (PSQLException e ) {
            System.out.println("boh");
            e.printStackTrace();
            return "";
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return "";
		}
   }
	
	public boolean InsertIntoPagDaAgg(String id){
        try {statement.execute("INSERT INTO public.pagine_da_aggiungere(da_aggiungere) VALUES('"+id+"')");		
            return true;
 
        } catch (PSQLException e ) {
        	//e.printStackTrace();
            return false;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return false;
		}
   }
	
	public boolean DeleteTable(String tabella){
		
        try {
        	statement.execute("DELETE FROM public."+tabella);
        return true;
            
        } catch (PSQLException a ) {
        	System.out.println("impossibile cancellare colonna");
        return false;
        
        } catch (SQLException e) {
    	System.out.println("Connection failed");    
        return false;
        }
	}
	
	public int Control(String tabella, String colonna1, String concetto1){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(id) FROM public."+tabella+" WHERE "+colonna1+"='"+concetto1+"'");
            resultSet.next();
            int i=resultSet.getInt("count");
            return i;
 
        } catch (PSQLException e ) {
            //System.out.println("null");
            //e.printStackTrace();
            return 0;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return 0;
		}
   }
	
	public ArrayList<String> SelectTesto1P(){
        try {
        	
        	ArrayList<String> ltc= new ArrayList<String>();
            ResultSet resultSet = statement.executeQuery("SELECT titolo FROM public.testo WHERE titolo NOT LIKE '% %'");
            while(resultSet.next()) {
            	String titolo=resultSet.getString("titolo");
            	ltc.add(titolo);
            }
            	
            return ltc;
 
        } catch (PSQLException e ) {
            //System.out.println("null");
            //e.printStackTrace();
            return null;
        } catch (SQLException e) {
        	System.out.println("Connection failed");
        	return null;
		}
   }
	
	
} 


    

