package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	
	private CorsoDAO dao;
	
	public Model() {
		dao = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		return dao.getCorsiByPeriodo(pd);
	}
	

	public Map<Corso,Integer> getIscrittiByPeriodo(Integer pd){
		return dao.getIscrittiByPeriodo(pd);
	}
	
	public List<Studente> getStudentibyCorso(Corso corso){
		return dao.getStudentibyCorso(corso);
	}
	
	public boolean esisteCorso (String codins) {
		return dao.esisteCorso(codins);
	}
	
	/*public Map<String, Integer> getDivisioneCorso(Corso c){
		List<Studente> studenti=this.dao.getStudentibyCorso(c);
		Map<String, Integer>statistiche=new HashMap<String,Integer>();
		
		for(Studente s: studenti) {
			//Verifico che lo studente è iscritto ad un corso
			if(s.getCDS() != null && !s.getCDS().equals("")) {
			if(statistiche.containsKey(s.getCDS()))
				statistiche.put(s.getCDS(), statistiche.get(s.getCDS()) +1);
			else
				statistiche.put(s.getCDS(), 1);
		}
		}
		return statistiche;
	}*/
	
	public Map<String, Integer> getDivisioneCDS(Corso c){
		return dao.getDivisioneCDS(c);
	}
}
