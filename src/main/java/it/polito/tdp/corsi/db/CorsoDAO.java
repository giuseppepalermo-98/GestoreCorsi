package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {
	
	public boolean esisteCorso(String codins) {
		String sql="SELECT * FROM corso WHERE codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next()) {
				conn.close();
				return true;
			}else {
				conn.close();
				return false;
			}
		
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		
		String sql = "select * from corso where pd = ?";
		List<Corso> result = new ArrayList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
		
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		String sql = "select c.codins, c.nome, c.crediti, c.pd, COUNT(*) as tot " +//Le cose da selezionare e come chiamarle 
				"from corso as c, iscrizione i " + //Le tabelle che mi servono
				"where c.codins = i.codins and c.pd = ? " + //Per incrociare le due tabelle in cui mi servono le informazioni
				"group by c.codins, c.nome, c.crediti, c.pd "; //Per dare un ordinamento
		
		Map<Corso, Integer> result = new HashMap<Corso,Integer>(); //La mappa ha come chiave il corso stesso e come valore
		                                                           //il conto totale degli iscritti al corso
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, pd); //1 indica il primo ? e pd è il valore passato come parametro
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				Integer n = rs.getInt("tot"); // Qui mi ritorna il COUNT totale che gli ho chiesto di fare contando il join tra le tabelle
				result.put(c, n);
			}
			
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
	

	public List<Studente> getStudentibyCorso(Corso c){
		String sql="SELECT s.matricola, s.cognome, s.nome, s.cds FROM studente s, iscrizione i"+ 
				" WHERE s.matricola = i.matricola AND i.codins= ?";
		
		LinkedList<Studente> result=new LinkedList<Studente>();
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodins()); 
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s=new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
				result.add(s);
			}
			
			conn.close();
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}

		return result;
	}
	
	public Map<String, Integer> getDivisioneCDS(Corso c){
		String sql="SELECT s.cds, COUNT(*) as tot "+ 
				"FROM  studente s, iscrizione i "+ 
				"WHERE s.matricola = i.matricola AND i.codins=? "+
				"GROUP BY s.cds";
		
		Map<String, Integer>result=new HashMap<String, Integer>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, c.getCodins()); 
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				result.put(rs.getString("CDS"), rs.getInt("tot"));
			}
			
			conn.close();
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}

		return result;
		
	}
}
